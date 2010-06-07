package com.github.tux2323.demo.chat.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import com.github.tux2323.demo.chat.Activator;
import com.github.tux2323.demo.chat.client.WebChatServer;
import com.github.tux2323.demo.chatserver.ChatClient;
import com.github.tux2323.demo.chatserver.ChatServer;
import com.github.tux2323.demo.chatserver.Session;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class WebChatServerImpl extends RemoteServiceServlet implements WebChatServer {

	private static int lastSessionId = 9999;
	
	private Map<String, WebChatSession> recievers = new HashMap<String, WebChatSession>();
	
	@Override
	public String login(String username, String password) {
		BundleContext context = Activator.getBundleContext();
		ServiceTracker chatServerTracker = new ServiceTracker(context, ChatServer.class.getName(), null);
		chatServerTracker.open();
		
		ChatServer chatServer = (ChatServer) chatServerTracker.getService();
		Session session = chatServer.login(username, password);
		
		chatServerTracker.close();
		
		String sessionId = String.valueOf(lastSessionId++);
		
		WebChatSession chatClient = new WebChatSession();
		chatClient.setSession(session);
		recievers.put(sessionId, chatClient);
		
		BundleContext bundleContext = Activator.getBundleContext();
		bundleContext.registerService(ChatClient.class.getName(), chatClient, null);
		
		return sessionId;
	}

	@Override
	public void sendMessage(String sessionId, String msg) {
		BundleContext context = Activator.getBundleContext();
		ServiceTracker chatServerTracker = new ServiceTracker(context, ChatServer.class.getName(), null);
		chatServerTracker.open();
		
		WebChatSession chatClient = recievers.get(sessionId);
		
		ChatServer chatServer = (ChatServer) chatServerTracker.getService();
		chatServer.sendMessage(chatClient.getSession(), msg);
		
		chatServerTracker.close();
	}

	@Override
	public List<String> pollMessages(String sessionId) {
		WebChatSession webChatSession = recievers.get(sessionId);
		if(webChatSession != null)
			return webChatSession.pollMessages();
		return new ArrayList<String>();
	}

}

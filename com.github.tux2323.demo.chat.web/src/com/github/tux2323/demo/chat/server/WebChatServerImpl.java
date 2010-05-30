package com.github.tux2323.demo.chat.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import com.github.tux2323.demo.chat.Activator;
import com.github.tux2323.demo.chat.client.WebChatServer;
import com.github.tux2323.demo.chat.client.WebSession;
import com.github.tux2323.demo.chatserver.ChatClient;
import com.github.tux2323.demo.chatserver.ChatServer;
import com.github.tux2323.demo.chatserver.Session;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class WebChatServerImpl extends RemoteServiceServlet implements WebChatServer {

	private static int lastSessionId = 9999;
	
	private Map<WebSession, WebChatClient> recievers = new HashMap<WebSession, WebChatClient>();
	
	@Override
	public WebSession login(String username, String password) {
		BundleContext context = Activator.getBundleContext();
		ServiceTracker chatServerTracker = new ServiceTracker(context, ChatServer.class.getName(), null);
		chatServerTracker.open();
		
		ChatServer chatServer = (ChatServer) chatServerTracker.getService();
		Session session = chatServer.login(username, password);
		
		chatServerTracker.close();
		
		WebSession webSession = new WebSession();
		webSession.setSessionId(String.valueOf(lastSessionId++));
		
		WebChatClient chatClient = new WebChatClient();
		chatClient.setSession(session);
		recievers.put(webSession, chatClient);
		
		BundleContext bundleContext = Activator.getBundleContext();
		bundleContext.registerService(ChatClient.class.getName(), chatClient, null);
		
		return webSession;
	}

	@Override
	public void sendMessage(WebSession webSession, String msg) {
		BundleContext context = Activator.getBundleContext();
		ServiceTracker chatServerTracker = new ServiceTracker(context, ChatServer.class.getName(), null);
		chatServerTracker.open();
		
		WebChatClient chatClient = recievers.get(webSession);
		
		ChatServer chatServer = (ChatServer) chatServerTracker.getService();
		chatServer.sendMessage(chatClient.getSession(), msg);
		
		chatServerTracker.close();
	}

	@Override
	public List<String> pollMessages(WebSession session) {
		WebChatClient chatClient = recievers.get(session);
		return chatClient.pollMessages();
	}

}

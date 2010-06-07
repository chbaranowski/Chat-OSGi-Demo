package com.github.tux2323.demo.chatserver.internal;

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import com.github.tux2323.demo.chatserver.ChatClient;
import com.github.tux2323.demo.chatserver.ChatServer;
import com.github.tux2323.demo.chatserver.Session;

public class ChatServiceImpl implements ChatServer{
	
	private long lastSessionId = 0;
	
	@Override
	public Session login(String username, String password) {
		Session session = new Session();
		session.setUsername(username);
		session.setSessionId(String.valueOf(lastSessionId++));
		return session;
	}

	@Override
	public void sendMessage(Session userSession, String msg) {
		BundleContext context = Activator.getContext();
		ServiceTracker chatClientTracker = new ServiceTracker(context, ChatClient.class.getName(), null);
		chatClientTracker.open();
		Object[] services = chatClientTracker.getServices();
		for (Object service : services) {
			ChatClient client = (ChatClient) service;
			client.receiveMessage(userSession.getUsername(), msg);
		}
		chatClientTracker.close();
	}

}

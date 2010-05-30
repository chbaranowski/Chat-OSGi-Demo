package com.github.tux2323.demo.chatserver.internal;

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import com.github.tux2323.demo.chatserver.ChatClient;
import com.github.tux2323.demo.chatserver.ChatServer;
import com.github.tux2323.demo.chatserver.Session;

public class ChatServiceImpl implements ChatServer{

	@Override
	public Session login(String username, String password) {
		return new Session();
	}

	@Override
	public void sendMessage(Session user, String msg) {
		BundleContext context = Activator.getContext();
		ServiceTracker chatClientTracker = new ServiceTracker(context, ChatClient.class.getName(), null);
		chatClientTracker.open();
		Object[] services = chatClientTracker.getServices();
		for (Object service : services) {
			ChatClient client = (ChatClient) service;
			client.receiveMessage(msg);
		}
		chatClientTracker.close();
	}

}

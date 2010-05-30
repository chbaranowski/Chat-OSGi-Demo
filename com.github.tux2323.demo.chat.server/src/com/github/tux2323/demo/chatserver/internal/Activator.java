package com.github.tux2323.demo.chatserver.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.github.tux2323.demo.chatserver.ChatServer;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}
	
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		ChatServer chatService = new ChatServiceImpl();
		context.registerService(ChatServer.class.getName(), chatService, null);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}

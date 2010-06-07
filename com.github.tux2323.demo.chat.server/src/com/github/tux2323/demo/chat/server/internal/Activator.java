package com.github.tux2323.demo.chat.server.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.github.tux2323.demo.chat.server.ChatServer;

public class Activator implements BundleActivator {

	public void start(BundleContext bundleContext) throws Exception {
		bundleContext.registerService(ChatServer.class.getName(), new ChatServerImpl(), null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		
	}

}

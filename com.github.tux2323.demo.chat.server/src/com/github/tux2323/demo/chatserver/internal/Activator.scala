package com.github.tux2323.demo.chatserver.internal

import com.github.tux2323.demo.chatserver._

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

class Activator extends BundleActivator {

	
	
	override def start(context : BundleContext) {
		ApplicationContext.context = context;
		var chatServer = new ChatServerImpl();
		context.registerService(classOf[ChatServer].getName(), chatServer, null);
	}
	
	override def stop(context : BundleContext) {
		ApplicationContext.context = null;
	}
	
	
	
}
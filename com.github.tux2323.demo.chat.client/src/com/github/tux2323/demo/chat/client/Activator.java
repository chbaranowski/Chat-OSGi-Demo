package com.github.tux2323.demo.chat.client;

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

import com.github.tux2323.demo.chat.server.ChatServer;

public class Activator implements BundleActivator {
	
	ServiceTracker chatServerServiceTracker;
	
	ServiceTracker httpServiceTracker;
	
	@Override
	public void start(BundleContext context) throws Exception {
		chatServerServiceTracker = new ServiceTracker(context, ChatServer.class.getName(), null);
		chatServerServiceTracker.open();
		ChatServer chatServer = (ChatServer) chatServerServiceTracker.getService();
		
		httpServiceTracker = new ServiceTracker(context, HttpService.class.getName(), null);
		httpServiceTracker.open();
		HttpService httpService = (HttpService) httpServiceTracker.getService();
		LoginServlet loginServlet = new LoginServlet();
		loginServlet.setChatServer(chatServer);
		httpService.registerServlet("/", loginServlet, new Properties(), null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		chatServerServiceTracker.close();
	}

}

package com.github.tux2323.demo.chat;

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

import com.github.tux2323.demo.chat.server.WebChatServerImpl;

public class Activator implements BundleActivator {

	private static BundleContext bundleContext;
	
	private ServiceTracker httpServiceTracker;
	
	@Override
	public void start(BundleContext context) throws Exception {
		bundleContext = context;
		
		httpServiceTracker = new ServiceTracker(context, HttpService.class.getName(), null);
		httpServiceTracker.open();
		HttpService httpService = (HttpService) httpServiceTracker.getService();
		HttpContext httpContext = httpService.createDefaultHttpContext();
		httpService.registerResources("/", "webapp", httpContext);
		
		WebChatServerImpl webChatServerServlet = new WebChatServerImpl();
		httpService.registerServlet("/simpleChat/chatServer", webChatServerServlet, new Properties(), httpContext);
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		bundleContext = null;
		httpServiceTracker.close();
	}

	public static BundleContext getBundleContext() {
		return bundleContext;
	}

}

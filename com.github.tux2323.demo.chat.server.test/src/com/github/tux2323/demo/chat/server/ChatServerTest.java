package com.github.tux2323.demo.chat.server;

import static org.junit.Assert.*;

import org.eclipse.core.runtime.adaptor.EclipseStarter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public class ChatServerTest {

	@Test
	public void testLogin() throws Exception {
		WebConversation wc = new WebConversation();
		String url = "http://localhost:8080/";
		WebRequest request = new GetMethodWebRequest(url);
		request.setParameter("username", "testuser");
		request.setParameter("password","tester");
		WebResponse response = wc.getResponse(request);
		assertEquals("OK", response.getText());
	}

	@Before
	public void setup() throws Exception {
		System.setProperty("osgi.clean", "true");
		System.setProperty("org.osgi.service.http.port", "8080");

		String[] equinoxArgs = { "-console" };

		BundleContext bundleContext = EclipseStarter.startup(equinoxArgs, null);
		assertNotNull(bundleContext);
		
		Bundle chatServerBundle = bundleContext.installBundle("file:///Users/developer/Develop/workspace/barcampkn-osgidemo/com.github.tux2323.demo.chat.server/bin");
		chatServerBundle.start();
		
		installPlatform(bundleContext);
		
		Bundle chatClientBundle = bundleContext.installBundle("file:///Users/developer/Develop/workspace/barcampkn-osgidemo/com.github.tux2323.demo.chat.client/bin");
		chatClientBundle.start();
	}

	public void installPlatform(BundleContext context) throws Exception {
		installPlatformBundle(context, "javax.servlet_2.5.0.v200806031605.jar");
		installPlatformBundle(context,
				"javax.servlet.jsp_2.0.0.v200806031607.jar");
		installPlatformBundle(context,
				"org.apache.commons.el_1.0.0.v200806031608.jar");
		installPlatformBundle(context,
				"org.apache.commons.logging_1.1.1.v200904062255.jar");
		installPlatformBundle(context,
				"org.apache.jasper_5.5.17.v200903231320.jar");
		installPlatformBundle(context,
				"org.eclipse.osgi.services_3.2.0.v20090520-1800.jar");
		installPlatformBundle(context,
				"org.eclipse.osgi.util_3.2.0.v20090520-1800.jar");
		installPlatformBundle(context,
				"org.eclipse.equinox.common_3.5.1.R35x_v20090807-1100.jar");
		installPlatformBundle(context,
				"org.eclipse.equinox.util_1.0.100.v20090520-1800.jar");
		installPlatformBundle(context,
				"org.eclipse.equinox.ds_1.1.1.R35x_v20090806.jar");
		installPlatformBundle(context,
				"org.mortbay.jetty.util_6.1.15.v200905182336.jar");
		installPlatformBundle(context,
				"org.mortbay.jetty.server_6.1.15.v200905151201.jar");
		installPlatformBundle(context,
				"org.eclipse.equinox.registry_3.4.100.v20090520-1800.jar");
		installPlatformBundle(context,
				"org.eclipse.equinox.http.servlet_1.0.200.v20090520-1800.jar");
		installPlatformBundle(context,
				"org.eclipse.equinox.http.registry_1.0.200.v20090520-1800.jar");
		installPlatformBundle(context,
				"org.eclipse.equinox.http.jetty_2.0.0.v20090520-1800.jar");
	}

	private void installPlatformBundle(BundleContext context, String bundleName)
			throws BundleException {
		String platformPath = "/Users/developer/Develop/workspace/barcampkn-osgidemo/demo-platform/bundles/";
		Bundle bundle = context.installBundle("file://" + platformPath
				+ bundleName);
		bundle.start();
	}

}

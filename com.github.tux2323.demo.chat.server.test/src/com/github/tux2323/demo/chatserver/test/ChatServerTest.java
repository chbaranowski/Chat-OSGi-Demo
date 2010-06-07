package com.github.tux2323.demo.chatserver.test;

import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

import com.github.tux2323.demo.chatserver.ChatClient;
import com.github.tux2323.demo.chatserver.ChatServer;
import com.github.tux2323.demo.chatserver.Session;

public class ChatServerTest {
	
	Mockery mockery = new Mockery();

	ChatServer chatServer;
	
	ChatClient mockChatClient;
	
	ServiceTracker chatServerTracker;
	
	ServiceRegistration mockChatClientService ;
	
	@Before
	public void setup() throws Exception {
		BundleContext context = Activator.getContext();
		chatServerTracker = new ServiceTracker(context, ChatServer.class.getName(), null);
		chatServerTracker.open();
		chatServer = (ChatServer) chatServerTracker.getService();
		
		mockChatClient = mockery.mock(ChatClient.class);
		mockChatClientService = context.registerService(ChatClient.class.getName(), mockChatClient, null);
	}
	
	@After
	public void tearDown() throws Exception{
		chatServerTracker.close();
		mockChatClientService.unregister();
	}
	
	@Test
	public void testLogin(){
		Session session = chatServer.login("tester", "test");
		assertNotNull(session);
		assertEquals("tester", session.getUsername());
	}
	
	@Test
	public void testSendMessage(){
		final String testMsg = "Test MSG";
		Session session = chatServer.login("tester", "test");
		
		mockery.checking(new Expectations() {{
		    oneOf(mockChatClient).receiveMessage("tester", testMsg);
		}});
		
		chatServer.sendMessage(session, testMsg);
		
		mockery.assertIsSatisfied();
	}
	
	
}

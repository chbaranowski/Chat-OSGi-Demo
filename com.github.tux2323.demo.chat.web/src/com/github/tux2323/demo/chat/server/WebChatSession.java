package com.github.tux2323.demo.chat.server;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.ServiceReference;

import com.github.tux2323.demo.chatserver.ChatClient;
import com.github.tux2323.demo.chatserver.Session;

public class WebChatSession implements ChatClient {
	
	private Session session;
	
	private List<String> messages = new ArrayList<String>();
	
	private ServiceReference serviceReference;
	
	@Override
	public void receiveMessage(String fromUser, String msg) {
		messages.add("["+fromUser+"] " + msg);
	}
	
	public List<String> pollMessages(){
		List<String> result = messages;
		messages = new ArrayList<String>();
		return result;
	}

	public void setServiceReference(ServiceReference serviceReference) {
		this.serviceReference = serviceReference;
	}

	public ServiceReference getServiceReference() {
		return serviceReference;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Session getSession() {
		return session;
	}

}

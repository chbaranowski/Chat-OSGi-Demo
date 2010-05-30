package com.github.tux2323.demo.chat.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("chatServer")
public interface WebChatServer extends RemoteService {

	WebSession login(String username, String password);
	
	void sendMessage(WebSession session, String msg);
	
	List<String> pollMessages(WebSession session);
	
	
}

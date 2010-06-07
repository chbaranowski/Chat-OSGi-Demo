package com.github.tux2323.demo.chat.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("chatServer")
public interface WebChatServer extends RemoteService {

	String login(String username, String password);
	
	void sendMessage(String session, String msg);
	
	List<String> pollMessages(String session);
	
	
}

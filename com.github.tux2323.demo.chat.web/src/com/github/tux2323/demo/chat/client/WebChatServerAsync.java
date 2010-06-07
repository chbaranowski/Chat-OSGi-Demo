package com.github.tux2323.demo.chat.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WebChatServerAsync {

	void login(String username, String password, AsyncCallback<String> callback);

	void sendMessage(String session, String msg,
			AsyncCallback<Void> callback);

	void pollMessages(String session, AsyncCallback<List<String>> callback);
	
}

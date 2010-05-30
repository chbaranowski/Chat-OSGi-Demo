package com.github.tux2323.demo.chat.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WebChatServerAsync {

	void login(String username, String password, AsyncCallback<WebSession> callback);

	void sendMessage(WebSession session, String msg,
			AsyncCallback<Void> callback);

	void pollMessages(WebSession session, AsyncCallback<List<String>> callback);
	
}

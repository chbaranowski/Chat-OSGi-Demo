package com.github.tux2323.demo.chatserver;

public interface ChatServer {

	Session login(String username, String password);
	
	void sendMessage(Session session, String msg);
	
}

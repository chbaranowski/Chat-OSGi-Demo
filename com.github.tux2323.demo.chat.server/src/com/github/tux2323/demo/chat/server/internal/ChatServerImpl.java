package com.github.tux2323.demo.chat.server.internal;

import com.github.tux2323.demo.chat.server.ChatServer;
import com.github.tux2323.demo.chat.server.Session;

public class ChatServerImpl implements ChatServer {

	@Override
	public Session login(String username, String password) {
		Session session = new Session();
		session.setUsername(username);
		// TODO IDM Binding
		return session;
	}

}

package com.github.tux2323.demo.chatserver.internal;

import java.util.List;
import java.util.Vector;

import com.github.tux2323.demo.chatserver.ChatClient;
import com.github.tux2323.demo.chatserver.ChatServer;
import com.github.tux2323.demo.chatserver.Session;

public class ChatServiceImpl implements ChatServer{
	
	private long lastSessionId = 0;
	
	private List<ChatClient> chatClients = new Vector<ChatClient>();
	
	@Override
	public Session login(String username, String password) {
		Session session = new Session();
		session.setUsername(username);
		session.setSessionId(String.valueOf(lastSessionId++));
		return session;
	}

	@Override
	public void sendMessage(Session userSession, String msg) {
		for (ChatClient chatClient : chatClients) {
			chatClient.receiveMessage(userSession.getUsername(), msg);
		}
	}

	public void setChatClients(ChatClient chatClient) {
		chatClients.add(chatClient);
	}

	
	public void unsetChatClients(ChatClient chatClient) {
		chatClients.remove(chatClient);
	}
	
	
	public List<ChatClient> getChatClients() {
		return chatClients;
	}

}

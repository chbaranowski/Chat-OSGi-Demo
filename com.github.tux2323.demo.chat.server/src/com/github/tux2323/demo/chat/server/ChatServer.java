package com.github.tux2323.demo.chat.server;

public interface ChatServer {

	Session login(String username, String password);

}

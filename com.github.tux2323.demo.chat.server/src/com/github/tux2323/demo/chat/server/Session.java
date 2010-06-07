package com.github.tux2323.demo.chat.server;

import java.io.Serializable;

public class Session implements Serializable {

	private String username;

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
	
}

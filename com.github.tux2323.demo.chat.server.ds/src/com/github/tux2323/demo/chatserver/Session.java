package com.github.tux2323.demo.chatserver;

import java.io.Serializable;

public class Session implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sessionId;
	
	private String username;

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}
	
}

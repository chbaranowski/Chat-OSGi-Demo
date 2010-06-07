package com.github.tux2323.demo.chat.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.tux2323.demo.chat.server.ChatServer;
import com.github.tux2323.demo.chat.server.Session;

public class LoginServlet extends HttpServlet {

	private ChatServer chatServer;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession httpSession = req.getSession();
		Session chatSession = (Session) httpSession.getAttribute("chatSession");
		if(chatSession == null){
			System.out.println("Do Login");
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			chatSession = chatServer.login(username, password);
			httpSession.setAttribute("chatSession", chatSession);
		}
		PrintWriter out = resp.getWriter();
		out.print("OK");
	}

	public void setChatServer(ChatServer chatServer) {
		this.chatServer = chatServer;
	}

	public ChatServer getChatServer() {
		return chatServer;
	}
	
}

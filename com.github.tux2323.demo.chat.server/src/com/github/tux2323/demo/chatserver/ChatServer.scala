package com.github.tux2323.demo.chatserver

trait ChatServer {

	def login(username : String , password : String) : Session
	
	def sendMessage(session : Session, msg : String)
	
}
package com.github.tux2323.demo.chatserver.internal

import com.github.tux2323.demo.chatserver._
import org.osgi.util.tracker.ServiceTracker
import org.osgi.framework.ServiceReference

class ChatServerImpl extends ChatServer {

	override def login(username : String , password : String) : Session = {
		if(username == "Tester" && password == "test")
			new Session();
		else throw new RuntimeException("Invalid Login");
	}
	
	override def sendMessage(session : Session, msg : String) {
		val context = ApplicationContext.context;
		val serviceTracker = new ServiceTracker(context, classOf[ChatClient].getName(), null);
		serviceTracker.open();
		val services = serviceTracker.getServices();
		services.foreach(service => service.asInstanceOf[ChatClient].receiveMessage(msg));
		serviceTracker.close();
	}
	
	
}
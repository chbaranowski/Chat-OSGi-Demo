package com.github.tux2323.demo.chat.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SimpleChat implements EntryPoint {
	
	private final WebChatServerAsync webChatServer = GWT.create(WebChatServer.class);
	
	private WebSession session = null;
	
	private final TextArea messageTextArea = new TextArea();
	
	private final Button sendButton = new Button("Send");
	
	private final VerticalPanel chatMessagesPanel = new VerticalPanel();

	private class LoginDialog extends DialogBox {

		final TextBox usernameTextBox = new TextBox();

		final PasswordTextBox passwordTextBox = new PasswordTextBox();

		final Button loginButton = new Button("Login");
		
		public LoginDialog() {
			// Set the dialog box's caption.
			setText("Chat Login");

			// Enable animation.
			setAnimationEnabled(true);

			// Enable glass background.
			setGlassEnabled(true);

			VerticalPanel loginPanel = new VerticalPanel();
			loginPanel.add(new Label("Username:"));
			loginPanel.add(usernameTextBox);
			loginPanel.add(new Label("Password"));
			loginPanel.add(passwordTextBox);
			loginPanel.add(loginButton);
			
			loginButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent clickEvent) {
					String username = usernameTextBox.getText();
					String password = passwordTextBox.getText();
					webChatServer.login(username, password, new AsyncCallback<WebSession>() {
						
						@Override
						public void onSuccess(WebSession newSession) {
							session = newSession;
							hide();
						}
						
						@Override
						public void onFailure(Throwable exp) {
							// TODO : Show Error Msg in Login Dialog
						}
						
					});
				}
				
			});
			
			setWidget(loginPanel);
		}
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		LoginDialog loginDialog = new LoginDialog();
		loginDialog.show();
		
		HorizontalPanel sendPanel = new HorizontalPanel();
		sendPanel.add(messageTextArea);
		sendPanel.add(sendButton);
		
		VerticalPanel chatPanel = new VerticalPanel();
		chatPanel.add(chatMessagesPanel);
		chatPanel.add(sendPanel);
		
		RootPanel rootPanel = RootPanel.get("chat");
		rootPanel.add(chatPanel);
		
		sendButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent clickEvent) {
				messageTextArea.setEnabled(false);
				webChatServer.sendMessage(session, messageTextArea.getText(), new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
					}
					
					@Override
					public void onFailure(Throwable exp) {
					}
				});
				messageTextArea.setText("");
				messageTextArea.setEnabled(true);
			}
			
		});
		
		Timer pollMessagesTimer = new Timer(){

			@Override
			public void run() {
				webChatServer.pollMessages(session, new AsyncCallback<List<String>>() {
					
					@Override
					public void onSuccess(List<String> messages) {
						for (String msg : messages) {
							Label msgLabel = new Label(msg);
							chatMessagesPanel.add(msgLabel);
						}
						schedule(1000);
					}
					
					@Override
					public void onFailure(Throwable exp) {
						schedule(3000);
					}
				});
			}
			
		};
		pollMessagesTimer.schedule(1000);
	}
	
}

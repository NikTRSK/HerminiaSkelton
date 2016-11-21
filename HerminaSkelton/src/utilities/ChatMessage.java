package utilities;

import java.io.Serializable;

public class ChatMessage implements Serializable {
	private String message;
	private String user;
	
	public ChatMessage(String message, String username) {
		this.message = message;
		this.user = username;
	}
	
	public ChatMessage(String message) {
		this.message = message;
		this.user = null;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getUsername() {
		return this.user;
	}
	
	public void setUsername(String username) {
		this.user = username;
	}
}

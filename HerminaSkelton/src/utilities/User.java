package utilities;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1247135059287883221L;
	private String username;
	private String password;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
}

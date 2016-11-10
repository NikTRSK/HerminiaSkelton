package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseLogic {
	// SQL Connection
	Connection conn = null;
	Statement statement = null;
	PreparedStatement loginStatement = null;
	PreparedStatement createStatement = null;
	ResultSet rs = null;
	
	DatabaseLogic() {
		initDB();
	}
	
	// returns true if user valid; false otherwise
	public boolean loginUser(String username, String password) throws SQLException {
		loginStatement.setString(1, username);
		rs = loginStatement.executeQuery();
		
		// user not found
		if (!rs.next()) {
			return false;
		}
		else {
			String pword = rs.getString("password");
			// wrong password
			if (!pword.equals(password))
				return false;
		}
		return true;
	}
	
	// return false if duplicate user; true otherwise
	public boolean createUser(String username, String password) throws SQLException {
		// check if user exits
		loginStatement.setString(1, username);
		rs = loginStatement.executeQuery();
		if (rs.next())
			return false;
		else {
			// if valid insert user
			createStatement.setString(1, username);
			createStatement.setString(2, password);
			createStatement.executeUpdate();
			return true;
		}
	}
	
	private void initDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/HerminiaSkeltonDB?user=root&password=root&useSSL=false");
			loginStatement = conn.prepareStatement("SELECT * FROM users WHERE username=?");
			createStatement = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
		} catch (SQLException sql) {
			System.out.println("sqle: " + sql.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		}
	}
}

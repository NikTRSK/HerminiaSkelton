package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseLogic {
	// SQL Connection
	Connection conn = null;
	Statement statement = null;
	PreparedStatement loginStatement = null;
	PreparedStatement createStatement = null;
	PreparedStatement scoreStatement = null;
	ResultSet rs = null;
	
	public DatabaseLogic() {
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
	
	public ArrayList<Integer> getScores(String username) {
		scoreStatement.setString(1, username);
		rs = scoreStatement.executeQuery();
		rs.
	}
	
	private void initDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/HerminiaSkelton_DB?user=root&password=root&useSSL=false");
			loginStatement = conn.prepareStatement("SELECT * FROM users WHERE username=?");
			createStatement = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
			scoreStatement = conn.prepareStatement("SELECT score1,score2,score3,score4,score5 FROM users WHERE username=?");
		} catch (SQLException sql) {
			System.out.println("sqle: " + sql.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		}
	}
}

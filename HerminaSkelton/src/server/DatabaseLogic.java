package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class DatabaseLogic {
	// SQL Connection
	Connection conn = null;
	Statement statement = null;
	PreparedStatement loginStatement = null;
	PreparedStatement createStatement = null;
	PreparedStatement scoreStatement = null;
	PreparedStatement updateScoresStatement = null;
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
	
	// get all scores for user
	protected ArrayList<Integer> getScores(String username, Integer newScore) {
		ArrayList<Integer> scores = new ArrayList<Integer>();
		try {
			scoreStatement.setString(1, username);
			rs = scoreStatement.executeQuery();
			int i = 1;
			while (rs.next()) {
				scores.add(rs.getInt("score" + i++));
			}
			// add the new score and sort the array list
			scores.add(newScore);
			Collections.sort(scores, Collections.reverseOrder());
			// TESTING ONLY
			System.out.println("Checking sorting****");
			for (Integer iger : scores)
		    System.out.println("value: " + iger);
			// TESTING ONLY
			
			// remove the last (weekest) score
			scores.remove(scores.size() - 1);
			
			// update scores in database
			for (int scoreID = 0; scoreID < scores.size(); scoreID++) {
				updateScoresStatement.setInt(i+1, scores.get(scoreID));
			}
			updateScoresStatement.setString(6, username);
			rs = updateScoresStatement.executeQuery();
			
		} catch (SQLException sqle) { utilities.Util.printExceptionToCommand(sqle);	}
		return scores;
	}
	
	private void initDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/HerminiaSkelton_DB?user=root&password=root&useSSL=false");
			loginStatement = conn.prepareStatement("SELECT * FROM users WHERE username=?");
			createStatement = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
			scoreStatement = conn.prepareStatement("SELECT score1,score2,score3,score4,score5 FROM users WHERE username=?");
			updateScoresStatement = conn.prepareStatement("INSERT INTO users SET score1=?, score2=?, score3=?, score4=?, score5=? WHERE username=?");
		} catch (SQLException sql) {
			System.out.println("sqle: " + sql.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		}
	}
}

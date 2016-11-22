package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import utilities.GameScore;

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
  protected ArrayList<GameScore> getScores(String userName, Integer newScore) {
    ArrayList<GameScore> scores = new ArrayList<GameScore>();
    try {
      ///
      rs = scoreStatement.executeQuery();
      while (rs.next()) {
        Integer score = rs.getInt("score");
        String user = rs.getString("username");
        scores.add(new GameScore(user, score));
      }
      scores.add(new GameScore(userName, newScore));
      
      // Sort the list
      Collections.sort(scores, new Comparator<GameScore>() {
        @Override
        public int compare(GameScore gs1, GameScore gs2) {
          return gs2.getScore() - gs1.getScore();
        }
      });
      
      // remove the last (weekest) score
      scores.remove(scores.size() - 1);
      
      // update the table
      for (int i = 0; i < scores.size(); ++i) {
        updateScoresStatement.setInt(1, scores.get(i).getScore());
        updateScoresStatement.setString(2, scores.get(i).getUsername());
        updateScoresStatement.setInt(3, i+1);
        updateScoresStatement.addBatch();
      }
      // execute the update on topScores
      updateScoresStatement.executeBatch();
      
    } catch (SQLException sqle) { utilities.Util.printExceptionToCommand(sqle); }
    // return top 5 scores
    return scores;
  }
  
  private void initDB() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection("jdbc:mysql://localhost/HerminiaSkelton_DB?user=root&password=root&useSSL=false");
      loginStatement = conn.prepareStatement("SELECT * FROM users WHERE username=?");
      createStatement = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
      scoreStatement = conn.prepareStatement("SELECT * FROM topScores");
      updateScoresStatement = conn.prepareStatement("UPDATE topScores SET score=?, username=? WHERE scoreID=?");
    } catch (SQLException sql) {
      System.out.println("sqle: " + sql.getMessage());
    } catch (ClassNotFoundException cnfe) {
      System.out.println("cnfe: " + cnfe.getMessage());
    }
  }
}

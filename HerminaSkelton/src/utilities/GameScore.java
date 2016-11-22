package utilities;

import java.io.Serializable;

public class GameScore implements Serializable {
  private static final long serialVersionUID = -2493113959811480589L;
  String username;
  Integer score;
  
  public GameScore(String username, Integer score) {
    this.username = username;
    this.score = score;
  }
  
  public void setScore(Integer score) {
    this.score = score;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public Integer getScore() {
    return this.score;
  }
  
  public String getUsername() {
    return this.username;
  }
}

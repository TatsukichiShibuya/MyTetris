package tetris.game.object;

import java.util.*;

class ScoreManager{
  //field
  private int score;
  private final String SCORE_FORMAT = "SCORE : %010d";

  // constractor
  public ScoreManager(){
    score = 0;
  }

  public void update(String str){
    switch(str){
      case "clear":
        score += 10000;
        break;
      case "Md":
        score += 100;
        break;
    }
  }

  public String getScoreWithFormat(){
    return String.format(SCORE_FORMAT, score);
  }
}

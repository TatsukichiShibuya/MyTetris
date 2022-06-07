package tetris.game.object;

import java.util.*;

class TimeManager{
  // field
  private int time;
  private int landtime;
  private boolean landing;
  private final int MOVE_TIME = 1;
  private final int FALL_TIME = 20;
  private final int POP_TIME = 10;

  // constractor
  public TimeManager(){
    time = 1;
    landtime = time;
    landing = false;
  }

  // method
  public void update(Board board, ArrayDeque<Character> input){
    // move and rotate
    if(time%MOVE_TIME==0){
      board.execute(input);
    }
    // fall
    if(landing!=board.landing()){
      landing = !landing;
      landtime = time;
    }
    if(!landing&&time%FALL_TIME==0){
      board.fall();
    }
    // fix and pop
    if(landing && time-landtime>POP_TIME){
      landing = false;
      board.fix();
      board.pop();
    }
    time++;
  }
}

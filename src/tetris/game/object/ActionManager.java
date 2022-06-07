package tetris.game.object;

import java.util.*;

class ActionManager{
  // field
  private final int INIT_X;
  private final int INIT_Y;
  private final HashMap<Character, String> keyMap;
  {
    keyMap = new HashMap<>();
    keyMap.put('a', "Ml");
    keyMap.put('s', "Md");
    keyMap.put('d', "Mr");
    keyMap.put('j', "Rl");
    keyMap.put('l', "Rr");
  }
  private ScoreManager sm;

  // constractor
  public ActionManager(int x, int y, ScoreManager sm){
    INIT_X = x;
    INIT_Y = y;
    this.sm = sm;
  }

  // method
  public void execute(char in, int[][] tetriminoMap, Tetrimino tetrimino){
    if(keyMap.keySet().contains(in)){
      int[][] moved = tetrimino.getPosition(keyMap.get(in));
      if(!isOverrap(tetriminoMap, moved)){
        tetrimino.action(keyMap.get(in));
        sm.update(keyMap.get(in));
      }
    }
  }
  public boolean landing(int[][] tetriminoMap, Tetrimino tetrimino){
    int[][] moved = tetrimino.getPosition("Md");
    return isOverrap(tetriminoMap, moved);
  }

  public void fall(int[][] tetriminoMap, Tetrimino tetrimino){
    int[][] moved = tetrimino.getPosition("Md");
    if(!isOverrap(tetriminoMap, moved)){
      tetrimino.action("Md");
    }
  }

  public int[][] fix(int[][] tetriminoMap, Tetrimino tetrimino){
    int[][] current = tetrimino.getPosition("C");
    for(int[] xy: current){
      tetriminoMap[xy[0]][xy[1]] = tetrimino.getShape();
    }
    tetriminoMap = clearRaw(tetriminoMap);
    return tetriminoMap;
  }

  public Tetrimino pop(int[][] tetriminoMap, Tetrimino tetrimino, TetriminoManager tetriminoManager){
    if(tetriminoMap[INIT_X][INIT_Y]>0){
      return null;
    }else{
      return tetriminoManager.poll();
    }
  }

  private boolean isOverrap(int[][] tetriminoMap, int[][] moved){
    for(int[] xy: moved){
      if(xy[0]<0 || xy[0]>=tetriminoMap.length) return true;
      if(xy[1]<0 || xy[1]>=tetriminoMap[0].length) return true;
      if(tetriminoMap[xy[0]][xy[1]]>0) return true;
    }
    return false;
  }
  private int[][] clearRaw(int[][] tetriminoMap){
    int[][] ret = new int[tetriminoMap.length][tetriminoMap[0].length];
    int clear = 0;
    for(int i=tetriminoMap[0].length-1; i>=0; i--){
      boolean flag = true;
      for(int j=0; j<tetriminoMap.length; j++){
        flag &= (tetriminoMap[j][i]>0);
      }
      if(flag){
        clear++;
        sm.update("clear");
      }else{
        for(int j=0; j<tetriminoMap.length; j++){
          ret[j][i+clear] = tetriminoMap[j][i];
        }
      }
    }
    return ret;
  }
}

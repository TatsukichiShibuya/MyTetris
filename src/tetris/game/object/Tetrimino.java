package tetris.game.object;

import java.util.*;
class Tetrimino{
  private final char SHAPE;
  private final int SIZE;
  private final Integer[][][] RELATED_COORD;

  private final HashMap<Character, Integer> sizeMap;
  private final HashMap<Character, Integer[][][]> coordMap;

  {
    sizeMap = new HashMap<>();
    sizeMap.put('I', 4);
    sizeMap.put('O', 4);
    sizeMap.put('T', 4);
    sizeMap.put('J', 4);
    sizeMap.put('L', 4);
    sizeMap.put('S', 4);
    sizeMap.put('Z', 4);

    coordMap = new HashMap<>();
    coordMap.put('I', new Integer[][][]{{{0,0}, {0, 1}, {0, 2}, {0, 3}},
                                        {{0,0}, {-1,0}, {-2,0}, {-3,0}},
                                        {{0,0}, {0, -1}, {0,-2}, {0,-3}},
                                        {{0,0}, {1, 0}, {2, 0}, {3, 0}}});//
    coordMap.put('O', new Integer[][][]{{{0,0}, {1, 0}, {1, 1}, {0, 1}},
                                        {{0,0}, {0, 1}, {-1,1}, {-1,0}},
                                        {{0,0}, {-1,0}, {-1,-1}, {0,-1}},
                                        {{0,0}, {0,-1}, {1,-1}, {1, 0}}});//
    coordMap.put('T', new Integer[][][]{{{0,0}, {1, 0}, {0, 1}, {-1, 0}},
                                        {{0,0}, {0, 1}, {-1,0}, {0,-1}},
                                        {{0,0}, {-1,0}, {0,-1}, {1, 0}},
                                        {{0,0}, {0,-1}, {1, 0}, {0, 1}}});//
    coordMap.put('J', new Integer[][][]{{{0,0}, {0, 1}, {0, 2}, {-1,2}},
                                        {{0,0}, {-1,0}, {-2,0}, {-2,-1}},
                                        {{0,0}, {0,-1}, {0,-2}, {1,-2}},
                                        {{0,0}, {1, 0}, {2, 0}, {2, 1}}});
    coordMap.put('L', new Integer[][][]{{{0,0}, {0, 1}, {0, 2}, {1,2}},
                                        {{0,0}, {-1,0}, {-2,0}, {-2,1}},
                                        {{0,0}, {0,-1}, {0,-2}, {-1,-2}},
                                        {{0,0}, {1, 0}, {2, 0}, {2,-1}}});
    coordMap.put('S', new Integer[][][]{{{1, 0}, {0,0}, {0, 1}, {-1,1}},
                                        {{0,-1}, {0,0}, {1, 0}, {1, 1}},
                                        {{-1,0}, {0, 0}, {0,-1}, {1,-1}},
                                        {{0, 1}, {0,0}, {-1,0}, {-1,-1}}});//
    coordMap.put('Z', new Integer[][][]{{{-1,0}, {0,0}, {0, 1}, {1, 1}},
                                        {{0,-1}, {0,0}, {-1,0}, {-1,1}},
                                        {{1, 0}, {0,0}, {0,-1}, {-1,-1}},
                                        {{0, 1}, {0,0}, {1, 0}, {1, -1}}});//
  }

  private final int ID;
  private int pose;
  private int x, y;

  public Tetrimino(int id, int x, int y, char shape){
    this.ID = id;
    this.x = x;
    this.y = y;
    this.pose = 0;

    this.SHAPE = shape;
    this.SIZE = sizeMap.get(this.SHAPE);
    this.RELATED_COORD = coordMap.get(this.SHAPE);
  }

  public void action(String s) {
    switch(s){
      case "Ml": x-=1; break;
      case "Md": y+=1; break;
      case "Mr": x+=1; break;
      case "Rl": pose = (pose-1+4)%4; break;
      case "Rr": pose = (pose+1)%4; break;
    }
  }

  public int[][] getPosition(String s){
    int nx = x, ny = y, npose = pose;
    switch(s){
      case "Ml": nx-=1; break;
      case "Md": ny+=1; break;
      case "Mr": nx+=1; break;
      case "Rl": npose=(pose-1+4)%4; break;
      case "Rr": npose=(pose+1)%4; break;
      case "Origin": nx=0; ny=0; pose=0; break;
    }
    return getPosition(nx, ny, npose);
  }

  private int[][] getPosition(int nx, int ny, int npose){
    int[][] position = new int[SIZE][2];
    for(int i=0; i<SIZE; i++){
      position[i][0] = nx+RELATED_COORD[npose][i][0];
      position[i][1] = ny+RELATED_COORD[npose][i][1];
    }
    return position;
  }

  public int getX(){ return x; }

  public int getY(){ return y; }

  public int getID(){ return ID; }

  public char getShape(){ return SHAPE; }

  @Override
  public String toString(){
    return String.format("%s(%d) (%d, %d) %d", SHAPE, ID, x, y, pose);
  }
}

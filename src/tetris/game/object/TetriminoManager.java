package tetris.game.object;

import java.util.*;

class TetriminoManager{
  // field
  private ArrayDeque<Tetrimino> deque;
  private int id = 0;
  private final int INIT_X;
  private final int INIT_Y;
  private ArrayList<Character> shapelist;
  {
    shapelist = new ArrayList<>();
    shapelist.add('I');
    shapelist.add('O');
    shapelist.add('T');
    shapelist.add('J');
    shapelist.add('L');
    shapelist.add('S');
    shapelist.add('Z');
  }

  // constractor
  public TetriminoManager(int x, int y){
    INIT_X = x;
    INIT_Y = y;

    deque = new ArrayDeque<>();
    updateDeque();
  }

  // method
  public Tetrimino poll(){
    if(deque.size()<=3) updateDeque();
    return deque.pollFirst();
  }

  private void updateDeque(){
    Collections.shuffle(shapelist);
    for(int i=0; i<shapelist.size(); i++){
      deque.addLast(new Tetrimino(id++, INIT_X, INIT_Y, shapelist.get(i)));
    }
  }

  public Tetrimino peek(int i) throws NullPointerException{
    if(i>=3) throw new NullPointerException();
    try{
      Tetrimino[] next = new Tetrimino[i+1];
      for(int j=0; j<=i; j++){
        next[j] = deque.pollFirst();
      }
      for(int j=i; j>=0; j--){
        deque.addFirst(next[j]);
      }
      return next[i];
    }catch(NullPointerException e){
      throw e;
    }
  }
}

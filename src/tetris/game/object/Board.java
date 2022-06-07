package tetris.game.object;

import java.util.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Board{
  // field
  private final int BOARD_WIDTH;
  private final int BOARD_HEIGHT;
  private final boolean HIDDEN_NEXT;
  private final int INIT_X;
  private final int INIT_Y;
  private final int WINDOW_WIDTH;
  private final int WINDOW_HEIGHT;
  private int[][] tetriminoMap;
  private Tetrimino tetrimino;
  private TetriminoManager tetriminoManager;
  private int id = 0;

  private Canvas canvas;

  private TimeManager tm;
  private ScoreManager sm;
  private DrawManager dm;
  private ActionManager am;

  // constractor
  public Board(int w, int h, HashMap<String, Integer> setting, VBox vb){
    BOARD_WIDTH = setting.get("board width:");
    BOARD_HEIGHT = setting.get("board height:");
    HIDDEN_NEXT = (setting.get("hidden next:")==1);
    INIT_X = BOARD_WIDTH/2;
    INIT_Y = 0;
    WINDOW_WIDTH = w;
    WINDOW_HEIGHT = h;

    tetriminoMap = new int[BOARD_WIDTH][BOARD_HEIGHT];
    tetriminoManager = new TetriminoManager(INIT_X, INIT_Y);

    tm = new TimeManager();
    sm = new ScoreManager();
    dm = new DrawManager(BOARD_WIDTH, BOARD_HEIGHT, WINDOW_WIDTH, WINDOW_HEIGHT, HIDDEN_NEXT, vb);
    am = new ActionManager(INIT_X, INIT_Y, sm);

    tetrimino = tetriminoManager.poll();
  }

  // method
  public void update(ArrayDeque<Character> input){
    tm.update(this, input);
  }

  public void draw(){
    dm.draw(tetriminoMap, tetrimino, tetriminoManager);
    dm.setText(sm.getScoreWithFormat());
  }

  public void execute(ArrayDeque<Character> input){
    while(input.size()>0){
      char in = input.pollLast();
      am.execute(in, tetriminoMap, tetrimino);
    }
  }

  public boolean landing(){
    return am.landing(tetriminoMap, tetrimino);
  }

  public void fall(){
    am.fall(tetriminoMap, tetrimino);
  }

  public void fix(){
    tetriminoMap = am.fix(tetriminoMap, tetrimino);
  }

  public void pop() throws NullPointerException{
    tetrimino = am.pop(tetriminoMap, tetrimino, tetriminoManager);
    if(tetrimino==null) throw new NullPointerException();
  }
}

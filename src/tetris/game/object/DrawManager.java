package tetris.game.object;

import java.util.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.text.Font;


class DrawManager{
  // field
  private final int BOARD_WIDTH;
  private final int BOARD_HEIGHT;
  private final int WINDOW_WIDTH;
  private final int WINDOW_HEIGHT;

  private final boolean HIDDEN_NEXT;
  private final HashMap<Character, Color> colorMap;
  {
    colorMap = new HashMap<>();
    colorMap.put('I', Color.SKYBLUE);
    colorMap.put('O', Color.GOLD);
    colorMap.put('T', Color.PURPLE);
    colorMap.put('J', Color.BLUE);
    colorMap.put('L', Color.ORANGE);
    colorMap.put('S', Color.GREEN);
    colorMap.put('Z', Color.RED);
  }
  private Canvas canvas;
  private GraphicsContext gc;

  private Canvas nextcanvas;
  private GraphicsContext nextgc;

  private final int CANVAS_WIDTH;
  private final int CANVAS_HEIGHT;
  private final int NEXT_WIDTH;
  private final int NEXT_HEIGHT;

  private Label score;

  // constractor
  public DrawManager(int bw, int bh, int ww, int wh, boolean hidden, VBox vb){
    BOARD_WIDTH = bw;
    BOARD_HEIGHT = bh;
    WINDOW_WIDTH = ww;
    WINDOW_HEIGHT = wh;
    HIDDEN_NEXT = hidden;
    // layout
    NEXT_WIDTH = 50;
    NEXT_HEIGHT = 180;
    CANVAS_WIDTH = WINDOW_WIDTH-NEXT_WIDTH;
    CANVAS_HEIGHT = WINDOW_HEIGHT-10;

    this.canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    this.gc = canvas.getGraphicsContext2D();

    this.nextcanvas = new Canvas(NEXT_WIDTH, NEXT_HEIGHT);
    this.nextgc = nextcanvas.getGraphicsContext2D();

    this.score = new Label();
    this.score.setFont(new Font("fancytext", 20));

    HBox hb = new HBox();
    hb.setAlignment(Pos.CENTER);
    hb.getChildren().addAll(canvas, nextcanvas);

    vb.setAlignment(Pos.CENTER);
    vb.getChildren().addAll(hb, score);
  }

  // method
  public void draw(int[][] tetriminoMap, Tetrimino tetrimino, TetriminoManager tetriminoManager){
    float w = (float)CANVAS_WIDTH/BOARD_WIDTH;
    float h = (float)CANVAS_HEIGHT/BOARD_HEIGHT;

    gc.setFill(Color.WHITE);
    gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

    // draw grid
    gc.setFill(Color.BLACK);
    gc.setLineWidth(0.05);
    for(int i=1; i<tetriminoMap.length; i++){
      gc.strokeLine(i*w, 0, i*w, tetriminoMap[0].length*h);
    }
    for(int j=1; j<tetriminoMap[0].length; j++){
      gc.strokeLine(0, j*h, tetriminoMap.length*w, j*h);
    }

    // draw box
    for(int i=0; i<tetriminoMap.length; i++){
      for(int j=0; j<tetriminoMap[0].length; j++){
        if(tetriminoMap[i][j]>0){
          gc.setFill(colorMap.get((char)tetriminoMap[i][j]));
          gc.fillRect(i*w+1, j*h+1, w-2, h-2);
        }
      }
    }

    // draw tetrimino
    double alpha = gc.getGlobalAlpha();
    gc.setGlobalAlpha(0.5);
    gc.setFill(colorMap.get(tetrimino.getShape()));
    int[][] current = tetrimino.getPosition("C");
    for(int[] xy: current){
      gc.fillRect(xy[0]*w+1, xy[1]*h+1, w-2, h-2);
    }
    gc.setGlobalAlpha(alpha);

    // draw next
    nextgc.setFill(Color.WHITE);
    nextgc.fillRect(0, 0, NEXT_WIDTH, NEXT_HEIGHT);
    nextgc.setFill(Color.BLACK);
    nextgc.setLineWidth(2);
    nextgc.strokeRect(0, 0, NEXT_WIDTH, NEXT_HEIGHT);

    nextgc.setFill(Color.BLACK);
    nextgc.setLineWidth(0.05);
    w = (float)NEXT_WIDTH/5;
    h = (float)NEXT_HEIGHT/18;
    for(int i=1; i<5; i++){
      nextgc.strokeLine(i*w, 0, i*w, 18*h);
    }
    for(int j=1; j<18; j++){
      nextgc.strokeLine(0, j*h, 5*w, j*h);
    }
    nextgc.setLineWidth(2);

    if(HIDDEN_NEXT) return;

    for(int i=0; i<3; i++){
      try{
        Tetrimino nextTetrimino = tetriminoManager.peek(i);
        int[][] next = nextTetrimino.getPosition("Origin");
        nextgc.setFill(colorMap.get(nextTetrimino.getShape()));
        for(int[] xy: next){
          nextgc.fillRect(w*(2+xy[0]), h*(1+xy[1]+6*i), w-2, h-2);
        }
      }catch(NullPointerException e){
        // hidden
      }
    }
  }

  public void setText(String s){
    score.setText(s);
  }
}

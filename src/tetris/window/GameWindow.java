package tetris.window;
import tetris.game.object.*;
import tetris.utils.*;

import javafx.animation.*;
import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.*;
import javafx.geometry.Pos;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.canvas.*;
import java.util.*;
import javafx.scene.text.Font;

public class GameWindow extends Stage{
  // field
  private final int WINDOW_WIDTH = 400;
  private final int WINDOW_HEIGHT = 1000;
  private ArrayDeque<Character> input;

  MainGame mg;
  VBox gameVBox;
  Button stopButton;

  // constractor
  public GameWindow(){
    VBox root = new VBox();
    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    // game handling
    HashMap<String, Integer> setting = SettingIO.load(StartWindow.SETTING_PATH);
    gameVBox = new VBox();
    mg = new MainGame(WINDOW_WIDTH, WINDOW_HEIGHT/2, setting, gameVBox);
    input = new ArrayDeque<>();

    // setting layout
    Label title = new Label("TETRIS");
    title.setFont(new Font("fancytext", 80));

    stopButton = new Button("Pause");
    stopButton.setPrefWidth(WINDOW_WIDTH/2);
    stopButton.setPrefHeight(WINDOW_HEIGHT/10);
    stopButton.setOnAction(
      ev->{
        String s = ((Button)ev.getSource()).getText();
        if(s.equals("Pause")){
          mg.stop();
          stopButton.setText("Resume");
        }else{
          if(s.equals("Resume")){
            mg.start();
          }else if(s.equals("Restart")){
            gameVBox = new VBox();
            mg = new MainGame(WINDOW_WIDTH, WINDOW_HEIGHT/2, setting, gameVBox);
            root.getChildren().set(1, gameVBox);
            mg.start();
          }
          stopButton.setText("Pause");
        }
      });

    Button closeButton = new Button("Exit");
    closeButton.setPrefWidth(WINDOW_WIDTH/2);
    closeButton.setPrefHeight(WINDOW_HEIGHT/10);
    closeButton.setOnAction(
      ev->{
        mg.stop();
        this.close();
        StartWindow sw = new StartWindow();
        sw.start(new Stage());
      });

    scene.setOnKeyTyped(
      ev->{
        if(stopButton.getText().equals("Pause")){
          input.addFirst(ev.getCharacter().charAt(0));
        }
      });
    root.setAlignment(Pos.CENTER);
    root.getChildren().add(title);
    root.getChildren().add(gameVBox);
    root.getChildren().add(stopButton);
    root.getChildren().add(closeButton);

    // setting stage
    this.setWidth(WINDOW_WIDTH);
    this.setHeight(WINDOW_HEIGHT);
    this.setResizable(false);
    this.setScene(scene);
    this.setTitle("Game Window");
    this.show();

    mg.start();
  }

  public void stopGame(){
    mg.stop();
    stopButton.setText("Restart");
  }

  // inner class
  public class MainGame extends AnimationTimer{
    // field
    private Board board;

    // constractor
    public MainGame(int h, int w, HashMap<String, Integer> setting, VBox vb){
      board = new Board(h, w, setting, vb);
      board.draw();
    }

    // method
    @Override
    public void handle(long now){
      try{
        board.update(input);
        board.draw();
      }catch(NullPointerException e){
        System.out.println("GAMEOVER");
        stopGame();
      }
    }
  }
}

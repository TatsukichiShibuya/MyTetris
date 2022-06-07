package tetris.window;

import java.nio.file.*;

import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.*;
import javafx.geometry.Pos;
//import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.Font;


public class StartWindow extends Application{
  // field
  private final int WINDOW_WIDTH = 400;
  private final int WINDOW_HEIGHT = 400;
  public static final Path SETTING_PATH = Paths.get("asset/log/setting.txt");

  // constractor
  public StartWindow(){
  }

  // method
  @Override
  public void start(Stage stage){
    VBox root = new VBox();
    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    // setting layout
    Label title = new Label("TETRIS");
    title.setFont(new Font("fancytext", 20));

    Button startButton = new Button("Start");
    startButton.setStyle("-fx-base: ORANGE;");
    startButton.setPrefWidth(WINDOW_WIDTH/2);
    startButton.setPrefHeight(WINDOW_HEIGHT/10);
    startButton.setOnAction(
      ev->{
        stage.close();
        GameWindow sw = new GameWindow();
      });

    Button settingButton = new Button("Setting");
    settingButton.setPrefWidth(WINDOW_WIDTH/2);
    settingButton.setPrefHeight(WINDOW_HEIGHT/10);
    settingButton.setOnAction(
      ev->{
        stage.close();
        SettingWindow sw = new SettingWindow();
      });

    Button closeButton = new Button("Exit");
    closeButton.setPrefWidth(WINDOW_WIDTH/2);
    closeButton.setPrefHeight(WINDOW_HEIGHT/10);
    closeButton.setOnAction(
      ev->{
        stage.close();
      });

    root.setAlignment(Pos.CENTER);
    root.getChildren().add(title);
    root.getChildren().add(startButton);
    root.getChildren().add(settingButton);
    root.getChildren().add(closeButton);

    // setting stage
    stage.setWidth(WINDOW_WIDTH);
    stage.setHeight(WINDOW_HEIGHT);
    stage.setResizable(false);
    stage.setScene(scene);
    stage.setTitle("Main Window");
    stage.show();
  }
}

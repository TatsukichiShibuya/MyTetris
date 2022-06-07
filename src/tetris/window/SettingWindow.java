package tetris.window;
import tetris.utils.*;

import java.util.*;
import java.io.*;

import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.beans.value.ObservableValue;

public class SettingWindow extends Stage{
  // field
  private final int WINDOW_WIDTH = 400;
  private final int WINDOW_HEIGHT = 400;
  private final int MAX_BOARD_WIDTH = 30;
  private final int MIN_BOARD_WIDTH = 5;
  private final int MAX_BOARD_HEIGHT = 40;
  private final int MIN_BOARD_HEIGHT = 10;

  private int boardWidth;
  private int boardHeight;

  // constractor
  public SettingWindow(){
    // load setting
    HashMap<String, Integer> setting = SettingIO.load(StartWindow.SETTING_PATH);

    // setting layout
    VBox root = new VBox();
    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    // sliders and checkbox
    // width
    boardWidth = setting.get("board width:");
    float value = (boardWidth-MIN_BOARD_WIDTH)/(float)(MAX_BOARD_WIDTH-MIN_BOARD_WIDTH);
    Slider boardWidthSlider = new Slider(0, 1, value);
    Label boardWidthLabel = new Label(String.valueOf(boardWidth));
    boardWidthSlider.valueProperty().addListener(
      (ObservableValue<? extends Number> ov, Number oldv, Number newv)->{
        boardWidth = (int)((MAX_BOARD_WIDTH-MIN_BOARD_WIDTH)*(float)boardWidthSlider.getValue()+MIN_BOARD_WIDTH);
        setting.replace("board width:", boardWidth);
        boardWidthLabel.setText(String.valueOf(boardWidth));
      });
    HBox hbwidth = new HBox();
    hbwidth.setAlignment(Pos.CENTER);
    hbwidth.getChildren().add(boardWidthLabel);
    hbwidth.getChildren().add(boardWidthSlider);
    //height
    boardHeight = setting.get("board height:");
    value = (boardHeight-MIN_BOARD_HEIGHT)/(float)(MAX_BOARD_HEIGHT-MIN_BOARD_HEIGHT);
    Slider boardHeightSlider = new Slider(0, 1, value);
    Label boardHeightLabel = new Label(String.valueOf(boardHeight));
    boardHeightSlider.valueProperty().addListener(
      (ObservableValue<? extends Number> ov, Number oldv, Number newv)->{
        boardHeight = (int)((MAX_BOARD_HEIGHT-MIN_BOARD_HEIGHT)*(float)boardHeightSlider.getValue()+MIN_BOARD_HEIGHT);
        setting.replace("board height:", boardHeight);
        boardHeightLabel.setText(String.valueOf(boardHeight));
      });
    HBox hbheight = new HBox();
    hbheight.setAlignment(Pos.CENTER);
    hbheight.getChildren().add(boardHeightLabel);
    hbheight.getChildren().add(boardHeightSlider);
    // checkbox
    CheckBox hiddenNextBox = new CheckBox("Hidden Next");
    hiddenNextBox.setSelected(setting.get("hidden next:")==1);
    hiddenNextBox.selectedProperty().addListener(
      (ObservableValue<? extends Boolean> ov, Boolean oldv, Boolean newv)->{
        if(newv) setting.replace("hidden next:", 1);
        else setting.replace("hidden next:", 0);
      });

    // buttons
    HBox hb = new HBox();
    Button saveButton = new Button("Save and Exit");
    saveButton.setPrefWidth(WINDOW_WIDTH/3);
    saveButton.setPrefHeight(WINDOW_HEIGHT/3);
    saveButton.setOnAction(
      ev->{
        this.close();
        // save setting
        SettingIO.save(StartWindow.SETTING_PATH, setting);
        // return start window
        StartWindow sw = new StartWindow();
        sw.start(new Stage());
      });
    Button closeButton = new Button("Don't Save and Exit");
    closeButton.setPrefWidth(WINDOW_WIDTH/3);
    closeButton.setPrefHeight(WINDOW_HEIGHT/3);
    closeButton.setOnAction(
      ev->{
        this.close();
        // return start window (with no save)
        StartWindow sw = new StartWindow();
        sw.start(new Stage());
      });
    hb.setAlignment(Pos.CENTER);
    hb.getChildren().add(saveButton);
    hb.getChildren().add(closeButton);

    root.setAlignment(Pos.CENTER);
    root.getChildren().add(hbwidth);
    root.getChildren().add(hbheight);
    root.getChildren().add(hiddenNextBox);
    root.getChildren().add(hb);

    // setting stage
    this.setWidth(WINDOW_WIDTH);
    this.setHeight(WINDOW_HEIGHT);
    this.setResizable(false);
    this.setScene(scene);
    this.setTitle("Setting Window");
    this.show();
  }
}

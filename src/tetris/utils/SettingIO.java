package tetris.utils;

import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;

public class SettingIO{
  // static field
  public static final HashMap<String, Integer> INIT_SETTING;
  static{
    INIT_SETTING = new HashMap<>();
    INIT_SETTING.put("board width:", 15);
    INIT_SETTING.put("board height:", 30);
    INIT_SETTING.put("hidden next:", 0); // 0 means false, 1 means true
  }

  // static method
  public static HashMap<String, Integer> load(Path path){
    HashMap<String, Integer> setting = new HashMap<>();
    try{
      List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
      for(String key: INIT_SETTING.keySet()){
        setting.put(key, findValue(key, lines));
      }
    }catch (IOException e) {
      for(String key: INIT_SETTING.keySet()){
        setting.put(key, INIT_SETTING.get(key));
      }
    }
    return setting;
  }

  public static boolean save(Path path, HashMap<String, Integer> setting){
    ArrayList<String> lines = new ArrayList<>();
    for(String key: setting.keySet()){
      lines.add(key+setting.get(key).toString());
    }
    if(!Files.exists(path)){
      try{
        Files.createFile(path);
      }catch(IOException e){
        // todo
        e.printStackTrace();
        return false;
      }
    }
    try{
      Files.write(path, lines);
    }catch(IOException e){
      // todo
      e.printStackTrace();
      return false;
    }
    return true;
  }

  private static Integer findValue(String key, List<String> lines){
    for(String line: lines){
      if(line.contains(key)) return Integer.parseInt(line.split(":")[1]);
    }
    return INIT_SETTING.get(key);
  }
}

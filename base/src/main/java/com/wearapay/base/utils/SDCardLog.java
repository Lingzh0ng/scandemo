package com.wearapay.base.utils;

import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Kindy on 2016-07-05.
 * just for debug to write log on SDCard
 */
public class SDCardLog {
  private static final boolean DEBUG = false;
  private FileOutputStream fileOutputStream;
  private static SimpleDateFormat simpleDateFormat =
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
  private final String directory = "/wp/wanlaifu/";
  private String fileName;

  public static SDCardLog getInstance() {
    return LogHolder.instance;
  }

  private SDCardLog() {
    if (DEBUG) {
      String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
      fileName = rootPath + directory + new SimpleDateFormat("yyyMMddHHmmss", Locale.CHINA).format(
          new Date(System.currentTimeMillis())) + ".log";
      System.out.println("fileName = " + fileName);
      createFile(fileName);
      try {
        fileOutputStream = new FileOutputStream(fileName);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public String getFileName() {
    return fileName;
  }

  public void log(String str) {
    if (!DEBUG) {
      return;
    }
    synchronized (this) {
      try {
        String time = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        byte[] bytes = (time + " " + str).getBytes("utf-8");
        fileOutputStream.write(bytes);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void close() {
    if (fileOutputStream != null) {
      try {
        fileOutputStream.close();
      } catch (IOException e) {
      }
    }
  }

  public File createFile(String file) {
    File file1 = new File(file);
    if (file1.exists()) {
      return file1;
    }

    int end = file.lastIndexOf(File.separator);
    String _filePath = file.substring(0, end + 1);
    String _fileName = file.substring(end + 1);
    return createFile(_filePath, _fileName);
  }

  public File createFile(String path, String name) {

    File file1 = new File(path);
    if (!file1.exists()) {
      file1.mkdirs();
    }

    File file2 = new File(path + name);
    if (!file2.exists()) {
      try {
        file2.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    if (file2.exists()) {
      return file2;
    }

    return null;
  }

  private static class LogHolder {
    private static SDCardLog instance = new SDCardLog();
  }
}
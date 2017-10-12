package com.wearapay.base.utils;

/**
 * Created by Kindy on 2016-02-21.
 */

import android.util.Log;

public class L {

  public final static byte O = 0;  //System.out
  public final static byte V = 1;  //verbose
  public final static byte I = 2;  //info
  public final static byte D = 3;  //DEBUG
  public final static byte W = 4;  //warning
  public final static byte E = 5;  //error
  public final static byte LOG = 6;  //log.txt
  public final static byte NULL = 7;  //no log

  private static byte mode = O;

  //-------------------- System.out ------------------------------------------
  public static void o(Object tag, Object... s) {
    StringBuffer sb = new StringBuffer();
    for (Object ts : s) {
      sb.append(ts);
    }
    o(tag, sb.toString());
  }

  public static void o(Object tag, String s) {
    if (mode <= O) {
      System.out.println(getTag(tag) + " " + s);
      SDCardLog.getInstance().log("O : " + s + "\n");
    }
  }

  //-------------------- verbose ------------------------------------------
  public static void v(Object tag, Object... s) {
    StringBuffer sb = new StringBuffer();
    for (Object ts : s) {
      sb.append(ts);
    }
    v(tag, sb.toString());
  }

  public static void v(Object tag, String s) {
    if (mode <= V) {
      Log.v(getTag(tag), s);
      SDCardLog.getInstance().log("V : " + s + "\n");
    }
  }

  //-------------------- DEBUG --------------------------------------------
  public static void d(Object tag, Object... s) {
    StringBuffer sb = new StringBuffer();
    for (Object ts : s) {
      sb.append(ts);
    }
    d(tag, sb.toString());
  }

  public static void d(Object tag, String s) {
    if (mode <= D) {
      Log.d(getTag(tag), s);
      SDCardLog.getInstance().log("D : " + s + "\n");
    }
  }

  //-------------------- info ---------------------------------------------
  public static void i(Object tag, Object... s) {
    StringBuffer sb = new StringBuffer();
    for (Object ts : s) {
      sb.append(ts);
    }
    i(tag, sb.toString());
  }

  public static void i(Object tag, String s) {
    if (mode <= I) {
      Log.i(getTag(tag), s);
      SDCardLog.getInstance().log("I : " + s + "\n");
    }
  }

  //-------------------- warning ------------------------------------------
  public static void w(Object tag, Object... s) {
    StringBuffer sb = new StringBuffer();
    for (Object ts : s) {
      sb.append(ts);
    }
    w(tag, sb.toString());
  }

  public static void w(Object tag, String s) {
    w(tag, s, null);
  }

  public static void w(Object tag, String s, Throwable tr) {
    if (mode <= W) {
      if (tr == null) {
        Log.w(getTag(tag), s);
      } else {
        Log.w(getTag(tag), s, tr);
      }
      SDCardLog.getInstance().log("W : " + s + "\n");
    }
  }

  //-------------------- error --------------------------------------------
  public static void e(Object tag, Object... s) {
    StringBuffer sb = new StringBuffer();
    for (Object ts : s) {
      sb.append(ts);
    }
    e(tag, sb.toString());
  }

  public static void e(Object tag, String s) {
    e(tag, s, null);
  }

  public static void e(Object tag, String s, Throwable tr) {
    if (mode <= E) {
      if (tr == null) {
        Log.e(getTag(tag), s);
      } else {
        Log.e(getTag(tag), s, tr);
      }
      SDCardLog.getInstance().log("E : " + s + "\n");
    }
  }

  private static String getTag(Object o) {
    String tag = "";
    if (o != null) {
      if (o instanceof String) {
        tag = (String) o;
      } else {
        tag = o.getClass().getSimpleName().trim();
        if (tag.length() == 0) {
          tag = o.getClass().getName();
        }
      }
    }

    return tag;
  }

  public static byte getMode() {
    return mode;
  }

  public static void setMode(byte mode) {
    L.mode = mode;
  }

  public static void setMode(String mode) {
    if (mode == null || mode.trim().equalsIgnoreCase("")) {
      return;
    }

    byte bModel = O;

    if (mode.equalsIgnoreCase("O")) {
      bModel = O;
    } else if (mode.equalsIgnoreCase("V")) {
      bModel = V;
    } else if (mode.equalsIgnoreCase("I")) {
      bModel = I;
    } else if (mode.equalsIgnoreCase("D")) {
      bModel = D;
    } else if (mode.equalsIgnoreCase("W")) {
      bModel = W;
    } else if (mode.equalsIgnoreCase("E")) {
      bModel = E;
    } else if (mode.equalsIgnoreCase("LOG")) {
      bModel = LOG;
    } else if (mode.equalsIgnoreCase("NULL")) {
      bModel = NULL;
    }

    setMode(bModel);
  }
}

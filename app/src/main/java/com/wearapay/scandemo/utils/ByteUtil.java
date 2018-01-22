package com.wearapay.scandemo.utils;

import java.nio.ByteBuffer;

/**
 * Created by Kindy on 2016-02-23.
 */
public class ByteUtil {

  public static byte mergeByte(byte highBit, byte lowBit) {
    return (byte) ((highBit << 4) | lowBit);
  }

  public static String bytes2HexString(final byte[] data) {
    return bytes2HexString(data, true);
  }

  public static String bytes2HexString(final byte[] data, boolean isLog) {
    if (data == null) {
      return null;
    }
    final StringBuilder stringBuilder = new StringBuilder(data.length);
    String dataFormat;
    if (isLog) {
      dataFormat = "%02X ";
    } else {
      dataFormat = "%02X";
    }
    for (byte byteChar : data) {
      stringBuilder.append(String.format(dataFormat, byteChar));
    }
    return stringBuilder.toString();
  }

  public static byte[] short2Bytes(short s) {
    byte[] b = new byte[2];
    b[0] = (byte) (s >> 8);
    b[1] = (byte) (s >> 0);
    return b;
  }

  public static byte[] int2ThreeBytes(int i) {
    byte[] b = new byte[3];
    b[0] = (byte) (i >> 16);
    b[1] = (byte) (i >> 8);
    b[2] = (byte) (i >> 0);
    return b;
  }

  public static int threeBytes2Int(byte[] data) {
    if (data == null || data.length < 3) {
      return 0;
    }
    ByteBuffer byteBuffer = ByteBuffer.allocate(4);
    byteBuffer.put(((data[0] & 0x80) == 0x80) ? (byte) 0xff : 0x00);
    byteBuffer.put(data);
    return fourBytes2Int(byteBuffer.array());
  }

  public static int fourBytes2Int(byte[] data) {
    if (data == null || data.length < 4) {
      return 0;
    }
    return ((data[0] & 0xff) << 24
        | (data[1] & 0xff) << 16
        | (data[2] & 0xff) << 8
        | data[3] & 0xff);
  }

  public static short byte2short(byte b) {
    if (b < 0) return (short) (256 + (short) b);
    return (short) b;
  }

  public static short bytes2Short(byte[] b) {
    return (short) (((b[0] << 8) | b[1] & 0xff));
  }

  public static byte[] hexString2Bytes(String hexString) {
    if (hexString == null || hexString.isEmpty()) {
      throw new IllegalArgumentException("hex string is null.");
    }
    hexString = hexString.replaceAll("\\s+", "");
    hexString = hexString.toUpperCase();
    boolean isEvenNum = hexString.length() % 2 == 0;
    if (!isEvenNum) {
      throw new IllegalArgumentException("invalid hex string.");
    }
    int len = (hexString.length() / 2);
    byte[] result = new byte[len];
    char[] achar = hexString.toCharArray();
    for (int i = 0; i < len; i++) {
      int pos = i * 2;
      result[i] = (byte) (hexChar2Byte(achar[pos]) << 4 | hexChar2Byte(achar[pos + 1]));
    }
    return result;
  }

  public static byte[] string2Bytes(String string) {
    ByteBuffer byteBuffer = ByteBuffer.allocate(string.length());

    for (int i = 0; i < string.length(); i++) {
      byteBuffer.put(hexChar2Byte(string.charAt(i)));
    }

    return byteBuffer.array();
  }

  private static byte hexChar2Byte(char c) {
    byte b = (byte) "0123456789ABCDEF".indexOf(Character.toUpperCase(c));
    return b;
  }

  //------------------------- 华丽的分割线 -------------------------------

  /**
   * 转换int为byte数组
   */
  public static void putInt(byte[] bb, int x, int index) {
    bb[index + 3] = (byte) (x >> 24);
    bb[index + 2] = (byte) (x >> 16);
    bb[index + 1] = (byte) (x >> 8);
    bb[index + 0] = (byte) (x >> 0);
  }

  /**
   * 通过byte数组取到int
   *
   * @param index 第几位开始
   */
  public static int getInt(byte[] bb, int index) {
    return (int) ((((bb[index + 3] & 0xff) << 24) | ((bb[index + 2] & 0xff) << 16) | ((bb[index + 1]
        & 0xff) << 8) | ((bb[index + 0] & 0xff) << 0)));
  }

  /**
   * 转换long型为byte数组
   */
  public static void putLong(byte[] bb, long x, int index) {
    bb[index + 7] = (byte) (x >> 56);
    bb[index + 6] = (byte) (x >> 48);
    bb[index + 5] = (byte) (x >> 40);
    bb[index + 4] = (byte) (x >> 32);
    bb[index + 3] = (byte) (x >> 24);
    bb[index + 2] = (byte) (x >> 16);
    bb[index + 1] = (byte) (x >> 8);
    bb[index + 0] = (byte) (x >> 0);
  }

  /**
   * 通过byte数组取到long
   */
  public static long getLong(byte[] bb, int index) {
    return ((((long) bb[index + 7] & 0xff) << 56)
        | (((long) bb[index + 6] & 0xff) << 48)
        | (((long) bb[index + 5] & 0xff) << 40)
        | (((long) bb[index + 4] & 0xff) << 32)
        | (((long) bb[index + 3] & 0xff) << 24)
        | (((long) bb[index + 2] & 0xff) << 16)
        | (((long) bb[index + 1] & 0xff) << 8)
        | (((long) bb[index + 0] & 0xff) << 0));
  }

  /**
   * 字符到字节转换
   */
  public static void putChar(byte[] bb, char ch, int index) {
    int temp = (int) ch;
    // byte[] b = new byte[2];
    for (int i = 0; i < 2; i++) {
      bb[index + i] = new Integer(temp & 0xff).byteValue(); // 将最高位保存在最低位
      temp = temp >> 8; // 向右移8位
    }
  }

  /**
   * 字节到字符转换
   */
  public static char getChar(byte[] b, int index) {
    int s = 0;
    if (b[index + 1] > 0) {
      s += b[index + 1];
    } else {
      s += 256 + b[index + 0];
    }
    s *= 256;
    if (b[index + 0] > 0) {
      s += b[index + 1];
    } else {
      s += 256 + b[index + 0];
    }
    char ch = (char) s;
    return ch;
  }

  /**
   * float转换byte
   */
  public static void putFloat(byte[] bb, float x, int index) {
    // byte[] b = new byte[4];
    int l = Float.floatToIntBits(x);
    for (int i = 0; i < 4; i++) {
      bb[index + i] = new Integer(l).byteValue();
      l = l >> 8;
    }
  }

  /**
   * 通过byte数组取得float
   */
  public static float getFloat(byte[] b, int index) {
    int l;
    l = b[index + 0];
    l &= 0xff;
    l |= ((long) b[index + 1] << 8);
    l &= 0xffff;
    l |= ((long) b[index + 2] << 16);
    l &= 0xffffff;
    l |= ((long) b[index + 3] << 24);
    return Float.intBitsToFloat(l);
  }

  /**
   * double转换byte
   */
  public static void putDouble(byte[] bb, double x, int index) {
    // byte[] b = new byte[8];
    long l = Double.doubleToLongBits(x);
    for (int i = 0; i < 4; i++) {
      bb[index + i] = new Long(l).byteValue();
      l = l >> 8;
    }
  }

  /**
   * 通过byte数组取得float
   */
  public static double getDouble(byte[] b, int index) {
    long l;
    l = b[0];
    l &= 0xff;
    l |= ((long) b[1] << 8);
    l &= 0xffff;
    l |= ((long) b[2] << 16);
    l &= 0xffffff;
    l |= ((long) b[3] << 24);
    l &= 0xffffffffl;
    l |= ((long) b[4] << 32);
    l &= 0xffffffffffl;
    l |= ((long) b[5] << 40);
    l &= 0xffffffffffffl;
    l |= ((long) b[6] << 48);
    l &= 0xffffffffffffffl;
    l |= ((long) b[7] << 56);
    return Double.longBitsToDouble(l);
  }


  public static int bytesToInt(byte[] data) {
    int length = data.length;
    if (length <= 4 && length >= 1) {
      int sum = 0;

      for (int i = 0; i < length; ++i) {
        sum |= (data[i] & 255) << (length - i - 1) * 8;
      }

      return sum;
    } else {
      return 0;
    }
  }
}

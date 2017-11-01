package com.wearapay.net.Exception;

/**
 * Created by Leo Ren(leo.ren@paypos.ca) on 10/8/15.
 */
public class PPCodedException extends RuntimeException {
  private String code;
  private String msg;

  public PPCodedException(String code, String message) {
    this.code = code;
    this.msg = message;
  }

  public PPCodedException(String messages) {
    this.msg = messages;
  }

  public String getMessages() {
    return this.msg;
  }

  @Override public String getMessage() {
    return "code :" + code + " ,   msg : " + msg;
  }
}

package com.wearapay.net.Exception;

public class NotLoginException extends RuntimeException {
  public NotLoginException(String detailMessage) {
    super(detailMessage);
  }
}

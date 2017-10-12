package com.wearapay.net.Exception;

/**
 * Created by lyz on 2017/10/11.
 */

public class ApiException extends RuntimeException {
  public ApiException() {
  }

  public ApiException(String message) {
    super(message);
  }

  public ApiException(String message, Throwable cause) {
    super(message, cause);
  }
}

package com.wearapay.base;

/**
 * Created by lyz on 2017/11/1.
 */

public class BaseBean<T> {
  private boolean success;
  private String code;
  private String msg;
  private T data;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  @Override public String toString() {
    return "BaseBean{"
        + "success="
        + success
        + ", code='"
        + code
        + '\''
        + ", msg='"
        + msg
        + '\''
        + ", data="
        + data
        + '}';
  }
}

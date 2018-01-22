package com.wearapay.data.bean;

/**
 * loginId	String	登陆名
 nickname	String	昵称
 password1	String	密码 /
 password2	String	密码确认 /
 verifyCode	String	验证码
 * Created by lyz on 2018/1/20.
 */

public class VerifyCodeRegister {
  private String loginId;
  private String nickname;
  private String password1;
  private String password2;
  private String verifyCode;

  public String getLoginId() {
    return loginId;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getPassword1() {
    return password1;
  }

  public void setPassword1(String password1) {
    this.password1 = password1;
  }

  public String getPassword2() {
    return password2;
  }

  public void setPassword2(String password2) {
    this.password2 = password2;
  }

  public String getVerifyCode() {
    return verifyCode;
  }

  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }
}

package com.wearapay.domain.user;

import io.reactivex.Observable;

public interface IUserMgmt {
  boolean getLoginStatus();

  String getUserTaken();

  void saveUserTaken(String taken);

  void updateLoginStatus(boolean status);

  void logout();

  void login(String taken);

  Observable<String> login(String name, String pwd);

  String test(String name, String pwd);
}

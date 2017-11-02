package com.wearapay.domain.user;

import io.reactivex.Observable;

public interface IUserMgmt {
  boolean getLoginStatus();

  String getUserTaken();

  void saveUserTaken(String taken);

  void updateLoginStatus(boolean status);

  Observable<Boolean> logout();

  void logoutLocal();

  Observable<String> login(String name, String pwd);

  String test(String name, String pwd);

  Observable<Boolean> register(String loginId, String password1, String password2, String nickname);

  Observable<Boolean> changePassword(String password, String password1,
      String password2);
}

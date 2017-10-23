package com.wearapay.domain.repository;

import com.wearapay.base.net.IRepository;

/**
 * Created by lyz on 2017/10/23.
 */

public interface ILocalRepository extends IRepository {
  boolean getLoginStatus();

  String getUserTaken();

  void saveUserTaken(String taken);

  void updateLoginStatus(boolean status);

  void logout();

  void login(String taken);
}

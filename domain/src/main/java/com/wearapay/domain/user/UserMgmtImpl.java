package com.wearapay.domain.user;

import com.wearapay.data.repository.ILocalRepository;
import com.wearapay.data.repository.IUserRepository;
import io.reactivex.Observable;

/**
 * Created by lyz on 2017/10/31.
 */

public class UserMgmtImpl implements IUserMgmt {
  ILocalRepository iLocalRepository;
  IUserRepository iUserRepository;

  public UserMgmtImpl(ILocalRepository iLocalRepository, IUserRepository iUserRepository) {
    this.iLocalRepository = iLocalRepository;
    this.iUserRepository = iUserRepository;
  }

  @Override public boolean getLoginStatus() {
    return iLocalRepository.getLoginStatus();
  }

  @Override public String getUserTaken() {
    return iLocalRepository.getUserTaken();
  }

  @Override public void saveUserTaken(String taken) {
    iLocalRepository.saveUserTaken(taken);
  }

  @Override public void updateLoginStatus(boolean status) {
    iLocalRepository.updateLoginStatus(status);
  }

  @Override public void logout() {
    iLocalRepository.logout();
  }

  @Override public void login(String taken) {
    iLocalRepository.login(taken);
  }

  @Override public Observable<String> login(String name, String pwd) {
    return iUserRepository.login(name, pwd);
  }

  @Override public String test(String name, String pwd) {
    return iUserRepository.test(name, pwd);
  }
}

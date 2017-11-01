package com.wearapay.domain.user;

import com.wearapay.data.repository.ILocalRepository;
import com.wearapay.data.repository.IUserRepository;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

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

  @Override public Observable<Boolean> logout() {
    return iUserRepository.logout(iLocalRepository.getUserTaken())
        .flatMap(new Function<Void, ObservableSource<Boolean>>() {
          @Override public ObservableSource<Boolean> apply(Void aVoid) throws Exception {
            iLocalRepository.logout();
            return Observable.just(true);
          }
        });
  }

  @Override public Observable<String> login(String name, String pwd) {
    return iUserRepository.login(name, pwd)
        .flatMap(new Function<String, ObservableSource<String>>() {
          @Override public ObservableSource<String> apply(String s) throws Exception {
            iLocalRepository.login(s);
            return Observable.just(s);
          }
        });
  }

  @Override public String test(String name, String pwd) {
    return iUserRepository.test(name, pwd);
  }

  @Override public Observable<Boolean> register(String loginId, String password1, String password2,
      String nickname) {
    return iUserRepository.register(loginId, password1, password2, nickname)
        .flatMap(new Function<Void, ObservableSource<Boolean>>() {
          @Override public ObservableSource<Boolean> apply(Void aVoid) throws Exception {
            return Observable.just(true);
          }
        });
  }

  @Override
  public Observable<Boolean> changePassword(String password, String password1, String password2) {
    return iUserRepository.changePassword(iLocalRepository.getUserTaken(), password, password1,
        password2).flatMap(new Function<Void, ObservableSource<Boolean>>() {
      @Override public ObservableSource<Boolean> apply(Void aVoid) throws Exception {
        return Observable.just(true);
      }
    });
  }
}

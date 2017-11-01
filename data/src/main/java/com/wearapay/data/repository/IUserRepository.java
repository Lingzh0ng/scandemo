package com.wearapay.data.repository;

import com.wearapay.base.net.IRepository;
import io.reactivex.Observable;

/**
 * Created by lyz on 2017/10/12.
 */

public interface IUserRepository extends IRepository {
  Observable<String> login(String loginId, String password);

  Observable<Void> register(String loginId, String password1, String password2, String nickname);

  Observable<Void> changePassword(String token, String password, String password1,
      String password2);

  Observable<Void> logout(String token);

  String test(String name, String pwd);
}

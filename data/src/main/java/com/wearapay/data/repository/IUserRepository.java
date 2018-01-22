package com.wearapay.data.repository;

import com.wearapay.base.net.IRepository;
import com.wearapay.data.bean.VerifyCodeRegister;
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

  Observable<Void> verifyCodeRegister(VerifyCodeRegister verifyCodeRegister);

  Observable<Void> requestRegisterCode(String number);

  Observable<Void> verifyCodeRegister2(
      String loginId, String password1,
      String password2,  String nickname,String verifyCode);

  String test(String name, String pwd);
}

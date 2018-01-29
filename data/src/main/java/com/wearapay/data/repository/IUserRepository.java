package com.wearapay.data.repository;

import com.wearapay.base.net.IRepository;
import com.wearapay.data.bean.VerifyCodeRegister;
import io.reactivex.Observable;

/**
 * Created by lyz on 2017/10/12.
 */

public interface IUserRepository extends IRepository {
  Observable<String> login(String loginId, String password);

  Observable<Boolean> register(String loginId, String password1, String password2, String nickname);

  Observable<Boolean> changePassword(String token, String password, String password1,
      String password2);

  Observable<Boolean> logout(String token);

  Observable<Boolean> verifyCodeRegister(VerifyCodeRegister verifyCodeRegister);

  Observable<Boolean> requestRegisterCode(String number);

  Observable<Boolean> verifyCodeRegister2(
      String loginId, String password1,
      String password2,  String nickname,String verifyCode);

  String test(String name, String pwd);
}

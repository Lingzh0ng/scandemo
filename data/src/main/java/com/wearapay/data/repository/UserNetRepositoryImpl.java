package com.wearapay.data.repository;

import android.content.Context;
import com.wearapay.data.retorfit.RestUserService;
import com.wearapay.net.BaseNetRepositoryModel;
import io.reactivex.Observable;
import javax.inject.Singleton;
import retrofit2.Retrofit;

/**
 * Created by lyz on 2017/10/12.
 */
@Singleton public class UserNetRepositoryImpl extends BaseNetRepositoryModel<RestUserService>
    implements IUserRepository {

  public UserNetRepositoryImpl(Context context, Retrofit retrofit) {
    super(context, retrofit);
  }

  @Override protected RestUserService initRetrofitClient(Context context) {
    return retrofit.create(getServiceType());
  }

  @Override public Observable<String> login(String name, String pwd) {
    return getService().login(name, pwd);
  }

  @Override public Observable<Void> register(String loginId, String password1, String password2,
      String nickname) {
    return getService().register(loginId, password1, password2, nickname);
  }

  @Override public Observable<Void> changePassword(String token, String password, String password1,
      String password2) {
    return getService().changePassword(token, password, password1, password2);
  }

  @Override public Observable<Void> logout(String token) {
    return getService().logout(token);
  }

  @Override public String test(String name, String pwd) {
    return name + pwd + " test";
  }
}

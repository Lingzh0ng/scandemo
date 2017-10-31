package com.wearapay.data.repository;

import android.content.Context;
import com.wearapay.data.retorfit.RestTransactionService;
import com.wearapay.net.BaseNetRepositoryModel;
import io.reactivex.Observable;
import javax.inject.Singleton;
import retrofit2.Retrofit;

/**
 * Created by lyz on 2017/10/12.
 */
@Singleton public class UserNetRepositoryImpl extends BaseNetRepositoryModel<RestTransactionService>
    implements IUserRepository {

  public UserNetRepositoryImpl(Context context, Retrofit retrofit) {
    super(context, retrofit);
  }

  @Override protected RestTransactionService initRetrofitClient(Context context) {
    return retrofit.create(getServiceType());
  }

  @Override public Observable<String> login(String name, String pwd) {
    return getService().login(name, pwd);
  }

  @Override public String test(String name, String pwd) {
    return name + pwd + " test";
  }
}

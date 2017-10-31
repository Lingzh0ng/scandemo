package com.wearapay.scandemo.module.login.presenter;

import android.content.Context;
import com.wearapay.domain.user.IUserMgmt;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.module.login.view.ILoginView;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/23.
 */

public class LoginPresenter extends BaseFragmentPresenter<ILoginView> {

  private final IUserMgmt userMgmt;

  @Inject
  public LoginPresenter(Context mContext, IUserMgmt userMgmt) {
    super(mContext);
    this.userMgmt = userMgmt;
  }

  public void login(String username,String password){
    //wrap(userRepository.login(username,password)).flatMap(s -> {
    //  if (s == null) {
    //    s = "1234";
    //  }
    //  return Observable.just(s);
    //}).subscribe(new Observer<String>() {
    //  @Override public void onSubscribe(@NonNull Disposable d) {
    //
    //  }
    //
    //  @Override public void onNext(@NonNull String s) {
    //
    //  }
    //
    //  @Override public void onError(@NonNull Throwable e) {
    //
    //  }
    //
    //  @Override public void onComplete() {
    //
    //  }
    //});
    userMgmt.login("123456");
    view.LoginSuccess();

  }
}

package com.wearapay.scandemo.module.login.presenter;

import android.content.Context;
import com.wearapay.domain.user.IUserMgmt;
import com.wearapay.net.BaseObserver;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.module.login.view.ILoginView;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/23.
 */

public class LoginPresenter extends BaseFragmentPresenter<ILoginView> {

  private final IUserMgmt userMgmt;

  @Inject public LoginPresenter(Context mContext, IUserMgmt userMgmt) {
    super(mContext);
    this.userMgmt = userMgmt;
  }

  public void login(String username, String password) {
    view.showProgress("");
    wrap(userMgmt.login(username, password)).subscribe(new BaseObserver<String>(view) {

      @Override public void onNext(String s) {
        view.LoginSuccess();
      }

      @Override protected void handlerError(Throwable e) {
        view.LoginFailure();
      }
    });
  }
}

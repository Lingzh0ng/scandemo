package com.wearapay.scandemo.module.login.presenter;

import android.content.Context;
import com.wearapay.domain.user.IUserMgmt;
import com.wearapay.net.BaseObserver;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.module.login.view.IRegView;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/23.
 */

public class RegPresenter extends BaseFragmentPresenter<IRegView> {

  private final IUserMgmt userMgmt;

  @Inject public RegPresenter(Context mContext, IUserMgmt userMgmt) {
    super(mContext);
    this.userMgmt = userMgmt;
  }

  public void reg(String username, String password) {
    view.showProgress("");
    wrap(userMgmt.register(username, password, password, username)).subscribe(
        new BaseObserver<Boolean>(view) {

          @Override public void onNext(Boolean aBoolean) {
            view.RegSuccess();
          }

          @Override protected void handlerError(Throwable e) {
            view.RegFailure();
          }
        });
  }
}

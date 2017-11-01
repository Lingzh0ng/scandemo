package com.wearapay.scandemo.module.user.presenter;

import android.content.Context;
import com.wearapay.domain.user.IUserMgmt;
import com.wearapay.net.BaseObserver;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.module.user.view.IChangePswView;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/23.
 */

public class ChangePswPresenter extends BaseFragmentPresenter<IChangePswView> {

  private final IUserMgmt userMgmt;

  @Inject
  public ChangePswPresenter(Context mContext, IUserMgmt userMgmt) {
    super(mContext);
    this.userMgmt = userMgmt;
  }
  public void changePsw(String username,String password){
    view.showProgress("");
    wrap(userMgmt.changePassword(username, password, password, username)).subscribe(
        new BaseObserver<Boolean>(view) {

          @Override public void onNext(Boolean aBoolean) {
            //view.();
          }

          @Override protected void handlerError(Throwable e) {
            //view.RegFailure();
          }
        });


  }
}

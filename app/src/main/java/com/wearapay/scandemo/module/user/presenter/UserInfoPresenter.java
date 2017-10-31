package com.wearapay.scandemo.module.user.presenter;

import android.content.Context;
import com.wearapay.domain.user.IUserMgmt;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.module.user.view.IUserInfoView;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/23.
 */

public class UserInfoPresenter extends BaseFragmentPresenter<IUserInfoView> {

  private final IUserMgmt userMgmt;

  @Inject
  public UserInfoPresenter(Context mContext, IUserMgmt userMgmt) {
    super(mContext);
    this.userMgmt = userMgmt;
  }

  public void logout() {
    userMgmt.logout();
  }
}

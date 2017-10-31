package com.wearapay.scandemo.module.home.presenter;

import android.content.Context;
import com.wearapay.domain.user.IUserMgmt;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.module.home.view.IHomeView;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/23.
 */

public class HomePresenter extends BaseFragmentPresenter<IHomeView> {

  private final IUserMgmt userMgmt;

  @Inject
  public HomePresenter(Context mContext, IUserMgmt userMgmt) {
    super(mContext);
    this.userMgmt = userMgmt;
  }

  public boolean getLoginStatus(){
    return userMgmt.getLoginStatus();
  }
}

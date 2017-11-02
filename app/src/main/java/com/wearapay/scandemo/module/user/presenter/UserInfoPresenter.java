package com.wearapay.scandemo.module.user.presenter;

import android.content.Context;
import android.os.Bundle;
import com.wearapay.domain.user.IUserMgmt;
import com.wearapay.net.BaseObserver;
import com.wearapay.scandemo.AppConstant;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.module.user.view.IUserInfoView;
import com.wearapay.scandemo.utils.ActivityUtils;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/23.
 */

public class UserInfoPresenter extends BaseFragmentPresenter<IUserInfoView> {

  private final IUserMgmt userMgmt;

  @Inject public UserInfoPresenter(Context mContext, IUserMgmt userMgmt) {
    super(mContext);
    this.userMgmt = userMgmt;
  }

  public void logout() {
    wrap(userMgmt.logout()).subscribe(new BaseObserver<Boolean>(view) {
      @Override public void onNext(Boolean aBoolean) {
        view.showMessage("退出登录成功");
        Bundle bundle = ActivityUtils.getBundle("登录", false, true);
        ActivityUtils.startFragment(view.getUseContext(), AppConstant.FragmentType.Login, bundle);
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        userMgmt.logoutLocal();
        Bundle bundle = ActivityUtils.getBundle("登录", false, true);
        ActivityUtils.startFragment(view.getUseContext(), AppConstant.FragmentType.Login, bundle);
      }
    });
  }
}

package com.wearapay.scandemo.module.device.presenter;

import android.content.Context;
import com.wearapay.domain.user.IUserMgmt;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.module.device.view.IDeviceHistoryView;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/23.
 */

public class DeviceHistoryPresenter extends BaseFragmentPresenter<IDeviceHistoryView> {

  private final IUserMgmt userMgmt;

  @Inject
  public DeviceHistoryPresenter(Context mContext, IUserMgmt userMgmt) {
    super(mContext);
    this.userMgmt = userMgmt;
  }
  public void reg(String username,String password){
    //wrap(userRepository.login(username,password)).subscribe(new Observer<String>() {
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


  }
}

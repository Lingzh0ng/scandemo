package com.wearapay.scandemo.module.device.presenter;

import android.content.Context;
import com.wearapay.domain.repository.ILocalRepository;
import com.wearapay.domain.repository.IUserRepository;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.module.device.view.IChargeView;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/23.
 */

public class ChargePresenter extends BaseFragmentPresenter<IChargeView> {

  private final ILocalRepository localRepository;
  private final IUserRepository userRepository;

  @Inject
  public ChargePresenter(Context mContext, IUserRepository userRepository, ILocalRepository localRepository) {
    super(mContext);
    this.userRepository = userRepository;
    this.localRepository = localRepository;
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

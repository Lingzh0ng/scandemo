package com.wearapay.scandemo.module.login.presenter;

import android.content.Context;
import com.wearapay.domain.repository.ILocalRepository;
import com.wearapay.domain.repository.IUserRepository;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.module.login.view.IRegView;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/23.
 */

public class RegPresenter extends BaseFragmentPresenter<IRegView> {

  private final ILocalRepository localRepository;
  private final IUserRepository userRepository;

  @Inject
  public RegPresenter(Context mContext, IUserRepository userRepository, ILocalRepository localRepository) {
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
    //localRepository.login("123456");
    view.RegSuccess();

  }
}
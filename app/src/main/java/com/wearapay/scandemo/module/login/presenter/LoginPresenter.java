package com.wearapay.scandemo.module.login.presenter;

import android.content.Context;
import com.wearapay.domain.repository.ILocalRepository;
import com.wearapay.domain.repository.IUserRepository;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.module.login.view.ILoginView;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/23.
 */

public class LoginPresenter extends BaseFragmentPresenter<ILoginView> {

  private final ILocalRepository localRepository;
  private final IUserRepository userRepository;

  @Inject
  public LoginPresenter(Context mContext, IUserRepository userRepository, ILocalRepository localRepository) {
    super(mContext);
    this.userRepository = userRepository;
    this.localRepository = localRepository;
  }

  public void login(String username,String password){
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
    localRepository.login("123456");
    view.LoginSuccess();

  }
}

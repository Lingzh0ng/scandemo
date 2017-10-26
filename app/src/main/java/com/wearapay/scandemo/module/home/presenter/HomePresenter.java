package com.wearapay.scandemo.module.home.presenter;

import android.content.Context;
import com.wearapay.domain.repository.ILocalRepository;
import com.wearapay.domain.repository.IUserRepository;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.module.home.view.IHomeView;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/23.
 */

public class HomePresenter extends BaseFragmentPresenter<IHomeView> {

  private final ILocalRepository localRepository;
  private final IUserRepository userRepository;

  @Inject
  public HomePresenter(Context mContext, IUserRepository userRepository, ILocalRepository localRepository) {
    super(mContext);
    this.userRepository = userRepository;
    this.localRepository = localRepository;
  }

  public boolean getLoginStatus(){
    return localRepository.getLoginStatus();
  }
}

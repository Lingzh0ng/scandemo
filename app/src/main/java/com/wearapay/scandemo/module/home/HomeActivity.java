package com.wearapay.scandemo.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.wearapay.base.mvp.BasePresenter;
import com.wearapay.scandemo.App;
import com.wearapay.scandemo.AppConstant;
import com.wearapay.scandemo.BaseMvpActivity;
import com.wearapay.scandemo.R;
import com.wearapay.scandemo.utils.ActivityUtils;
import com.wearapay.scandemo.utils.UIUtil;

/**
 * Created by lyz54 on 2017/10/14.
 */

public class HomeActivity extends BaseMvpActivity {
  @Override protected CharSequence getActionBarTitle() {
    return null;
  }

  @Override protected BasePresenter[] initPresenters() {
    return new BasePresenter[0];
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    showFragment();
  }

  private void showFragment() {
    Fragment fragment = new HomeFragment();
    Bundle bundle = ActivityUtils.getBundle("扫码解锁", false, true);
    getIntent().putExtras(bundle);
    fragment.setArguments(getIntent().getExtras());
    FragmentManager supportFragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.contentFrame, fragment, "home");
    fragmentTransaction.commit();
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_home;
  }

  long touchTime = 0;

  @Override public void onBackPressed() {
    long currentTime = System.currentTimeMillis();
    if ((currentTime - touchTime) >= AppConstant.EXIT_BACK_PRESSED_INTERVAL) {
      UIUtil.showToast(this, R.string.message_double_confirm_logout);
      touchTime = currentTime;
    } else {
      App.app.exitApp();
    }
  }
}

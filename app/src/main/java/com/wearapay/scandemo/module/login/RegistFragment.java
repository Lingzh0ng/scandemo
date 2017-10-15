package com.wearapay.scandemo.module.login;

import com.wearapay.scandemo.BaseMvpFragment;
import com.wearapay.scandemo.R;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;

/**
 * Created by lyz on 2017/10/13.
 */

public class RegistFragment extends BaseMvpFragment {
  @Override public void onCleanBeforeDetach() {

  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_regrist;
  }

  @Override protected BaseFragmentPresenter[] initPresenters() {
    return new BaseFragmentPresenter[0];
  }
}

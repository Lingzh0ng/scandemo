package com.wearapay.scandemo;

import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;

/**
 * Created by lyz on 2017/10/13.
 */

public class TestFragment extends BaseMvpFragment {
  @Override public void onCleanBeforeDetach() {

  }

  @Override protected int getLayoutId() {
    return R.layout.test_fragment;
  }

  @Override protected BaseFragmentPresenter[] initPresenters() {
    return new BaseFragmentPresenter[0];
  }
}

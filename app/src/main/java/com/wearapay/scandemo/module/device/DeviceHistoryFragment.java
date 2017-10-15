package com.wearapay.scandemo.module.device;

import com.wearapay.scandemo.BaseMvpFragment;
import com.wearapay.scandemo.R;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;

/**
 * Created by lyz on 2017/10/13.
 */

public class DeviceHistoryFragment extends BaseMvpFragment {
  @Override public void onCleanBeforeDetach() {

  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_device_history;
  }

  @Override protected BaseFragmentPresenter[] initPresenters() {
    return new BaseFragmentPresenter[0];
  }
}

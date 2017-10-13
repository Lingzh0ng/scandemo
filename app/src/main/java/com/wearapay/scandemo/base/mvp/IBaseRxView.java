package com.wearapay.scandemo.base.mvp;

import android.content.Context;
import com.wearapay.base.mvp.IBaseView;
import com.wearapay.scandemo.base.BaseActivity;
import com.wearapay.scandemo.base.BaseFragment;

/**
 * Created by lyz on 2017/10/13.
 */

public interface IBaseRxView extends IBaseView {

  BaseFragment getUseFragment();

  BaseActivity getUseActivity();

  Context getUseContext();
}

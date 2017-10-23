package com.wearapay.scandemo.module.login.view;

import com.wearapay.scandemo.base.mvp.IBaseRxView;

/**
 * Created by lyz on 2017/10/23.
 */

public interface ILoginView extends IBaseRxView{

  void LoginSuccess();

  void LoginFailure();
}

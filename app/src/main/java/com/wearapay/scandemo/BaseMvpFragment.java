package com.wearapay.scandemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.wearapay.base.utils.L;
import com.wearapay.domain.user.IUserMgmt;
import com.wearapay.scandemo.base.BaseActivity;
import com.wearapay.scandemo.base.BaseFragment;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.base.mvp.IBaseRxView;
import com.wearapay.scandemo.module.home.HomeActivity;
import com.wearapay.scandemo.utils.ActivityUtils;
import com.wearapay.scandemo.utils.ToastUtils;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/13.
 */

public abstract class BaseMvpFragment extends BaseFragment implements IBaseRxView {
  private SDProgressDialog progressDialog;
  protected BaseFragmentPresenter[] presenters;

  protected abstract BaseFragmentPresenter[] initPresenters();

  @Inject IUserMgmt userMgmt;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = super.onCreateView(inflater, container, savedInstanceState);
    ButterKnife.bind(this, view);
    App.app.getComponent().inject(this);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    presenters = initPresenters();

    if (presenters != null) {
      for (BaseFragmentPresenter presenter : presenters) {
        if (presenter != null) {
          presenter.setView(this);
        }
      }
    } else {
      L.e(this, "presenters are null ！");
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
  }

  @Override public void onDetach() {
    super.onDetach();
    if (presenters != null && presenters.length > 0) {
      for (int i = 0; i < presenters.length; i++) {
        presenters[i].onDestroy();
      }
    }
  }

  @Override public void showMessage(String message) {
    ToastUtils.showShort(message);
  }

  @Override public void showMessage(int messageId) {
    showMessage(getString(messageId));
  }

  @Override public void showDiglog(String message) {

  }

  @Override public void showProgress(String message) {
    if (progressDialog == null) {
      progressDialog = new SDProgressDialog(getActivity(), message);
    }
    progressDialog.show();
  }

  @Override public void showProgress(int messageResourceId) {
    if (progressDialog == null) {
      progressDialog = new SDProgressDialog(getActivity(), getString(messageResourceId));
    }
    progressDialog.show();
  }

  @Override public void hideProgress() {
    if (progressDialog != null) {
      progressDialog.dismiss();
    }
  }

  @Override public void processFail(Throwable t, String errorMessage) {

  }

  @Override public void navToHomePage() {
    Bundle bundle = ActivityUtils.getBundle(R.color.test_color, "扫码开锁", false, true);
    Intent intent = new Intent(getActivity(), HomeActivity.class);
    intent.putExtras(bundle);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    getActivity().startActivity(intent);
  }

  @Override public void navToLoginPage() {
    userMgmt.logoutLocal();
    Bundle bundle = ActivityUtils.getBundle(R.color.test_color, "登录", false, true);
    ActivityUtils.startFragment(getActivity(), AppConstant.FragmentType.Login, bundle);
  }

  @Override public BaseFragment getUseFragment() {
    return this;
  }

  @Override public BaseActivity getUseActivity() {
    return (BaseActivity) getActivity();
  }

  @Override public Context getUseContext() {
    return getContext();
  }
}

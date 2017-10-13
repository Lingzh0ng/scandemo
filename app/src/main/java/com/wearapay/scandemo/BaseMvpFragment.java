package com.wearapay.scandemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wearapay.base.utils.L;
import com.wearapay.scandemo.base.BaseActivity;
import com.wearapay.scandemo.base.BaseFragment;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.base.mvp.IBaseRxView;

/**
 * Created by lyz on 2017/10/13.
 */

public abstract class BaseMvpFragment extends BaseFragment implements IBaseRxView {
  private SDProgressDialog progressDialog;
  protected BaseFragmentPresenter[] presenters;

  protected abstract BaseFragmentPresenter[] initPresenters();

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
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

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {


    return super.onCreateView(inflater, container, savedInstanceState);
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

  }

  @Override public void showMessage(int messageId) {

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

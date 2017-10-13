package com.wearapay.scandemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.wearapay.base.mvp.BasePresenter;
import com.wearapay.scandemo.base.BaseActivity;
import com.wearapay.scandemo.base.BaseFragment;
import com.wearapay.scandemo.base.mvp.IBaseRxView;
import com.wearapay.scandemo.utils.ToastUtils;

/**
 * Created by lyz54 on 2017/6/28.
 */

public abstract class BaseMvpActivity extends BaseActivity implements IBaseRxView {


  protected BasePresenter[] presenters;
  protected ImageView ivBack;
  protected ImageView ivMenu;
  protected TextView tvTitle;
  private View myActBar;
  private SDProgressDialog progressDialog;
  protected boolean canBack = true;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (presenters == null) {
      presenters = initPresenters();
      if (presenters != null && presenters.length > 0) {
        for (int i = 0; i < presenters.length; i++) {
          System.out.println("presenters");
          presenters[i].setView(this);
        }
      }
    }

    myActBar = findViewById(R.id.my_action_bar);
    if (myActBar != null) {
      ivBack = (ImageView) myActBar.findViewById(R.id.ivBack);
      ivMenu = (ImageView) myActBar.findViewById(R.id.ivMenu);
      tvTitle = (TextView) myActBar.findViewById(R.id.tvTitle);
      tvTitle.setText(getActionBarTitle());
      ivBack.setVisibility(View.VISIBLE);
      ivBack.setOnClickListener(innerOnClickListener);
      ivMenu.setOnClickListener(innerOnClickListener);
    }
  }

  private View.OnClickListener innerOnClickListener = new View.OnClickListener() {
    @Override public void onClick(View view) {
      if (view == ivBack) {
        if (canBack) onBackPressed(true);
      } else if (view == ivMenu) {
        OnClickMenu();
      }
    }
  };

  /**
   * 菜单点击事件
   */
  protected void OnClickMenu() {
  }

  protected abstract CharSequence getActionBarTitle();

  @Override protected void onStart() {
    super.onStart();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (presenters != null && presenters.length > 0) {
      for (int i = 0; i < presenters.length; i++) {
        presenters[i].onDestroy();
      }
    }
  }

  protected abstract BasePresenter[] initPresenters();

  @Override public void showMessage(String message) {
    ToastUtils.showShort(message);
  }

  @Override public void showMessage(int messageId) {
    ToastUtils.showShort(messageId);
  }

  @Override public void showDiglog(String message) {

  }

  @Override public void showProgress(String message) {
    if (progressDialog == null) {
      progressDialog = new SDProgressDialog(this, message);
    }
    progressDialog.show();
  }

  @Override public void showProgress(int messageResourceId) {
    if (progressDialog == null) {
      progressDialog = new SDProgressDialog(this, getString(messageResourceId));
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
    return null;
  }

  @Override public BaseActivity getUseActivity() {
    return this;
  }

  @Override public Context getUseContext() {
    return this;
  }
}

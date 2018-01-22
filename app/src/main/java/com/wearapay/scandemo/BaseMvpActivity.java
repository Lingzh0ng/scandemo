package com.wearapay.scandemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wearapay.base.mvp.BasePresenter;
import com.wearapay.scandemo.base.BaseActivity;
import com.wearapay.scandemo.base.BaseFragment;
import com.wearapay.scandemo.base.mvp.IBaseRxView;
import com.wearapay.scandemo.module.home.HomeActivity;
import com.wearapay.scandemo.utils.ActivityUtils;
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
    AppBarLayout appbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        color = getIntent().getIntExtra(AppConstant.STATUS_COLOR, color);
//    color = getResources().getColor(colorId);
        canBack = getIntent().getBooleanExtra(AppConstant.CAN_BACK, true);
        String title = getIntent().getStringExtra(AppConstant.TITLE);
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
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        if (appbar != null) {
            appbar.setBackgroundResource(color);
        }
        if (!TextUtils.isEmpty(title) && tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    private View.OnClickListener innerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == ivBack) {
                if (canBack) {onBackPressed(true);}
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

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenters != null && presenters.length > 0) {
            for (int i = 0; i < presenters.length; i++) {
                presenters[i].onDestroy();
            }
        }
    }

    protected abstract BasePresenter[] initPresenters();

    @Override
    public void showMessage(String message) {
        ToastUtils.showLong(message);
    }

    @Override
    public void showMessage(int messageId) {
        ToastUtils.showLong(messageId);
    }

    @Override
    public void showDiglog(String message) {

    }

    @Override
    public void showProgress(String message) {
        if (progressDialog == null) {
            progressDialog = new SDProgressDialog(this, message);
        }
        progressDialog.show();
    }

    @Override
    public void showProgress(int messageResourceId) {
        if (progressDialog == null) {
            progressDialog = new SDProgressDialog(this, getString(messageResourceId));
        }
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void processFail(Throwable t, String errorMessage) {

    }

    @Override
    public void navToHomePage() {
        Bundle bundle = ActivityUtils.getBundle(R.color.test_color, "扫码开锁", false, true);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtras(bundle);
        intent.setFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override public void navToLoginPage() {
        Bundle bundle = ActivityUtils.getBundle(R.color.test_color, "登录", false, true);
        ActivityUtils.startFragment(this, AppConstant.FragmentType.Login, bundle);
    }

    @Override
    public BaseFragment getUseFragment() {
        return null;
    }

    @Override
    public BaseActivity getUseActivity() {
        return this;
    }

    @Override
    public Context getUseContext() {
        return this;
    }
}

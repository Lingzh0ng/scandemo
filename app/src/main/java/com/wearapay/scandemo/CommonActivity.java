package com.wearapay.scandemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.FrameLayout;

import com.wearapay.base.mvp.BasePresenter;
import com.wearapay.scandemo.base.BaseFragment;
import com.wearapay.scandemo.utils.ActivityUtils;

import java.io.Serializable;

import butterknife.BindView;

/**
 * Created by lyz on 2017/7/27.
 */
public class CommonActivity extends BaseMvpActivity {
    @BindView(R.id.contentFrame)
    FrameLayout contentFrame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common;
    }

    @Override
    protected CharSequence getActionBarTitle() {
        return null;
    }

    @Override
    protected BasePresenter[] initPresenters() {
        return new BasePresenter[0];
    }

    private void showFragment() {
        Serializable serializable = getIntent().getSerializableExtra(AppConstant.FRAGMENT_TYPE);
        if (serializable == null) {
            Log.e("CommonActivity", AppConstant.FRAGMENT_TYPE + " is null ");
            finish();
            return;
        }

        try {
            AppConstant.FragmentType fragmentType = (AppConstant.FragmentType) serializable;
            Class<BaseFragment> fragmentClass = fragmentType.getClazz();
            Fragment fragment = fragmentClass.newInstance();
            Bundle bundle = getIntent().getExtras();
            fragment.setArguments(bundle);
            ActivityUtils.replaceFragment(getSupportFragmentManager(), fragment, R.id.contentFrame);
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    @Override
    public void finish() {
        if (isTaskRoot() && !App.app.isExit()) {
           navToHomePage();
            Log.e("CommonActivity", "start HomeActivity");
        }
        super.finish();
    }
}

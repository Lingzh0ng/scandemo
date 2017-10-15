package com.wearapay.scandemo.module.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wearapay.scandemo.BaseMvpFragment;
import com.wearapay.scandemo.R;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.weight.CustomEditCell;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lyz on 2017/10/13.
 */

public class ChangePswFragment extends BaseMvpFragment {
    @BindView(R.id.cellOldPwd)
    CustomEditCell cellOldPwd;
    @BindView(R.id.cellPassword)
    CustomEditCell cellPassword;
    @BindView(R.id.cellSurePassword)
    CustomEditCell cellSurePassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    Unbinder unbinder;

    @Override
    public void onCleanBeforeDetach() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_change_psw;
    }

    @Override
    protected BaseFragmentPresenter[] initPresenters() {
        return new BaseFragmentPresenter[0];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
    }
}

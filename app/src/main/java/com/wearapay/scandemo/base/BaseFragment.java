package com.wearapay.scandemo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trello.rxlifecycle2.components.support.RxFragment;
import com.wearapay.scandemo.AppConstant;
import com.wearapay.scandemo.R;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by lyz54 on 2017/6/27.
 */

public abstract class BaseFragment extends RxFragment {
    private boolean isNeedAddOnBackPressedListener = true;

    protected AtomicBoolean isViewActive;
    protected ImageView ivBack;
    protected ImageView ivMenu;
    protected TextView tvTitle;
    private View myActBar;
    protected boolean canBack = true;
    protected boolean showMenu = false;
    //    protected int color;
    protected String title;
    protected AppBarLayout appbar;
    private int colorId;

    public abstract void onCleanBeforeDetach();

    protected abstract int getLayoutId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        canBack = getActivity().getIntent().getBooleanExtra(AppConstant.CAN_BACK, true);
        showMenu = getActivity().getIntent().getBooleanExtra(AppConstant.SHOW_MENU, false);

        colorId = getActivity().getIntent().getIntExtra(AppConstant.STATUS_COLOR, R.color.status_bar_bg);
        title = getActivity().getIntent().getStringExtra(AppConstant.TITLE);
//        color = getResources().getColor(colorId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    protected void setIsNeedAddOnBackPressedListener(boolean need) {
        isNeedAddOnBackPressedListener = need;
    }

    protected boolean onBackPressed(boolean isFromKey) {
        if (!isFromKey) {
            ((BaseActivity) getActivity()).onBackPressed(isFromKey);
            return true;
        }
        return false;
    }

    private BaseActivity.OnBackPressedListener mOnBackPressedListener =
            new BaseActivity.OnBackPressedListener() {

                @Override
                public boolean onBackPressed() {
                    if (!BaseFragment.this.isHidden()) {
                        return BaseFragment.this.onBackPressed(true);
                    }

                    return false;
                }
            };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        isViewActive = new AtomicBoolean(true);
        super.onViewCreated(view, savedInstanceState);

        myActBar = view.findViewById(R.id.my_action_bar);
        appbar = (AppBarLayout) view.findViewById(R.id.appbar);
        if (myActBar != null) {
            ivBack = (ImageView) myActBar.findViewById(R.id.ivBack);
            ivMenu = (ImageView) myActBar.findViewById(R.id.ivMenu);
            tvTitle = (TextView) myActBar.findViewById(R.id.tvTitle);
            tvTitle.setText(getActionBarTitle());
            ivBack.setVisibility(View.VISIBLE);
            ivMenu.setVisibility(showMenu ? View.VISIBLE : View.GONE);
            ivBack.setOnClickListener(innerOnClickListener);
            ivMenu.setOnClickListener(innerOnClickListener);
        }
        if (appbar != null) {
            appbar.setBackgroundResource(colorId);
        }
    }

    private View.OnClickListener innerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == ivBack) {
                if (canBack) onBackPressed(false);
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

    protected CharSequence getActionBarTitle() {
        return title;
    }

    protected CharSequence setTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
        return this.title = title;
    }

    @Override
    public void onDestroyView() {
        isViewActive.set(false);
        super.onDestroyView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (isNeedAddOnBackPressedListener) {
            ((BaseActivity) getActivity()).addOnBackPressedListener(mOnBackPressedListener);
        }
    }

    @Override
    public void onDetach() {
        ((BaseActivity) getActivity()).removeOnBackPressedListener(mOnBackPressedListener);

        onCleanBeforeDetach();
        super.onDetach();
    }
}

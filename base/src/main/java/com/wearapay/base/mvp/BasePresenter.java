package com.wearapay.base.mvp;

import android.content.Context;

/**
 * Created by lyz54 on 2017/6/27.
 */

public class BasePresenter<T> {
    protected Context mContext;
    protected T view;

    public BasePresenter(Context mContext) {
        this.mContext = mContext;
    }

    public void setView(T view) {
        this.view = view;
    }

    public void onDestroy() {
        mContext = null;
        view = null;
    }
}

package com.wearapay.scandemo.base.mvp;

import android.content.Context;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.wearapay.base.mvp.BasePresenter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lyz54 on 2017/6/27.
 */

public abstract class BaseRxPresenter<T> extends BasePresenter<T> {

  public BaseRxPresenter(Context mContext) {
    super(mContext);
  }

  public abstract <T> LifecycleTransformer<T> bindToLifecycle();

  public abstract <T> LifecycleTransformer<T> bindUntilLifecycle(FragmentEvent event);

  protected <T> Observable<T> wrap(Observable<T> origin) {
    return (Observable<T>) origin.compose(bindToLifecycle())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  protected <T> Observable<T> wrapBindUntil(Observable<T> origin, FragmentEvent event) {
    return (Observable<T>) origin.compose(bindUntilLifecycle(event))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}

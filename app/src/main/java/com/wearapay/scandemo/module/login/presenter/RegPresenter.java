package com.wearapay.scandemo.module.login.presenter;

import android.content.Context;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.wearapay.data.bean.VerifyCodeRegister;
import com.wearapay.domain.user.IUserMgmt;
import com.wearapay.net.BaseObserver;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.module.login.view.IRegView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/23.
 */

public class RegPresenter extends BaseFragmentPresenter<IRegView> {

  private final IUserMgmt userMgmt;

  @Inject public RegPresenter(Context mContext, IUserMgmt userMgmt) {
    super(mContext);
    this.userMgmt = userMgmt;
  }

  public void reg(String username, String password) {
    view.showProgress("");
    wrap(userMgmt.register(username, password, password, username)).subscribe(
        new BaseObserver<Boolean>(view) {

          @Override public void onNext(Boolean aBoolean) {
            view.RegSuccess();
          }

          @Override protected void handlerError(Throwable e) {
            view.RegFailure();
          }
        });
  }


  public void reg(String username, String password,String ver) {
    view.showProgress("");
    wrap(userMgmt.verifyCodeRegister2(username, password, password, username,ver)).subscribe(
        new BaseObserver<Boolean>(view) {

          @Override public void onNext(Boolean aBoolean) {
            view.RegSuccess();
          }

          @Override protected void handlerError(Throwable e) {
            view.RegFailure();
          }
        });
  }

  public void reg(VerifyCodeRegister verifyCodeRegister) {
    view.showProgress("");
    if (disposable != null && disposable.isDisposed()) {
      disposable.dispose();
      disposable = null;
    }
    wrap(userMgmt.verifyCodeRegister(verifyCodeRegister)).subscribe(
        new BaseObserver<Boolean>(view) {

          @Override public void onNext(Boolean aBoolean) {
            view.RegSuccess();
          }

          @Override protected void handlerError(Throwable e) {
            view.RegFailure();
          }
        });
  }

  public void requestRegisterCode(String name) {
    view.showProgress("");
    wrap(userMgmt.requestRegisterCode(name)).subscribe(
        new BaseObserver<Boolean>(view) {

          @Override public void onNext(Boolean aBoolean) {
            view.VerSuccess();
          }

          @Override protected void handlerError(Throwable e) {
            view.VerFailure();
          }
        });
  }

  private Disposable disposable;

  public void VerCodeTime(){
    Observable<Long> intervalObservable =
        wrapBindUntil(Observable.interval(1, TimeUnit.SECONDS, Schedulers.computation()),
            FragmentEvent.DESTROY);

    intervalObservable.subscribe(new Observer<Long>() {

      @Override public void onSubscribe(Disposable d) {
        disposable = d;
      }

      @Override public void onNext(Long aLong) {
        System.out.println("query number : " + aLong);
       view.UpdateTime(aLong);
       if (aLong == 60) {
         disposable.isDisposed();
         disposable = null;
       }
      }

      @Override public void onError(Throwable e) {

      }

      @Override public void onComplete() {

      }
    });
  }
}

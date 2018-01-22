package com.wearapay.net;

import android.content.Context;
import com.wearapay.base.mvp.IBaseView;
import com.wearapay.net.Exception.NotLoginException;
import com.wearapay.net.Exception.PPCodedException;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import java.net.SocketTimeoutException;
import retrofit2.HttpException;

/**
 * Created by lyz54 on 2017/6/28.
 */

public abstract class BaseObserver<T> implements Observer<T> {
  private Context context;
  private IBaseView view;

  public BaseObserver(IBaseView baseView) {
   this(baseView,true);
  }

  public BaseObserver(IBaseView baseView,boolean show) {
    this.view = baseView;
    if (show) {
      view.showProgress("");
    }
  }

  @Override public void onSubscribe(@NonNull Disposable d) {

  }

  @Override public void onError(@NonNull Throwable e) {
    if (view != null && isHideProgress()) {
      view.hideProgress();
    }
    e.printStackTrace();
    if (e instanceof NotLoginException) {
      //context.startActivity(new Intent(context, LoginActivity.class));
      view.showMessage("请登录");
      view.navToLoginPage();
      return;
    } else if (e instanceof java.net.ConnectException
        || e instanceof java.net.SocketTimeoutException) {
      view.showMessage("网络错误");
    } else if (e instanceof PPCodedException) {
      PPCodedException codedException = (PPCodedException) e;
      view.showMessage(codedException.getMessage());
    } else if (e instanceof HttpException || e instanceof SocketTimeoutException) {
      view.showMessage("网络错误");
    } else {
      System.out.println("sever error");
      handlerError(e);
    }
  }

  protected void handlerError(Throwable e) {

  }

  @Override public void onComplete() {
    if (view != null && isHideProgress()) {
      view.hideProgress();
    }
  }

  public boolean isHideProgress() {
    return true;
  }
}

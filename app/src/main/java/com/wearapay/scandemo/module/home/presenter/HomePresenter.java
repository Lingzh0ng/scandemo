package com.wearapay.scandemo.module.home.presenter;

import android.content.Context;
import android.text.TextUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.wearapay.data.bean.DeviceStatus;
import com.wearapay.domain.devices.IDeviceMgmt;
import com.wearapay.domain.user.IUserMgmt;
import com.wearapay.net.BaseObserver;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.module.home.view.IHomeView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/23.
 */

public class HomePresenter extends BaseFragmentPresenter<IHomeView> {

  private final IUserMgmt userMgmt;
  private final IDeviceMgmt deviceMgmt;
  private static final long INTERVAL_TIME = 4L;

  private String reqId;
  private Observable<Long> intervalObservable;
  private Disposable disposable;

  @Inject public HomePresenter(Context mContext, IUserMgmt userMgmt, IDeviceMgmt deviceMgmt) {
    super(mContext);
    this.userMgmt = userMgmt;
    this.deviceMgmt = deviceMgmt;
  }

  public boolean getLoginStatus() {
    return userMgmt.getLoginStatus();
  }

  public void unDevice(String deviceNo) {
    wrap(deviceMgmt.unlock(deviceNo)).subscribe(new BaseObserver<String>(view) {
      @Override public void onNext(String s) {
        reqId = s;
        if (!TextUtils.isEmpty(reqId)) {
          initQuery();
        } else {
          view.showMessage("解锁失败");
        }
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
      }
    });
  }

  private void initQuery() {
    view.showWaitProgress();
    intervalObservable =
        wrapBindUntil(Observable.interval(INTERVAL_TIME, TimeUnit.SECONDS, Schedulers.trampoline()),
            FragmentEvent.DESTROY);

    intervalObservable.subscribe(new Observer<Long>() {
      @Override public void onSubscribe(Disposable d) {
        disposable = d;
      }

      @Override public void onNext(Long aLong) {
        System.out.println("query number : " + aLong);
        query(aLong);
      }

      @Override public void onError(Throwable e) {
        disposable.dispose();
        initQuery();
      }

      @Override public void onComplete() {

      }
    });
  }

  private void query(long number) {
    if (TextUtils.isEmpty(reqId)) {
      System.out.println("reqId is Empty");
      return;
    }
    wrapBindUntil(deviceMgmt.queryRequest(reqId), FragmentEvent.DESTROY).subscribe(deviceStatus -> {
      if (deviceStatus != null && deviceStatus != DeviceStatus.SEND_WAIT_RESP) {
        view.dismissWaitProgress();
        if (disposable != null) {
          disposable.dispose();
          disposable = null;
        }
        if (intervalObservable != null) {
          intervalObservable = null;
        }

        switch (deviceStatus) {
          case NO_DEVICE:
            view.showMessage("此设备不存在");
            view.unDeviceFail();
            break;
          case SEND_ERROR:
            view.showMessage("开启设备失败");
            view.unDeviceFail();
            break;
          case RESP_OPEN:
            view.showMessage("开启设备成功");
            view.unDeviceSuccess();
            break;
          case RESP_ERROR:
            view.showMessage("开启设备失败");
            view.unDeviceFail();
            break;
          case DEVICE_OFFLINE:
            view.showMessage("设备不在线");
            view.unDeviceFail();
            break;
          default:
            break;
        }
      }
    });
  }
}

package com.wearapay.scandemo.module.home.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.wearapay.base.utils.L;
import com.wearapay.domain.devices.IDeviceMgmt;
import com.wearapay.domain.user.IUserMgmt;
import com.wearapay.net.BaseObserver;
import com.wearapay.net.Exception.ApiException;
import com.wearapay.scandemo.App;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.module.home.view.IHomeView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/23.
 */

public class HomePresenter extends BaseFragmentPresenter<IHomeView> {

  private final IUserMgmt userMgmt;
  private final IDeviceMgmt deviceMgmt;
  private static final long INTERVAL_TIME = 5L;
  private static final long TIME_OUT = 60 * 1000L;

  private String reqId;
  private String deviceNo;
  private Observable<Long> intervalObservable;
  private Disposable disposable;
  private LocationManager locationManager;
  private LocationListener networkLocationListener;
  private static Handler handler = new Handler(Looper.getMainLooper());

  @Inject public HomePresenter(Context mContext, IUserMgmt userMgmt, IDeviceMgmt deviceMgmt) {
    super(mContext);
    this.userMgmt = userMgmt;
    this.deviceMgmt = deviceMgmt;
    initLocationService();
  }

  //public void setHomeActivity(Activity activity){
  //
  //}

  public void initDeviceInfo() {
    String deviceNo = getDeviceNo();
    if (!TextUtils.isEmpty(deviceNo)) {
      checkDeviceAccountStatus(deviceNo);
    }
  }

  public void checkDeviceAccountStatus(String deviceNo) {//返回值：1 - 锁定， 2 - 未锁 例子:
    wrapBindUntil(deviceMgmt.accountStatus(deviceNo), FragmentEvent.DESTROY).subscribe(
        new BaseObserver<Integer>(view, false) {
          @Override public void onNext(Integer s) {
            //reqId = s;
            if (s == 1) {
              //initQuery();
              //view.showMessage("解锁成功");
              System.out.println("checkDeviceAccountStatus 设备已经结算了");
            } else if (s == 2) {
              System.out.println("checkDeviceAccountStatus 设备还在使用");
              initQuery(deviceNo);
            }else if (s == 0) {
              System.out.println("checkDeviceAccountStatus 设备查询状态失败");
            }
          }

          @Override public boolean isHideProgress() {
            return false;
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            Activity homeActivity = App.app.getHomeActivity();
            if (!homeActivity.isDestroyed()
                && TextUtils.isEmpty(HomePresenter.this.deviceNo)
                && deviceNo.equals(getDeviceNo()) && getLoginStatus()) {
              checkDeviceAccountStatus(deviceNo);
            }
          }
        });
  }

  public String getDeviceNo() {
    return userMgmt.getDeviceNo();
  }

  private void initLocationService() {
    locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

    networkLocationListener = new LocationListener() {
      @Override public void onStatusChanged(String provider, int status, Bundle arg2) {
        switch (status) {
          //GPS状态为可见时
          case LocationProvider.AVAILABLE:
            L.d(this, "ccc 当前" + provider + "状态为可见状态");
            break;
          //GPS状态为服务区外时
          case LocationProvider.OUT_OF_SERVICE:
            L.d(this, "ccc 当前" + provider + "状态为服务区外状态");
            break;
          //GPS状态为暂停服务时
          case LocationProvider.TEMPORARILY_UNAVAILABLE:
            L.d(this, "ccc 当前" + provider + "状态为暂停服务状态");
            break;
          default:
            break;
        }
      }

      @Override public void onProviderEnabled(String provider) {
        L.d(this, "ccc onProviderEnabled provider" + provider);
      }

      @Override public void onProviderDisabled(String provider) {
        L.d(this, "ccc onProviderDisabled provider" + provider);
      }

      @Override public void onLocationChanged(Location location) {
        L.d(this, "ccc network success, 位置信息:" + location);
        removeLocationListener();
        unDevice(deviceNo, location);
      }
    };
  }

  private void getLocation() {
    List<String> providers = locationManager.getProviders(true);
    removeLocationListener();
    L.d(this, "ccc 获取所有可用的位置提供器 " + providers.toString());
    if (providers.contains(LocationManager.NETWORK_PROVIDER) && locationManager.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER)) {
      L.d(this, "ccc " + LocationManager.NETWORK_PROVIDER + " start");
      if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
          != PackageManager.PERMISSION_GRANTED
          && ActivityCompat.checkSelfPermission(mContext,
          Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        L.d(this, "ccc  获取位置信息失败");
        view.showMessage("获取位置信息失败,请去权限列表开启位置权限");
        view.dismissWaitProgress();
        view.unDeviceFail();
        return;
      }
      locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
          networkLocationListener);
    }
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        L.d(this, "ccc  定位结束");
        view.showMessage("获取位置信息失败,请重试");
        view.dismissWaitProgress();
        view.unDeviceFail();
        removeLocationListener();
        //sendLocation(mLocation);
      }
    }, TIME_OUT);
  }

  private void removeLocationListener() {
    if (locationManager != null) {
      //移除监听器
      locationManager.removeUpdates(networkLocationListener);
    }
    handler.removeCallbacksAndMessages(null);
  }

  public boolean getLoginStatus() {
    return userMgmt.getLoginStatus();
  }

  public void unDevice(String deviceNo) {
    this.deviceNo = deviceNo;
    getLocation();
  }

  public void checkDevice(String deviceNo) {
    view.showWaitProgress();
    wrap(deviceMgmt.getDeviceStatus(deviceNo)).subscribe(new BaseObserver<Integer>(view, false) {
      @Override public void onNext(Integer s) {
        //reqId = s;
        if (s == 1) {

          unDevice(deviceNo);
        } else if(s == 2){
          view.dismissWaitProgress();
          view.showMessage("设备正在使用中，请稍后重试");
          view.unDeviceFail();
        } else {
          view.dismissWaitProgress();
          view.showMessage("系统忙，请重试");
          view.unDeviceFail();
        }
      }

      @Override public boolean isHideProgress() {
        return false;
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.hideProgress();
        view.dismissWaitProgress();
        view.unDeviceFail();
      }
    });
  }

  public void unDevice(String deviceNo, Location location) {
    wrap(deviceMgmt.getFailState(deviceNo))/*.flatMap(integer -> {
      if (integer == 1) {
        return wrap(deviceMgmt.getFailState(deviceNo));
      } else {
        return Observable.error(new ApiException("设备正在使用中，请稍后重试"));
      }
    })*/.flatMap(integer -> {
      if (integer == 1) {
        return wrap(deviceMgmt.unlock(deviceNo, location.getLatitude(), location.getLongitude()));
      } else if (integer == 2){
        return Observable.error(new ApiException("设备故障，解锁失败，请稍后重试"));
      }else {
        return Observable.error(new ApiException("系统忙，请重试"));
      }
    }).subscribe(
        new BaseObserver<Boolean>(view, false) {
          @Override public void onNext(Boolean s) {
            view.dismissWaitProgress();
            //reqId = s;
            if (s) {
              //initQuery();
              userMgmt.saveDeviceNo(deviceNo);
              view.showMessage("解锁成功");
              view.unDeviceSuccess();
              initQuery(deviceNo);
            } else {
              view.showMessage("解锁失败");
              view.unDeviceFail();
            }
          }

          @Override public boolean isHideProgress() {
            return false;
          }

          @Override protected void handlerError(Throwable e) {
            super.handlerError(e);
            if (e instanceof ApiException) {
              view.showMessage(e.getMessage());
            }
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            view.hideProgress();
            view.dismissWaitProgress();
            view.unDeviceFail();
          }
        });

    //wrap(deviceMgmt.unlock(deviceNo, location.getLatitude(), location.getLongitude()))
  }

  private void initQuery(String deviceNo) {
    try {
    if (intervalObservable != null && disposable != null && !disposable.isDisposed()) {
      disposable.dispose();
      disposable = null;

      intervalObservable = null;
    }
    intervalObservable =
        wrapBindUntil(Observable.interval(INTERVAL_TIME, TimeUnit.SECONDS, Schedulers.computation()),
            FragmentEvent.DESTROY);
      Consumer subscriber = (Consumer<Long>) aLong -> {
        System.out.println("query number : " + aLong);
        if (!App.app.getHomeActivity().isDestroyed()
            && !TextUtils.isEmpty(deviceNo)
            && getLoginStatus()) {
          query(deviceNo);
        }else {
          dospose();
        }
      };
      disposable = intervalObservable.subscribe(subscriber, throwable -> {
        dospose();
        initQuery(deviceNo);
      });

      //intervalObservable.subscribe(new Observer<Long>() {
      //@Override public void onSubscribe(Disposable d) {
      //  HomePresenter.this.disposable = d;
      //}
      //
      //@Override public void onNext(Long aLong) {
      //  System.out.println("query number : " + aLong);
      //  if (!App.app.getHomeActivity().isDestroyed()
      //      && !TextUtils.isEmpty(deviceNo)
      //      && getLoginStatus()) {
      //    query(deviceNo);
      //  }else {
      //    dospose();
      //  }
      //}
      //
      //@Override public void onError(Throwable e) {
      //  dospose();
      //  initQuery(deviceNo);
      //}
      //
      //@Override public void onComplete() {
      //
      //}
    //});
    } catch (Exception e) {
      e.printStackTrace();
      view.dismissWaitProgress();
    }
  }

  private void query(String number) {
    if (TextUtils.isEmpty(number)) {
      System.out.println("number is Empty");
      return;
    }
    wrapBindUntil(deviceMgmt.accountStatus(number), FragmentEvent.DESTROY).subscribe(deviceStatus -> {
      if ( deviceStatus == 1 ) {
        System.out.println("设备已结算");
        userMgmt.saveDeviceNo("");

        dospose();
        if (intervalObservable != null) {
          intervalObservable = null;
          view.showMessage("设备SN:"+number+" 已结算");
          view.unDeviceFail();
          deviceNo = null;
        }

        //switch (deviceStatus) {
        //  case NO_DEVICE:
        //    view.showMessage("此设备不存在");
        //    view.unDeviceFail();
        //    break;
        //  case SEND_ERROR:
        //    view.showMessage("开启设备失败");
        //    view.unDeviceFail();
        //    break;
        //  case RESP_OPEN:
        //    view.showMessage("开启设备成功");
        //    view.unDeviceSuccess();
        //    break;
        //  case RESP_ERROR:
        //    view.showMessage("开启设备失败");
        //    view.unDeviceFail();
        //    break;
        //  case DEVICE_OFFLINE:
        //    view.showMessage("设备不在线,wait...");
        //    //view.unDeviceFail();
        //    break;
        //  default:
        //    break;
        //}
      } else if (deviceStatus == 2) {
        System.out.println("设备正在使用..." + deviceStatus);
      }else {
        System.out.println("设备状态.." + deviceStatus);
      }
    });
  }

  private void dospose() {
    if (disposable != null && !disposable.isDisposed()) {
      disposable.dispose();
      disposable = null;
    }
  }
}

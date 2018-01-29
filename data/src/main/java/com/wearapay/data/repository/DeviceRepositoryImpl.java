package com.wearapay.data.repository;

import android.content.Context;
import com.wearapay.data.retorfit.RestDeviceService;
import com.wearapay.net.BaseNetRepositoryModel;
import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by lyz on 2017/11/1.
 */

public class DeviceRepositoryImpl  extends BaseNetRepositoryModel<RestDeviceService> implements IDeviceRepository {
  public DeviceRepositoryImpl(Context context, Retrofit retrofit) {
    super(context, retrofit);
  }

  @Override public Observable<Boolean> unlock(String token, String deviceNo,double latitude, double longitude) {
    return getService().unlock(token, deviceNo,latitude,longitude);
  }

  @Override public Observable<Integer> queryRequest(String token, String reqId) {
    return getService().queryRequest(token,reqId);
  }

  @Override public Observable<Integer> getDeviceStatus(String token, String reqId) {
    return getService().getDeviceStatus(token,reqId);
  }

  @Override public Observable<Integer> accountStatus(String token, String reqId) {
    return getService().accountStatus(token,reqId);
  }

  @Override public Observable<Integer> getFailState(String token, String reqId) {
    return getService().getFailState(token,reqId);
  }

  @Override protected RestDeviceService initRetrofitClient(Context context) {
    return retrofit.create(getServiceType());
  }
}

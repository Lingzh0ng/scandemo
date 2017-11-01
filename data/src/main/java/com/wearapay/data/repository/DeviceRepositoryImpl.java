package com.wearapay.data.repository;

import android.content.Context;
import com.wearapay.data.bean.DeviceStatus;
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

  @Override public Observable<String> unlock(String token, String deviceNo) {
    return getService().unlock(token, deviceNo);
  }

  @Override public Observable<DeviceStatus> queryRequest(String token, String reqId) {
    return getService().queryRequest(token,reqId);
  }

  @Override protected RestDeviceService initRetrofitClient(Context context) {
    return retrofit.create(getServiceType());
  }
}

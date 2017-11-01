package com.wearapay.data.retorfit;

import com.wearapay.data.bean.DeviceStatus;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by lyz on 2017/11/1.
 */
public interface RestDeviceService {
  @FormUrlEncoded
  @POST("/api/device/unlock") Observable<String> unlock(@Field("token") String token,
      @Field("deviceNo") String deviceNo);

  @FormUrlEncoded
  @POST("/api/device/queryRequest") Observable<DeviceStatus> queryRequest(@Field("token") String token,
      @Field("reqId") String reqId);
}

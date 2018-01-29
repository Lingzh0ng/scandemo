package com.wearapay.data.retorfit;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by lyz on 2017/11/1.
 */
public interface RestDeviceService {
  @FormUrlEncoded
  @POST("api/device/unlock2") Observable<Boolean> unlock(@Field("token") String token,
      @Field("deviceNo") String deviceNo,@Field("latitude") double latitude,@Field("longitude") double longitude);

  @FormUrlEncoded
  @POST("api/device/getState2") Observable<Integer> getDeviceStatus(@Field("token") String token,
      @Field("deviceNo") String reqId);

  @FormUrlEncoded
  @POST("api/device/getLockState2") Observable<Integer> queryRequest(@Field("token") String token,
      @Field("deviceNo") String reqId);

  @FormUrlEncoded
  @POST("api/device/accountStatus") Observable<Integer> accountStatus(@Field("token") String token,
      @Field("deviceNo") String reqId);

  @FormUrlEncoded
  @POST("api/device/getFailState") Observable<Integer> getFailState(@Field("token") String token,
      @Field("deviceNo") String reqId);
}

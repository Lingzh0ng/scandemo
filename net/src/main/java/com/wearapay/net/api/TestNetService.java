package com.wearapay.net.api;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by lyz on 2017/11/1.
 */

public interface TestNetService {
  @FormUrlEncoded
  @POST("factory.php") Observable<String> login(@Field("action") String action);
}

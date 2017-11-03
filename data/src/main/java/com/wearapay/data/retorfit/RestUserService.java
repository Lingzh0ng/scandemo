package com.wearapay.data.retorfit;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by lyz on 2017/11/1.
 */

public interface RestUserService {
  @FormUrlEncoded @POST("/api/user/register") Observable<Void> register(
      @Field("loginId") String loginId, @Field("password1") String password1,
      @Field("password2") String password2, @Field("nickname") String nickname);

  @FormUrlEncoded @POST("/api/user/login") Observable<String> login(
      @Field("loginId") String loginId, @Field("password") String password);

  @FormUrlEncoded @POST("/api/user/changePassword") Observable<Void> changePassword(
      @Field("token") String token, @Field("password") String password,
      @Field("password1") String password1, @Field("password2") String password2);

  @FormUrlEncoded @POST("/api/user/logout") Observable<Void> logout(
      @Field("token") String token);
}
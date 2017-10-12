package com.wearapay.net;

/**
 * Created by lyz on 2017/10/11.
 */

public class ApiManager {

  private final RetrofitManager retrofitManager;

  private ApiManager() {
    retrofitManager = RetrofitManager.get();
  }

  private static final class ApiManagerHolder {
    private static final ApiManager instance = new ApiManager();
  }

  public static ApiManager get() {
    return ApiManagerHolder.instance;
  }


}

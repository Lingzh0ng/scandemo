package com.wearapay.net;

import android.content.Context;
import com.wearapay.net.api.TestNetRepositoryModel;
import com.wearapay.net.api.TestNetService;

/**
 * Created by lyz on 2017/10/11.
 */

public class ApiManager {

  private final RetrofitManager retrofitManager;
  private Context appContext;
  private TestNetRepositoryModel testNetRepositoryModel;

  private ApiManager() {
    retrofitManager = RetrofitManager.get();
  }

  private static final class ApiManagerHolder {
    private static final ApiManager instance = new ApiManager();
  }

  public static ApiManager get() {
    return ApiManagerHolder.instance;
  }

  public void init(Context appContext) {
    this.appContext = appContext;
    testNetRepositoryModel = new TestNetRepositoryModel(appContext,
        retrofitManager.getRetrofit(appContext, "http://szt.alxfj.com:808/app/"));
  }

  public TestNetService getTestNetRepositoryModel() {
    return testNetRepositoryModel.getService();
  }
}

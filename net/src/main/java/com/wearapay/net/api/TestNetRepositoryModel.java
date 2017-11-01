package com.wearapay.net.api;

import android.content.Context;
import com.wearapay.net.BaseNetRepositoryModel;
import retrofit2.Retrofit;

/**
 * Created by lyz on 2017/11/1.
 */

public class TestNetRepositoryModel extends BaseNetRepositoryModel<TestNetService> {

  public TestNetRepositoryModel(Context context, Retrofit retrofit) {
    super(context, retrofit);
  }

  @Override protected TestNetService initRetrofitClient(Context context) {
    return retrofit.create(getServiceType());
  }
}

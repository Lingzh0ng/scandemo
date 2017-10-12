package com.wearapay.net;

import android.content.Context;
import com.wearapay.base.net.BaseRepositoryModel;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import retrofit2.Retrofit;

/**
 * Created by lyz on 2017/10/12.
 */

public abstract class BaseNetRepositoryModel<T> extends BaseRepositoryModel {

  public T getService() {
    return service;
  }

  protected T service;
  protected Retrofit retrofit;


  public BaseNetRepositoryModel(Context context, Retrofit retrofit) {
    super(context);
    this.retrofit = retrofit;
    service = initRetrofitClient(context);
  }

  public Class<T> getServiceType() {
    Class<T> entityClass = null;
    Type t = getClass().getGenericSuperclass();
    Type[] p = ((ParameterizedType) t).getActualTypeArguments();
    entityClass = (Class<T>) p[0];
    return entityClass;
  }

  protected abstract T initRetrofitClient(Context context);
}

package com.wearapay.base.net;

import android.content.Context;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by lyz on 2017/10/12.
 */

public abstract class BaseRepositoryModel<T> {
  protected Context context;

  public BaseRepositoryModel(Context context) {
    this.context = context;
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

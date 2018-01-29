package com.wearapay.net.converter;

import com.google.gson.Gson;
import com.wearapay.base.BaseBean;
import com.wearapay.net.Exception.NotLoginException;
import com.wearapay.net.Exception.PPCodedException;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Leo Ren(leo.ren@paypos.ca) on 10/8/15.
 */
final class PPRestResponseBodyConverter<T> implements Converter<ResponseBody, T> {

  private Gson gson;
  private Type type;

  public PPRestResponseBodyConverter(Gson gson, Type type) {
    this.gson = gson;
    this.type = type;
  }

  static void closeQuietly(Closeable closeable) {
    if (closeable == null) return;
    try {
      closeable.close();
    } catch (IOException ignored) {
    }
  }

  @Override public T convert(ResponseBody value) throws IOException {
    String responseStr = value.string();
    System.out.println("responseStr = " + responseStr);

    BaseBean result = gson.fromJson(responseStr, BaseBean.class);
    if (result.isSuccess()) {
      if (result.getData() == null) {
        return (T) Boolean.TRUE;
      }
      return gson.fromJson(gson.toJson(result.getData()), type);
    } else {
      if (result.getCode().equals("not-login")) {
        throw new NotLoginException(result.getMsg());
      } else {
        throw new PPCodedException(result.getCode(), result.getMsg());
      }
    }
  }
}
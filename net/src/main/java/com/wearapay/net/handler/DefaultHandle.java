package com.wearapay.net.handler;

import com.wearapay.net.Exception.ApiException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lyz on 2017/10/11.
 */

public class DefaultHandle implements RequestHandler {
  @Override public Request onBeforeRequest(Request request, Interceptor.Chain chain) {
    Request.Builder builder = chain.request().newBuilder();
    return builder./*addHeader("header1", "")
        .addHeader("header2", "")
        .addHeader("header3", "").*/
        build();
  }

  @Override public Response onAfterRequest(Response response, Interceptor.Chain chain)
      throws ApiException {
    ApiException e = null;
    if (401 == response.code()) {
      throw new ApiException("登录已过期,请重新登录!");
    } else if (403 == response.code()) {
      throw new ApiException("禁止访问!");
    } else if (404 == response.code()) {
      throw new ApiException("链接错误");
    } else if (503 == response.code()) {
      throw new ApiException("服务器升级中!");
    } else if (500 == response.code()) {
      throw new ApiException("服务器内部错误!");
    }
    return response;
  }
}

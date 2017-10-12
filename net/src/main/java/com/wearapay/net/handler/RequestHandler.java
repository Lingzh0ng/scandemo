package com.wearapay.net.handler;

import com.wearapay.net.Exception.ApiException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp 拦截接口
 *
 * Created by lyz on 2017/10/11.
 */

public interface RequestHandler {
  public Request onBeforeRequest(Request request, Interceptor.Chain chain);

  public Response onAfterRequest(Response response, Interceptor.Chain chain) throws ApiException;
}

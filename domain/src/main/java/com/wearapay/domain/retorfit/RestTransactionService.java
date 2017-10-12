package com.wearapay.domain.retorfit;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by swang on 1/28/16.
 */
public interface RestTransactionService {
  @GET("market/transactions/latest/{rows}") Observable<String> login(
      @Path("rows") String name,@Path("rows") String pwd);
}

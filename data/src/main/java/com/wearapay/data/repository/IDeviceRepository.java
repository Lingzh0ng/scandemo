package com.wearapay.data.repository;

import com.wearapay.base.net.IRepository;
import io.reactivex.Observable;

/**
 * Created by lyz on 2017/10/12.
 */

public interface IDeviceRepository extends IRepository {
  Observable<Boolean> unlock(String token, String deviceNo,double latitude, double longitude);

  Observable<Integer> queryRequest(String token, String reqId);

  Observable<Integer> getDeviceStatus(String token, String reqId);
}

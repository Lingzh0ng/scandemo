package com.wearapay.domain.devices;

import io.reactivex.Observable;

/**
 * Created by lyz on 2017/11/1.
 */

public interface IDeviceMgmt {
  Observable<Boolean> unlock(String deviceNo,double latitude,double longitude);

  Observable<Integer> queryRequest(String reqId);

  Observable<Integer> getDeviceStatus(String reqId);
}

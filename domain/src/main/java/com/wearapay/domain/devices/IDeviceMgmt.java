package com.wearapay.domain.devices;

import com.wearapay.data.bean.DeviceStatus;
import io.reactivex.Observable;

/**
 * Created by lyz on 2017/11/1.
 */

public interface IDeviceMgmt {
  Observable<String> unlock(String deviceNo);

  Observable<DeviceStatus> queryRequest(String reqId);
}

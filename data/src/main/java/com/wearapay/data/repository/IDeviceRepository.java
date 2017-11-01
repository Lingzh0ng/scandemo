package com.wearapay.data.repository;

import com.wearapay.base.net.IRepository;
import com.wearapay.data.bean.DeviceStatus;
import io.reactivex.Observable;

/**
 * Created by lyz on 2017/10/12.
 */

public interface IDeviceRepository extends IRepository {
  Observable<String> unlock(String token, String deviceNo);

  Observable<DeviceStatus> queryRequest(String token, String reqId);
}

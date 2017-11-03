package com.wearapay.domain.devices;

import com.wearapay.data.bean.DeviceStatus;
import com.wearapay.data.repository.IDeviceRepository;
import com.wearapay.data.repository.ILocalRepository;
import io.reactivex.Observable;

/**
 * Created by lyz on 2017/11/1.
 */

public class DeviceMgmtImpl implements IDeviceMgmt {

  ILocalRepository iLocalRepository;
  IDeviceRepository iDeviceRepository;

  public DeviceMgmtImpl(ILocalRepository iLocalRepository, IDeviceRepository iDeviceRepository) {
    this.iLocalRepository = iLocalRepository;
    this.iDeviceRepository = iDeviceRepository;
  }

  @Override public Observable<String> unlock(String deviceNo,double jd,double wd) {
    return iDeviceRepository.unlock(iLocalRepository.getUserTaken(), deviceNo);
  }

  @Override public Observable<DeviceStatus> queryRequest(String reqId) {
    return iDeviceRepository.queryRequest(iLocalRepository.getUserTaken(), reqId);
  }
}

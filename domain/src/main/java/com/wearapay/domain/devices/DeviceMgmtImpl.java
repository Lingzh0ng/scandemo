package com.wearapay.domain.devices;

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

  @Override public Observable<Boolean> unlock(String deviceNo,double latitude,double longitude) {
    return iDeviceRepository.unlock(iLocalRepository.getUserTaken(), deviceNo,latitude,longitude);
  }

  @Override public Observable<Integer> queryRequest(String reqId) {
    return iDeviceRepository.queryRequest(iLocalRepository.getUserTaken(), reqId);
  }

  @Override public Observable<Integer> getDeviceStatus(String reqId) {
    return iDeviceRepository.getDeviceStatus(iLocalRepository.getUserTaken(),reqId);
  }

  @Override public Observable<Integer> accountStatus(String reqId) {
    return iDeviceRepository.accountStatus(iLocalRepository.getUserTaken(),reqId);
  }

  @Override public Observable<Integer> getFailState( String reqId) {
    return iDeviceRepository.getFailState(iLocalRepository.getUserTaken(),reqId);
  }
}

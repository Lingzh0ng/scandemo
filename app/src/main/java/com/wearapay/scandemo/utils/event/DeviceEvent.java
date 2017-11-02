package com.wearapay.scandemo.utils.event;

import com.wearapay.data.bean.DeviceStatus;

/**
 * Created by lyz on 2017/11/2.
 */

public class DeviceEvent {
  private DeviceStatus deviceStatus;

  public DeviceEvent(DeviceStatus deviceStatus) {
    this.deviceStatus = deviceStatus;
  }
}

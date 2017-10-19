package com.wearapay.scandemo.bean;

/**
 * Created by lyz on 2017/10/17.
 */

public class DeviceHistory {
  private Device device;
  private long time;

  public DeviceHistory() {
  }

  public Device getDevice() {
    return device;
  }

  public void setDevice(Device device) {
    this.device = device;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  @Override public String toString() {
    return "DeviceHistory{" + "device=" + device + ", time=" + time + '}';
  }
}

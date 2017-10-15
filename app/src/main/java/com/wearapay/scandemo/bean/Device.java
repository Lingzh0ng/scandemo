package com.wearapay.scandemo.bean;

/**
 * Created by lyz54 on 2017/10/15.
 */

public class Device {
    private String name;
    private String DeviceNo;
    private String pin;

    @Override
    public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", DeviceNo='" + DeviceNo + '\'' +
                ", pin='" + pin + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceNo() {
        return DeviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        DeviceNo = deviceNo;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}

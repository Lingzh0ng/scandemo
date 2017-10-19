package com.wearapay.scandemo.bean;

/**
 * Created by lyz on 2017/10/17.
 */

public enum  ChargeType {

  CHARGE_10("充值10元",10),
  CHARGE_50("充值50元",50),
  CHARGE_100("充值100元",100),
  CHARGE_200("充值200元",200),
  CHARGE_500("充值500元",500),
  CHARGE_1000("充值1000元",1000),
  CHARGE_10000("充值10000元",10000);

  public String getShowMsg() {
    return showMsg;
  }

  public void setShowMsg(String showMsg) {
    this.showMsg = showMsg;
  }

  public long getAccoumt() {
    return accoumt;
  }

  public void setAccoumt(long accoumt) {
    this.accoumt = accoumt;
  }

  private String showMsg;

  private long accoumt;

  ChargeType(String showMsg,long accoumt) {
    this.showMsg = showMsg;
    this.accoumt = accoumt;
  }
}

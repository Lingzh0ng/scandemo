package com.wearapay.scandemo;

import com.wearapay.domain.user.IUserMgmt;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/12.
 */

public class Dest {
  IUserMgmt userMgmt;

  @Inject public Dest(IUserMgmt userMgmt) {
    this.userMgmt = userMgmt;
  }

  public String show(String name, String pwd) {
    return userMgmt.test(name, pwd);
  }

  public boolean getLoginStatus(){
    return userMgmt.getLoginStatus();
  }
}

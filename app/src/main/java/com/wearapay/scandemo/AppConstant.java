package com.wearapay.scandemo;

import com.wearapay.scandemo.module.device.AddDeviceFragment;
import com.wearapay.scandemo.module.device.ChargeFragment;
import com.wearapay.scandemo.module.device.DeviceHistoryFragment;
import com.wearapay.scandemo.module.device.DeviceManagerFragment;
import com.wearapay.scandemo.module.home.HomeFragment;
import com.wearapay.scandemo.module.login.LoginFragment;
import com.wearapay.scandemo.module.login.RegistFragment;
import com.wearapay.scandemo.module.user.ChangePswFragment;
import com.wearapay.scandemo.module.user.UserInfoFragment;

/**
 * Created by lyz on 2017/10/12.
 */

public interface AppConstant {
  String FRAGMENT_TYPE = "fragment_type";
  String CAN_BACK = "can_back";
  String SHOW_MENU = "show_menu";
  String STATUS_COLOR = "status_color";
  String TITLE = "title";
  long EXIT_BACK_PRESSED_INTERVAL = 3000L;

  enum FragmentType {
    Test(TestFragment.class),
    Charge(ChargeFragment.class),
    DeviceHistory(DeviceHistoryFragment.class),
    DeviceManager(DeviceManagerFragment.class),
    Home(HomeFragment.class),
    Login(LoginFragment.class),
    Regist(RegistFragment.class),
    ChangePsw(ChangePswFragment.class),
    UserInfo(UserInfoFragment.class),
    AddDevice(AddDeviceFragment.class);

    private Class clazz;

    FragmentType(Class clazz) {
      this.clazz = clazz;
    }

    public Class getClazz() {
      return clazz;
    }
  }
}

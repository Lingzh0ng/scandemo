package com.wearapay.scandemo;

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
    Test(TestFragment.class);

    private Class clazz;

    FragmentType(Class clazz) {
      this.clazz = clazz;
    }

    public Class getClazz() {
      return clazz;
    }
  }
}

package com.wearapay.scandemo;

/**
 * Created by lyz on 2017/10/12.
 */

public interface AppConstant {
  String FRAGMENT_TYPE = "fragment_type";
  String CAN_BACK = "can_back";
  String STATUS_COLOR = "status_color";

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

package com.wearapay.scandemo;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by lyz on 2017/12/21.
 */

public class MyAccessibilityService extends AccessibilityService {
  @Override public void onAccessibilityEvent(AccessibilityEvent event) {
    //event.getSource().f
  }

  @Override public void onInterrupt() {

  }
}

package com.wearapay.scandemo.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import butterknife.ButterKnife;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;
import com.wearapay.scandemo.R;
import com.wearapay.scandemo.utils.AppUtils;
import com.wearapay.scandemo.utils.SystemBarTintManager;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by lyz on 2017/6/27.
 */
public abstract class BaseActivity extends RxFragmentActivity {

  public int getStatusColor() {
    return color;
  }

  protected int color = R.color.status_bar_bg;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    //            Window window = getWindow();
    //            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
    //                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    //            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    //                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    //                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    //            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    //            window.setStatusBarColor(Color.TRANSPARENT);
    //            window.setNavigationBarColor(Color.TRANSPARENT);
    //        }
    //        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    //        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    setContentView(getLayoutId());
    ButterKnife.bind(this);
    //StatusBarCompat.compat(this, getStatusColor());
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      setTranslucentStatus(true);
      SystemBarTintManager tintManager = new SystemBarTintManager(this);
      tintManager.setStatusBarTintEnabled(true);

      //通知栏所需颜色
      tintManager.setStatusBarTintResource(getStatusColor());
      //tintManager.setStatusBarTintResource(R.color.status_bar_bg);
    }
    isActive = new AtomicBoolean(true);
  }

  @TargetApi(Build.VERSION_CODES.KITKAT) private void setTranslucentStatus(boolean on) {
    Window win = getWindow();
    WindowManager.LayoutParams winParams = win.getAttributes();
    final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
    if (on) {
      winParams.flags |= bits;
    } else {
      winParams.flags &= ~bits;
    }
    win.setAttributes(winParams);
  }



  protected abstract int getLayoutId();

  private boolean isAutoHideInputView = true;
  private boolean isClearFocusWhileAutoHideInputView = false;
  private AtomicBoolean isActive;

  protected ArrayList<OnBackPressedListener> mOnBackPressedListeners = new ArrayList<>();

  public void addOnBackPressedListener(OnBackPressedListener l) {
    mOnBackPressedListeners.add(l);
  }

  public void removeOnBackPressedListener(OnBackPressedListener l) {
    mOnBackPressedListeners.remove(l);
  }

  public boolean isAutoHideInputView() {
    return isAutoHideInputView;
  }

  public void setAutoHideInputView(boolean isAutoHideInputView) {
    this.isAutoHideInputView = isAutoHideInputView;
  }

  public void setClearFocusWhileAutoHideInputView(boolean clear) {
    isClearFocusWhileAutoHideInputView = clear;
  }

  @Override public boolean dispatchTouchEvent(MotionEvent ev) {
    if (isAutoHideInputView) {
      if (ev.getAction() == MotionEvent.ACTION_DOWN) {
        View v = getCurrentFocus();
        if (v != null) {
          if ((v instanceof EditText)) {
            if (!AppUtils.isEventInVIew(v, ev)) {
              if (AppUtils.hideInput(this, v) && isClearFocusWhileAutoHideInputView) {
                v.clearFocus();
              }
            }
          }
        }
        return super.dispatchTouchEvent(ev);
      }
      // 必不可少，否则所有的组件都不会有TouchEvent了
      if (getWindow().superDispatchTouchEvent(ev)) {
        return true;
      }
    }

    try {
      return super.dispatchTouchEvent(ev);
    } catch (Exception e) {
      // ignored
    }
    return false;
  }

  @Override public void onBackPressed() {
    for (int i = mOnBackPressedListeners.size() - 1; i >= 0; i--) {
      if (mOnBackPressedListeners.get(i).onBackPressed()) {
        return;
      }
    }
    super.onBackPressed();
  }

  public void onBackPressed(boolean isFromKey) {
    if (isFromKey) {
      onBackPressed();
    } else {
      super.onBackPressed();
    }
  }

  @Override protected void onDestroy() {
    AppUtils.fixInputMethodManagerLeak(this);
    super.onDestroy();
    isActive.set(false);
  }

  public AtomicBoolean isActive() {
    return isActive;
  }

  public interface OnBackPressedListener {
    boolean onBackPressed();
  }
}

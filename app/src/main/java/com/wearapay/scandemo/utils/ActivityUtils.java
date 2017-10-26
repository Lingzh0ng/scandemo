package com.wearapay.scandemo.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.wearapay.scandemo.AppConstant;
import com.wearapay.scandemo.CommonActivity;
import com.wearapay.scandemo.R;
import com.wearapay.scandemo.base.BaseFragment;

public class ActivityUtils {

  public static Bundle getBundle(String title) {
    return getBundle(R.color.test_color, title, true, false);
  }

  public static Bundle getBundle(String title, boolean canBack, boolean showMenu) {
    return getBundle(R.color.test_color, title, canBack, showMenu);
  }

  public static Bundle getBundle(int color, String title) {
    return getBundle(color, title, true, false);
  }

  public static Bundle getBundle(int color, String title, boolean canBack) {
    return getBundle(color, title, canBack, false);
  }

  public static Bundle getBundle(int color, String title, boolean canBack, boolean showMenu) {
    Bundle bundle = new Bundle();
    bundle.putInt(AppConstant.STATUS_COLOR, color);
    bundle.putString(AppConstant.TITLE, title);
    bundle.putBoolean(AppConstant.CAN_BACK, canBack);
    bundle.putBoolean(AppConstant.SHOW_MENU, showMenu);
    return bundle;
  }

  public static void addFragment(@NonNull FragmentManager fragmentManager,
      @NonNull Fragment sourceFragment, @NonNull Fragment destFragment, int frameId) {
    checkNotNull(fragmentManager);
    checkNotNull(sourceFragment);
    checkNotNull(destFragment);
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.hide(sourceFragment);
    transaction.add(frameId, destFragment);
    transaction.addToBackStack(null);
    transaction.commit();
  }

  public static void addFragment(@NonNull FragmentManager fragmentManager,
      @NonNull Fragment sourceFragment, @NonNull AppConstant.FragmentType fragmentType) {
    addFragment(fragmentManager, sourceFragment, fragmentType, false);
  }

  public static void addFragment(@NonNull FragmentManager fragmentManager,
      @NonNull Fragment sourceFragment, @NonNull AppConstant.FragmentType fragmentType,
      boolean removeSourceFragment) {
    addFragment(fragmentManager, sourceFragment, fragmentType, removeSourceFragment, null);
  }

  public static void addFragment(@NonNull FragmentManager fragmentManager,
      @NonNull Fragment sourceFragment, @NonNull AppConstant.FragmentType fragmentType,
      boolean removeSourceFragment, Bundle args) {
    addFragment(fragmentManager, sourceFragment, fragmentType, R.id.contentFrame,
        removeSourceFragment, args);
  }

  /**
   * pass argument.
   */
  public static void addFragment(@NonNull FragmentManager fragmentManager,
      @NonNull Fragment sourceFragment, @NonNull AppConstant.FragmentType fragmentType,
      Bundle args) {
    addFragment(fragmentManager, sourceFragment, fragmentType, R.id.contentFrame, false, args);
  }

  public static void addFragment(@NonNull FragmentManager fragmentManager,
      @NonNull Fragment sourceFragment, @NonNull AppConstant.FragmentType fragmentType, int frameId,
      boolean removeSourceFragment, Bundle args) {
    checkNotNull(fragmentManager);
    checkNotNull(sourceFragment);
    checkNotNull(fragmentType);
    if (removeSourceFragment) {
      fragmentManager.popBackStack();
    }
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    if (!removeSourceFragment) {
      transaction.hide(sourceFragment);
    }
    Class<BaseFragment> fragmentClass = fragmentType.getClazz();
    try {
      Fragment fragment = fragmentClass.newInstance();
      if (args != null) {
        fragment.setArguments(args);
      }
      transaction.add(frameId, fragment);
      transaction.addToBackStack(null);
      transaction.commit();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  public static void replaceFragment(@NonNull FragmentManager fragmentManager,
      @NonNull AppConstant.FragmentType fragmentType, int frameId, Bundle args) {
    checkNotNull(fragmentManager);
    checkNotNull(fragmentType);
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    Class<BaseFragment> fragmentClass = fragmentType.getClazz();
    try {
      Fragment fragment = fragmentClass.newInstance();
      if (args != null) {
        fragment.setArguments(args);
      }
      transaction.replace(frameId, fragment);
      transaction.commit();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  public static void replaceFragment(@NonNull FragmentManager fragmentManager,
      @NonNull AppConstant.FragmentType fragmentType, Bundle bundle) {
    replaceFragment(fragmentManager, fragmentType, R.id.contentFrame, bundle);
  }

  public static void replaceFragment(@NonNull FragmentManager fragmentManager,
      @NonNull Fragment fragment, int frameId) {
    checkNotNull(fragmentManager);
    checkNotNull(fragment);
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.replace(frameId, fragment);
    transaction.commit();
  }

  public static void startFragment(@NonNull Context context,
      @NonNull AppConstant.FragmentType fragmentType, @Nullable Bundle bundle) {
    startFragment(context, fragmentType, bundle, 0);
  }

  public static void startFragment(@NonNull Context context,
      @NonNull AppConstant.FragmentType fragmentType, @Nullable Bundle bundle, int flags) {
    checkNotNull(context);
    checkNotNull(fragmentType);

    Intent intent = new Intent(context, CommonActivity.class);
    if (flags != 0) {
      intent.addFlags(flags);
    }
    intent.putExtra(AppConstant.FRAGMENT_TYPE, fragmentType);
    if (bundle != null) {
      intent.putExtras(bundle);
    }
    context.startActivity(intent);
  }

  public static <T> T checkNotNull(T reference) {
    if (reference == null) {
      throw new NullPointerException();
    }
    return reference;
  }
}

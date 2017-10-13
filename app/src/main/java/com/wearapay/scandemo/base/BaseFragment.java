package com.wearapay.scandemo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.trello.rxlifecycle2.components.support.RxFragment;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by lyz54 on 2017/6/27.
 */

public abstract class BaseFragment extends RxFragment {
  private boolean isNeedAddOnBackPressedListener = true;

  protected AtomicBoolean isViewActive;

  public abstract void onCleanBeforeDetach();

  protected abstract int getLayoutId();

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(getLayoutId(), container, false);
  }

  protected void setIsNeedAddOnBackPressedListener(boolean need) {
    isNeedAddOnBackPressedListener = need;
  }

  protected boolean onBackPressed(boolean isFromKey) {
    if (!isFromKey) {
      ((BaseActivity) getActivity()).onBackPressed(isFromKey);
      return true;
    }
    return false;
  }

  private BaseActivity.OnBackPressedListener mOnBackPressedListener =
      new BaseActivity.OnBackPressedListener() {

        @Override public boolean onBackPressed() {
          if (!BaseFragment.this.isHidden()) {
            return BaseFragment.this.onBackPressed(true);
          }

          return false;
        }
      };

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    isViewActive = new AtomicBoolean(true);
    super.onViewCreated(view, savedInstanceState);
  }

  @Override public void onDestroyView() {
    isViewActive.set(false);
    super.onDestroyView();
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (isNeedAddOnBackPressedListener) {
      ((BaseActivity) getActivity()).addOnBackPressedListener(mOnBackPressedListener);
    }
  }

  @Override public void onDetach() {
    ((BaseActivity) getActivity()).removeOnBackPressedListener(mOnBackPressedListener);

    onCleanBeforeDetach();
    super.onDetach();
  }
}

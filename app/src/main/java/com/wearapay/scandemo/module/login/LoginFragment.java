package com.wearapay.scandemo.module.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.wearapay.base.utils.SimpleTextWatcher;
import com.wearapay.scandemo.App;
import com.wearapay.scandemo.AppConstant;
import com.wearapay.scandemo.BaseMvpFragment;
import com.wearapay.scandemo.R;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.module.login.presenter.LoginPresenter;
import com.wearapay.scandemo.module.login.view.ILoginView;
import com.wearapay.scandemo.utils.ActivityUtils;
import com.wearapay.scandemo.utils.UIUtil;
import com.wearapay.scandemo.weight.CustomEditCell;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/13.
 */

public class LoginFragment extends BaseMvpFragment implements ILoginView {
  @BindView(R.id.cellUserName) CustomEditCell cellUserName;
  @BindView(R.id.cellPassword) CustomEditCell cellPassword;
  @BindView(R.id.btnLogin) Button btnLogin;
  @BindView(R.id.btnReg) Button btnReg;
  Unbinder unbinder;
  private String name;
  private String pwd;

  @Inject LoginPresenter loginPresenter;

  @Override public void onCleanBeforeDetach() {

  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    App.app.getComponent().inject(this);
  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_login;
  }

  @Override protected BaseFragmentPresenter[] initPresenters() {
    return new BaseFragmentPresenter[] { loginPresenter };
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    unbinder = ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ivBack.setVisibility(View.INVISIBLE);
    cellPassword.setTextWatcher(watcher);
    cellUserName.setTextWatcher(watcher);
    updateButtonStatus();
  }

  private SimpleTextWatcher watcher = new SimpleTextWatcher() {
    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
      name = cellUserName.getText();
      pwd = cellPassword.getText();
      updateButtonStatus();
    }
  };

  private void updateButtonStatus() {
    btnLogin.setEnabled(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pwd) && pwd.length() >= 6);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({ R.id.btnLogin, R.id.btnReg }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.btnLogin:
        loginPresenter.login(name, pwd);
        break;
      case R.id.btnReg:
        regriset();
        break;
    }
  }

  private void regriset() {
    Bundle bundle = ActivityUtils.getBundle(R.color.test_color, "注册");
    ActivityUtils.startFragment(getActivity(), AppConstant.FragmentType.Regist, bundle);
  }

  private void login() {
    navToHomePage();

    getActivity().finish();
  }

  @Override public void LoginSuccess() {
    login();
  }

  @Override public void LoginFailure() {

  }

  long touchTime = 0;

  @Override protected boolean onBackPressed(boolean isFromKey) {
    long currentTime = System.currentTimeMillis();
    if ((currentTime - touchTime) >= AppConstant.EXIT_BACK_PRESSED_INTERVAL) {
      UIUtil.showToast(getActivity(), R.string.message_double_confirm_logout);
      touchTime = currentTime;
    } else {
      App.app.exitApp();
    }
    return true;
  }
}

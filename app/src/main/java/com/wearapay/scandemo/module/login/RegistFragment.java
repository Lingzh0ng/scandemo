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
import com.wearapay.scandemo.BaseMvpFragment;
import com.wearapay.scandemo.R;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.module.login.presenter.RegPresenter;
import com.wearapay.scandemo.module.login.view.IRegView;
import com.wearapay.scandemo.weight.CustomEditCell;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/13.
 */

public class RegistFragment extends BaseMvpFragment implements IRegView {
  @Inject RegPresenter regPresenter;
  @BindView(R.id.cellUserName) CustomEditCell cellUserName;
  @BindView(R.id.cellPassword) CustomEditCell cellPassword;
  @BindView(R.id.cellSurePassword) CustomEditCell cellSurePassword;
  @BindView(R.id.btnLogin) Button btnLogin;
  Unbinder unbinder;

  private String pwd2;
  private String name;
  private String pwd;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    App.app.getComponent().inject(this);
  }

  @Override public void onCleanBeforeDetach() {

  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_regrist;
  }

  @Override protected BaseFragmentPresenter[] initPresenters() {
    return new BaseFragmentPresenter[] { regPresenter };
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // TODO: inflate a fragment view
    View rootView = super.onCreateView(inflater, container, savedInstanceState);
    unbinder = ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView();
  }

  private void initView() {

  }

  private SimpleTextWatcher watcher = new SimpleTextWatcher() {

    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
      name = cellUserName.getText();
      pwd = cellPassword.getText();
      pwd2 = cellSurePassword.getText();
      updateButtonStatus();
    }
  };

  private void updateButtonStatus() {
    btnLogin.setEnabled(!TextUtils.isEmpty(name)
        && !TextUtils.isEmpty(pwd)
        && pwd.length() >= 6
        && !TextUtils.isEmpty(pwd2)
        && pwd2.length() >= 6);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick(R.id.btnLogin) public void onViewClicked() {
    if (!TextUtils.isEmpty(pwd) && !pwd.equals(pwd2)) {
      showMessage("两次密码输入不一致");
    } else {
      regPresenter.reg("", "");
    }
  }

  @Override public void RegSuccess() {
    showMessage("注册成功,请登录");
    onBackPressed(false);
  }

  @Override public void RegFailure() {

  }
}

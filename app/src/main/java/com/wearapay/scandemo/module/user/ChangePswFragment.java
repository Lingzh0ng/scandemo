package com.wearapay.scandemo.module.user;

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
import com.wearapay.scandemo.module.user.presenter.ChangePswPresenter;
import com.wearapay.scandemo.module.user.view.IChangePswView;
import com.wearapay.scandemo.weight.CustomEditCell;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/13.
 */

public class ChangePswFragment extends BaseMvpFragment implements IChangePswView {
  @BindView(R.id.cellOldPwd) CustomEditCell cellOldPwd;
  @BindView(R.id.cellPassword) CustomEditCell cellPassword;
  @BindView(R.id.cellSurePassword) CustomEditCell cellSurePassword;
  @BindView(R.id.btnLogin) Button btnLogin;
  Unbinder unbinder;
  private String pwd2;
  private String pwd1;
  private String oldPwd;

  @Inject ChangePswPresenter changePswPresenter;

  @Override public void onCleanBeforeDetach() {

  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    App.app.getComponent().inject(this);
  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_change_psw;
  }

  @Override protected BaseFragmentPresenter[] initPresenters() {
    return new BaseFragmentPresenter[] { changePswPresenter };
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
    cellPassword.setTextWatcher(watcher);
    cellOldPwd.setTextWatcher(watcher);
    cellSurePassword.setTextWatcher(watcher);
  }

  private SimpleTextWatcher watcher = new SimpleTextWatcher() {

    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
      oldPwd = cellOldPwd.getText();
      pwd1 = cellPassword.getText();
      pwd2 = cellSurePassword.getText();
      updateButtonStatus();
    }
  };

  private void updateButtonStatus() {
    btnLogin.setEnabled(!TextUtils.isEmpty(oldPwd)
        && !TextUtils.isEmpty(pwd1)
        && pwd1.length() >= 6
        && !TextUtils.isEmpty(pwd2)
        && pwd2.length() >= 6);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick(R.id.btnLogin) public void onViewClicked() {
    if (pwd1.equals(pwd2)) {
      changePswPresenter.changePsw(oldPwd, pwd1);
    } else  {
      showMessage("两次输入密码不一致");
    }
  }

  @Override public void ChangePswSuccess() {
    onBackPressed(false);
  }

  @Override public void ChangePswFailure() {
    cellOldPwd.clearText();
    cellPassword.clearText();
    cellSurePassword.clearText();
  }
}

package com.wearapay.scandemo.module.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
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
  @BindView(R.id.cellVer) CustomEditCell cellVer;
  @BindView(R.id.btnLogin) Button btnLogin;
  @BindView(R.id.btnVer) Button btnVer;
  Unbinder unbinder;

  private String pwd2;
  private String name;
  private String pwd;
  private String verifyCode;
  private boolean isVerifyCode = true;

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
    cellPassword.setTextWatcher(watcher);
    cellUserName.setTextWatcher(watcher);
    cellSurePassword.setTextWatcher(watcher);
    cellVer.setTextWatcher(watcher);
    cellUserName.getEditText().setKeyListener(new NumberKeyListener() {
      @Override
      protected char[] getAcceptedChars() {
        return new char[] { '1', '2', '3', '4', '5', '6', '7', '8','9', '0' };
      }
      @Override
      public int getInputType() {
        // TODO Auto-generated method stub
        return android.text.InputType.TYPE_CLASS_PHONE;
      }
    });

    btnVer.setEnabled(false);

    btnVer.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (!TextUtils.isEmpty(name) && name.length() == 11) {
          btnVer.setEnabled(false);
          btnVer.setText("60秒");
          regPresenter.requestRegisterCode(name);
          updateButtonStatus(false);
        }
        //regPresenter.VerCodeTime();
      }
    });
  }

  private SimpleTextWatcher watcher = new SimpleTextWatcher() {

    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
      name = cellUserName.getText();

      pwd = cellPassword.getText();
      pwd2 = cellSurePassword.getText();
      verifyCode = cellVer.getText();
      if (name.length() > 11) {
        name = name.substring(0, 11);
        cellUserName.setText(name);
      } else if (name.length() == 11) {
        cellUserName.getEditText().setSelection(name.length());
      }
      updateButtonStatus();
    }
  };

  private void updateButtonStatus() {
   updateButtonStatus(true);
  }

  private void updateButtonStatus(boolean verEnabled) {
    btnLogin.setEnabled(!TextUtils.isEmpty(name) && name.length() == 11
        && !TextUtils.isEmpty(pwd)
        && pwd.length() >= 6
        && !TextUtils.isEmpty(pwd2)
        && pwd2.length() >= 6&& !TextUtils.isEmpty(verifyCode));

    btnVer.setEnabled(!TextUtils.isEmpty(name) && name.length() == 11 && verEnabled && isVerifyCode);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick(R.id.btnLogin) public void onViewClicked() {
    if (!TextUtils.isEmpty(pwd) && !pwd.equals(pwd2)) {
      showMessage("两次密码输入不一致");
    } else {
      //VerifyCodeRegister verifyCodeRegister = new VerifyCodeRegister();
      //verifyCodeRegister.setLoginId(name);
      //verifyCodeRegister.setNickname(name);
      //verifyCodeRegister.setPassword1(pwd);
      //verifyCodeRegister.setPassword2(pwd2);
      //verifyCodeRegister.setVerifyCode(verifyCode);
      //regPresenter.reg(verifyCodeRegister);

      regPresenter.reg(name,pwd,verifyCode);
    }
  }

  @Override public void RegSuccess() {
    showMessage("注册成功,请登录");
    onBackPressed(false);
  }

  @Override public void RegFailure() {

  }

  @Override public void VerSuccess() {
    isVerifyCode = false;
    showMessage("获取验证码成功");
    regPresenter.VerCodeTime();
  }

  @Override public void VerFailure() {
    isVerifyCode = true;
    showMessage("获取验证码失败");
    btnVer.setText("获取验证码");
    btnVer.setEnabled(true);
  }

  @Override public void UpdateTime(Long l) {
    if (l < 60) {
      btnVer.setText((60 - l )+ "秒");
      isVerifyCode = false;
      updateButtonStatus(false);
    } else {
      isVerifyCode = true;
      btnVer.setText("获取验证码");
      updateButtonStatus(true);
    }
  }
}

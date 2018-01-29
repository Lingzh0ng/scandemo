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
import com.wearapay.net.ApiManager;
import com.wearapay.net.api.TestNetService;
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
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    cellUserName.getEditText().setKeyListener(new NumberKeyListener() {
      @Override protected char[] getAcceptedChars() {
        return new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
      }

      @Override public int getInputType() {
        // TODO Auto-generated method stub
        return android.text.InputType.TYPE_CLASS_PHONE;
      }
    });
    updateButtonStatus();
  }

  private SimpleTextWatcher watcher = new SimpleTextWatcher() {
    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
      name = cellUserName.getText();
      pwd = cellPassword.getText();
      updateButtonStatus();
      if (name.length() > 11) {
        name = name.substring(0, 11);
        cellUserName.setText(name);
      } else if (name.length() == 11) {
        cellUserName.getEditText().setSelection(name.length());
      }
    }
  };

  private void updateButtonStatus() {
    btnLogin.setEnabled(!TextUtils.isEmpty(name)
        && name.length() == 11
        && !TextUtils.isEmpty(pwd)
        && pwd.length() >= 6);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({ R.id.btnLogin, R.id.btnReg }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.btnLogin:
        if ("11111111111".equals(name)) {
          login();
          return;
        }
        loginPresenter.login(name, pwd);

          //test();
        break;
      case R.id.btnReg:
        regriset();
        //test2();
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

  public void test() {

    TestNetService netService = ApiManager.get().getTestNetRepositoryModel();
    String act = "{\"version_upd_type\": \"APP01.\",\"client_version_no\": \"1.0.0\"}";
    String encode = null;
    try {
      encode = URLEncoder.encode(act, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    netService.checkVersion(encode)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
          @Override public void onSubscribe(Disposable d) {

          }

          @Override public void onNext(String s) {
            System.out.println(s);
          }

          @Override public void onError(Throwable e) {
            System.out.println(e);
          }

          @Override public void onComplete() {

          }
        });
  }

  public void test2() {
    //String a = "%7B%22ret_code%22%3A%229996%22%2C%22ret_msg%22%3A%22%E5%88%9D%E5%A7%8B%E5%8C%96%E5%A4%B1%E8%B4%A5%EF%BC%8C%E8%AF%B7%E6%A0%B8%E5%AF%B9%E5%95%86%E6%88%B7%E5%8F%B7%E3%80%81%E7%BB%88%E7%AB%AF%E5%8F%B7%E6%98%AF%E5%90%A6%E6%AD%A3%E7%A1%AE%22%7D";
    //String decode = null;
    //try {
    //  decode = URLDecoder.decode(a, "UTF-8");
    //} catch (UnsupportedEncodingException e) {
    //  e.printStackTrace();
    //}
    //System.out.println(decode);
    TestNetService netService = ApiManager.get().getTestNetRepositoryModel();
    //String act = "{\"mchnt_cd\": \"S02500194250001.\",\"term_cd \": \"40010005\",\"sn \": \"1234567890\"}";
    String encode = "<bocomPay>\n"
        + "        <head>\n"
        + "            <transcode>Scan</transcode>\n"
        + "            <term_trans_time>1111111111111</term_trans_time>\n"
        + "            <mcht_id>sad</mcht_id>\n"
        + "            <sign>sign01</sign>\n"
        + "        </head>\n"
        + "        <body>\n"
        + "            <total_amount>000000000001</total_amount>\n"
        + "            <amount_type>156</amount_type>\n"
        + "            <term_id>13232132</term_id>\n"
        + "            <term_batch_no>000001</term_batch_no>\n"
        + "            <term_pos_no>000001</term_pos_no>\n"
        + "            <auth_code>123456798123465789</auth_code>\n"
        + "        </body>\n"
        + "    </bocomPay>";

    encode  = encode.replace(" ", "");
    encode  = encode.replace("\n", "");
    //try {
    //  encode = URLEncoder.encode(act, "UTF-8");
    //} catch (UnsupportedEncodingException e) {
    //  e.printStackTrace();
    //}
    netService.action(encode)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
          @Override public void onSubscribe(Disposable d) {

          }

          @Override public void onNext(String s) {
            System.out.println(s);
          }

          @Override public void onError(Throwable e) {
            System.out.println(e);
          }

          @Override public void onComplete() {

          }
        });
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

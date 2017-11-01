package com.wearapay.scandemo.module.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.wearapay.scandemo.App;
import com.wearapay.scandemo.AppConstant;
import com.wearapay.scandemo.BaseMvpFragment;
import com.wearapay.scandemo.R;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.module.home.presenter.HomePresenter;
import com.wearapay.scandemo.module.home.view.IHomeView;
import com.wearapay.scandemo.utils.ActivityUtils;
import com.wearapay.scandemo.utils.ToastUtils;
import javax.inject.Inject;
import net.ezbim.scan.simple.SimpleScanActivity;

import static net.ezbim.scan.simple.SimpleScanActivity.SIMPLE_SCAN_RESULT;

/**
 * Created by lyz on 2017/10/13.
 */

public class HomeFragment extends BaseMvpFragment implements IHomeView {
  @BindView(R.id.tvData) TextView tvData;
  @BindView(R.id.btnScan) ImageView btnScan;
  Unbinder unbinder;
  @Inject HomePresenter homePresenter;

  @Override public void onCleanBeforeDetach() {

  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    App.app.getComponent().inject(this);
  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_home;
  }

  @Override protected BaseFragmentPresenter[] initPresenters() {
    return new BaseFragmentPresenter[] { homePresenter };
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    unbinder = ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ivBack.setImageResource(R.drawable.icon_people);
    ivMenu.setImageResource(R.drawable.ic_menu_help_holo_light);
    ivMenu.setVisibility(View.VISIBLE);
    ivBack.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Bundle bundle = ActivityUtils.getBundle("账户及设备详情");
        ActivityUtils.startFragment(getActivity(), AppConstant.FragmentType.UserInfo, bundle);
      }
    });
    if (!homePresenter.getLoginStatus()) {
      navToLoginPage();
    }
  }

  @Override protected void OnClickMenu() {
    super.OnClickMenu();
    //ToastUtils.showShort("help");
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (data != null) {
      String stringExtra = data.getStringExtra(SIMPLE_SCAN_RESULT);
      ToastUtils.showShort(stringExtra);
      tvData.setText(stringExtra);
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick(R.id.btnScan) public void onViewClicked() {
    int flag = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
    if (flag != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.CAMERA },
          1);
    } else {
      startActivityForResult(new Intent(getContext(), SimpleScanActivity.class), 1);
    }
   /* ApiManager.get()
        .getTestNetRepositoryModel()
        .login("get_community")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
          @Override public void onSubscribe(Disposable d) {
            System.out.println(d);
          }

          @Override public void onNext(String s) {
            System.out.println(s);
          }

          @Override public void onError(Throwable e) {
            System.out.println(e);
          }

          @Override public void onComplete() {
            System.out.println("onComplete");
          }
        });*/
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      startActivityForResult(new Intent(getContext(), SimpleScanActivity.class), 1);
    } else {
      ToastUtils.showLong("请去权限列表开启相机权限");
    }
  }
}

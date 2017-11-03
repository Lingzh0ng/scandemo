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
import com.wearapay.scandemo.SDProgressDialog;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.module.home.presenter.HomePresenter;
import com.wearapay.scandemo.module.home.view.IHomeView;
import com.wearapay.scandemo.utils.ActivityUtils;
import com.wearapay.scandemo.utils.RxBus;
import com.wearapay.scandemo.utils.ToastUtils;
import com.wearapay.scandemo.utils.event.DeviceEvent;
import javax.inject.Inject;
import net.ezbim.scan.simple.SimpleScanActivity;

import static net.ezbim.scan.simple.SimpleScanActivity.SIMPLE_SCAN_RESULT;

/**
 * Created by lyz on 2017/10/13.
 */

public class HomeFragment extends BaseMvpFragment implements IHomeView {
  private static final int WRITE_COARSE_LOCATION_REQUEST_CODE = 1000;
  private static final int CAMERA_REQUEST_CODE = 1;
  @BindView(R.id.tvData) TextView tvData;
  @BindView(R.id.btnScan) ImageView btnScan;
  Unbinder unbinder;
  @Inject HomePresenter homePresenter;
  private SDProgressDialog waitProgressDialog;
  private String deviceNo;

  private boolean useDevice = false;

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
    ivBack.setOnClickListener(v -> {
      Bundle bundle = ActivityUtils.getBundle("账户及设备详情");
      ActivityUtils.startFragment(getActivity(), AppConstant.FragmentType.UserInfo, bundle);
    });
    if (!homePresenter.getLoginStatus()) {
      navToLoginPage();
    }

    initRxBus();
  }

  private void initRxBus() {
    RxBus.getInstance().toObserverable(DeviceEvent.class).subscribe(deviceEvent -> {
      //Device 事件
    });
  }

  @Override protected void OnClickMenu() {
    super.OnClickMenu();
    //ToastUtils.showShort("help");
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (data != null) {
      deviceNo = data.getStringExtra(SIMPLE_SCAN_RESULT);
      ToastUtils.showShort(deviceNo);
      tvData.setText(deviceNo);
      //if (ContextCompat.checkSelfPermission(getActivity(),
      //    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      //  //申请WRITE_EXTERNAL_STORAGE权限
      //  ActivityCompat.requestPermissions(getActivity(),
      //      new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
      //      WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
      //} else {
      homePresenter.unDevice(deviceNo.split("\\*")[1]);
      //}
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick(R.id.btnScan) public void onViewClicked() {
    if (useDevice) {
      showMessage("当前有正在使用的设备");
      return;
    }
    int flag = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
    if (flag != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(getActivity(), new String[] {
          Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION,
          Manifest.permission.ACCESS_FINE_LOCATION
      }, CAMERA_REQUEST_CODE);
    } else {
      startActivityForResult(new Intent(getContext(), SimpleScanActivity.class), 1);
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
      case CAMERA_REQUEST_CODE:
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          if (grantResults[1] == PackageManager.PERMISSION_GRANTED || grantResults[2] == PackageManager.PERMISSION_GRANTED) {
            startActivityForResult(new Intent(getContext(), SimpleScanActivity.class), 1);
          } else {
            ToastUtils.showLong("请去权限列表开启位置权限");
          }
        } else {
          ToastUtils.showLong("请去权限列表开启相机权限");
        }

        break;
      default:
        break;
    }
  }

  @Override public void showWaitProgress() {
    waitProgressDialog = new SDProgressDialog(getContext(), "正在解锁中...");
    waitProgressDialog.setCancelable(false);
    waitProgressDialog.show();
  }

  @Override public void dismissWaitProgress() {
    if (waitProgressDialog != null) {
      waitProgressDialog.dismiss();
    }
  }

  @Override public void unDeviceSuccess() {
    tvData.setText("正在使用 " + deviceNo + " 设备");
    useDevice = true;
  }

  @Override public void unDeviceFail() {
    tvData.setText("");
    useDevice = false;
  }
}

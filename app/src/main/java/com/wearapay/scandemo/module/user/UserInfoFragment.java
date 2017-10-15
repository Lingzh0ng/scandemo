package com.wearapay.scandemo.module.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wearapay.scandemo.AppConstant;
import com.wearapay.scandemo.BaseMvpFragment;
import com.wearapay.scandemo.R;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.utils.ActivityUtils;
import com.wearapay.scandemo.utils.ToastUtils;
import com.wearapay.scandemo.weight.CustomLabelCell;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lyz on 2017/10/13.
 */

public class UserInfoFragment extends BaseMvpFragment {
    @BindView(R.id.cellBanlace)
    CustomLabelCell cellBanlace;
    @BindView(R.id.cellFeng)
    CustomLabelCell cellFeng;
    @BindView(R.id.bntCharge)
    Button bntCharge;
    @BindView(R.id.bntPwd)
    Button bntPwd;
    @BindView(R.id.btnInfo)
    Button btnInfo;
    @BindView(R.id.cellDeviceNo)
    CustomLabelCell cellDeviceNo;
    @BindView(R.id.btnDeviceManage)
    Button btnDeviceManage;
    @BindView(R.id.btnLogout)
    Button btnLogout;
    Unbinder unbinder;

    @Override
    public void onCleanBeforeDetach() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_info;
    }

    @Override
    protected BaseFragmentPresenter[] initPresenters() {
        return new BaseFragmentPresenter[0];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bntCharge, R.id.bntPwd, R.id.btnInfo, R.id.btnDeviceManage, R.id.btnLogout})
    public void onViewClicked(View view) {
        Bundle bundle = null;
        switch (view.getId()) {
            case R.id.bntCharge:
                ToastUtils.showShort("充值");
                bundle = ActivityUtils.getBundle("充值");
                ActivityUtils.startFragment(getActivity(), AppConstant.FragmentType.Charge,bundle);
                break;
            case R.id.bntPwd:
                ToastUtils.showShort("修改密码");
                bundle = ActivityUtils.getBundle("修改密码");
                ActivityUtils.startFragment(getActivity(), AppConstant.FragmentType.ChangePsw,bundle);
                break;
            case R.id.btnInfo:
                ToastUtils.showShort("设备使用历史信息");
                bundle = ActivityUtils.getBundle("设备使用历史信息");
                ActivityUtils.startFragment(getActivity(), AppConstant.FragmentType.DeviceHistory,bundle);
                break;
            case R.id.btnDeviceManage:
                ToastUtils.showShort("设备管理");
                bundle = ActivityUtils.getBundle("设备管理");
                ActivityUtils.startFragment(getActivity(), AppConstant.FragmentType.DeviceManager,bundle);
                break;
            case R.id.btnLogout:
                ToastUtils.showShort("退出登录");
                bundle = ActivityUtils.getBundle("登录");
                ActivityUtils.startFragment(getActivity(), AppConstant.FragmentType.Login,bundle);
                break;
        }
    }
}

package com.wearapay.scandemo.module.device;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.wearapay.scandemo.AppConstant;
import com.wearapay.scandemo.BaseMvpFragment;
import com.wearapay.scandemo.R;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.bean.Device;
import com.wearapay.scandemo.module.device.adapter.DeviceManangerRecyclerViewAdapter;
import com.wearapay.scandemo.utils.ActivityUtils;
import java.util.ArrayList;

/**
 * Created by lyz on 2017/10/13.
 */

public class DeviceManagerFragment extends BaseMvpFragment {
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.btnLoss)
    Button btnLoss;
    Unbinder unbinder;
    private ArrayList<Device> devices;
    private DeviceManangerRecyclerViewAdapter adapter;

    @Override
    public void onCleanBeforeDetach() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_device_manager;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        devices = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Device device = new Device();
            device.setDeviceNo("1234566" + i);
            devices.add(device);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.addItemDecoration(
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new DeviceManangerRecyclerViewAdapter(devices,
                new DeviceManangerRecyclerViewAdapter.OnListFragmentInteractionListener() {
                    @Override
                    public void onListFragmentInteraction(Device item) {
                        devices.remove(item);
                        adapter.notifyDataSetChanged();

                    }
                });
        recycleView.setHasFixedSize(true);
        recycleView.setNestedScrollingEnabled(false);
        recycleView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnLoss)
    public void onViewClicked() {
        Bundle bundle = ActivityUtils.getBundle("添加设备");
        ActivityUtils.addFragment(getFragmentManager(), this, AppConstant.FragmentType.AddDevice, bundle);
    }
}

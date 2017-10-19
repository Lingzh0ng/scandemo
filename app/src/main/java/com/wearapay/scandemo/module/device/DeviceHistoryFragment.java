package com.wearapay.scandemo.module.device;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.wearapay.scandemo.BaseMvpFragment;
import com.wearapay.scandemo.R;
import com.wearapay.scandemo.base.mvp.BaseFragmentPresenter;
import com.wearapay.scandemo.bean.Device;
import com.wearapay.scandemo.bean.DeviceHistory;
import com.wearapay.scandemo.module.device.adapter.DeviceHistoryRecyclerViewAdapter;
import java.util.ArrayList;

/**
 * Created by lyz on 2017/10/13.
 */

public class DeviceHistoryFragment extends BaseMvpFragment {
  @BindView(R.id.recycleView) RecyclerView recycleView;
  Unbinder unbinder;
  private DeviceHistoryRecyclerViewAdapter adapter;

  @Override public void onCleanBeforeDetach() {

  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_device_history;
  }

  @Override protected BaseFragmentPresenter[] initPresenters() {
    return new BaseFragmentPresenter[0];
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ArrayList<DeviceHistory> devices = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Device device = new Device();
      device.setDeviceNo("1234566" + i);
      device.setName("zhansan");
      DeviceHistory deviceHistory = new DeviceHistory();
      deviceHistory.setDevice(device);
      deviceHistory.setTime(System.currentTimeMillis());
      devices.add(deviceHistory);
    }

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

    recycleView.setLayoutManager(linearLayoutManager);
    //recycleView.addItemDecoration(
    //    new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    adapter = new DeviceHistoryRecyclerViewAdapter(devices,
        new DeviceHistoryRecyclerViewAdapter.OnListFragmentInteractionListener() {
          @Override public void onListFragmentInteraction(DeviceHistory item) {

          }
        });
    //recycleView.setHasFixedSize(true);
    //recycleView.setNestedScrollingEnabled(false);
    recycleView.setAdapter(adapter);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // TODO: inflate a fragment view
    View rootView = super.onCreateView(inflater, container, savedInstanceState);
    unbinder = ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}

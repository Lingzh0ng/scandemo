package com.wearapay.scandemo.module.device;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
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
import com.wearapay.scandemo.bean.ChargeType;
import com.wearapay.scandemo.module.device.adapter.DeviceChargerRecyclerViewAdapter;
import com.wearapay.scandemo.utils.ToastUtils;

/**
 * Created by lyz on 2017/10/13.
 */

public class ChargeFragment extends BaseMvpFragment {
  @BindView(R.id.recycleView) RecyclerView recycleView;
  Unbinder unbinder;
  private DeviceChargerRecyclerViewAdapter adapter;

  @Override public void onCleanBeforeDetach() {

  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_device_charge;
  }

  @Override protected BaseFragmentPresenter[] initPresenters() {
    return new BaseFragmentPresenter[0];
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
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

    recycleView.setLayoutManager(gridLayoutManager);
    //recycleView.addItemDecoration(
    //    new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    adapter = new DeviceChargerRecyclerViewAdapter(
        new DeviceChargerRecyclerViewAdapter.OnListFragmentInteractionListener() {
          @Override public void onListFragmentInteraction(ChargeType item) {
            ToastUtils.showShort("充值:" + item.getAccoumt());
          }
        });
    recycleView.setAdapter(adapter);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}

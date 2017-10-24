package com.wearapay.scandemo.module.device.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wearapay.scandemo.R;
import com.wearapay.scandemo.bean.ChargeType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceChargerRecyclerViewAdapter
    extends RecyclerView.Adapter<DeviceChargerRecyclerViewAdapter.ViewHolder> {

  private static final List<ChargeType> mValues = new ArrayList<>();
  private final OnListFragmentInteractionListener mListener;

  static {
//    mValues.add(ChargeType.CHARGE_10);
    mValues.add(ChargeType.CHARGE_50);
    mValues.add(ChargeType.CHARGE_100);
    mValues.add(ChargeType.CHARGE_150);
    mValues.add(ChargeType.CHARGE_200);
    mValues.add(ChargeType.CHARGE_300);
    mValues.add(ChargeType.CHARGE_500);
    mValues.add(ChargeType.CHARGE_1000);
//    mValues.add(ChargeType.CHARGE_10000);
  }

  public DeviceChargerRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
    mListener = listener;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_device_charge_list, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.btnCharge.setText(mValues.get(position).getShowMsg());

    holder.btnCharge.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (null != mListener) {
          mListener.onListFragmentInteraction(mValues.get(holder.getLayoutPosition()));
        }
      }
    });
  }

  @Override public int getItemCount() {
    return mValues == null ? 0 : mValues.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    ;
    @BindView(R.id.btnCharge) Button btnCharge;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      mView = view;
    }
  }

  public interface OnListFragmentInteractionListener {
    // TODO: Update argument type and name
    void onListFragmentInteraction(ChargeType item);
  }
}

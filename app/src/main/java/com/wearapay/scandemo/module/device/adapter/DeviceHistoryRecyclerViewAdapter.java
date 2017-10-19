package com.wearapay.scandemo.module.device.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.wearapay.scandemo.R;
import com.wearapay.scandemo.bean.DeviceHistory;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DeviceHistoryRecyclerViewAdapter
    extends RecyclerView.Adapter<DeviceHistoryRecyclerViewAdapter.ViewHolder> {

  private final SimpleDateFormat simpleDateFormat =
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
  private final List<DeviceHistory> mValues;
  private final OnListFragmentInteractionListener mListener;

  public DeviceHistoryRecyclerViewAdapter(List<DeviceHistory> items,
      OnListFragmentInteractionListener listener) {
    mValues = items;
    mListener = listener;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_device_history_list, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(final ViewHolder holder, int position) {
    //holder.cellBanlace.setSubTitle(mValues.get(position).getDeviceNo());
    DeviceHistory deviceHistory = mValues.get(position);
    holder.tv1.setText(deviceHistory.getDevice().getName());
    holder.tv2.setText(deviceHistory.getDevice().getDeviceNo());
    holder.tv3.setText(simpleDateFormat.format(deviceHistory.getTime()));
    holder.mView.setOnClickListener(new View.OnClickListener() {
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
    @BindView(R.id.tv1) TextView tv1;
    @BindView(R.id.tv2) TextView tv2;
    @BindView(R.id.tv3) TextView tv3;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      mView = view;
    }
  }

  public interface OnListFragmentInteractionListener {
    // TODO: Update argument type and name
    void onListFragmentInteraction(DeviceHistory item);
  }
}

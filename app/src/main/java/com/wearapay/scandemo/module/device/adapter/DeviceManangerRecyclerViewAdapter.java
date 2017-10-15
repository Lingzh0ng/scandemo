package com.wearapay.scandemo.module.device.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wearapay.scandemo.R;
import com.wearapay.scandemo.bean.Device;
import com.wearapay.scandemo.weight.CustomLabelCell;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceManangerRecyclerViewAdapter
        extends RecyclerView.Adapter<DeviceManangerRecyclerViewAdapter.ViewHolder> {

    private final List<Device> mValues;
    private final OnListFragmentInteractionListener mListener;


    public DeviceManangerRecyclerViewAdapter(List<Device> items,
                                             OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_mananger_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.cellBanlace.setSubTitle(mValues.get(position).getDeviceNo());

        holder.btnLoss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(mValues.get(holder.getLayoutPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues == null ? 0 : mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.cellBanlace)
        CustomLabelCell cellBanlace;
        @BindView(R.id.btnLoss)
        Button btnLoss;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;

        }
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Device item);
    }
}

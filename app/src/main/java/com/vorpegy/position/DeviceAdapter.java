package com.vorpegy.position;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by xiaopeng on 15/02/2017.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>{

    private Collection<Beacon> mBeacons;
    private ArrayList<Beacon> mSelectedBeacons;
    private LayoutInflater mInflater;

    public DeviceAdapter(Context context, Collection<Beacon> beacons){
        mBeacons = beacons;
        mInflater = LayoutInflater.from(context);
        mSelectedBeacons = new ArrayList<Beacon>();
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DeviceViewHolder holder = new DeviceViewHolder(mInflater.inflate(R.layout.device_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, final int position) {
        final ArrayList<Beacon> beacons = new ArrayList<Beacon>(mBeacons );
        String id3 = beacons.get(position).getId3().toString();
        holder.tv.setText(id3);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    mSelectedBeacons.add(beacons.get(position));
                }else {
                    mSelectedBeacons.remove(beacons.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeacons.size();
    }

    public ArrayList<Beacon> getSelectedBeacons(){
        return mSelectedBeacons;
    }

    static class DeviceViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv;
        CheckBox checkBox;

        public DeviceViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.holder_name);
            checkBox =  (CheckBox) itemView.findViewById(R.id.checkbox_item);
        }
    }
}

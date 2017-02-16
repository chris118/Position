package com.vorpegy.position;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;

public class DevicesActivity extends Activity implements
        LocationApplication.onRangeBeaconsInRegionListener {

    private DeviceAdapter adapter;
    private RecyclerView recycleView;
    Collection<Beacon> mBeacons;

    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);
        recycleView = (RecyclerView)this.findViewById(R.id.id_recyclerview);

        mBeacons = new ArrayList<Beacon>();
        adapter = new DeviceAdapter(this.getApplicationContext(), mBeacons);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);
//        recycleView.addItemDecoration(new DividerGridItemDecoration(this));
        recycleView.setAdapter(adapter);


        nextBtn = (Button)this.findViewById(R.id.next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(DevicesActivity.this, MainActivity.class);
                ArrayList<Beacon> beacons = adapter.getSelectedBeacons();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("beacons", beacons);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
        if (beacons != null) {
            if (beacons != null) {
                for (Beacon beacon : beacons) {
                    if(!mBeacons.contains(beacon)) {
                        mBeacons.add(beacon);
                    }
                }

            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((LocationApplication) this.getApplication())
                .setOnRangeBeaconsInRegionListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        ((LocationApplication) this.getApplication())
                .setOnRangeBeaconsInRegionListener(null);

    }
}

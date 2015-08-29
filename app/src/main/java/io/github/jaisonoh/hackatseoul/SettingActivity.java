package io.github.jaisonoh.hackatseoul;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import io.github.jaisonoh.hackatseoul.adapter.BeaconListAdapter;
import io.github.jaisonoh.hackatseoul.adapter.BleListAdapter;
import io.github.jaisonoh.hackatseoul.ble.BleScanner;
import io.github.jaisonoh.hackatseoul.model.Beacon;
import io.github.jaisonoh.hackatseoul.model.DataContainer;
import io.github.jaisonoh.hackatseoul.model.Preference;
import io.github.jaisonoh.hackatseoul.widget.CanvasView;

/**
 * Created by jaisonoh on 2015. 8. 29..
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    public static DataContainer mDataContainer = new DataContainer();

    private BleScanner mBleScanner;
    private BleListAdapter mBleListAdapter;
    private Preference mPreference;

    private Button btn_scan;
    private CanvasView mMapView;
    private ListView mListView;

    private int pos_x;
    private int pos_y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbarTitle.setText(getSupportActionBar().getTitle());
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0);

        }

        mMapView = (CanvasView)findViewById(R.id.map);
        mMapView.setDataContainer(mDataContainer);
        mListView = (ListView) findViewById(R.id.beacon_list);
        showBeacons();



        Button beaconAdd = (Button) findViewById(R.id.btn_beacon_add);
        beaconAdd.setOnClickListener(this);

        mBleListAdapter = new BleListAdapter(this);
        mBleScanner = new BleScanner(this, BluetoothAdapter.getDefaultAdapter(), mBleListAdapter);
    }

    private void showBeacons() {
        mPreference = new Preference(this);
        int index = mPreference.getPreferenceInt("beacon_number");

        if(index > 0) {
            mDataContainer.beacons.clear();
            for(int i=0; i<index; i++) {
                int id = mPreference.getPreferenceInt("beacon_id" + i);
                String mac = mPreference.getPreferenceString("beacon_mac" + i);
                int x = mPreference.getPreferenceInt("beacon_x" + i);
                int y = mPreference.getPreferenceInt("beacon_y" + i);

                Beacon beacons = new Beacon(id, mac, x, y);
                mDataContainer.beacons.add(beacons);
            }

            mListView.setAdapter(new BeaconListAdapter(getApplicationContext(), mDataContainer.getBeacons()));
            mMapView.invalidate();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_beacon_add:
                mBleScanner.scanLeDevice(true);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setTitle("Scan");

                // 버튼 생성
                alertBuilder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mBleScanner.scanLeDevice(false);
                                dialog.dismiss();
                            }
                        });

                alertBuilder.setAdapter(mBleScanner.getBleListAdapter(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mBleScanner.scanLeDevice(false);

                        final String mac = mBleScanner.getBleListAdapter().getItem(which).toString();

                        AlertDialog.Builder innBuilder = new AlertDialog.Builder(SettingActivity.this);
                        LayoutInflater inflater = LayoutInflater.from(SettingActivity.this);
                        final View v = inflater.inflate(R.layout.custom_dialog, null);
                        innBuilder.setView(v);


                        final CanvasView map = (CanvasView) v.findViewById(R.id.map);
                        map.setDataContainer(mDataContainer);

                        mDataContainer.beacons.clear();

                        final TextView macText = (TextView) v.findViewById(R.id.mac);
                        macText.setText("Beacon : " + mac);

                        final EditText xPoint = (EditText) v.findViewById(R.id.x);
                        final EditText yPoint = (EditText) v.findViewById(R.id.y);
                        final Button setPoint = (Button) v.findViewById(R.id.set_point);

                        map.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                mDataContainer.beacons.clear();
                                final int x = (int) event.getX();
                                final int y = (int) event.getY();
                                pos_x = x;
                                pos_y = y;

                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        xPoint.setText(Integer.toString(x));
                                        yPoint.setText(Integer.toString(y));

                                        Beacon newBeacon = new Beacon(x, y);
                                        mDataContainer.beacons.add(newBeacon);
                                        map.invalidate();

                                        break;
                                }
                                return false;
                            }
                        });

                        setPoint.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try {
                                    final int x = Integer.parseInt(xPoint.getText().toString());
                                    final int y = Integer.parseInt(yPoint.getText().toString());
                                    pos_x = x;
                                    pos_y = y;
                                    mDataContainer.beacons.clear();
                                    Beacon newBeacon = new Beacon(x, y);
                                    mDataContainer.beacons.add(newBeacon);
                                    map.invalidate();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        innBuilder.setTitle("Beacon Location");
                        innBuilder.setPositiveButton(
                                "Confirm",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        int index = mPreference.getPreferenceInt("beacon_number");

                                        mPreference.putPreference("beacon_id"+index,index);
                                        mPreference.putPreference("beacon_mac"+index, mac);
                                        mPreference.putPreference("beacon_x"+index, pos_x);
                                        mPreference.putPreference("beacon_y"+index, pos_y);

                                        mPreference.putPreference("beacon_number", index+1);


                                        dialog.dismiss();
                                        showBeacons();
                                    }
                                });
                        innBuilder.show();
                    }
                });
                alertBuilder.show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

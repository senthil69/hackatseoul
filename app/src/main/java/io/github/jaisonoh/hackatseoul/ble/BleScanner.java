package io.github.jaisonoh.hackatseoul.ble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

/**
 * Created by jaisonoh on 2015. 8. 20..
 */
public class BleScanner {
    private Activity activity;
    private BleListAdapter bleListAdapter;
    private BluetoothAdapter bluetoothAdapter;

    public BleScanner(Activity activity, BluetoothAdapter bluetoothAdapter, BleListAdapter bleListAdapter) {
        this.activity = activity;
        this.bleListAdapter = bleListAdapter;
        this.bluetoothAdapter = bluetoothAdapter;
    }


    public void scanLeDevice(final boolean enable) {
        if (enable) {
            bluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            bluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {


                @Override
                public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {

                    //final int txPower = new BleDataParsing().parseScanRecord(scanRecord);

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bleListAdapter.addDevice(device);
                            bleListAdapter.notifyDataSetChanged();
                        }
                    });
                }
            };

    public BleListAdapter getBleListAdapter() {
        return bleListAdapter;
    }
}

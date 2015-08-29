package io.github.jaisonoh.hackatseoul.ble;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.jaisonoh.hackatseoul.R;
import io.github.jaisonoh.hackatseoul.model.Beacon;


/**
 * Created by jaisonoh on 2015. 8. 19..
 */
// Adapter for holding devices found through scanning.
public class BleListAdapter extends BaseAdapter {
    private ArrayList<BluetoothDevice> mLeDevices;
    private ArrayList<Beacon> mBeaconInfo;
    private LayoutInflater mInflator;

    public BleListAdapter(Activity activity) {
        super();
        mLeDevices = new ArrayList<BluetoothDevice>();
        mBeaconInfo = new ArrayList<Beacon>();
        mInflator = activity.getLayoutInflater();
    }

    public void addDevice(BluetoothDevice device) {
        Beacon data = new Beacon(device.getName(), device.getAddress());

        if(mLeDevices.contains(device)) {
            int index = mLeDevices.indexOf(device);
            mBeaconInfo.remove(index);
            mBeaconInfo.add(index, data);
        } else {
            mBeaconInfo.add(data);
            mLeDevices.add(device);
        }
    }

    public String getName(int position) {
        String name;

        name = mLeDevices.get(position).getName();
        if(name == null) {
            name = "Unknown Device";
        }
        return name;
    }

    public void clear() {
        mLeDevices.clear();
    }

    @Override
    public int getCount() {
        return mLeDevices.size();
    }

    @Override
    public Object getItem(int i) {

        return mLeDevices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            view = mInflator.inflate(R.layout.listitem_device, null);
            viewHolder = new ViewHolder();
            //viewHolder.deviceDistance = (TextView) view.findViewById(R.id.device_distance);
            viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
            viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);

            //viewHolder.deviceDistance.setAnimation(new BeaconAnim().blink());
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Beacon device = mBeaconInfo.get(i);

        final String deviceName = device.getName();

        if (deviceName != null && deviceName.length() > 0) {
            viewHolder.deviceName.setText(deviceName);
        }
        else {
            viewHolder.deviceName.setText("unknown_device");
        }

        viewHolder.deviceAddress.setText("MAC : " + device.getAddress());
        return view;
    }

    static class ViewHolder {
        TextView deviceDistance;
        TextView deviceName;
        TextView deviceAddress;
    }
}

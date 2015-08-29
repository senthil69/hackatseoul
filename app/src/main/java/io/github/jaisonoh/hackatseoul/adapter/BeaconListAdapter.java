package io.github.jaisonoh.hackatseoul.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.github.jaisonoh.hackatseoul.R;
import io.github.jaisonoh.hackatseoul.model.Beacon;

/**
 * Created by jaisonoh on 2015. 7. 26..
 */
public class BeaconListAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mInflater;
    ArrayList<Beacon> mBeaconList;

    public BeaconListAdapter(Context context, ArrayList<Beacon> mBeaconList) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mBeaconList = mBeaconList;
    }

    private static class ViewHolder {
        public TextView beaconId;
        public TextView macAddr;
        public TextView coordinate;
    }

    @Override
    public int getCount() {
        return mBeaconList.size();
    }

    @Override
    public Beacon getItem(int position) {
        return mBeaconList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_beacon, null);

            holder = new ViewHolder();
            holder.beaconId = (TextView) convertView.findViewById(R.id.beacon_id);
            holder.macAddr = (TextView) convertView.findViewById(R.id.mac_addr);
            holder.coordinate = (TextView) convertView.findViewById(R.id.coordinate);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Beacon beacon = mBeaconList.get(position);

        holder.beaconId.setText("" + beacon.getBeacon_id());
        holder.macAddr.setText(beacon.getAddress());
        holder.coordinate.setText(beacon.getPos_x() + ", " + beacon.getPos_y());

        return convertView;
    }
}

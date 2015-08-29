package io.github.jaisonoh.hackatseoul.model;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by jaisonoh on 2015. 8. 14..
 */
public class Beacon {

    private int beacon_id;
    private String name;
    private String address;
    private String uuid;
    private String major;
    private String minor;
    private int rssi;
    private int txPower;

    private int pos_x;
    private int pos_y;
    private String distance;

    public Beacon(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Beacon(int pos_x, int pos_y) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }

    public Beacon(int beacon_id, String address, int pos_x, int pos_y) {
        this.beacon_id = beacon_id;
        this.address = address;
        this.pos_x = pos_x;
        this.pos_y =pos_y;
    }


    public int getBeacon_id() {
        return beacon_id;
    }

    public void setBeacon_id(int beacon_id) {
        this.beacon_id = beacon_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getTxPower() {
        return txPower;
    }

    public void setTxPower(int txPower) {
        this.txPower = txPower;
    }

    public int getPos_x() {
        return pos_x;
    }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}

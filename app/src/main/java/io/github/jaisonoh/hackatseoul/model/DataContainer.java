package io.github.jaisonoh.hackatseoul.model;

import java.util.ArrayList;

/**
 * Created by jaisonoh on 2015. 8. 27..
 */
public class DataContainer
{
    public ArrayList<Beacon> beacons = new ArrayList<>();


    public Beacon findBeacon(int beaconID) {
        for(int i = 0; i < beacons.size(); ++i)
            if(beacons.get(i).getBeacon_id() == beaconID)
                return beacons.get(i);

        return null;
    }
}

package io.github.jaisonoh.hackatseoul.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

import io.github.jaisonoh.hackatseoul.R;
import io.github.jaisonoh.hackatseoul.model.Beacon;
import io.github.jaisonoh.hackatseoul.model.DataContainer;


public class CanvasView extends ImageView
{
    int beacon_radius[] = { 20, 30, 40 };
    Paint beaconZonePaint[] = { null, null, null };
    Paint beaconPaint;

    private DataContainer m_dataContainer;

    public CanvasView(Context context) {
        super(context);
        initPaint();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initPaint() {

        beaconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        beaconPaint.setColor(getResources().getColor(R.color.beacon));
        beaconPaint.setStyle(Paint.Style.FILL);

        beaconZonePaint[0] = new Paint(Paint.ANTI_ALIAS_FLAG);
        beaconZonePaint[0].setColor(getResources().getColor(R.color.near));
        beaconZonePaint[0].setStyle(Paint.Style.FILL);

        beaconZonePaint[1] = new Paint(Paint.ANTI_ALIAS_FLAG);
        beaconZonePaint[1].setColor(Color.BLUE);
        beaconZonePaint[1].setStyle(Paint.Style.FILL);

        beaconZonePaint[2] = new Paint(Paint.ANTI_ALIAS_FLAG);
        beaconZonePaint[2].setColor(Color.GREEN);
        beaconZonePaint[2].setStyle(Paint.Style.FILL);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
    }

    public void setDataContainer(DataContainer dataContainer)
    {
        m_dataContainer = dataContainer;
    }


    Map<Integer, Integer> attachedUserCounter = new HashMap<>();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(m_dataContainer == null)
            return;

        for(int i = 0; i < m_dataContainer.beacons.size(); ++i)
        {
            Beacon beacon = m_dataContainer.beacons.get(i);

            //drawBeaconZone(beacon.getPos_x(), beacon.getPos_y(), 2, canvas);
            //drawBeaconZone(beacon.getPos_x(), beacon.getPos_y(), 1, canvas);
            drawBeaconZone(beacon.getPos_x(), beacon.getPos_y(), 0, canvas);

            drawBeacon(beacon.getPos_x(), beacon.getPos_y(), canvas);
        }
    }

    private void drawBeacon(int x, int y, Canvas canvas)
    {
        final int beaconSize = 10;
        canvas.drawCircle(x, y, beaconSize, beaconPaint);
    }

    private void drawBeaconZone(int x, int y, int distance, Canvas canvas)
    {
        canvas.drawCircle(x, y, beacon_radius[distance], beaconZonePaint[distance]);
    }

}
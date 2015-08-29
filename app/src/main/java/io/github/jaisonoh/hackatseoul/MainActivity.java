package io.github.jaisonoh.hackatseoul;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.github.jaisonoh.hackatseoul.adapter.BleListAdapter;
import io.github.jaisonoh.hackatseoul.ble.BleScanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BleScanner mBleScanner;
    private BleListAdapter mBleListAdapter;

    private Button btn_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbarTitle.setText(getSupportActionBar().getTitle());
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mBleListAdapter = new BleListAdapter(this);
        mBleScanner = new BleScanner(this, BluetoothAdapter.getDefaultAdapter(), mBleListAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

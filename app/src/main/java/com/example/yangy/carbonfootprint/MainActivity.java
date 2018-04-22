package com.example.yangy.carbonfootprint;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.os.BatteryManager;
import android.widget.Toast;

import com.genymobile.mirror.annotation.Class;

import junit.runner.Version;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = MainActivity.this;

        BatteryManager mBatteryManager = (BatteryManager) context.getSystemService(context.BATTERY_SERVICE);

        int initialCap = (int) mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        int currentCap;

        TextView init = findViewById(R.id.initial);
        init.setText(String.valueOf(initialCap));

        IntentFilter batteryChangeFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent yetAnotherIntent = context.registerReceiver(null, batteryChangeFilter);

        TextView dif = findViewById(R.id.battery);
        dif.setText("some text");

        /*
        TextView dif = findViewById(R.id.battery);
        dif.setOnClickListener(new BatteryDifOnClickListener(context, mBatteryManager, initialCap));



        TextView curr = findViewById(R.id.current);
        curr.setOnClickListener(new BatteryCurrentOnClickListener(context, mBatteryManager));


        TextView status = findViewById(R.id.status);
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentStatus = mBatteryManager.
                        getIntProperty(BatteryManager.BATTERY_PROPERTY_STATUS);
                Toast.makeText(MainActivity.this,
                        String.valueOf(currentStatus),Toast.LENGTH_SHORT).show();
            }
        });

        TextView charging = findViewById(R.id.charging);
        charging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "some text", Toast.LENGTH_SHORT).show();
            }
        });
        */

        /*
        IntentFilter batteryFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, batteryFilter);

        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        Toast.makeText(MainActivity.this, String.valueOf(status) +" "+ String.valueOf(isCharging),
                Toast.LENGTH_SHORT).show();
        */

    }

    // github code for when I was trying to figure out
    // how to implement this for older APIs
    // please ignore

    public void getBatteryCapacity() {
        Object mPowerProfile_ = null;

        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try {
            mPowerProfile_ = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class).newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            double batteryCapacity = (Double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getAveragePower", java.lang.String.class)
                    .invoke(mPowerProfile_, "battery.capacity");
            Toast.makeText(MainActivity.this, batteryCapacity + " mah",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class BatteryDifOnClickListener implements View.OnClickListener {
    Context context;
    BatteryManager mBatteryManager;
    int initial;
    int current;

    public BatteryDifOnClickListener(Context context, BatteryManager manager, int initial) {
        this.context = context;
        this.mBatteryManager = manager;
        this.initial = initial;
    }

    @Override
    public void onClick(View v) {
        current = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
        Toast.makeText(context, String.valueOf(current - initial), Toast.LENGTH_SHORT).show();
    }
}

class BatteryInitialOnClickListener implements View.OnClickListener {
    Context context;
    int initial;

    public BatteryInitialOnClickListener(Context context, int initial) {
        this.context = context;
        this.initial = initial;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(context, String.valueOf(initial), Toast.LENGTH_SHORT).show();
    }
}

class BatteryCurrentOnClickListener implements View.OnClickListener {
    Context context;
    BatteryManager mBatteryManager;
    int current;

    public BatteryCurrentOnClickListener(Context context, BatteryManager manager) {
        this.context = context;
        this.mBatteryManager = manager;
    }

    @Override
    public void onClick(View v) {
        current = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
        Toast.makeText(context, String.valueOf(current), Toast.LENGTH_SHORT).show();
    }
}
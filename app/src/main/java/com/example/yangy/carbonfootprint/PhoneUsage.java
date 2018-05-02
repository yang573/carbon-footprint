package com.example.yangy.carbonfootprint;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PhoneUsage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_usage);

        Context context = PhoneUsage.this;
        BatteryManager mBatteryManager = (BatteryManager) context.getSystemService(BATTERY_SERVICE);

        //BroadcastReceiver receiver = new BatteryReceiver();

        IntentFilter batteryChangeFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, batteryChangeFilter);

        int startLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int maxLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        int currentLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);




    }

    public static double getBatteryCapacity() {
        return 0.0;
    }

    public static int getBatteryPercentage(BatteryManager manager) {
        return manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
    }
}

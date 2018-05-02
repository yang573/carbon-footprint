package com.example.yangy.carbonfootprint;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class PhoneUsage extends AppCompatActivity {

    final int batteryCap = (int) getBatteryCapacity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_usage);

        Context context = PhoneUsage.this;
        BatteryManager mBatteryManager = (BatteryManager) context.getSystemService(BATTERY_SERVICE);

        // truncation of % sign (eg. 68% => 68)
        int currentBatteryPercent = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);;

        int electricChargeUsed = (batteryCap / 100) * (100 - currentBatteryPercent);

        TextView displayECUsed = (TextView) findViewById(R.id.phoneUsage);
        displayECUsed.setText(String.valueOf(electricChargeUsed) + " mAh used");

        double batteryVoltage = 4.0;
        double energyUsed = (electricChargeUsed * batteryVoltage) / 1000;
        energyUsed = Math.round(energyUsed * 100) / 100;

        TextView displayEnergyUsed = (TextView) findViewById(R.id.phoneEnergyUsage);
        displayEnergyUsed.setText(String.valueOf(energyUsed) + " Watt hours");



        // limitations placed on system broadcasts
        //BroadcastReceiver receiver = new BatteryReceiver();

        // this uses broadcasts
        /*
        IntentFilter batteryChangeFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, batteryChangeFilter);


        int startLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int maxLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        */

        //int currentLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

        /*
        // JobScheduler for this class (why are you using this class?)
        long periodLength = 60000 / 4;
        JobScheduler scheduleBatteryLevel = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName currentLevel = new ComponentName(this, this.getClass());
        JobInfo batteryJob = new JobInfo
                .Builder(R.integer.batteryLevel, currentLevel)
                .setPeriodic(periodLength)
                .build();
        scheduleBatteryLevel.schedule(batteryJob);
        */


        // AlarmManager probably isn't suited
        // considering limitations placed on broadcast and service
        /*
        try {
            AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

            // cannot use this
            PendingIntent getBatteryLevel =

            alarm.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, new Date().getTime(),
                    AlarmManager.INTERVAL_FIFTEEN_MINUTES, getBatteryLevel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        */


    }

    // From StackOverflow
    // NOT MINE
    // This could be a dummy value, so just pretend it is.
    // Returns Battery Capacity in mAh (eg. 3000 mAh)
    public double getBatteryCapacity() {
        Object mPowerProfile = null;

        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try {
            mPowerProfile = Class
                    .forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class)
                    .newInstance(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        double batteryCapacity = 0.0;

        try {
            batteryCapacity = (Double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getAveragePower", java.lang.String.class)
                    .invoke(mPowerProfile, "battery.capacity");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return batteryCapacity;
    }
}

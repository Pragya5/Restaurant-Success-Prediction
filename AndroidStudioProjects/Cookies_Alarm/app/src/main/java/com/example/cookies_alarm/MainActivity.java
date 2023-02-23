package com.example.cookies_alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity
{
    AlarmReceiver broadcast = new AlarmReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("MAINACTIVITY CREATED", "MainActivity Created");
        IntentFilter intent = new IntentFilter();
        Log.i("MAINACTIVITY RESUMED", "MAINACTIVITY RESUMED");
        intent.addAction("android.intent.action.BATTERY_OKAY");
        intent.addAction("android.intent.action.ACTION_PHONE_STATE_CHANGED:");
        intent.addAction("android.intent.action.BATTERY_LOW");
        intent.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intent.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        intent.addAction("android.intent.action.ACTION_BATTERY_CHANGED");

        // registering receiver
        registerReceiver(broadcast, intent);
        // Find the container where the fragment will be added
        FrameLayout container = findViewById(R.id.fragment_container);

        // Create an instance of the fragment
        AlarmSettingFragment alarmSettingFragment = new AlarmSettingFragment();

        if (savedInstanceState == null) {
            // Get the FragmentManager and start a FragmentTransaction
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AlarmSettingFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        unregisterReceiver(broadcast);
    }
}

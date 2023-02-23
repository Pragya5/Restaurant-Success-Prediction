package com.example.cookies_alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {

        //STOP SERVICE FOR LOW-BATTERY && POWER CONNECTED
        //STOP SERVICE FOR OKAY-BATTERY && POWER CONNECTED

        if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
            int bat = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            if (bat <= 15 && intent.getAction().equals(Intent.ACTION_POWER_CONNECTED))
            {
                Toast.makeText(context, "" + "CHARGE YOUR PHONE (<15%)", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "STOPPING SERVICE FOR LOW BATTERY & POWER CONNECTED!", Toast.LENGTH_LONG).show();
                context.stopService(new Intent(context, AlarmService.class));
                Log.i("BATTERY IS LOW", "CHARGE YOUR PHONE (<15%)");
            }
            else if (bat > 15 && intent.getAction().equals(Intent.ACTION_POWER_CONNECTED))
            {
                Toast.makeText(context, "" + "BATTERY IS OKAY (>15%)", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "STOPPING SERVICE FOR LOW BATTERY & POWER CONNECTED!", Toast.LENGTH_LONG).show();
                context.stopService(new Intent(context, AlarmService.class));
                Log.i("Battery is OKAY", "Battery level okay");

            }
        }

        if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW) && intent.getAction().equals(Intent.ACTION_POWER_CONNECTED))
        {
            Toast.makeText(context, "" + "CHARGE YOUR PHONE (<15%)", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "STOPPING SERVICE FOR LOW BATTERY & POWER CONNECTED!", Toast.LENGTH_LONG).show();
            context.stopService(new Intent(context, AlarmService.class));
            Log.i("BATTERY IS LOW", "CHARGE YOUR PHONE (<15%)");
        }

        if (intent.getAction().equals(Intent.ACTION_BATTERY_OKAY) && intent.getAction().equals(Intent.ACTION_POWER_CONNECTED))
        {
            Toast.makeText(context, "" + "BATTERY IS OKAY (>15%)", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "STOPPING SERVICE FOR LOW BATTERY & POWER CONNECTED!", Toast.LENGTH_LONG).show();
            context.stopService(new Intent(context, AlarmService.class));
            Log.i("Battery is OKAY", "Battery level okay");
        }

        else if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW) && intent.getAction().equals(Intent.ACTION_POWER_CONNECTED))

        {
            Toast.makeText(context, "" + "BATTERY IS LOW (<15%)", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "STOPPING SERVICE FOR BATTERY LOW & POWER CONNECTED!", Toast.LENGTH_LONG).show();
            context.stopService(new Intent(context, AlarmService.class));
            Log.i("STOPPING SERVICE!", "Power Connected & Battery low");

        }

        else if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED) && intent.getAction().equals(Intent.ACTION_POWER_CONNECTED))
        {
            Toast.makeText(context, "" + "BATTERY IS OKAY (>15%)", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "STOPPING SERVICE FOR BATTERY OKAY & POWER CONNECTED!", Toast.LENGTH_LONG).show();
            context.stopService(new Intent(context, AlarmService.class));
            Log.i("STOPPING SERVICE:", "Power Connected & Battery is Okay");

        }

        else if (intent.getAction().equals(Intent.ACTION_CALL) && intent.getAction().equals(Intent.ACTION_BATTERY_LOW))
        {
            Log.i("STOPPING SERVICE:", "Incoming Call & Low Battery");
            Toast.makeText(context, "STOPPING SERVICE FOR INCOMING CALL & LOW BATTERY!", Toast.LENGTH_LONG).show();
            context.stopService(new Intent(context, AlarmService.class));
        }

        else if (intent.getAction().equals(Intent.ACTION_CALL) && intent.getAction().equals(Intent.ACTION_BATTERY_LOW))
        {
            Log.i("STOPPING SERVICE:", "Incoming Call & OKAY Battery");
            Toast.makeText(context, "STOPPING SERVICE FOR INCOMING CALL & OKAY BATTERY!", Toast.LENGTH_LONG).show();
            context.stopService(new Intent(context, AlarmService.class));
        }

    }
}

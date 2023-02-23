package com.example.cookies_alarm;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class AlarmService extends Service {
    private int inputHour, inputMinute;
    private MediaPlayer mediaPlayer;
    private Handler handler;
    private Runnable runnable;
    private boolean isRinging;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                checkAlarm();
                handler.postDelayed(this, 10000);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        inputHour = intent.getIntExtra("hour", -1);
        inputMinute = intent.getIntExtra("minute", -1);
        isRinging = false;
        handler.post(runnable);
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        stopMediaPlayer(); // Stop the media player
        Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();
        Log.d("COOKIE ALARM IS STOPPED", "Service stopped");
    }

    private void checkAlarm() {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        if (currentHour == inputHour && currentMinute == inputMinute && !isRinging) {
            isRinging = true;
            if (mediaPlayer != null)
            {
                mediaPlayer.start();
                Toast.makeText(this, "Alarm ringing", Toast.LENGTH_SHORT).show();

            }
            Log.d("COOKIE ALARM IS RINGING", "Alarm ringing");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isRinging = false;
                    Toast.makeText(AlarmService.this, "Alarm stopped", Toast.LENGTH_SHORT).show();
                    Log.d("COOKIE ALARM IS STOPPED", "Alarm stopped");
                    stopMediaPlayer();
                    resetTimePicker();
                    //stopSelf();
                }
            }, 10000);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void stopMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void resetTimePicker() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("RESET_TIME_PICKER");
        sendBroadcast(broadcastIntent);
    }
}

package com.example.cookies_alarm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Locale;

public class AlarmSettingFragment extends Fragment {
    private TimePicker timePicker;
    private Button startButton, stopButton;
    private Intent serviceIntent;
    TextView inputTextView;
    private String inputText = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_setting, container, false);

        timePicker = view.findViewById(R.id.time_picker);
        startButton = view.findViewById(R.id.start_button);
        stopButton = view.findViewById(R.id.stop_button);
        inputTextView = view.findViewById(R.id.input_text);
        serviceIntent = new Intent(getActivity(), AlarmService.class);

        // Restore the state of the TextView if it exists
        if (savedInstanceState != null) {
            inputText = savedInstanceState.getString("inputText", "");
            inputTextView.setText(inputText);
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    hour = timePicker.getHour();
                }
                int minute = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    minute = timePicker.getMinute();
                }
                String amOrPm = "";
                if (hour < 12) {
                    amOrPm = "AM";
                } else {
                    amOrPm = "PM";
                }

                String input = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
                //inputText = "THE ALARM TIMING IS : " + input + " " + amOrPm;
                //inputTextView.setText(inputText);
                String newInputText = "THE ALARM TIMING IS : " + input + " " + amOrPm;

                if (inputText.isEmpty()) {
                    inputText = newInputText;
                } else {
                    inputText += "\n" + newInputText;
                }

                inputTextView.setText(inputText);
                serviceIntent.putExtra("hour", hour);
                serviceIntent.putExtra("minute", minute);
                getActivity().startService(serviceIntent);
                Toast.makeText(getActivity(), "Service started", Toast.LENGTH_SHORT).show();
                Log.i("SERVICE STARTED:", "SERVICE STARTED");
                Log.i("SELECTED TIME IS:", "Selected time is: " + hour + ":" + minute + " " + amOrPm);

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().stopService(serviceIntent);
                Toast.makeText(getActivity(), "Service stopped", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // Save the state of the TextView
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("inputText", inputText);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            outState.putInt("hour", hour);
            outState.putInt("minute", minute);
        }
    }

    // Restore the state of the TextView
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            inputText = savedInstanceState.getString("inputText", "");
            inputTextView.setText(inputText);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                int hour = savedInstanceState.getInt("hour");
                int minute = savedInstanceState.getInt("minute");
                serviceIntent.putExtra("hour", hour);
                serviceIntent.putExtra("minute", minute);
            }
        }
    }
}
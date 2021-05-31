package com.example.esp_rgb;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.suke.widget.SwitchButton;

import java.util.Calendar;

public class PopupHengio extends Activity {
    TimePicker timePicker_start;
    TimePicker timePicker_stop;
    TextView textView_time_start;
    TextView textView_time_stop;
    com.suke.widget.SwitchButton button_OnOff;
    Button button_OK;
    Button button_Cancel;
    int hour_begin = 0, min_begin = 0;
    int hour_finish = 0, min_finish = 0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean OnOff = true;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_hengio);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);// Trong suot nen PopUp
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .9), (int) (height * .5));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = 300;
        getWindow().setAttributes(params);
        textView_time_start = (TextView) findViewById(R.id.text_time_start);
        textView_time_stop = (TextView) findViewById(R.id.text_time_stop);
        button_OK = (Button) findViewById(R.id.btn_ok);
        button_Cancel = (Button) findViewById(R.id.btn_cancel);
        button_OnOff = (com.suke.widget.SwitchButton) findViewById(R.id.switch_button);
        button_OnOff.toggle();     //switch state
        button_OnOff.toggle(true);//switch without animation
        button_OnOff.setShadowEffect(true);//disable shadow effect
        button_OnOff.setEnabled(true);//disable button
        button_OnOff.setEnableEffect(true);//disable the switch animation
        timePicker_start = (TimePicker) findViewById(R.id.timer_start);
        timePicker_start.setIs24HourView(true);
        timePicker_stop = (TimePicker) findViewById(R.id.timer_stop);
        timePicker_stop.setIs24HourView(true);
        initPreferences();
        Switch_ONOFF();
        TimePicker();
        Button_OK();
        Button_Cancel();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        hour_begin = sharedPreferences.getInt("hourBegin", 0);
        min_begin = sharedPreferences.getInt("minBegin", 0);
        hour_finish = sharedPreferences.getInt("hourFinish", 0);
        min_finish = sharedPreferences.getInt("minFinish", 0);
        OnOff = sharedPreferences.getBoolean("Switch", true);
        textView_time_start.setText(hour_begin + " : " + min_begin);
        textView_time_stop.setText(hour_finish + " : " + min_finish);
        timePicker_start.setHour(hour_begin);
        timePicker_start.setMinute(min_begin);
        timePicker_stop.setHour(hour_finish);
        timePicker_stop.setMinute(min_finish);
        button_OnOff.setChecked(OnOff);
        if (OnOff == true) {
            timePicker_start.setEnabled(true);
            timePicker_stop.setEnabled(true);
        } else {
            timePicker_start.setEnabled(false);
            timePicker_stop.setEnabled(false);
        }
    }

    public void TimePicker() {
        timePicker_start.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                textView_time_start.setText(hourOfDay + " : " + minute);
                hour_begin = hourOfDay;
                min_begin = minute;
            }
        });
        timePicker_stop.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                textView_time_stop.setText(hourOfDay + " : " + minute);
                hour_finish = hourOfDay;
                min_finish = minute;
            }
        });
    }

    public void Switch_ONOFF() {
        button_OnOff.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                OnOff = button_OnOff.isChecked();
                if (OnOff == true) {
                    timePicker_start.setEnabled(true);
                    timePicker_stop.setEnabled(true);
                } else {
                    timePicker_start.setEnabled(false);
                    timePicker_stop.setEnabled(false);
                }
                Log.d(String.valueOf(OnOff), "onClick: ");
            }
        });

    }

    public void Button_OK() {
        button_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                editor.putInt("hourBegin", hour_begin);
                editor.putInt("minBegin", min_begin);
                editor.putInt("hourFinish", hour_finish);
                editor.putInt("minFinish", min_finish);
                editor.putBoolean("Switch", OnOff);
                editor.commit();
                Log.d(String.valueOf(1), "onClick: ");
                intent.putExtra("ONOFF", String.valueOf((OnOff == true) ? 1 : 0));
                intent.putExtra("TimeBegin", hour_begin + ":" + min_begin);
                intent.putExtra("TimeFinish", hour_finish + ":" + min_finish);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void Button_Cancel() {
        button_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
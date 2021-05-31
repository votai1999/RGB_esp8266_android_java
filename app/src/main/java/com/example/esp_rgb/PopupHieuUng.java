package com.example.esp_rgb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

public class PopupHieuUng extends Activity {
    ImageButton imageButton_fade;
    ImageButton imageButton_fade_random;
    ImageButton imageButton_color_1;
    ImageButton imageButton_color_2;
    ImageButton imageButton_color_3;
    ImageButton imageButton_color_4;
    ImageButton imageButton_color_5;
    ImageButton imageButton_color_6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_hieu_ung);
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
        imageButton_fade = (ImageButton) findViewById(R.id.btn_fade);
        imageButton_fade_random = (ImageButton) findViewById(R.id.btn_fade_random);
        imageButton_color_1 = (ImageButton) findViewById(R.id.btn_color_1);
        imageButton_color_2 = (ImageButton) findViewById(R.id.btn_color_2);
        imageButton_color_3 = (ImageButton) findViewById(R.id.btn_color_3);
        imageButton_color_4 = (ImageButton) findViewById(R.id.btn_color_4);
        imageButton_color_5 = (ImageButton) findViewById(R.id.btn_color_5);
        imageButton_color_6 = (ImageButton) findViewById(R.id.btn_color_6);
        Set_Btn_Color();
    }

    public void Set_Btn_Color() {
        Intent intent = new Intent();
        imageButton_fade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Color", 2);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        imageButton_fade_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Color", 3);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        imageButton_color_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Color", 4);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        imageButton_color_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Color", 5);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        imageButton_color_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Color", 6);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        imageButton_color_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Color", 7);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        imageButton_color_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Color", 8);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        imageButton_color_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Color", 9);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
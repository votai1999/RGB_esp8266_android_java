package com.example.esp_rgb;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.DhcpInfo;
import android.os.Bundle;

import java.io.IOException;

import java.io.OutputStream;
import java.net.Socket;

import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.format.Formatter;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.skydoves.colorpickerview.listeners.ColorListener;
import com.suke.widget.SwitchButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.WeakHashMap;

import tech.gusavila92.websocketclient.WebSocketClient;

import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;

@SuppressLint("SetTextI18n")
public class MainActivity extends Activity {
    Thread Thread1 = null;

    TextView textView_connect;
    LinearLayout Led_connect;
    Button button_HieuUng;
    Button button_HenGio;
    ColorPickerView colorPickerView;
    //    private final SurfaceHolder surfaceHolder;
//    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    int SERVER_PORT = 8888;
    String IP = "192.168.4.1";
    SeekBar seekbar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean check = true;
    boolean check_start_websocket = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView_connect = (TextView) findViewById(R.id.text_status);
        Led_connect = (LinearLayout) findViewById(R.id.led_status);
        button_HieuUng = (Button) findViewById(R.id.btn_hieuung);
        button_HenGio = (Button) findViewById(R.id.btn_hengio);
        setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES);
        refresh();
        IntenPopup_Hieuung();
        IntenPopup_Hengio();

    }

    public void SeekBar() {
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String seekbarValue = "%" + String.valueOf(progress) + "\n";
                Log.d(String.valueOf(progress), "onProgressChanged: ");
                if (!seekbarValue.isEmpty()) {
                    new Thread(new Thread3(seekbarValue)).start();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void refresh() {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        String ID = getWifiIPAddress();
                        if (ID.equals(IP) == true && check == true) {
//                            if (a != 0) {
//                                output.close();
//                                try {
//                                    socket.close();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
                            Thread1 = new Thread(new Thread1(IP));
                            Thread1.start();
//                            a = 1;
                            Log.d(ID, "run: ");
                            Log.d(IP, "run: ");
                            check = false;
                        } else if (ID.equals(IP) == false) {
                            check_start_websocket = false;
                            textView_connect.setText("Device Not Available");
                            Led_connect.getBackground().setColorFilter((0xFFFF9800), PorterDuff.Mode.SRC_ATOP);
                            check = true;
                        }
                    }
                });
            }

        }, 0, 3000);
    }

    public String getWifiIPAddress() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        DhcpInfo dhcp = wifiMgr.getDhcpInfo();
        int dhc = dhcp.serverAddress;
        Log.d(Formatter.formatIpAddress(dhc), "getWifiIPAddress: ");
        return Formatter.formatIpAddress(dhc);
    }

    private PrintWriter output;
    private BufferedReader input;
    Socket socket;

    class Thread1 implements Runnable {
        private String IP_Address;

        Thread1(String message) {
            this.IP_Address = message;
        }

        public void run() {
            try {
                socket = new Socket(IP_Address, SERVER_PORT);
                output = new PrintWriter(socket.getOutputStream());
                socket.setTcpNoDelay(true);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        check_start_websocket = true;
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("yy:MM:dd:HH:mm:ss");
                        String formattedDate = "S" + df.format(c.getTime()) + "\n";
                        if (!formattedDate.isEmpty())
                            new Thread(new Thread3(formattedDate)).start();
                        textView_connect.setText("Device Available");
                        Led_connect.getBackground().setColorFilter((0xFF21EF0A), PorterDuff.Mode.SRC_ATOP);
                        SeekBar();
                        colorPickerView = (ColorPickerView) findViewById(R.id.colorPickerView);
                        colorPickerView.setColorListener(new ColorEnvelopeListener() {
                            @Override
                            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                int led = Color.parseColor("#" + envelope.getHexCode());
                                seekbar.setProgressTintList(ColorStateList.valueOf(led));
                                String hex = "#" + envelope.getHexCode() + "\n";
                                if (!hex.isEmpty()) {
                                    new Thread(new Thread3(hex)).start();
                                }
                            }
                        });
                    }
                });
                new Thread(new Thread2()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        output.close();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValue(String data, char separator, int index) {
        int found = 0;
        int strIndex[] = {0, -1};
        int maxIndex = data.length() - 1;

        for (int i = 0; i <= maxIndex && found <= index; i++) {
            if (data.charAt(i) == separator || i == maxIndex) {
                found++;
                strIndex[0] = strIndex[1] + 1;
                strIndex[1] = (i == maxIndex) ? i + 1 : i;
            }
        }
        return found > index ? data.substring(strIndex[0], strIndex[1]) : "";
    }

    class Thread2 implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
//                    final String message = input.readLine();
                    final String message = input.readLine();
                    if (message != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                editor.putString("IP_Address", message);
//                                editor.commit();
                                seekbar.setProgress(Integer.parseInt(getValue(message, ',', 0)));
                                Log.d(String.valueOf(Integer.parseInt(getValue(message, ',', 0))), "run: ");
                                String hex_rgb = "#" + getValue(message, ',', 1).substring(0, 8);
                                Log.d(hex_rgb, "run: ");
                                int led = Color.parseColor(hex_rgb);
                                seekbar.setProgressTintList(ColorStateList.valueOf(led));
                            }
                        });
                    } else {
                        Thread1 = new Thread(new Thread1(IP));
                        Thread1.start();
                        return;
                    }
                } catch (IOException e) {
                    Log.d("dfdfdf", "run: ");
                    e.printStackTrace();
                }
            }
        }
    }

    public void reconnect(String IP) {
        Thread1 = new Thread(new Thread1(IP));
        Thread1.start();
    }

    class Thread3 implements Runnable {
        private String message;

        Thread3(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            output.write(message);
            output.flush();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    etMessage.setText("");
                }
            });
        }
    }

    public void IntenPopup_Hieuung() {
        Intent myIntent = new Intent(MainActivity.this, PopupHieuUng.class);
        registerForContextMenu(button_HieuUng);
        button_HieuUng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(myIntent, 1);
            }
        });
    }

    public void IntenPopup_Hengio() {
        Intent myIntent = new Intent(MainActivity.this, PopupHengio.class);
        registerForContextMenu(button_HenGio);
        button_HenGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(myIntent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String Time_begin = data.getStringExtra("TimeBegin");
                String Time_finish = data.getStringExtra("TimeFinish");
                String OnOff = data.getStringExtra("ONOFF");
                String Send_esp_time = "T" + OnOff + ":" + Time_begin + ":" + Time_finish + "\n";
                if (!Send_esp_time.isEmpty() && check_start_websocket == true)
                    new Thread(new Thread3(Send_esp_time)).start();
                Log.d(Time_begin, "onActivityResult: ");
                Log.d(Time_finish, "onActivityResult: ");
            }
        }
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String Color_popup = "F" + data.getIntExtra("Color", 0) + "\n";
                if (!Color_popup.isEmpty() && check_start_websocket == true)
                    new Thread(new Thread3(Color_popup)).start();
            }
        }
    }
}

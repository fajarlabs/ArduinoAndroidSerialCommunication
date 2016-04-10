package us.fajar.arduino.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.StrictMode;

import java.util.HashMap;
import java.util.Map;

import us.fajar.arduino.util.FSocket;


public class MainActivity extends AppCompatActivity {

    private FSocket socket;
    private Map<String,Boolean> lock = new HashMap<String,Boolean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final Button btnLmpMerah = (Button) findViewById(R.id.btnLmpMerah);
        final Button btnLmpHijau = (Button) findViewById(R.id.btnLmpHijau);
        final Button btnLmpKuning = (Button) findViewById(R.id.btnLmpKuning);

        final TextView textViewVPNTujuan = (TextView) findViewById(R.id.editTextVPNTujuan);
        final TextView textViewPortAktif = (TextView) findViewById(R.id.editTextPortAktif);


        btnLmpMerah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Init socket
                socket = new FSocket(textViewVPNTujuan.getText().toString(),textViewPortAktif.getText().toString());
                if (socket.connect()) {
                    JSONObject obj = new JSONObject();
                        if((lock.get("red") == null) ||(lock.get("red") == false)) {
                            try {
                                obj.put("command", "r");
                                lock.put("red",true);
                                btnLmpMerah.setText("Merah Hidup");
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                obj.put("command", "R");
                                lock.put("red",false);
                                btnLmpMerah.setText("Merah Mati");
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    socket.sendRequest(obj.toString());
                    socket.disconnect();
                }
            }
        });


        btnLmpKuning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Init socket
                socket = new FSocket(textViewVPNTujuan.getText().toString(),textViewPortAktif.getText().toString());
                if (socket.connect()) {
                    JSONObject obj = new JSONObject();
                    if((lock.get("yellow") == null) ||(lock.get("yellow") == false)) {
                        try {
                            obj.put("command", "y");
                            lock.put("yellow",true);
                            btnLmpKuning.setText("Kuning Hidup");
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            obj.put("command", "Y");
                            lock.put("yellow",false);
                            btnLmpKuning.setText("Kuning Mati");
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    socket.sendRequest(obj.toString());
                    socket.disconnect();
                }
            }
        });

        btnLmpHijau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Init socket
                socket = new FSocket(textViewVPNTujuan.getText().toString(),textViewPortAktif.getText().toString());
                if (socket.connect()) {
                    JSONObject obj = new JSONObject();
                    if((lock.get("green") == null) ||(lock.get("green") == false)) {
                        try {
                            obj.put("command", "g");
                            lock.put("green",true);
                            btnLmpHijau.setText("Hijau Hidup");
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            obj.put("command", "G");
                            lock.put("green",false);
                            btnLmpHijau.setText("Hijau Mati");
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    socket.sendRequest(obj.toString());
                    socket.disconnect();
                }
            }
        });
    }
}

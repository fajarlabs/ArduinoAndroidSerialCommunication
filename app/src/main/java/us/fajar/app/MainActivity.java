package us.fajar.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.os.StrictMode;
import android.widget.Toast;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import us.fajar.app.controller.R;
import us.fajar.app.socket.util.CommandSenderUtil;
import us.fajar.app.socket.util.SocketClientUtil;


public class MainActivity extends AppCompatActivity {

    private SocketClientUtil socket;
    private Map<String,Boolean> lock = new HashMap<String,Boolean>();
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Permit policy
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Kumpulkan component buttons
        final Button btnSaklarA = (Button) findViewById(R.id.btnSaklarA);
        final Button btnSaklarB = (Button) findViewById(R.id.btnSaklarB);
        final Button btnSaklarC = (Button) findViewById(R.id.btnSaklarC);
        final Button btnSaklarD = (Button) findViewById(R.id.btnSaklarD);

        final Button btnSynchronizer = (Button) findViewById(R.id.btnSynchronizer);

        final TextView textViewVPNTujuan = (TextView) findViewById(R.id.editTextVPNTujuan);
        final TextView textViewPortAktif = (TextView) findViewById(R.id.editTextPortAktif);

        final CommandSenderUtil socket = new CommandSenderUtil(textViewVPNTujuan.getText().toString(),textViewPortAktif.getText().toString());

        btnSynchronizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (socket.connect()) {
                    String receive = socket.sendData("", "", "synchronize");
                    JSONParser parser = new JSONParser();
                    // Receive JSON Object
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = (JSONObject) parser.parse(receive);
                        for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
                            String key = (String) iterator.next();
                            String val = (String) jsonObject.get(key);
                            if(key.equals("S1")) {
                                if(val.equals("a"))
                                    btnSaklarA.setText("Saklar A : ON");
                                else
                                    btnSaklarA.setText("Saklar A : OFF");
                            }

                            if(key.equals("S2")) {
                                if(val.equals("b"))
                                    btnSaklarB.setText("Saklar B : ON");
                                else
                                    btnSaklarB.setText("Saklar B : OFF");
                            }

                            if(key.equals("S3")) {
                                if(val.equals("c"))
                                    btnSaklarC.setText("Saklar C : ON");
                                else
                                    btnSaklarC.setText("Saklar C : OFF");
                            }

                            if(key.equals("S4")) {
                                if(val.equals("d"))
                                    btnSaklarD.setText("Saklar C : ON");
                                else
                                    btnSaklarD.setText("Saklar C : OFF");
                            }
                        }
                    } catch(Exception e) {
                        Log.e(TAG,e.toString());
                    }
                }
            }
        });

        btnSaklarA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Connect first
                if (socket.connect()) {
                    if((lock.get("a") == null) ||(lock.get("a") == false)) {
                        try {
                            socket.sendData("S1","a","arduino");
                            lock.put("a", true);
                            btnSaklarA.setText("Saklar A : ON");
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            socket.sendData("S1","A","arduino");
                            lock.put("a", false);
                            btnSaklarA.setText("Saklar A : OFF");
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        btnSaklarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Connect first
                if (socket.connect()) {
                    if((lock.get("b") == null) ||(lock.get("b") == false)) {
                        try {
                            socket.sendData("S2", "b","arduino");
                            lock.put("b",true);
                            btnSaklarB.setText("Saklar B : ON");
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            socket.sendData("S2","B","arduino");
                            lock.put("b",false);
                            btnSaklarB.setText("Saklar B : OFF");
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        btnSaklarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Connect first
                if (socket.connect()) {
                    if((lock.get("c") == null) ||(lock.get("c") == false)) {
                        try {
                            socket.sendData("S3","c","arduino");
                            lock.put("c",true);
                            btnSaklarC.setText("Saklar C : ON");
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            socket.sendData("S3","C","arduino");
                            lock.put("c",false);
                            btnSaklarC.setText("Saklar C : OFF");
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        btnSaklarD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // Connect first
            if (socket.connect()) {
                if((lock.get("d") == null) ||(lock.get("d") == false)) {
                    try {
                        socket.sendData("S4","d","arduino");
                        lock.put("d",true);
                        btnSaklarD.setText("Saklar D : ON");
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        socket.sendData("S4","D","arduino");
                        lock.put("d",false);
                        btnSaklarD.setText("Saklar D : OFF");
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            }
        });

    }
}

package app.gate.door;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class RCActivity extends AppCompatActivity {

    BleuthoothConnection btComm;
    Boolean stop = true;
    Boolean action = false;
    Integer response = 0;

    @SuppressLint({"ClickableViewAccessibility", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        btComm = new BleuthoothConnection();
        action = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(RCActivity.this);

        builder.setMessage(R.string.bt_permission_request);
        builder.setPositiveButton(R.string.allow, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                btComm.setBtPermission(true);
                btComm.reply();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                btComm.setBtPermission(false);
                btComm.reply();
            }
        });

        AlertDialog btPermissionAlert = builder.create();

        Context context = getApplicationContext();
        CharSequence text1 = getString(R.string.bt_disabled);
        CharSequence text2 = getString(R.string.bt_failed);


        Toast btDisabledToast = Toast.makeText(context, text1, Toast.LENGTH_LONG);
        Toast btFailedToast = Toast.makeText(context, text2, Toast.LENGTH_LONG);

        String macAdd = getIntent().getStringExtra("macAdd");
        //if(btComm.enableBT(btPermissionAlert, btEnabledToast)){
        //}
        if (!btComm.initBT()) {
            // User did not enable Bluetooth
            btDisabledToast.show();
            Intent intent = new Intent(RCActivity.this, ConnectActivity.class);
            startActivity(intent);
        }

        if (!btComm.connectToEV3(macAdd)) {
            //Cannot connect to given mac address, return to connect activity
            btFailedToast.show();
            Intent intent = new Intent(RCActivity.this, AccesPortailActivity.class);
            startActivity(intent);
        } else {
            new Thread(new Client()).start();
        }

        final Button buttonT = (Button) findViewById(R.id.buttonR);
        buttonT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    btComm.writeMessage((byte) 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Thread(new Client()).start();
            }
        });

        final Button buttonP = (Button) findViewById(R.id.buttonL);
        buttonP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    btComm.writeMessage((byte) 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Thread(new Client()).start();
            }
        });
        final Button buttonQuitter = (Button) findViewById(R.id.buttonB);

        buttonQuitter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        new Thread(new Thread3((byte) 3)).start();
                        new Thread(new Client()).start();
                        Intent intent = new Intent(RCActivity.this, AccesPortailActivity.class);
                        if (response == 6 || response == 2) {
                            startActivity(intent);
                        } else {
                            while (response != 6 && response != 2) {
                            }
                            startActivity(intent);
                        }

                        return true;
                }
                return false;
            }
        });
    }

    class Client implements Runnable {

        @SuppressLint("WrongConstant")
        @Override
        public void run() {
            try {
                ImageView image = (ImageView) findViewById(R.id.imageView);
                TextView text = (TextView) findViewById(R.id.textState);
                InputStreamReader in = new InputStreamReader(btComm.socket_ev3.getInputStream());
                while (true) {
                    if (in.ready()) {
                        response = in.read();
                        Log.d("response", response.toString());
                        switch (response) {
                            case 1:
                                image.setImageResource(R.drawable.door_opened);
                                text.setText("ouverte");
                                break;
                            case 2:
                                image.setImageResource(R.drawable.door_closed);
                                text.setText("fermée");
                                break;

                            case 3:
                                image.setImageResource(R.drawable.door_opening);
                                text.setText("en ouverture");
                                break;
                            case 4:
                                image.setImageResource(R.drawable.door_closing);
                                text.setText("en fermuture");
                                break;
                            case 5:
                                image.setImageResource(R.drawable.door_opened);
                                text.setText("ouverte partiellement ");
                                break;
                            case 6:
                                image.setImageResource(R.drawable.door_closed);
                                text.setText("fermee partiellement");
                                break;
                            case 7:
                                image.setImageResource(R.drawable.door_opening);
                                text.setText("en ouverture partielle");
                                break;
                            case 8:
                                image.setImageResource(R.drawable.door_closing);
                                text.setText("en fermuture partielle");
                                break;
                            case 9:
                                image.setImageResource(R.drawable.ic_blocked_door);
                                text.setText("blocké");
                                break;
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    }


    class Thread3 implements Runnable {
        private Byte message;

        Thread3(Byte message) {
            this.message = message;
        }

        @Override
        public void run() {
            try {
                btComm.writeMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

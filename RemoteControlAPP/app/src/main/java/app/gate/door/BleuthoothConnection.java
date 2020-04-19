package app.gate.door;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;


import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import android.util.Log;
import android.widget.Toast;

public class BleuthoothConnection {
    private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    BluetoothAdapter localAdapter;
    BluetoothSocket socket_ev3;
    private DataInputStream in;
    boolean success = false;
    private boolean btPermission = false;
    private boolean alertReplied = false;

    public void reply() {
        this.alertReplied = true;
    }

    public void setBtPermission(boolean btPermission) {
        this.btPermission = btPermission;
    }

    public boolean initBT() {
        localAdapter = BluetoothAdapter.getDefaultAdapter();
        return localAdapter.isEnabled();
    }

    public boolean connectToEV3(String macAdd) {
        Log.d("macAdd", macAdd);
        BluetoothDevice ev3_1 = localAdapter.getRemoteDevice(macAdd);
        try {
            socket_ev3 = ev3_1.createRfcommSocketToServiceRecord(UUID
                    .fromString(SPP_UUID));
            socket_ev3.connect();
            in = new DataInputStream(socket_ev3.getInputStream());
            success = true;
        } catch (IOException e) {
            Log.d("Bluetooth", "Err: Device not found or cannot connect " + macAdd);
            success = false;


        }
        return success;

    }


    public void writeMessage(byte msg) throws InterruptedException {
        BluetoothSocket connSock;

        connSock = socket_ev3;

        if (connSock != null) {
            try {
                OutputStreamWriter out = new OutputStreamWriter(connSock.getOutputStream());
                out.write(msg);
                out.flush();
                Thread.sleep(1000);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            //Error
        }
    }

    /*  public int readMessage(){
          BluetoothSocket connSock;
          byte buffer;
          int n;
              connSock= socket_ev3;
          if(connSock!=null){
              try {
                  if(in.available()!= -1){
                      Log.e("MyTagGoesHere", in.toString());
                      n= in.read();
                  }else {
                      n = 1;
                  }
                  return n;
  
  
              } catch (IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                  return -1;
              }
          }else{
              return -1;
          }
  
      }*/
    /*public int receiveData() throws IOException {
        byte[] buffer = new byte[256];
        int result;
        ByteArrayInputStream input = new ByteArrayInputStream(buffer);
        InputStream inputStream = socket_ev3.getInputStream();
        result = inputStream.read(buffer);
        String text = new String(buffer, 0, result);
        return Integer.parseInt(text);
    }*/

    /*    public int readMessage() throws InterruptedException {
            BluetoothSocket connSock;
            int n = 0;
            connSock = socket_ev3;

            if (connSock != null) {
                try {
                    InputStreamReader in = new InputStreamReader(connSock.getInputStream());
                    if (in.ready()) {
                        n = in.read();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                Log.i("input", "error");
            }
            return n;
        }*/
    public int readMessage() {
        int n;
        if (socket_ev3 != null) {
            try {
                InputStreamReader in = new InputStreamReader(socket_ev3.getInputStream());
                n = in.read();
                return n;

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return -1;
            }
        } else {
            //Error
            return -1;
        }
    }

}
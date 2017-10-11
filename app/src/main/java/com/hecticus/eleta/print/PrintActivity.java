package com.hecticus.eleta.print;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.hecticus.eleta.BluetoothDevicesList;
import com.hecticus.eleta.R;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrintActivity extends AppCompatActivity {

    @BindView(R.id.message)
    TextView message;

    private static BluetoothSocket btsocket;
    private static OutputStream btoutputstream;
    boolean error = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_printer);
        ButterKnife.bind(this);
        this.setFinishOnTouchOutside(false);
        String text = getIntent().getStringExtra("text");
        message.setText(text);
    }


    @OnClick(R.id.printButton)
    protected void connect() {
        if(btsocket == null){
            Intent BTIntent = new Intent(getApplicationContext(), BluetoothDevicesList.class);
            this.startActivityForResult(BTIntent, BluetoothDevicesList.REQUEST_CONNECT_BT);
        }
        else{
            printBytes();
        }

    }
    private void printBytes() {
        try {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            btoutputstream = btsocket.getOutputStream();
            String msg = message.getText().toString()+"\n";
            Log.d("TEST","mensaje"+msg);
            btoutputstream.write(msg.getBytes());
            btoutputstream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            if (!error){
                error = true;
                openSocketAgain();
            }else {
                Toast.makeText(getApplicationContext(), getString(R.string.cannot_establish_connection), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Runnable socketErrorRunnable = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), getString(R.string.cannot_establish_connection), Toast.LENGTH_SHORT).show();
        }
    };

    private void openSocketAgain(){
        Thread connectThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UUID uuid = BluetoothDevicesList.getSelectedDevice().getUuids()[0] .getUuid();
                    btsocket = BluetoothDevicesList.getSelectedDevice().createRfcommSocketToServiceRecord(uuid);
                    btsocket.connect();
                } catch (IOException ex) {
                    runOnUiThread(socketErrorRunnable);
                    try {
                        btsocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    btsocket = null;
                    return;
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            printBytes();
                        }
                    });
                }
            }
        });
        connectThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if(btsocket!= null){
                btoutputstream.close();
                btsocket.close();
                btsocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            btsocket = BluetoothDevicesList.getSocket();
            if(btsocket != null){
                printBytes();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
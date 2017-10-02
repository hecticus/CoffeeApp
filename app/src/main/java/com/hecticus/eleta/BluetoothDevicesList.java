package com.hecticus.eleta;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 17/9/17.
 */

public class BluetoothDevicesList extends AppCompatActivity{

    static public final int REQUEST_CONNECT_BT = 0x2300;
    static private final int REQUEST_ENABLE_BT = 0x1000;

    static private BluetoothAdapter mBluetoothAdapter = null;
    static private ArrayAdapter<String> mArrayAdapter = null;
    static private ArrayAdapter<BluetoothDevice> btDevices = null;

    static private BluetoothDevice selectedDevice = null;
    static private BluetoothSocket mbtSocket = null;

    @BindView(R.id.bluetooth_devices_list_view)
    ListView listView;

    @DebugLog
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_bluetooth);
        ButterKnife.bind(this);

        try {
            if (initDevicesList() != 0) {
                this.finish();
                return;
            }

        } catch (Exception ex) {
            this.finish();
            return;
        }

        IntentFilter btIntentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mBTReceiver, btIntentFilter);
    }

    @DebugLog
    public static BluetoothSocket getSocket() {
        return mbtSocket;
    }

    @DebugLog
    public static BluetoothDevice getSelectedDevice() {
        return selectedDevice;
    }

    @DebugLog
    private void flushData() {
        try {
            if (mbtSocket != null) {
                mbtSocket.close();
                mbtSocket = null;
            }

            if (mBluetoothAdapter != null) {
                mBluetoothAdapter.cancelDiscovery();
            }

            if (btDevices != null) {
                btDevices.clear();
                btDevices = null;
            }

            if (mArrayAdapter != null) {
                mArrayAdapter.clear();
                mArrayAdapter.notifyDataSetChanged();
                mArrayAdapter.notifyDataSetInvalidated();
                mArrayAdapter = null;
            }

            finalize();

        } catch (Exception ex) {}
        catch (Throwable e) {}
    }

    @DebugLog
    private int initDevicesList() {
        flushData();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),getString(R.string.bluetooth_not_supported), Toast.LENGTH_LONG).show();
            return -1;
        }
        if (!mBluetoothAdapter.isEnabled()){
            Intent enableBtnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtnIntent,REQUEST_ENABLE_BT);
        }

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        mArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        listView.setAdapter(mArrayAdapter);

        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        try {
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } catch (Exception ex) {
            return -2;
        }

        Toast.makeText(getApplicationContext(),getString(R.string.getting_all_available_bluetooth_devices),Toast.LENGTH_SHORT).show();
        return 0;
    }

    @DebugLog
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent intent) {
        super.onActivityResult(reqCode, resultCode, intent);

        switch (reqCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {
                    Set<BluetoothDevice> btDeviceList = mBluetoothAdapter
                            .getBondedDevices();
                    try {
                        if (btDeviceList.size() > 0) {
                            for (BluetoothDevice device : btDeviceList) {
                                //if (btDeviceList.contains(device) == false)
                                 {
                                    btDevices.add(device);
                                    mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                                    mArrayAdapter.notifyDataSetInvalidated();
                                }
                            }
                        }
                    } catch (Exception ex) { }
                }
                break;
        }

        mBluetoothAdapter.startDiscovery();
    }

    private final BroadcastReceiver mBTReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                try {
                    if (btDevices == null) {
                        btDevices = new ArrayAdapter<BluetoothDevice>(getApplicationContext(), android.R.layout.simple_list_item_1);
                    }

                    if (btDevices.getPosition(device) < 0) {
                        btDevices.add(device);
                        mArrayAdapter.add(device.getName() + "\n"+ device.getAddress() + "\n" );
                        mArrayAdapter.notifyDataSetInvalidated();
                    }
                } catch (Exception ex) {}
            }
        }
    };

    private Runnable socketErrorRunnable = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), getString(R.string.cannot_establish_connection), Toast.LENGTH_SHORT).show();
            mBluetoothAdapter.startDiscovery();
        }
    };
    @DebugLog
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, Menu.FIRST, Menu.NONE, getString(R.string.refresh_scanning));
        return true;
    }
    @DebugLog
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case Menu.FIRST:
                initDevicesList();
                break;
        }

        return true;
    }

    @OnItemSelected(R.id.bluetooth_devices_list_view)
    public void onItemClick(AdapterView<?> adapterView, View view,final int position, long l) {
        if (mBluetoothAdapter == null) {
            return;
        }

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        Toast.makeText(
                getApplicationContext(),
                getString(R.string.connecting_to) + btDevices.getItem(position).getName() + ","
                + btDevices.getItem(position).getAddress(),
                Toast.LENGTH_SHORT).show();

        Thread connectThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    selectedDevice = btDevices.getItem(position);
                    UUID uuid = btDevices.getItem(position).getUuids()[0].getUuid();
                    mbtSocket = btDevices.getItem(position).createRfcommSocketToServiceRecord(uuid);
                    mbtSocket.connect();
                } catch (IOException ex) {
                    runOnUiThread(socketErrorRunnable);
                    try {
                        mbtSocket.close();
                    } catch (IOException e) { }
                    mbtSocket = null;
                    return;
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finish();

                        }
                    });
                }
            }
        });

        connectThread.start();
    }
}
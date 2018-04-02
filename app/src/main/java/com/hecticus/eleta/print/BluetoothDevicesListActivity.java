package com.hecticus.eleta.print;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.hecticus.eleta.R;
import com.hecticus.eleta.util.Constants;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;
import com.zebra.sdk.printer.discovery.BluetoothDiscoverer;
import com.zebra.sdk.printer.discovery.DiscoveredPrinter;
import com.zebra.sdk.printer.discovery.DiscoveredPrinterBluetooth;
import com.zebra.sdk.printer.discovery.DiscoveryHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 17/9/17.
 */


public class BluetoothDevicesListActivity extends AppCompatActivity implements DiscoveryHandler, AdapterView.OnItemClickListener {

    static public final int REQUEST_CONNECT_BT = 0x2300;

    static private ArrayAdapter<String> mArrayAdapter = null;

    @BindView(R.id.bluetooth_devices_list_view)
    ListView listView;

    private ZebraPrinter mZebraPrinter;
    private Connection printerConnection;
    private DiscoveredPrinterBluetooth selectedPrinter;

    private String zplTextToPrint;


    @DebugLog
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_bluetooth);
        ButterKnife.bind(this);

        zplTextToPrint = getIntent().getStringExtra(Constants.PRINT_TEXT_FOR_ZPL);

        mArrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.item_bluetooth_device);
        listView.setAdapter(mArrayAdapter);
        listView.setOnItemClickListener(this);

        new Thread(new Runnable() {
            @DebugLog
            public void run() {
                Looper.prepare();
                try {
                    BluetoothDiscoverer.findPrinters(BluetoothDevicesListActivity.this, BluetoothDevicesListActivity.this);
                } catch (ConnectionException e) {
                    e.printStackTrace();
                    //new UIHelper(BluetoothDiscoveryActivity.this).showErrorDialogOnGuiThread(e.getMessage());
                } finally {
                    Looper.myLooper().quit();
                }
            }
        }).start();

        Toast.makeText(BluetoothDevicesListActivity.this, "Starting discovery", Toast.LENGTH_SHORT).show();

    }

    @DebugLog
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, Menu.FIRST, Menu.NONE, getString(R.string.refresh_scanning));
        return true;
    }

    @DebugLog
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
        new Thread(new Runnable() {
            public void run() {
                Looper.prepare();
                connectAndPrint();
                Looper.loop();
                Looper.myLooper().quit();
            }
        }).start();
    }

    @DebugLog
    private void connectAndPrint() {
        mZebraPrinter = getConnectedPrinter();
        if (mZebraPrinter != null) {
            sentTextToPrinter();
        } else {
            disconnect();
        }
    }

    @DebugLog
    private ZebraPrinter getConnectedPrinter() {
        toast(BluetoothDevicesListActivity.this.getString(R.string.connecting_to) + selectedPrinter.friendlyName);

        printerConnection = null;

        printerConnection = new BluetoothConnection(selectedPrinter.address);
        //SettingsHelper.saveBluetoothAddress(this, getMacAddressFieldText());

        try {
            printerConnection.open();
            toast("Connected");
        } catch (ConnectionException e) {
            toast("Communication Error! Disconnecting");
            disconnect();
        }

        ZebraPrinter printer = null;

        if (printerConnection.isConnected()) {
            try {
                printer = ZebraPrinterFactory.getInstance(printerConnection);
                PrinterLanguage pl = printer.getPrinterControlLanguage();
                toast("Printer Language " + pl);
            } catch (ConnectionException e) {
                e.printStackTrace();
                toast("Unknown Printer Language!");
                printer = null;
                disconnect();
            } catch (ZebraPrinterLanguageUnknownException e) {
                e.printStackTrace();
                toast("Unknown Printer Language");
                printer = null;
                disconnect();
            }
        }

        return printer;
    }

    @DebugLog
    public void disconnect() {
        try {
            if (printerConnection != null) {
                printerConnection.close();
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
            toast("COMM Error! Disconnected");
        }
    }

    @DebugLog
    private void sentTextToPrinter() {
        try {
            byte[] pageBytes = getPageBytesFromString(zplTextToPrint);
            printerConnection.write(pageBytes);
        } catch (ConnectionException e) {
            e.printStackTrace();
            toast("ConnectionException: " + e.getMessage());
            //setStatus(e.getMessage(), Color.RED);
        } finally {
            disconnect();
        }
    }

    @DebugLog
    private byte[] getPageBytesFromString(String textParam) throws ConnectionException {
        PrinterLanguage printerLanguage = mZebraPrinter.getPrinterControlLanguage();


        String start = "^XA^FO10,10^AD^FH^FD";

        String end = "^FS^XZ";

        String toPrint = start + textParam + end;

        byte[] pageBytes;

        if (printerLanguage == PrinterLanguage.ZPL) {

            pageBytes = toPrint.getBytes();

        } else {
            throw new ConnectionException("Printer must use ZPL language");
        }

        return pageBytes;
    }

    @DebugLog
    private void toast(final String message) {
        BluetoothDevicesListActivity.this.runOnUiThread(new Runnable() {
            @DebugLog
            @Override
            public void run() {
                Toast.makeText(BluetoothDevicesListActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @DebugLog
    @Override
    public void foundPrinter(final DiscoveredPrinter discoveredPrinter) {

        final String printerFriendlyDescription =
                ((DiscoveredPrinterBluetooth) discoveredPrinter).friendlyName + " [" + discoveredPrinter.address + "]";

        selectedPrinter = (DiscoveredPrinterBluetooth) discoveredPrinter;
        mArrayAdapter.add(printerFriendlyDescription);

        BluetoothDevicesListActivity.this.runOnUiThread(new Runnable() {
            @DebugLog
            @Override
            public void run() {
                mArrayAdapter.notifyDataSetInvalidated();
                Toast.makeText(BluetoothDevicesListActivity.this, "Found printer: " + printerFriendlyDescription, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @DebugLog
    @Override
    public void discoveryFinished() {
        BluetoothDevicesListActivity.this.runOnUiThread(new Runnable() {
            @DebugLog
            @Override
            public void run() {
                Toast.makeText(BluetoothDevicesListActivity.this, "Discovery finished", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @DebugLog
    @Override
    public void discoveryError(final String s) {
        BluetoothDevicesListActivity.this.runOnUiThread(new Runnable() {
            @DebugLog
            @Override
            public void run() {
                Toast.makeText(BluetoothDevicesListActivity.this, "Discovery error: " + s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
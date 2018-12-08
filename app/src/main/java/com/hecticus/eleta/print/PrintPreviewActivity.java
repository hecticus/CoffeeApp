package com.hecticus.eleta.print;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.hecticus.eleta.R;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

public class PrintPreviewActivity extends AppCompatActivity {

    @BindView(R.id.message)
    TextView previewTextView;
    private String textToPrint;
    private String textToShow;

    @DebugLog
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_printer);
        ButterKnife.bind(this);
        this.setFinishOnTouchOutside(false);
        textToPrint = getIntent().getStringExtra(Constants.PRINT_TEXT_FOR_ZPL);
        textToShow = getIntent().getStringExtra(Constants.PRINT_TEXT_FOR_PREVIEW);

        previewTextView.setMovementMethod(new ScrollingMovementMethod());
        previewTextView.setText(textToShow);
    }


    @DebugLog
    @OnClick(R.id.printButton)
    protected void connect() {
        //if(Util.getPrinterConnection() == null || Util.getSelectedPrinter() == null || Util.getmZebraPrinter() == null) {
            Intent BTIntent = new Intent(getApplicationContext(), BluetoothDevicesListActivity.class);
            BTIntent.putExtra(Constants.PRINT_TEXT_FOR_ZPL, textToPrint);
            this.startActivityForResult(BTIntent, BluetoothDevicesListActivity.REQUEST_CONNECT_BT);
        /*} else {
            Util.printBluetooth(PrintPreviewActivity.this, textToPrint);
        }*/
    }

    @DebugLog
    @OnClick(R.id.cancelButton)
    protected void cancel() {
        finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
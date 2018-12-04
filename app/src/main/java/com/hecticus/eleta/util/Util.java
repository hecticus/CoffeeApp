package com.hecticus.eleta.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hecticus.eleta.R;
import com.hecticus.eleta.model_new.SessionManager;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailPurity;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailsResponse;
import com.hecticus.eleta.model.response.invoice.ReceiptResponse;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.print.BluetoothDevicesListActivity;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;
import com.zebra.sdk.printer.discovery.DiscoveredPrinterBluetooth;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import hugo.weaving.DebugLog;
import io.realm.RealmObject;

/**
 * Created by Edwin on 2017-09-17.
 */

public class Util {

    public static Gson gson = null;
    private static ZebraPrinter mZebraPrinter = null;
    private static Connection printerConnection = null;
    private static DiscoveredPrinterBluetooth selectedPrinter = null;

    @DebugLog
    public static Gson getGson() throws ClassNotFoundException {
        if (gson != null)
            return gson;

        return gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .registerTypeAdapter(Class.forName("io.realm.ProviderRealmProxy"), new Provider())
                .registerTypeAdapter(Class.forName("io.realm.InvoiceRealmProxy"), new Invoice())
                .registerTypeAdapter(Class.forName("io.realm.InvoiceDetailsRealmProxy"), new InvoiceDetails())
                .registerTypeAdapter(Class.forName("io.realm.InvoiceDetailPurityRealmProxy"), new InvoiceDetailPurity())
                .create();
    }

    public static void loadImageFromBase64(String base64, ImageView imageView) {


        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);

       /* BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 6;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        imageView.setImageBitmap(bitmap);*/
    }

    public static String parseDateTimeZoneServerToLocal(String startDate){//2018-09-01 11:45:00
        Log.d("TIMEZONEjjjjyyy", startDate);

        DateTime fecha = new DateTime(Integer.parseInt(startDate.substring(0,4)),
                Integer.parseInt(startDate.substring(5,7)),
                Integer.parseInt(startDate.substring(8,10)),
                Integer.parseInt(startDate.substring(11,13)),
                Integer.parseInt(startDate.substring(14,16)),
                Integer.parseInt(startDate.substring(17,19)));//,
                /*DateTimeZone.forID("UTC"))*/

        SimpleDateFormat sdfMadrid = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Log.d("TIMEZONEjjjjyyy","Hora server:"+ sdfMadrid.format(fecha.toDate()));


        /*SimpleDateFormat sdfArgentina = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        TimeZone tz= Calendar.getInstance().getTimeZone();
        Log.d("TIMEZONEjjjjyyy", tz.getID());
        sdfArgentina.setTimeZone(TimeZone.getTimeZone(tz.getID()));
        Log.d("TIMEZONEjjjjyyy","Hora local:"+ sdfArgentina.format(fecha.toDate()));

        return sdfArgentina.format(fecha.toDate());*/
        return sdfMadrid.format(fecha.toDate());
    }

    public static String parseDateTimeZoneLocalToServer(String startDate){//2018-09-01 11:45:00

        Log.d("TIMEZONE", startDate);
        Calendar calendar = new GregorianCalendar();
        calendar.set(Integer.parseInt(startDate.substring(0,4)),
                Integer.parseInt(startDate.substring(5,7))-1,
                Integer.parseInt(startDate.substring(8,10)),
                Integer.parseInt(startDate.substring(11,13)),
                Integer.parseInt(startDate.substring(14,16)),
                Integer.parseInt(startDate.substring(17,19)));

        SimpleDateFormat sdfArgentina = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        TimeZone tz= Calendar.getInstance().getTimeZone();
        sdfArgentina.setTimeZone(TimeZone.getTimeZone(tz.getID()));
        Log.d("TIMEZONExxx","Hora local:"+ sdfArgentina.format(calendar.getTime()));

        /*SimpleDateFormat sdfMadrid = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        Log.d("TIMEZONEyyy","Hora server antes:"+ sdfMadrid.format(calendar.getTime()));
        sdfMadrid.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        Log.d("TIMEZONEyyy","Hora server despues:"+ sdfMadrid.format(calendar.getTime()));

        return sdfMadrid.format(calendar.getTime());*/
        return sdfArgentina.format(calendar.getTime());

    }

    /*public static String parseDateTimeZoneServerToLocal(String startDate){//2018-09-01 11:45:00
        Log.d("TIMEZONEjjjjyyy", startDate);

        DateTime fecha = new DateTime(Integer.parseInt(startDate.substring(0,4)),
                Integer.parseInt(startDate.substring(5,7)),
                Integer.parseInt(startDate.substring(8,10)),
                Integer.parseInt(startDate.substring(11,13)),
                Integer.parseInt(startDate.substring(14,16)),
                Integer.parseInt(startDate.substring(17,19)),
                DateTimeZone.forID("UTC"));

        SimpleDateFormat sdfMadrid = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Log.d("TIMEZONEjjjjyyy","Hora server:"+ sdfMadrid.format(fecha.toDate()));


        SimpleDateFormat sdfArgentina = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        TimeZone tz= Calendar.getInstance().getTimeZone();
        Log.d("TIMEZONEjjjjyyy", tz.getID());
        sdfArgentina.setTimeZone(TimeZone.getTimeZone(tz.getID()));
        Log.d("TIMEZONEjjjjyyy","Hora local:"+ sdfArgentina.format(fecha.toDate()));

        return sdfArgentina.format(fecha.toDate());
    }*/

    /*public static String parseDateTimeZoneLocalToServer(String startDate){//2018-09-01 11:45:00

        Log.d("TIMEZONE", startDate);
        Calendar calendar = new GregorianCalendar();
        calendar.set(Integer.parseInt(startDate.substring(0,4)),
                Integer.parseInt(startDate.substring(5,7))-1,
                Integer.parseInt(startDate.substring(8,10)),
                Integer.parseInt(startDate.substring(11,13)),
                Integer.parseInt(startDate.substring(14,16)),
                Integer.parseInt(startDate.substring(17,19)));

        SimpleDateFormat sdfArgentina = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        TimeZone tz= Calendar.getInstance().getTimeZone();
        sdfArgentina.setTimeZone(TimeZone.getTimeZone(tz.getID()));
        Log.d("TIMEZONExxx","Hora local:"+ sdfArgentina.format(calendar.getTime()));

        SimpleDateFormat sdfMadrid = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        Log.d("TIMEZONEyyy","Hora server antes:"+ sdfMadrid.format(calendar.getTime()));
        sdfMadrid.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        Log.d("TIMEZONEyyy","Hora server despues:"+ sdfMadrid.format(calendar.getTime()));

        return sdfMadrid.format(calendar.getTime());

    }*/

    public static void loadThumbnailsImageFromPath(String path, ImageView imageView) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 6;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        imageView.setImageBitmap(bitmap);
    }

    @DebugLog
    public static String getCurrentDateLocal() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = sdf.format(new Date());

        return startDate;
    }

    @DebugLog
    public static String parseDateZH2(String fecha, String formato){
        Log.d("DEBUG" , "FECHAS ANTES Q EXPLOTE" +fecha);
        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        //sd1.setTimeZone(TimeZone.getTimeZone("Etc/UTC")); //todo no entiendo bien como funciona
        Date dt = null;
        try {
            dt = sd1.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String date;
        SimpleDateFormat sd2 = new SimpleDateFormat(formato);
        date = sd2.format(dt);
        return date;
    }

    @DebugLog
    public static String parseDateZH(String fecha, String formato){
        Log.d("DEBUG" , "FECHAS ANTES Q EXPLOTE" +fecha);
        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        //sd1.setTimeZone(TimeZone.getTimeZone("Etc/UTC")); //todo no entiendo bien como funciona
        Date dt = null;
        try {
            dt = sd1.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String date;
        SimpleDateFormat sd2 = new SimpleDateFormat(formato);
        date = sd2.format(dt);
        return date;
    }

    public static String parseDateDni(Date fecha){
        Format formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(fecha);
    }


    @DebugLog
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = sdf.format(new Date());

        Calendar calendar = new GregorianCalendar();
        calendar.set(Integer.parseInt(startDate.substring(0,4)),
                Integer.parseInt(startDate.substring(5,7))-1,
                Integer.parseInt(startDate.substring(8,10)),
                0,
                0,
                0);

        SimpleDateFormat sdfMadrid = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return sdfMadrid.format(calendar.getTime());
    }



    @DebugLog
    public static String getCurrentDateFinisth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = sdf.format(new Date());

        Calendar calendar = new GregorianCalendar();
        calendar.set(Integer.parseInt(startDate.substring(0,4)),
                Integer.parseInt(startDate.substring(5,7))-1,
                Integer.parseInt(startDate.substring(8,10)),
                23,
                59,
                59);

        SimpleDateFormat sdfMadrid = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        return sdfMadrid.format(calendar.getTime());
    }

    @DebugLog
    public static String getCurrentDateForInvoice() {
        // Server always converts seconds to 00, so I'll do it myself here to prevent errors
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return (sdf.format(new Date())) + ":00";
    }

    @DebugLog
    public static String getTomorrowDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, 1);
        Date tomorrowDate = calendar.getTime();

        return sdf.format(tomorrowDate) + " 00:00:00";
    }

    ///PRINTER
    @DebugLog
    public static String formatTextForPreview(Context context, ReceiptResponse receiptResponse, InvoiceDetailsResponse detailsResponse, float totalInvoice) {
        if (detailsResponse.getListInvoiceDetails() == null || detailsResponse.getListInvoiceDetails().size() <= 0) {
            return "";
        }

        Log.d("PRINTOFFLINE", "--->I'll print " + detailsResponse.getListInvoiceDetails().size() + " details");

        StringBuilder text = new StringBuilder();
        text.append(receiptResponse.getCompanyName()).append("\n");

        boolean isHarvest;

        if (receiptResponse.getInvoice().getProvider() != null) {
            //Remote invoice
            isHarvest = receiptResponse.getInvoice().getProvider().isHarvester();
        } else {
            //Local invoice
            isHarvest = receiptResponse.getInvoice().getType() == Constants.TYPE_HARVESTER;
        }

        if (isHarvest) {
            text.append(receiptResponse.getInvoiceDescription()).append("\n");
            text.append(receiptResponse.getInvoiceType()).append("\n\n");

            // Invoice number, hidden if local
            if (receiptResponse.getInvoice().getInvoiceId() > 0)
                text.append(context.getString(R.string.receipt)).append(": ").append(String.format("%08d", receiptResponse.getInvoice().getInvoiceId())).append("\n");

        } else { //Is purchase

            text.append(receiptResponse.getRUC()).append("\n");
            text.append(context.getString(R.string.phone)).append(": ").append(receiptResponse.getCompanyTelephone()).append("\n\n");

            // Invoice number, hidden if local
            if (receiptResponse.getInvoice().getInvoiceId() > 0)
                text.append(context.getString(R.string.proof_of_delivery)).append(": ").append(String.format("%08d", receiptResponse.getInvoice().getInvoiceId())).append("\n");

        }

        // Date
        text.append(context.getString(R.string.date))
                .append(": ").append(parseDateZH2(receiptResponse.getInvoice()
                .getInvoiceStartDate()/*.split(" ")[0]).append("\n")*/,"dd-MM-yyyy")).append("   ")
                .append(parseDateToString(Calendar.getInstance().getTime())).append("\n");

        DecimalFormat df = new DecimalFormat("0.00");
        DecimalFormat df1 = new DecimalFormat("0.0000");
        df.setRoundingMode(RoundingMode.HALF_UP);

        InvoiceDetails firstDetail = detailsResponse.getListInvoiceDetails().get(0);

        Log.d("PRINTOFFLINE", "--->firstDetail to print: " + firstDetail);

        if (isHarvest) {
            /*text.append(context.getString(R.string.lot)).append(": ").append(firstDetail.getLot().getName()).append("\n");*/

            if (receiptResponse.getInvoice().getProvider() != null) {// Remote invoice

                text.append(context.getString(R.string.harvester)).append(": ")
                        .append(receiptResponse.getInvoice().getProvider().getFullNameProvider());

            } else {// Local invoice

                text.append(context.getString(R.string.harvester)).append(": ")
                        .append(receiptResponse.getInvoice().getProviderName());
            }

            text.append("\n");

            // User(Operator) name
            text.append(context.getString(R.string.operator)).append(": ").append(SessionManager.getUserName(context)).append("\n\n");

            text.append(String.format("%15s", " ")).append("Lote    ").append("Libras")
                    .append("  ").append(String.format("%8s", "Precio")).append("   Monto\n");

            for (InvoiceDetails detail : detailsResponse.getListInvoiceDetails()) {
                String[] nameArray = detail.getItemType().getName().split(" ");
                ArrayList nameArrayList = new ArrayList();

                StringBuffer currentName = new StringBuffer();

                for (String aNameArray : nameArray) {
                    if (currentName.toString().isEmpty()) {
                        currentName.append(aNameArray);
                        continue;
                    }
                    int length = (currentName.toString() + Arrays.toString(nameArray)).length();
                    if (length <= 10) {
                        currentName.append(aNameArray);
                    } else {
                        nameArrayList.add(currentName.toString());
                        currentName = new StringBuffer(aNameArray);
                    }
                }

                if (!currentName.toString().isEmpty()) {
                    nameArrayList.add(currentName.toString());
                }

                for (int i = 0; i < nameArrayList.size(); i++) {
                    if (i == 0) {

                        float totalAmount = detail.getPriceByLot() * detail.getAmount();

                        text.append(nameArrayList.get(i))
                                .append(" ")
                                .append(String.format("%8s", detail.getLot().getName()))
                                .append(" ")
                                .append(String.format("%6s", df.format(detail.getAmount())))
                                .append(" ")
                                .append(String.format("%8s", df1.format(detail.getPriceByLot())))
                                .append(" ")
                                .append(String.format("%6s", df.format(totalAmount)))
                                .append("\n");
                    } else {
                        text.append(String.format("%-10s", nameArrayList.get(i))).append("\n");
                    }
                }
            }

            text.append("\nTotal: ").append(df.format(/*receiptResponse.getInvoice().getInvoiceTotal()*/totalInvoice)).append("\n\n");

            text.append("___________________" + "\n");
            text.append(" Por Cafe de Eleta");

        } else {//Is purchase

            //Provider: Provider name
            text.append(context.getString(R.string.provider)).append(": ");
            if (receiptResponse.getInvoice().getProvider() != null) {
                //Remote invoice
                text.append(receiptResponse.getInvoice().getProvider().getFullNameProvider());
            } else {
                //Local invoice
                text.append(receiptResponse.getInvoice().getProviderName());
            }

            text.append("\n");

            // User(Operator) name
            text.append(context.getString(R.string.operator)).append(": ").append(SessionManager.getUserName(context)).append("\n\n");

            text.append("- - - - - - - - - - - -\n");

            for (InvoiceDetails currentDetail : detailsResponse.getListInvoiceDetails()) {

                //Store: XXXX
                text.append(context.getString(R.string.gathering)).append(": ").append(currentDetail.getStore().getName()).append("\n");
                //Dispatched by: XXXX
                if (currentDetail.getDispatcherName() != null && !currentDetail.getDispatcherName().trim().isEmpty())
                    text.append(context.getString(R.string.dispatched_by)).append(": ").append(currentDetail.getDispatcherName()).append("\n");

                //Labels: Weight, Price, (Weight*Price)
                text.append(String.format("%20s", " "))
                        .append(/*firstDetail.getItemType().getUnit().getName()*/"Cant.")
                        .append("    Precio").append("        Monto\n");

                String[] nameArray = currentDetail.getItemType().getName().split(" ");
                ArrayList nameArrayList = new ArrayList();

                StringBuffer currentName = new StringBuffer();

                for (String aNameArray : nameArray) {
                    if (currentName.toString().isEmpty()) {
                        currentName.append(aNameArray);
                        continue;
                    }
                    int length = (currentName.toString() + Arrays.toString(nameArray)).length();
                    if (length <= 10) {
                        currentName.append(aNameArray);
                    } else {
                        nameArrayList.add(currentName.toString());
                        currentName = new StringBuffer(aNameArray);
                    }
                }

                if (!currentName.toString().isEmpty()) {
                    nameArrayList.add(currentName.toString());
                }

                for (int i = 0; i < nameArrayList.size(); i++) {
                    if (i == 0) {
                        float totalAmount = currentDetail.getPriceItem() * currentDetail.getAmount();

                        text.append(String.format("%-10s", nameArrayList.get(i))).append(" ")
                                .append(String.format("%6s", currentDetail.getAmount())).append(" ")
                                .append(String.format("%10s", df1.format(currentDetail.getPriceItem()))).append(" ")
                                .append(String.format("%11s", df.format(totalAmount)))
                                .append("\n");
                    } else {
                        text.append(String.format("%-10s", nameArrayList.get(i))).append("\n");
                    }
                }

                for (InvoiceDetailPurity purity : currentDetail.getDetailPurities()) {
                    text.append(String.format("%-10s", purity.getPurity().getName()))
                            .append(": ")
                            .append(String.format("%-8s", purity.getRateValue())).append("\n");
                }

                if (currentDetail.getObservation() != null && !currentDetail.getObservation().trim().isEmpty())
                    text.append("Observaciones: ").append(currentDetail.getObservation()).append("\n");

                text.append("- - - - - - - - - - - -\n");

            }

            text.append("\nTotal: ").append(df.format(/*receiptResponse.getInvoice().getInvoiceTotal()*/totalInvoice)).append("\n\n");

            text.append("_________________ | _________________" + "\n");
            text.append("Por Cafe de Eleta |   Entregado por   ");
        }

        return text.toString();
    }

    @DebugLog
    public static String formatTextForPrinting(String textForViewing) {
        String[] linesArray = textForViewing.split("\n");

        StringBuilder whole = new StringBuilder();

        String start = "^XA^POI^LL600" + "\r\n";
        String end = "^XZ";

        whole.append(start);

        int currentY = 15;

        for (String currentLine : linesArray) {
            String addition = "^FO10," + currentY + "\r\n" + "^AB,N,1,1" + "\r\n" + "^FD" + currentLine + "^FS" + "\r\n";
            whole.append(addition);
            currentY += 15;
        }

        whole.append(end);

        return whole.toString();
    }

    public static String parseDateToString(Date fecha){
        Format formatter = new SimpleDateFormat("hh:mm a");
        return formatter.format(fecha);
    }

    public static ZebraPrinter getmZebraPrinter() {
        return mZebraPrinter;
    }

    public static void setmZebraPrinter(ZebraPrinter mZebraPrinter1) {
        mZebraPrinter = mZebraPrinter1;
    }

    public static Connection getPrinterConnection() {
        return printerConnection;
    }

    public static void setPrinterConnection(Connection printerConnection1) {
        printerConnection = printerConnection1;
    }

    public static DiscoveredPrinterBluetooth getSelectedPrinter() {
        return selectedPrinter;
    }

    public static void setSelectedPrinter(DiscoveredPrinterBluetooth selectedPrinter1) {
        selectedPrinter = selectedPrinter1;
    }

    public static void printBluetooth(Context context, String zplTextToPrint){
        mZebraPrinter = getConnectedPrinter(context, zplTextToPrint);
        if (mZebraPrinter != null) {
            sentTextToPrinter(context, zplTextToPrint);
        } else {
            disconnect(context, zplTextToPrint);
        }
    }

    private static ZebraPrinter getConnectedPrinter(Context context, String zplTextToPrint) {
        printerConnection = null;

        printerConnection = new BluetoothConnection(selectedPrinter.address);

        ZebraPrinter printer = null;

        if (printerConnection.isConnected()) {
            try {
                printer = ZebraPrinterFactory.getInstance(printerConnection);
                PrinterLanguage pl = printer.getPrinterControlLanguage();
                toast(context, "Printer Language " + pl);
            } catch (ConnectionException e) {
                e.printStackTrace();
                toast(context,"code 101: Unknown Printer Language!");
                printer = null;
                disconnect(context, zplTextToPrint);
            } catch (ZebraPrinterLanguageUnknownException e) {
                e.printStackTrace();
                toast(context,"code 102: Unknown Printer Language");
                printer = null;
                disconnect(context, zplTextToPrint);
            }
        }

        return printer;
    }

    private static void sentTextToPrinter(Context context, String zplTextToPrint) {
        try {
            byte[] pageBytes = getPageBytesFromString(zplTextToPrint);
            printerConnection.write(pageBytes);

        } catch (ConnectionException e) {
            e.printStackTrace();
            toast(context,"code 103: ConnectionException: " + e.getMessage());
            disconnect(context, zplTextToPrint);
            //setStatus(e.getMessage(), Color.RED);
        }

    }

    private static void toast(final Context context, final String message) {
        ((Activity)context).runOnUiThread(new Runnable() {
            @DebugLog
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static void disconnect(Context context, String zplTextToPrint) {
        try {
            if (Util.getPrinterConnection() != null) {
                Util.getPrinterConnection().close();
            }
        }catch (ConnectionException e) {
            e.printStackTrace();
        }
        setPrinterConnection(null);
        setmZebraPrinter(null);
        setSelectedPrinter(null);
        Intent BTIntent = new Intent(context, BluetoothDevicesListActivity.class);
        BTIntent.putExtra(Constants.PRINT_TEXT_FOR_ZPL, zplTextToPrint);
        ((Activity) context).startActivityForResult(BTIntent, BluetoothDevicesListActivity.REQUEST_CONNECT_BT);
        toast(context, "Perdió comunicación con la impresora, conectarla nuevamente.");
    }

    @DebugLog
    private static byte[] getPageBytesFromString(String textParam) throws ConnectionException {
        PrinterLanguage printerLanguage = mZebraPrinter.getPrinterControlLanguage();

        String start = "^XA^FO10,10^AD^FH^FD";
        String end = "^FS^XZ";
        String toPrint = start + textParam + end;
        byte[] pageBytes;

        if (printerLanguage == PrinterLanguage.ZPL) {

            pageBytes = toPrint.getBytes();

        } else {
            throw new ConnectionException("code 104: Printer must use ZPL language");
        }

        return pageBytes;
    }

}

package com.hecticus.eleta.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

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

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import hugo.weaving.DebugLog;
import io.realm.RealmObject;

/**
 * Created by Edwin on 2017-09-17.
 */

public class Util {

    public static Gson gson = null;

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

    public static void loadThumbnailsImageFromPath(String path, ImageView imageView) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 6;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        imageView.setImageBitmap(bitmap);
    }

    @DebugLog
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Log.d("DEBUG FECHA", sdf.format(new Date()));
        return sdf.format(new Date());
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
    public static String formatTextForPreview(Context context, ReceiptResponse receiptResponse, InvoiceDetailsResponse detailsResponse) {
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
                .append(": ").append(receiptResponse.getInvoice()
                .getInvoiceStartDate().split(" ")[0]).append("\n");

        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.HALF_UP);

        InvoiceDetails firstDetail = detailsResponse.getListInvoiceDetails().get(0);

        Log.d("PRINTOFFLINE", "--->firstDetail to print: " + firstDetail);

        if (isHarvest) {
            text.append(context.getString(R.string.lot)).append(": ").append(firstDetail.getLot().getName()).append("\n");

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

            text.append(String.format("%12s", " ")).append("Libras")
                    .append("  ").append(String.format("%8s", "Precio")).append("      Monto\n");

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
                                .append(String.format("%11s", detail.getAmount()))
                                .append(" ")
                                .append(String.format("%9s", df.format(detail.getPriceByLot())))
                                .append(" ")
                                .append(String.format("%10s", df.format(totalAmount)))
                                .append("\n");
                    } else {
                        text.append(String.format("%-10s", nameArrayList.get(i))).append("\n");
                    }
                }
            }

            text.append("\nTotal: ").append(df.format(receiptResponse.getInvoice().getInvoiceTotal())).append("\n\n");

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
                                .append(String.format("%10s", df.format(currentDetail.getPriceItem()))).append(" ")
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

            text.append("\nTotal: ").append(df.format(receiptResponse.getInvoice().getInvoiceTotal())).append("\n\n");

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
}

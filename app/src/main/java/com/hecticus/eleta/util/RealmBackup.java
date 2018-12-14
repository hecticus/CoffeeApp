package com.hecticus.eleta.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;


import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model_new.persistence.ManagerDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


public class RealmBackup {

    private File EXPORT_REALM_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    private String EXPORT_REALM_FILE_NAME = "coffeEletaBackup.realm";
    private String IMPORT_REALM_FILE_NAME = "coffeEleta.realm"; // Eventually replace this if you're using a custom db name

    private final static String TAG = RealmBackup.class.getName();

    private Activity activity;
    private Realm realm;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public RealmBackup(Activity activity) {
        //this.realm = new DatabaseHandler(activity.getApplicationContext()).getRealmIstance();
        this.realm = realm;
        this.activity = activity;
    }

    public void backup() {
        // First check if we have storage permissions
        checkStoragePermissions(activity);
        File exportRealmFile;

        Log.d(TAG, "Realm DB Path = " + realm.getPath());

        EXPORT_REALM_PATH.mkdirs();

        // create a backup file
        exportRealmFile = new File(EXPORT_REALM_PATH, EXPORT_REALM_FILE_NAME);

        // if backup file already exists, delete it
        exportRealmFile.delete();

        // copy current realm to backup file
        realm.writeCopyTo(exportRealmFile);

        /*String msg = "File exported to Path: " + EXPORT_REALM_PATH + "/" + EXPORT_REALM_FILE_NAME;
        Toast.makeText(activity.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        Log.d(TAG, msg);

        realm.close();*/





        List<Provider> providersList = new ArrayList<>();
        List<Provider> providersAux =
                realm.where(Provider.class)
                        .equalTo("deleteOffline", false)
                        .equalTo("addOffline", true)
                        .findAllSorted("unixtime");
        if (providersAux != null) {
            providersList.addAll(realm.copyFromRealm(providersAux));
        }
        providersAux =
                realm.where(Provider.class)
                        .equalTo("deleteOffline", false)
                        .equalTo("addOffline", false)
                        .equalTo("editOffline", true)
                        .findAllSorted("unixtime");
        if (providersAux != null) {
            providersList.addAll(realm.copyFromRealm(providersAux));
        }
        for(Provider provider : providersList){
            realm.beginTransaction();
            provider.deleteFromRealm();
            realm.commitTransaction();
        }

        List<InvoicePost> invoicePostList = new ArrayList<>();
        invoicePostList.addAll(ManagerDB.getPendingInvoicePostsList(true));
        invoicePostList.addAll(ManagerDB.getPendingInvoicePostsList(false));

        for(InvoicePost invoicePost : invoicePostList){
            realm.beginTransaction();
            invoicePost.deleteFromRealm();
            realm.commitTransaction();
        }

        List<Provider> providersListDelete = new ArrayList<>();
        List<Provider> providers = realm
                .where(Provider.class)
                .equalTo("deleteOffline", true)
                .findAllSorted("unixtime");
        if (providers != null) {
            providersListDelete.addAll(realm.copyFromRealm(providers));
        }

        for(Provider provider : providersListDelete){
            realm.beginTransaction();
            provider.deleteFromRealm();
            realm.commitTransaction();
        }

        List<InvoiceDetails> invoiceDetailsAdd = new ArrayList<>();
        List<InvoiceDetails> invoiceDetailsList1 = realm
                .where(InvoiceDetails.class)
                .equalTo("addOffline", true)
                .findAllSorted("startDate");
        if (invoiceDetailsList1 != null) {
            invoiceDetailsAdd.addAll(realm.copyFromRealm(invoiceDetailsList1));
        }

        for(InvoiceDetails invoiceDetails : invoiceDetailsAdd){
            realm.beginTransaction();
            invoiceDetails.deleteFromRealm();
            realm.commitTransaction();
        }
        List<Invoice> invoiceList = new ArrayList<>();
        List<Invoice> invoices = realm.where(Invoice.class).equalTo("deleteOffline", true).findAllSorted("invoiceStartDate");
        if (invoices != null) {
            invoiceList.addAll(realm.copyFromRealm(invoices));
        }
        for(Invoice invoice : invoiceList){
            realm.beginTransaction();
            invoice.deleteFromRealm();
            realm.commitTransaction();
        }
        List<InvoiceDetails> invoiceDetailsEdit = new ArrayList<>();
        List<InvoiceDetails> invoiceDetailsList = realm
                .where(InvoiceDetails.class)
                .equalTo("editOffline", true)
                .findAllSorted("startDate");
        if (invoiceDetailsList != null) {
            invoiceDetailsEdit.addAll(realm.copyFromRealm(invoiceDetailsList));
        }

        for(InvoiceDetails invoiceDetails : invoiceDetailsEdit){
            realm.beginTransaction();
            invoiceDetails.deleteFromRealm();
            realm.commitTransaction();
        }
        List<InvoiceDetails> invoiceDetailsDelete = new ArrayList<>();
        List<InvoiceDetails> ofDays = realm
                .where(InvoiceDetails.class)
                .equalTo("deleteOffline", true)
                .findAllSorted("startDate");
        if (ofDays != null) {
            invoiceDetailsDelete.addAll(realm.copyFromRealm(ofDays));
        }
        for(InvoiceDetails invoiceDetails : invoiceDetailsDelete){
            realm.beginTransaction();
            invoiceDetails.deleteFromRealm();
            realm.commitTransaction();
        }
        List<Invoice> invoiceClosed = new ArrayList<>();
        List<Invoice> invoiceForClosed = realm
                .where(Invoice.class)
                .equalTo("isClosed", true)
                .findAllSorted("invoiceStartDate");
        if (invoiceForClosed != null) {
            invoiceClosed.addAll(realm.copyFromRealm(invoiceForClosed));
        }
        for(Invoice invoice : invoiceClosed){
            realm.beginTransaction();
            invoice.deleteFromRealm();
            realm.commitTransaction();
        }

        String msg = "File exported to Path: " + EXPORT_REALM_PATH + "/" + EXPORT_REALM_FILE_NAME;
        Toast.makeText(activity.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        Log.d(TAG, msg);

        realm.close();
    }

    public void restore() {
        checkStoragePermissions(activity);
        //Restore
        String restoreFilePath = EXPORT_REALM_PATH + "/" + EXPORT_REALM_FILE_NAME;

        Log.d(TAG, "oldFilePath = " + restoreFilePath);

        copyBundledRealmFile(restoreFilePath, IMPORT_REALM_FILE_NAME);
        Log.d(TAG, "Data restore is done");
    }

    private String copyBundledRealmFile(String oldFilePath, String outFileName) {
        try {
            File file = new File(activity.getApplicationContext().getFilesDir(), outFileName);

            FileOutputStream outputStream = new FileOutputStream(file);

            FileInputStream inputStream = new FileInputStream(new File(oldFilePath));

            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, bytesRead);
            }
            outputStream.close();
            Toast.makeText(activity.getApplicationContext(),"importo la db", Toast.LENGTH_LONG).show();
            return file.getAbsolutePath();
        } catch (IOException e) {
            Log.d(TAG, "Data restore is done. false, false, false");
            e.printStackTrace();
        }
        return null;
    }

    private void checkStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private String dbPath(){
        return realm.getPath();
    }

}

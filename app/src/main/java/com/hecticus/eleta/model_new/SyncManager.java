package com.hecticus.eleta.model_new;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ProgressBar;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hecticus.eleta.R;
import com.hecticus.eleta.home.HomeActivity;
import com.hecticus.eleta.model.request.invoice.ItemPost;
import com.hecticus.eleta.model.response.StatusInvoice;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.providers.ProviderType;
import com.hecticus.eleta.model_new.persistence.ManagerDB;
import com.hecticus.eleta.model_new.persistence.ManagerServices;
import com.hecticus.eleta.model.request.invoice.InvoicePost;
import com.hecticus.eleta.model.response.Message;
import com.hecticus.eleta.model.response.invoice.CreateInvoiceResponse;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.model.response.invoice.InvoiceDetailsResponse;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.providers.ProviderCreationResponse;
import com.hecticus.eleta.model_new.retrofit_interface.InvoiceRetrofitInterface;
import com.hecticus.eleta.model_new.retrofit_interface.ProviderRetrofitInterface;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.ErrorHandling;
import com.hecticus.eleta.util.FileUtils;
import com.hecticus.eleta.util.TimeHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import hugo.weaving.DebugLog;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by roselyn545 on 2/11/17.
 */

public class SyncManager {

    /*public enum SyncErrorType {
        DELETING_PROVIDER_WITH_OPEN_INVOICES,
        GENERIC
    }*/

    private static ProviderRetrofitInterface providersApi;
    private static InvoiceRetrofitInterface invoiceApi;
    private static Boolean isSyncParcial = false;
    private User user = new User(SessionManager.getUserId(HomeActivity.INSTANCE));

    private ProgressBar progressBar;
    private Integer countTotal = 0, countSuccess=0;
    private List<Provider> providersList;
    private List<InvoicePost> invoicePostList;
    private List<Invoice> invoiceList;
    private List<Invoice> invoiceClosed;
    private List<InvoiceDetails> invoiceDetailsDelete;
    private List<InvoiceDetails> invoiceDetailsEdit;
    private List<InvoiceDetails> invoiceDetailsAdd;

    private boolean somethingHasBeenSynced = false;

    private int failedImageUploads = 0;

    @DebugLog
    public SyncManager() {
        if (providersApi == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(20000, TimeUnit.SECONDS)
                    .readTimeout(20000, TimeUnit.SECONDS)
                    .writeTimeout(20000, TimeUnit.SECONDS)
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public okhttp3.Response intercept(Chain chain) throws IOException {
                                    Request request = chain.request().newBuilder()
                                            .addHeader("Authorization", SessionManager.getAccessToken(HomeActivity.INSTANCE))
                                            .addHeader("Content-Type", "application/json").build();
                                    return chain.proceed(request);
                                }
                            }).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient)
                    .build();

            providersApi = retrofit.create(ProviderRetrofitInterface.class);
            invoiceApi = retrofit.create(InvoiceRetrofitInterface.class);
        }
    }


    @DebugLog
    public void startSync(ProgressBar progressBar) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.progressBar = progressBar;
        List<Provider> providersList = new ArrayList<>();

        List<Provider> providersAux =
                Realm.getDefaultInstance().where(Provider.class)
                        .equalTo("deleteOffline", false)
                        .equalTo("addOffline", true)
                        .findAllSorted("unixtime");

        if (providersAux != null) {
            providersList.addAll(Realm.getDefaultInstance().copyFromRealm(providersAux));
        }

        providersAux =
                Realm.getDefaultInstance().where(Provider.class)
                        .equalTo("deleteOffline", false)
                        .equalTo("addOffline", false)
                        .equalTo("editOffline", true)
                        .findAllSorted("unixtime");

        if (providersAux != null) {
            providersList.addAll(Realm.getDefaultInstance().copyFromRealm(providersAux));
        }

        List<InvoicePost> invoicePostList = new ArrayList<>();
        invoicePostList.addAll(ManagerDB.getPendingInvoicePostsList(true));
        invoicePostList.addAll(ManagerDB.getPendingInvoicePostsList(false));





        List<Provider> providersListDelete = new ArrayList<>();
        List<Provider> providers = Realm.getDefaultInstance()
                .where(Provider.class)
                .equalTo("deleteOffline", true)
                .findAllSorted("unixtime");
        if (providers != null) {
            providersListDelete.addAll(Realm.getDefaultInstance().copyFromRealm(providers));
        }





        List<InvoiceDetails> invoiceDetailsAdd = new ArrayList<>();
        List<InvoiceDetails> invoiceDetailsList1 = Realm.getDefaultInstance()
                .where(InvoiceDetails.class)
                .equalTo("addOffline", true)
                .findAllSorted("startDate");
        if (invoiceDetailsList1 != null) {
            invoiceDetailsAdd.addAll(Realm.getDefaultInstance().copyFromRealm(invoiceDetailsList1));
        }







        List<Invoice> invoiceList = new ArrayList<>();
        List<Invoice> invoices = Realm.getDefaultInstance().where(Invoice.class).equalTo("deleteOffline", true).findAllSorted("invoiceStartDate");
        if (invoices != null) {
            invoiceList.addAll(Realm.getDefaultInstance().copyFromRealm(invoices));
        }



        List<InvoiceDetails> invoiceDetailsEdit = new ArrayList<>();
        List<InvoiceDetails> invoiceDetailsList = Realm.getDefaultInstance()
                .where(InvoiceDetails.class)
                .equalTo("editOffline", true)
                .findAllSorted("startDate");
        if (invoiceDetailsList != null) {
            invoiceDetailsEdit.addAll(Realm.getDefaultInstance().copyFromRealm(invoiceDetailsList));
        }





        List<InvoiceDetails> invoiceDetailsDelete = new ArrayList<>();

        List<InvoiceDetails> ofDays = Realm.getDefaultInstance()
                .where(InvoiceDetails.class)
                .equalTo("deleteOffline", true)
                .findAllSorted("startDate");

        if (ofDays != null) {
            invoiceDetailsDelete.addAll(Realm.getDefaultInstance().copyFromRealm(ofDays));
        }





        List<Invoice> invoiceClosed = new ArrayList<>();

        List<Invoice> invoiceForClosed = Realm.getDefaultInstance()
                .where(Invoice.class)
                .equalTo("isClosed", true)
                .findAllSorted("invoiceStartDate");

        if (invoiceForClosed != null) {
            invoiceClosed.addAll(Realm.getDefaultInstance().copyFromRealm(invoiceForClosed));
        }




        countTotal = providersList.size() + invoicePostList.size() + invoiceList.size() +
                invoiceClosed.size() + invoiceDetailsDelete.size() +
                invoiceDetailsEdit.size() + invoiceDetailsAdd.size() +providersListDelete.size();





        syncProviders();
    }

    @DebugLog
    public void syncProviders() {
        failedImageUploads = 0;

        providersList = new ArrayList<>();

        List<Provider> providersAux =
                Realm.getDefaultInstance().where(Provider.class)
                        .equalTo("deleteOffline", false)
                        .equalTo("addOffline", true)
                        .findAllSorted("unixtime");

        if (providersAux != null) {
            providersList.addAll(Realm.getDefaultInstance().copyFromRealm(providersAux));
        }

        providersAux =
                Realm.getDefaultInstance().where(Provider.class)
                        .equalTo("deleteOffline", false)
                        .equalTo("addOffline", false)
                        .equalTo("editOffline", true)
                        .findAllSorted("unixtime");

        if (providersAux != null) {
            providersList.addAll(Realm.getDefaultInstance().copyFromRealm(providersAux));
        }

        if (providersList.isEmpty()) {
            syncInvoices();
            return;
        }

        syncNextPendingProvider();
    }

    @DebugLog
    private void syncNextPendingProvider() {
        if (providersList.size() <= 0) {
            TimeHandler timeHandler = new TimeHandler(7000, new TimeHandler.OnTimeComplete() {
                @Override
                public void onFinishTime() {
                    syncInvoices();
                }
            });
            timeHandler.start();
            return;
        }

        final Provider currentProviderToSync = providersList.get(0);
        providersList.remove(currentProviderToSync);
        countSuccess++;
        progressBar.setProgress(countSuccess*100/countTotal);

        final Integer oldLocalProviderId;

        if (currentProviderToSync.getIdProvider() != null && currentProviderToSync.getIdProvider() < 0) {
            oldLocalProviderId = currentProviderToSync.getIdProvider();
            //currentProviderToSync.setIdProvider(null);
        } else {
            oldLocalProviderId = null;
        }

        somethingHasBeenSynced = true;
        Call<ProviderCreationResponse> call;

        final boolean isAdding = currentProviderToSync.isAddOffline();
        final String operationName;
        final Integer idLocal = currentProviderToSync.getIdProvider();

        if (isAdding) {
            operationName = "createProviderSync";
            currentProviderToSync.setIdProvider(null);
            currentProviderToSync.setProviderType(new ProviderType(currentProviderToSync.getIdProviderType()));
            call = providersApi.createProvider(currentProviderToSync);
        } else {
            operationName = "editProviderSync";
            currentProviderToSync.setProviderType(new ProviderType(currentProviderToSync.getIdProviderType()));
            call = providersApi.updateProviderData(currentProviderToSync.getIdProvider(), currentProviderToSync);
        }
        call.enqueue(new Callback<ProviderCreationResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<ProviderCreationResponse> call,
                                   @NonNull Response<ProviderCreationResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    try {

                        Integer newProviderId = null;

                        Realm realm = Realm.getDefaultInstance();
                        try {
                            newProviderId = response.body().getProvider().getIdProvider();
                            realm.beginTransaction();
                            /*currentProviderToSync.setIdProvider(newProviderId);
                            currentProviderToSync.setAddOffline(false);
                            currentProviderToSync.setEditOffline(false);
                            realm.insertOrUpdate(currentProviderToSync);*/
                            //currentProviderToSync.deleteFromRealm();
                            realm.insertOrUpdate(response.body().getProvider());
                            realm.commitTransaction();
                        } finally {
                            realm.close();
                            syncNextPendingProvider();
                            if (oldLocalProviderId != null){
                                Log.d("DEBUG", "oldlocalproviderid" + oldLocalProviderId + "newproviderid"+ newProviderId);
                                updateProviderIdInInvoices(oldLocalProviderId, newProviderId);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        isSyncParcial = true;
                        ErrorHandling.errorCodeInServerResponseProcessing(e);
                        if(operationName.equals("createProviderSync")){
                            if(logDataBase(new Gson().toJson(currentProviderToSync),"errorCatch Sync en endPoint (/provider) por post... "+ e.toString())!=-1){
                                Realm realm = Realm.getDefaultInstance();
                                realm.beginTransaction();
                                Provider provider = realm
                                        .where(Provider.class)
                                        .equalTo("unixtime", currentProviderToSync.getUnixtime())
                                        .findFirst();
                                provider.deleteFromRealm();
                                realm.commitTransaction();
                            }
                        } else {
                            if(logDataBase(new Gson().toJson(currentProviderToSync),"errorCatch Sync en endPoint (/provider/"+currentProviderToSync.getIdProvider()+") por put... "+ e.toString())!=-1){
                                Realm realm = Realm.getDefaultInstance();
                                realm.beginTransaction();
                                Provider provider = realm
                                        .where(Provider.class)
                                        .equalTo("unixtime", currentProviderToSync.getUnixtime())
                                        .findFirst();
                                provider.deleteFromRealm();
                                realm.commitTransaction();
                            }
                        }
                    }finally {
                        syncNextPendingProvider();
                    }
                } else {
                    if(response.code() == 409){
                        currentProviderToSync.setIdProvider(idLocal);
                        getProvider(currentProviderToSync);
                    } else {
                        isSyncParcial = true;
                        if (operationName.equals("createProviderSync")) {
                            try {
                                if (logDataBase(new Gson().toJson(currentProviderToSync), "error ResponseCode()=" + response.code() + " Sync en endPoint (/provider) por post... " + response.errorBody().string()) != -1) {
                                    Realm realm = Realm.getDefaultInstance();
                                    realm.beginTransaction();
                                    Provider provider = realm
                                            .where(Provider.class)
                                            .equalTo("unixtime", currentProviderToSync.getUnixtime())
                                            .findFirst();
                                    provider.deleteFromRealm();
                                    realm.commitTransaction();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                if (logDataBase(new Gson().toJson(currentProviderToSync), "error ResponseCode()=" + response.code() + " Sync en endPoint (/provider/" + currentProviderToSync.getIdProvider() + ") por put... " + response.errorBody().string()) != -1) {
                                    Realm realm = Realm.getDefaultInstance();
                                    realm.beginTransaction();
                                    Provider provider = realm
                                            .where(Provider.class)
                                            .equalTo("unixtime", currentProviderToSync.getUnixtime())
                                            .findFirst();
                                    provider.deleteFromRealm();
                                    realm.commitTransaction();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        syncNextPendingProvider();
                    }
                }
            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<ProviderCreationResponse> call, @NonNull Throwable t) {
                isSyncParcial = true;
                ErrorHandling.syncErrorCodeWebServiceFailed(t);
                /*if(operationName.equals("createProviderSync")){
                    if(*/logDataBase(new Gson().toJson(currentProviderToSync),"errorFatal Sync en endPoint (/provider) por post... " +t.toString());/*!=-1){
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        currentProviderToSync.deleteFromRealm();
                        realm.commitTransaction();
                    }

                } else {
                    if(logDataBase(new Gson().toJson(currentProviderToSync),"errorFatal Sync en endPoint (/provider/"+currentProviderToSync.getIdProvider()+") por put... "+t.toString())!=-1){
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        currentProviderToSync.deleteFromRealm();
                        realm.commitTransaction();
                    }
                }*/
                syncNextPendingProvider();
            }
        });
    }

    @DebugLog
    private void syncProviderPhoto(@NonNull final Provider provider) {
        File imageToUploadFile=null;
        try {
            imageToUploadFile = new File(provider.getMultimediaProfile().getMultimediaCDN().getUrl()); //todo img

        }catch (Exception e){
        }

        if (!imageToUploadFile.exists()) {
            //LogDataBase.e("PHOTO", "--->Can't sync image: " + provider.getMultimediaProfile().getMultimediaCDN().getUrl()); //todo img
            syncNextPendingProvider();
            return;
        }
        String base64Image =null;
        try {
            base64Image = FileUtils.getOptimizedBase64FromImagePath(provider.getMultimediaProfile().getMultimediaCDN().getUrl());//todo img

        }catch (Exception e){
        }


        //final String previousProviderImageString = provider.getPhotoProvider();
        //provider.setPhotoProvider(base64Image);

        MultimediaProfile media = new MultimediaProfile("image", new MultimediaCDN(base64Image));

        Call<ResponseBody> call = providersApi.updateProviderImage(provider.getIdProvider(), media);

        call.enqueue(new Callback<ResponseBody>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.d("PHOTO", "--->uploadImageRequest SYNC Success " + response.body());

                        //onImageUpdateSuccess(response.body().getUploadedImageUrl());

                        Realm realm = Realm.getDefaultInstance();
                        try {
                            realm.beginTransaction();

                            try {
                                //todo img
                                //provider.setPhotoProvider(response.body().getUploadedImageUrl());
                                //provider.setMultimediaProfile(new MultimediaProfile("image", new MultimediaCDN("",response.body().getUploadedImageUrl())));//new MultimediaProfile("image", new MultimediaCDN(base64Image));
                                //todo revisar arriba comentado
                            }catch (Exception e){
                            }
                            realm.insertOrUpdate(provider);
                            realm.commitTransaction();
                            Log.d("PHOTO", "--->Success syncProviderPhoto, updated local: " + provider);
                        } finally {
                            realm.close();
                            syncNextPendingProvider();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        failedImageUploads++;
                        syncNextPendingProvider();
                        //HomeActivity.INSTANCE.syncFailed();
                        //onImageUpdateError(provider, previousProviderImageString, null);
                    }
                } else {

                    String errorMessage;

                    try {
                        errorMessage = new JSONObject(response.errorBody().string()) + "";
                        Log.e("PHOTO", "--->uploadImageRequest SYNC Error " + errorMessage);
                    } catch (JSONException | IOException e) {
                        Log.e("PHOTO", "--->uploadImageRequest SYNC Error with error");
                        e.printStackTrace();
                    }

                    failedImageUploads++;
                    syncNextPendingProvider();
                    //HomeActivity.INSTANCE.syncFailed();
                    //onImageUpdateError(provider, previousProviderImageString, null);
                }
            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                t.printStackTrace();

                Log.d("RETRO", "--->ERROR: " + t.getMessage());

                failedImageUploads++;
                syncNextPendingProvider();
                //HomeActivity.INSTANCE.syncFailed();
                //onImageUpdateError(provider, previousProviderImageString, null);
            }
        });
    }


    @DebugLog
    private void updateProviderIdInInvoices(Integer oldLocalProviderId, final Integer newProviderId) {
        Log.d("DEBUG", "BRAYANPROVIDER old"+ oldLocalProviderId +" new "+ newProviderId);
        if (newProviderId == null || newProviderId.equals(oldLocalProviderId)) {
            Log.d("BUG", "--->updateProviderIdInInvoices not needed: " + newProviderId);
        } else {

            Realm realm = Realm.getDefaultInstance();

            final RealmResults<Invoice> existingInvoices = Realm.getDefaultInstance().where(Invoice.class)
                    .equalTo("providerId", oldLocalProviderId)
                    .findAll();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    for (Invoice invoice : existingInvoices) {
                        invoice.setProviderId(newProviderId);

                        if (invoice.getProvider() != null) {
                            invoice.getProvider().setIdProvider(newProviderId);
                            realm.insertOrUpdate(invoice);
                        }
                    }
                }
            });

            final RealmResults<InvoicePost> existingInvoicePosts = realm.where(InvoicePost.class)
                    .equalTo("providerId", oldLocalProviderId)
                    .findAll();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    for (InvoicePost invoicePost : existingInvoicePosts) {
                        invoicePost.setProviderId(newProviderId);
                        realm.insertOrUpdate(invoicePost);
                    }
                }
            });
        }
    }

    @DebugLog
    public void syncInvoices() {
        invoicePostList = new ArrayList<>();
        invoicePostList.addAll(ManagerDB.getPendingInvoicePostsList(true));
        invoicePostList.addAll(ManagerDB.getPendingInvoicePostsList(false));
        if (invoicePostList.size() <= 0) {
            syncDeletedProvider();
            return;
        }
        saveNextInvoice();
    }


    @DebugLog
    public void saveNextInvoice() {
        if (invoicePostList == null || invoicePostList.size() <= 0) {

            if (invoicePostList == null)
                Log.e("SYNC", "--->Rare saveNextInvoice() invoicePostList null");

            syncDeletedProvider();
            return;
        }

        final InvoicePost firstInvoicePost = invoicePostList.get(0);
        invoicePostList.remove(firstInvoicePost);
        countSuccess++;
        progressBar.setProgress(countSuccess*100/countTotal);

        somethingHasBeenSynced = true;
        Call<CreateInvoiceResponse> call;
        //if (firstInvoicePost.getInvoiceId() == -1) {

            final com.hecticus.eleta.model_new.Invoice invoice = new com.hecticus.eleta.model_new.Invoice(
                    firstInvoicePost, firstInvoicePost.getProviderId()); //todo error con provider
        Log.d("DEBUG invoice", "sync"+ new Gson().toJson(invoice));
            call = invoiceApi.newInvoiceDetail(invoice);
            call.enqueue(new Callback<CreateInvoiceResponse>() {
                @Override
                public void onResponse(Call<CreateInvoiceResponse> call, Response<CreateInvoiceResponse> response) {

                    Log.d("DEBUG", "response: " + response.code());
                    if (response.isSuccessful() && response.body() != null) {
                        try {

                            Realm realm = Realm.getDefaultInstance();

                            List<Invoice> invoiceList1 = realm.where(Invoice.class).findAll();
                            Log.d("DETAILS", "--->" + invoiceList1);


                            final Invoice invoiceInRealm = realm
                                    .where(Invoice.class)
                                    .equalTo("id2", firstInvoicePost.getInvoiceLocalId())
                                    .findFirst();

                            try {
                                if (invoiceInRealm != null) {
                                    realm.beginTransaction();
                                    //invoiceInRealm.deleteFromRealm();
                                    if(invoiceInRealm.getStatusInvo().equals("Cerrada") || invoiceInRealm.isClosed()){
                                        Log.d("DEBUG", "brayan---> cerrada");
                                        //Log.d("DEBUG", "brayan---> "+response.body().getResult().getInvoiceId());
                                        invoiceInRealm.setInvoiceId(response.body().getResult().getInvoiceId());
                                        invoiceInRealm.setAddOffline(false);
                                        invoiceInRealm.setClosed(true);
                                        invoiceInRealm.setStatusInvo("Cerrada");
                                        realm.insertOrUpdate(invoiceInRealm);
                                    }else{
                                        invoiceInRealm.deleteFromRealm();
                                    }

                                    realm.commitTransaction();
                                }/*else {
                                    realm.beginTransaction();
                                    invoiceInRealm.deleteFromRealm();
                                    realm.commitTransaction();
                                }*/
                                ManagerDB.findAndDeleteLocalInvoicePost(firstInvoicePost);

                            } finally {
                                realm.close();
                                //saveNextInvoice();
                            }
                        } catch (Exception e) {
                            Log.e("DEBUG exploto catch", "exploto catch"+e.toString());
                            isSyncParcial = true;
                            if(logDataBase(new Gson().toJson(invoice),"error catch Sync en endPoint (/invoice2) por post... " + e.toString())!=-1){
                                Realm realm = Realm.getDefaultInstance();
                                realm.beginTransaction();
                                Invoice invoiceInRealm = realm
                                        .where(Invoice.class)
                                        .equalTo("id2", firstInvoicePost.getInvoiceLocalId())
                                        .findFirst();
                                if (invoiceInRealm != null) {
                                    invoiceInRealm.deleteFromRealm();
                                }
                                realm.commitTransaction();
                            }
                            ErrorHandling.syncErrorCodeInServerResponseProcessing(e);
                            e.printStackTrace();
                        }finally {
                            saveNextInvoice();
                        }
                    } else {
                        isSyncParcial = true;
                        try {
                            if(logDataBase(new Gson().toJson(invoice),"error ResponseCode()="+ response.code()+" Sync en endPoint (/invoice2) por post... " + response.errorBody().string())!=-1){
                                Realm realm = Realm.getDefaultInstance();
                                realm.beginTransaction();
                                Invoice invoiceInRealm = realm
                                        .where(Invoice.class)
                                        .equalTo("id2", firstInvoicePost.getInvoiceLocalId())
                                        .findFirst();
                                if (invoiceInRealm != null) {
                                    invoiceInRealm.deleteFromRealm();
                                }
                                realm.commitTransaction();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        saveNextInvoice();

                    }
                }

                @Override
                public void onFailure(Call<CreateInvoiceResponse> call, Throwable t) {
                    Log.d("DEBUG", "exploto esta verga, en invoice2");
                    isSyncParcial = true;
                    ErrorHandling.syncErrorCodeWebServiceFailed(t);
                    /*if(*/logDataBase(new Gson().toJson(invoice),"errorFatal Sync en endPoint (/invoice2) por post... " +t.toString());/*!=-1){
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        firstInvoicePost.deleteFromRealm();
                        realm.commitTransaction();
                    }*/
                    saveNextInvoice();

                }
            });

        //}


        /*else {
            Log.d("DETAILS", "---> firstInvoicePost is an edition: " + firstInvoicePost);
            //todo put
            //call = invoiceApi.updateInvoiceDetail(firstInvoicePost);
        }*/


    }

    @DebugLog
    public void syncDeletedProvider() {
        providersList = new ArrayList<>();
        List<Provider> providers = Realm.getDefaultInstance()
                .where(Provider.class)
                .equalTo("deleteOffline", true)
                .findAllSorted("unixtime");
        if (providers != null) {
            providersList.addAll(Realm.getDefaultInstance().copyFromRealm(providers));
        }
        if (providersList.size() <= 0) {
            syncAddInvoiceDetails();
            return;
        }
        deleteNextProvider();
    }


    @DebugLog
    public void deleteNextProvider() {
        if (providersList.size() <= 0) {
            syncAddInvoiceDetails();
            return;
        }

        final Provider firstProvider = providersList.get(0);
        providersList.remove(firstProvider);
        countSuccess++;
        progressBar.setProgress(countSuccess*100/countTotal);

        somethingHasBeenSynced = true;

        Call<ResponseBody> call = providersApi.deleteProvider(firstProvider.getIdProvider());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d("DEBUG", "response: " + response.code());
                if (response.isSuccessful() && response.body()!=null) {
                    try {

                        Realm realm = Realm.getDefaultInstance();
                        try {

                            Provider provider = Realm.getDefaultInstance().where(Provider.class)
                                    .equalTo("idProvider", firstProvider.getIdProvider())
                                    .findFirst();


                            if (provider != null) {
                                realm.beginTransaction();
                                try{
                                    provider.deleteFromRealm();
                                }catch (Exception e){
                                    ErrorHandling.syncErrorCodeInServerResponseProcessing(e);
                                }
                                realm.commitTransaction();
                            }

                        } finally {
                            realm.close();
                            //deleteNextProvider();
                        }
                    } catch (Exception e) {
                        isSyncParcial = true;
                        ErrorHandling.syncErrorCodeInServerResponseProcessing(e);
                        if(logDataBase(new Gson().toJson(firstProvider.getIdProvider()),"err catch Sync en endPoint (/provider/"+firstProvider.getIdProvider()+") por delete... " +e.toString())!=-1){
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            Provider provider = Realm.getDefaultInstance().where(Provider.class)
                                    .equalTo("idProvider", firstProvider.getIdProvider())
                                    .findFirst();
                            provider.deleteFromRealm();
                            realm.commitTransaction();
                        }
                    } finally {
                        deleteNextProvider();
                    }
                } else {
                    isSyncParcial = true;
                    try {
                        if(logDataBase(new Gson().toJson(firstProvider.getIdProvider()),"error ResponseCode()="+ response.code()+"Sync en endPoint (/provider/"+firstProvider.getIdProvider()+") por delete... " +response.errorBody().string())!=-1){
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            Provider provider = Realm.getDefaultInstance().where(Provider.class)
                                    .equalTo("idProvider", firstProvider.getIdProvider())
                                    .findFirst();
                            provider.deleteFromRealm();
                            realm.commitTransaction();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    deleteNextProvider();
                    //Todo indicar que el proveedor no pudo ser eliminado xq posee invoice abierto
                    //HomeActivity.INSTANCE.syncFailed("deleteProviderSync errorResponse (DELETING_PROVIDER_WITH_OPEN_INVOICES): " + response);

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                isSyncParcial = true;
                ErrorHandling.syncErrorCodeWebServiceFailed(t);
                /*if(*/logDataBase(new Gson().toJson(firstProvider.getIdProvider()),"errorFatal Sync en endPoint (/provider/"+firstProvider.getIdProvider()+") por delete... " +t.toString());/*!=-1){
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    firstProvider.deleteFromRealm();
                    realm.commitTransaction();
                }*/
                deleteNextProvider();
            }
        });
    }

    @DebugLog
    public void syncAddInvoiceDetails() {
        invoiceDetailsAdd = new ArrayList<>();
        List<InvoiceDetails> invoiceDetailsList = Realm.getDefaultInstance()
                .where(InvoiceDetails.class)
                .equalTo("addOffline", true)
                .findAllSorted("startDate");
        if (invoiceDetailsList != null) {
            invoiceDetailsAdd.addAll(Realm.getDefaultInstance().copyFromRealm(invoiceDetailsList));
        }
        if (invoiceDetailsAdd.size() <= 0) {
            syncEditInvoiceDetail();
            return;
        }
        addNextInvoiceDetails();
    }

    @DebugLog
    private void addNextInvoiceDetails() {
        if (invoiceDetailsAdd.size() <= 0) {
            syncEditInvoiceDetail();
            return;
        }

        final InvoiceDetails firstInvoiceDetails = invoiceDetailsAdd.get(0);
        invoiceDetailsAdd.remove(firstInvoiceDetails);
        countSuccess++;
        progressBar.setProgress(countSuccess*100/countTotal);

        somethingHasBeenSynced = true;
        final InvoiceDetail invoiceDetail = new InvoiceDetail(firstInvoiceDetails);
        invoiceDetail.setId(null);
        Call<ResponseBody> call = invoiceApi.newInvoiceDetailAdd(invoiceDetail);

        call.enqueue(new Callback<ResponseBody>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        Realm realm = Realm.getDefaultInstance();

                        try {
                            JSONObject json = new JSONObject(response.body().string());
                            Log.d("DEBUG add details", "json " + json);
                            int id = (int) json.getJSONObject("result").getLong("id");

                            InvoiceDetails invoice = Realm.getDefaultInstance().where(InvoiceDetails.class)
                                    .equalTo("localId", firstInvoiceDetails.getLocalId())
                                    .findFirst();
                            if (invoice != null) {
                                realm.beginTransaction();
                                try {
                                    invoice.setId(id);
                                    invoice.setAddOffline(false);
                                    realm.insertOrUpdate(invoice);
                                } catch (Exception e) {
                                    ErrorHandling.errorCodeBdLocal(e);
                                }
                                realm.commitTransaction();
                            } else {
                                Log.e("DETAILS brayan", "--->invoiceDetails==null");
                            }
                        } finally {
                            realm.close();
                            //addNextInvoiceDetails();
                        }
                    }catch (Exception e) {
                        isSyncParcial = true;
                        ErrorHandling.errorCodeInServerResponseProcessing(e);
                        if(logDataBase(new Gson().toJson(invoiceDetail),"error catch Sync en endPoint (/invoiceDetail) por post... " +e.toString())!=-1){
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            InvoiceDetails invoiceDetails = realm.where(InvoiceDetails.class)
                                    .equalTo("localId", firstInvoiceDetails.getLocalId())
                                    .findFirst();
                            invoiceDetails.deleteFromRealm();
                            realm.commitTransaction();
                        }
                    } finally {
                        addNextInvoiceDetails();
                    }
                } else {
                    isSyncParcial = true;
                    try {
                        if(logDataBase(new Gson().toJson(invoiceDetail),"error ResponseCode()="+ response.code()+" Sync en endPoint (/invoiceDetail) por post... " + response.errorBody().string())!=-1){
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            InvoiceDetails invoiceDetails = realm.where(InvoiceDetails.class)
                                    .equalTo("localId", firstInvoiceDetails.getLocalId())
                                    .findFirst();
                            invoiceDetails.deleteFromRealm();
                            realm.commitTransaction();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    addNextInvoiceDetails();
                }

            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                isSyncParcial = true;
                ErrorHandling.syncErrorCodeWebServiceFailed(t);
                /*if(*/logDataBase(new Gson().toJson(invoiceDetail),"errorFatal Sync en endPoint (/invoiceDetail) por post... " +t.toString());/*!=-1){
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    firstInvoiceDetails.deleteFromRealm();
                    realm.commitTransaction();
                }*/
                addNextInvoiceDetails();
            }
        });

    }

    @DebugLog
    public void syncDeletedInvoice() {
        invoiceList = new ArrayList<>();
        List<Invoice> invoices = Realm.getDefaultInstance().where(Invoice.class).equalTo("deleteOffline", true).findAllSorted("invoiceStartDate");
        if (invoices != null) {
            invoiceList.addAll(Realm.getDefaultInstance().copyFromRealm(invoices));
        }
        if (invoiceList.size() <= 0) {
            syncCloseIncoice();
            return;
        }
        deleteNextInvoice();
    }

    @DebugLog
    private void deleteNextInvoice() {
        if (invoiceList.size() <= 0) {
            syncCloseIncoice();
            return;
        }

        final Invoice firstInvoice = invoiceList.get(0);
        invoiceList.remove(firstInvoice);
        countSuccess++;
        progressBar.setProgress(countSuccess*100/countTotal);

        if (firstInvoice.isAddOffline()) {
            Log.d("DETAILS", "--->deleteNextInvoice firstInvoice IGNORED (Created offline): " + firstInvoice);
            onInvoiceDeleteSyncSuccess(firstInvoice, null);
        } else {

            Log.d("DETAILS", "--->deleteNextInvoice firstInvoice (Not created offline): " + firstInvoice);

            somethingHasBeenSynced = true;

            Call<Message> call = invoiceApi.deleteInvoice(firstInvoice.getInvoiceId());

            call.enqueue(new Callback<Message>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<Message> call,
                                       @NonNull Response<Message> response) {

                    if(response.isSuccessful() && response.body()!=null){
                        try {
                            Realm realm = Realm.getDefaultInstance();

                            try {
                                Invoice invoice = realm.where(Invoice.class)
                                        .equalTo("id", firstInvoice.getInvoiceId())
                                        .findFirst();
                                if (invoice != null) {
                                    realm.beginTransaction();
                                    Log.d("DETAILS", "--->Success deleteproviderSync. (2/3) Deleting hodToDelete:" + invoice);
                                    try {
                                        invoice.deleteFromRealm();
                                    } catch (Exception e) {
                                    }
                                    realm.commitTransaction();
                                    Log.d("DETAILS", "--->Success deleteOfDaySync. (3/3) Deleted hodToDelete OK");
                                } else {
                                    Log.e("DETAILS", "--->Success deleteOfDaySync. (2/2/3) Can't delete NULL hodToDelete");
                                }
                            } finally {
                                realm.close();
                                //deleteNextInvoice();
                            }
                        }catch (Exception e) {
                            isSyncParcial = true;
                            if(logDataBase(new Gson().toJson(firstInvoice.getInvoiceId()),"errorFatal Sync en endPoint (/invoice/"+firstInvoice.getInvoiceId()+") por delete... "+e.toString())!=-1){
                                Realm realm = Realm.getDefaultInstance();
                                realm.beginTransaction();
                                Invoice invoice = realm.where(Invoice.class)
                                        .equalTo("id", firstInvoice.getInvoiceId())
                                        .findFirst();
                                invoice.deleteFromRealm();
                                realm.commitTransaction();
                            }
                            ErrorHandling.syncErrorCodeInServerResponseProcessing(e);
                        } finally {
                            deleteNextInvoice();
                        }

                    } else {
                        isSyncParcial = true;
                        try {
                            if(logDataBase(new Gson().toJson(firstInvoice.getInvoiceId()),"error ResponseCode()="+ response.code()+" Sync en endPoint (/invoice/"
                                                                                                    +firstInvoice.getInvoiceId()+") por delete... " +response.errorBody().string())!=-1){
                                Realm realm = Realm.getDefaultInstance();
                                realm.beginTransaction();
                                Invoice invoice = realm.where(Invoice.class)
                                        .equalTo("id", firstInvoice.getInvoiceId())
                                        .findFirst();
                                try {
                                    invoice.deleteFromRealm();
                                }catch (Exception e){

                                }
                                realm.commitTransaction();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        deleteNextInvoice();
                    }
                }

                @DebugLog
                @Override
                public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                    isSyncParcial = true;
                    ErrorHandling.syncErrorCodeWebServiceFailed(t);
                    /*if(*/logDataBase(new Gson().toJson(firstInvoice.getInvoiceId()),"errorFatal Sync en endPoint (/invoice/"+firstInvoice.getInvoiceId()+") por delete... " +t.toString());/*!=-1){
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        firstInvoice.deleteFromRealm();
                        realm.commitTransaction();
                    }*/
                    deleteNextInvoice();
                }
            });
        }
    }

    @DebugLog
    private void onInvoiceDeleteSyncSuccess(Invoice firstInvoice, Response<Message> response) {
        try {
            Realm realm = Realm.getDefaultInstance();
            try {
                realm.beginTransaction();
                firstInvoice.setAddOffline(false);
                firstInvoice.setEditOffline(false);
                firstInvoice.setDeleteOffline(false);
                realm.insertOrUpdate(firstInvoice);
                realm.commitTransaction();
            } finally {
                realm.close();
                deleteNextInvoice();
            }
        } catch (Exception e) {
            ErrorHandling.errorCodeBdLocal(e);
        }
    }

    @DebugLog
    public void syncEditInvoiceDetail() {
        invoiceDetailsEdit = new ArrayList<>();
        List<InvoiceDetails> invoiceDetailsList = Realm.getDefaultInstance()
                .where(InvoiceDetails.class)
                .equalTo("editOffline", true)
                .findAllSorted("startDate");


        if (invoiceDetailsList != null) {
            invoiceDetailsEdit.addAll(Realm.getDefaultInstance().copyFromRealm(invoiceDetailsList));
        }
        if (invoiceDetailsEdit.size() <= 0) {
            syncDeletedOfDay();
            return;
        }
        editNextInvoiceDetails();
    }

    @DebugLog
    public void editNextInvoiceDetails() {
        if (invoiceDetailsEdit.size() <= 0) {
            syncDeletedOfDay();
            return;
        }

        final InvoiceDetails firstInvoiceDetails = invoiceDetailsEdit.get(0);
        invoiceDetailsEdit.remove(firstInvoiceDetails);
        countSuccess++;
        progressBar.setProgress(countSuccess*100/countTotal);
        somethingHasBeenSynced = true;

        Call<ResponseBody> call = invoiceApi.updateInvoiceDetail(firstInvoiceDetails.getId(), new InvoiceDetail(firstInvoiceDetails));

        call.enqueue(new Callback<ResponseBody>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {

                if(response.isSuccessful() && response.body()!=null){
                    try {


                        Realm realm = Realm.getDefaultInstance();
                        try {
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    InvoiceDetails invoiceDetailsEdit = firstInvoiceDetails;
                                    invoiceDetailsEdit.setEditOffline(false);
                                    realm.insertOrUpdate(invoiceDetailsEdit);
                                }
                            });
                        } finally {
                            realm.close();
                            //editNextInvoiceDetails();
                        }
                    } catch (Exception e) {
                        isSyncParcial = true;
                        if(logDataBase(new Gson().toJson(new InvoiceDetail(firstInvoiceDetails)),"error catch Sync en endPoint (/invoiceDetail) por put... " + e.toString())!=-1){
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            InvoiceDetails invoiceDetails = realm.where(InvoiceDetails.class)
                                    .equalTo("id", firstInvoiceDetails.getId())
                                    .findFirst();
                            invoiceDetails.deleteFromRealm();
                            realm.commitTransaction();
                        }
                        ErrorHandling.syncErrorCodeInServerResponseProcessing(e);
                        e.printStackTrace();
                    }finally {
                        editNextInvoiceDetails();
                    }
                } else {
                    isSyncParcial = true;
                    try {
                        if(logDataBase(new Gson().toJson(new InvoiceDetail(firstInvoiceDetails)),"error ResponseCode()="+ response.code()+" Sync en endPoint (/invoiceDetail) por put... " + response.errorBody().string())!=-1){
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            InvoiceDetails invoiceDetails = realm.where(InvoiceDetails.class)
                                    .equalTo("id", firstInvoiceDetails.getId())
                                    .findFirst();
                            invoiceDetails.deleteFromRealm();
                            realm.commitTransaction();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    editNextInvoiceDetails();
                    /*Log.d("DETAILS", "--->editNextInvoiceDetails Sync onError. Response:" + response);
                    if (response.code() == 409)
                        HomeActivity.INSTANCE.syncFailed("editNextInvoiceDetails Sync errorResponse : " + response);
                    else
                        HomeActivity.INSTANCE.syncFailed("editNextInvoiceDetails Sync errorResponse: " + response);*/
                }
            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                isSyncParcial = true;
                ErrorHandling.syncErrorCodeWebServiceFailed(t);
                /*if(*/logDataBase(new Gson().toJson(new InvoiceDetail(firstInvoiceDetails)),"errorFatal Sync en endPoint (/invoiceDetail) por put... " + t.toString());/*!=-1){
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    firstInvoiceDetails.deleteFromRealm();
                    realm.commitTransaction();
                }*/
                editNextInvoiceDetails();
            }
        });

    }

    @DebugLog
    public void syncDeletedOfDay() {
        invoiceDetailsDelete = new ArrayList<>();

        List<InvoiceDetails> ofDays = Realm.getDefaultInstance()
                .where(InvoiceDetails.class)
                .equalTo("deleteOffline", true)
                .findAllSorted("startDate");

        if (ofDays != null) {
            invoiceDetailsDelete.addAll(Realm.getDefaultInstance().copyFromRealm(ofDays));
        }
        if (invoiceDetailsDelete.size() <= 0) {
            syncDeletedInvoice();
            return;
        }
        deleteNextOfDay();
    }

    @DebugLog
    public void deleteNextOfDay() {
        if (invoiceDetailsDelete.size() <= 0) {
            syncDeletedInvoice();
            return;
        }


        somethingHasBeenSynced = true;

        final InvoiceDetails firstOfDetails = invoiceDetailsDelete.get(0);
        invoiceDetailsDelete.remove(firstOfDetails);
        countSuccess++;
        progressBar.setProgress(countSuccess*100/countTotal);

        Call<InvoiceDetailsResponse> call = invoiceApi.deleteInvoiceDetail(firstOfDetails.getId());

        call.enqueue(new Callback<InvoiceDetailsResponse>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<InvoiceDetailsResponse> call,
                                   @NonNull Response<InvoiceDetailsResponse> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        try {

                            Realm realm = Realm.getDefaultInstance();
                            try {
                                InvoiceDetails invoiceDetail = Realm.getDefaultInstance().where(InvoiceDetails.class)
                                        .equalTo("id", firstOfDetails.getId())
                                        .findFirst();
                                if (invoiceDetail != null) {
                                    realm.beginTransaction();
                                    try{
                                        invoiceDetail.deleteFromRealm();
                                    }catch (Exception e){
                                        ErrorHandling.errorCodeBdLocal(e);
                                    }
                                    realm.commitTransaction();
                                } else {
                                    Log.e("DETAILS", "--->Success deleteOfDaySync. (2/2/3) Can't delete NULL hodToDelete");
                                    deleteNextOfDay();
                                }

                            } finally {
                                realm.close();
                                //deleteNextOfDay();
                            }

                        } catch (Exception e) {
                            isSyncParcial = true;
                            if(logDataBase(new Gson().toJson(firstOfDetails.getId()),"error catch Sync en endPoint (/invoiceDetail/"+firstOfDetails.getId()+") por delete... " +e.toString())!=-1){
                                Realm realm = Realm.getDefaultInstance();
                                realm.beginTransaction();
                                InvoiceDetails invoiceDetail = Realm.getDefaultInstance().where(InvoiceDetails.class)
                                        .equalTo("id", firstOfDetails.getId())
                                        .findFirst();
                                invoiceDetail.deleteFromRealm();
                                realm.commitTransaction();
                            }
                            ErrorHandling.errorCodeInServerResponseProcessing(e);

                        }finally {
                            deleteNextOfDay();
                        }
                    } else {
                        isSyncParcial = true;
                        try {
                            if(logDataBase(new Gson().toJson(firstOfDetails.getId()),"error ResponseCode()="+ response.code()+" Sync en endPoint (/invoiceDetail/"+firstOfDetails.getId()+") por delete... " + response.errorBody().string())!=-1){
                                Realm realm = Realm.getDefaultInstance();
                                realm.beginTransaction();
                                InvoiceDetails invoiceDetail = Realm.getDefaultInstance().where(InvoiceDetails.class)
                                        .equalTo("id", firstOfDetails.getId())
                                        .findFirst();
                                invoiceDetail.deleteFromRealm();
                                realm.commitTransaction();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        deleteNextOfDay();
                    }

            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<InvoiceDetailsResponse> call, @NonNull Throwable t) {
                isSyncParcial = true;
                ErrorHandling.syncErrorCodeWebServiceFailed(t);
                /*if(*/logDataBase(new Gson().toJson(firstOfDetails.getId()),"errorFatal Sync en endPoint (/invoiceDetail/"+firstOfDetails.getId()+") por delete... " +t.toString());/*!=-1){
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    firstOfDetails.deleteFromRealm();
                    realm.commitTransaction();
                }*/
                deleteNextOfDay();
            }
        });
    }

    @DebugLog
    public void syncCloseIncoice() {
        invoiceClosed = new ArrayList<>();

        Log.e("debug","BRAYAN222"+ "6" + "entro a closed");

        List<Invoice> invoiceForClosed = Realm.getDefaultInstance()
                .where(Invoice.class)
                .equalTo("isClosed", true)
                .findAllSorted("invoiceStartDate");

        Log.e("debug","BRAYAN222"+ "7" + invoiceForClosed.size());
        if (invoiceForClosed != null) {
            invoiceClosed.addAll(Realm.getDefaultInstance().copyFromRealm(invoiceForClosed));
        }

        if (invoiceClosed.isEmpty()) {
            Log.e("debug","BRAYAN222"+ "7" + "entro a if");
            if (somethingHasBeenSynced) {
                onSuccessfulSync();
            } else {
                HomeActivity.INSTANCE.onNothingToSync();
            }
        } else {
            Log.e("debug","BRAYAN222"+ "7" + "entro a else");
            nextClosedInvoice();
        }
    }

    @DebugLog
    public void nextClosedInvoice() {
        if (invoiceClosed.size() <= 0) {
            onSuccessfulSync();
            return;
        }

        final Invoice firstInvoiceClosed = invoiceClosed.get(0);
        invoiceClosed.remove(firstInvoiceClosed);
        countSuccess++;
        progressBar.setProgress(countSuccess*100/countTotal);

        final com.hecticus.eleta.model_new.Invoice invoice1
                = new com.hecticus.eleta.model_new.Invoice(firstInvoiceClosed,
                ManagerDB.getProviderById(firstInvoiceClosed.getProviderId()),
                new StatusInvoice(12, false, "Cerrada", null));

        Call<Message> call = invoiceApi.closeInvoice(firstInvoiceClosed.getInvoiceId(),invoice1);

        call.enqueue(new Callback<Message>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<Message> call,
                                   @NonNull Response<Message> response) {

                if(response.isSuccessful() && response.body() != null){
                    try {
                        Realm realm = Realm.getDefaultInstance();
                        try {
                            realm.beginTransaction();
                            firstInvoiceClosed.setClosed(false);
                            realm.insertOrUpdate(firstInvoiceClosed);
                            realm.commitTransaction();
                        } finally {
                            realm.close();
                            //nextClosedInvoice();
                        }

                    } catch (Exception e) {
                        isSyncParcial = true;
                        ErrorHandling.syncErrorCodeInServerResponseProcessing(e);
                        if(logDataBase(new Gson().toJson(invoice1),"error catch Sync en endPoint (/invoice/"+firstInvoiceClosed.getInvoiceId()+") por put... " +e.toString())!=-1){
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            Invoice invoice = realm.where(Invoice.class)
                                    .equalTo("id", firstInvoiceClosed.getInvoiceId())
                                    .findFirst();
                            invoice.deleteFromRealm();
                            firstInvoiceClosed.deleteFromRealm();
                            realm.commitTransaction();
                        }
                    }finally {
                        nextClosedInvoice();
                    }
                } else {
                    isSyncParcial = true;
                    try {
                        if(logDataBase(new Gson().toJson(invoice1),"error ResponseCode()="+ response.code()+" Sync en endPoint (/invoice/"+firstInvoiceClosed.getInvoiceId()+
                                                                                                                    ") por put... " +response.errorBody().string())!=-1){
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            Invoice invoice = realm.where(Invoice.class)
                                    .equalTo("id", firstInvoiceClosed.getInvoiceId())
                                    .findFirst();
                            invoice.deleteFromRealm();
                            //firstInvoiceClosed.deleteFromRealm();
                            realm.commitTransaction();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    nextClosedInvoice();
                }
            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                isSyncParcial = true;
                ErrorHandling.syncErrorCodeWebServiceFailed(t);
                /*if(*/logDataBase(new Gson().toJson(invoice1),"errorFatal Sync en endPoint (/invoice/"+firstInvoiceClosed.getInvoiceId()+") por put... " +t.toString());/*!=-1){
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    firstInvoiceClosed.deleteFromRealm();
                    realm.commitTransaction();
                }*/
                nextClosedInvoice();
            }
        });

    }


    @DebugLog
    private void onSuccessfulSync() {
        //todo nose
        try {
            FileUtils.clearTempImages();
        } catch (Exception e) {
        }
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ItemPost> results = realm.where(ItemPost.class).findAll();
        realm.beginTransaction();
        results.deleteAllFromRealm();
        realm.commitTransaction();
        RealmResults<InvoiceDetails> results1 = realm.where(InvoiceDetails.class).findAll();
        realm.beginTransaction();
        results1.deleteAllFromRealm();
        realm.commitTransaction();
        if (isSyncParcial) {
                HomeActivity.INSTANCE.syncPartial();
        } else {
            HomeActivity.INSTANCE.syncSuccessful(failedImageUploads);
        }
    }

    /*@DebugLog
    private void logDataBase(String json, String description){
        Call<ResponseBody> call = invoiceApi.logDataBase(new LogDataBase(user, json, description));

        call.enqueue(new Callback<ResponseBody>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    try {

                    } catch (Exception e) {
                        ErrorHandling.syncErrorCodeInServerResponseProcessing(e);
                    }
                }
            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                ErrorHandling.syncErrorCodeWebServiceFailed(t);
            }
        });
    }*/

    @DebugLog
    private int logDataBase(String json, String description) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        MediaType jsonType = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(jsonType,new Gson().toJson(new LogDataBase(user, json, description)));
        Request request = new Request.Builder()
                .url(Constants.BASE_URL+"logSyncApps")
                .post(body)
                .build();


        okhttp3.Response response = null;
        int code = -1;
        try {
            //--------------------------------------------------------------------------------------
            response = client.newCall(request).execute();
            //--------------------------------------------------------------------------------------

            if (response != null) {
                if(response.isSuccessful()){
                    return 200;
                }
                response.body().close(); //ToDo check this line
            } else{
                return -1;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return code;

    }

    @DebugLog
    private void getProvider(final Provider currentProvider) {

        OkHttpClient client = new OkHttpClient.Builder().build();
        MediaType jsonType = MediaType.parse("application/json; charset=utf-8");

        //RequestBody body = RequestBody.create(jsonType,new Gson().toJson(new LogDataBase(user, json, description)));
        Request request = new Request.Builder()
                .url(Constants.BASE_URL+"provider?nitProvider="+currentProvider.getIdentificationDocProvider())
                //.post(body)
                .build();

        okhttp3.Response response = null;
        int code = -1;
        try {
            //--------------------------------------------------------------------------------------
            response = client.newCall(request).execute();
            //--------------------------------------------------------------------------------------

            if (response != null) {
                if(response.isSuccessful()){
                    JSONObject json = new JSONObject(response.body().string());
                    Type founderListType = new TypeToken<ArrayList<Provider>>() {}.getType();
                    List<Provider> providers = new Gson().fromJson(json.getJSONArray("result").toString(), founderListType);
                    final Provider providerServer = providers.get(0);

                    String typeProvider;
                    String doc;
                    if(currentProvider.getProviderType().getIdProviderType() == 1 || currentProvider.getIdProviderType()==1){
                        typeProvider = "proveedor ";
                        doc= "RUC";
                    } else {
                        typeProvider = "cosechador ";
                        doc= "DNI";
                    }
                    String msj = "Se ha intentando sincronizar al " + typeProvider + currentProvider.getFullNameProvider() + " con el "+doc+": "+ currentProvider.getIdentificationDocProvider()
                            + ", dicho "+doc+" ya existe en el servidor con el nombre: "+ providerServer.getFullNameProvider() +"\n\n"
                            + "¿Desea reemplazar los datos del servidor?";


                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.INSTANCE);
                    builder.setCancelable(false);
                    builder.setTitle("Importante");
                    builder.setMessage(msj);
                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateProviderIdInInvoices(currentProvider.getIdProvider(), providerServer.getIdProvider());
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            currentProvider.setIdProvider(providerServer.getIdProvider());
                            currentProvider.setEditOffline(false);
                            currentProvider.setAddOffline(false);
                            realm.insertOrUpdate(currentProvider);
                            realm.commitTransaction();
                            updateProviderRequest(currentProvider);
                            syncNextPendingProvider();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            Provider provider = realm
                                    .where(Provider.class)
                                    .equalTo("unixtime", currentProvider.getUnixtime())
                                    .findFirst();
                            provider.deleteFromRealm();
                            realm.commitTransaction();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm.insertOrUpdate(providerServer);
                                }
                            });
                            updateProviderIdInInvoices(currentProvider.getIdProvider(), providerServer.getIdProvider());
                            syncNextPendingProvider();
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
                response.body().close(); //ToDo check this line
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ;

    }

    @DebugLog
    public void updateProviderRequest(final Provider providerParam) {

            Call<ProviderCreationResponse> call = providersApi.updateProviderData(providerParam.getIdProvider(),providerParam);

            call.enqueue(new Callback<ProviderCreationResponse>() {
                @DebugLog
                @Override
                public void onResponse(@NonNull Call<ProviderCreationResponse> call,
                                       @NonNull Response<ProviderCreationResponse> response) {


                    if (response.isSuccessful() && response.body() != null) {
                        try {
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            final Provider provider = Realm.getDefaultInstance()
                                    .where(Provider.class)
                                    .equalTo("unixtime", providerParam.getUnixtime())
                                    .findFirst();
                            provider.deleteFromRealm();
                            realm.commitTransaction();

                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    providerParam.setAddOffline(false);
                                    providerParam.setEditOffline(false);
                                    realm.insertOrUpdate(providerParam);
                                }
                            });

                        } catch (Exception e) {
                            ErrorHandling.errorCodeInServerResponseProcessing(e);
                            logDataBase(new Gson().toJson(providerParam),"error catch Sync en endPoint (/provider/"+providerParam.getIdProvider()+") por put... " +e.toString());
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            logDataBase(new Gson().toJson(providerParam),"error ResponseCode()="+ response.code()+" Sync en endPoint (/provider/"+providerParam.getIdProvider()+
                                    ") por put... " +response.errorBody().string());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @DebugLog
                @Override
                public void onFailure(@NonNull Call<ProviderCreationResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    ErrorHandling.syncErrorCodeWebServiceFailed(t);
                    logDataBase(new Gson().toJson(providerParam),"errorFatal Sync en endPoint (/provider/"+providerParam.getIdProvider()+") por put... " +t.toString());
                }
            });
    }

}


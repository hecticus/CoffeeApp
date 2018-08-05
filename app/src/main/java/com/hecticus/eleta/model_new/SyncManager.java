package com.hecticus.eleta.model_new;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hecticus.eleta.home.HomeActivity;
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
import com.hecticus.eleta.util.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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

    private List<Provider> providersList;
    private List<InvoicePost> invoicePostList;
    private List<Invoice> invoiceList;
    //private List<HarvestOfDay> ofDayList;

    private boolean somethingHasBeenSynced = false;

    private int failedImageUploads = 0;

    @DebugLog
    public SyncManager() {
        if (providersApi == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public okhttp3.Response intercept(Chain chain) throws IOException {

                                    Log.d("DEBUG contructor", SessionManager.getAccessToken(HomeActivity.INSTANCE));

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
    public void startSync() {
        syncDeletedInvoice();
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
            syncInvoices();
            return;
        }

        final Provider currentProviderToSync = providersList.get(0);

        providersList.remove(currentProviderToSync);

        final Integer oldLocalProviderId;

        if (currentProviderToSync.getIdProvider() != null && currentProviderToSync.getIdProvider() < 0) {
            oldLocalProviderId = currentProviderToSync.getIdProvider();

            Log.d("BUG", "--->Provider to sync (Local id " + oldLocalProviderId + " set to null): " + currentProviderToSync);
            currentProviderToSync.setIdProvider(null);
        } else {
            oldLocalProviderId = null;
            Log.d("BUG", "--->Provider to sync (No id patch): " + currentProviderToSync);
        }

        somethingHasBeenSynced = true;
        Call<ProviderCreationResponse> call;

        final boolean isAdding = currentProviderToSync.isAddOffline();
        final String operationName;

        if (isAdding) {
            operationName = "createProviderSync";
            Log.d("DETAILS", "---> Provider new" + currentProviderToSync);
            currentProviderToSync.setProviderType(new ProviderType(currentProviderToSync.getIdProviderType()));
            call = providersApi.createProvider(currentProviderToSync);
        } else {
            operationName = "editProviderSync";
            Log.d("DETAILS", "---> Provider edit" + currentProviderToSync);
            currentProviderToSync.setProviderType(new ProviderType(currentProviderToSync.getIdProviderType()));
            call = providersApi.updateProviderData(currentProviderToSync.getIdProvider(), currentProviderToSync);
        }
        new ManagerServices<>(call, new ManagerServices.ServiceListener<ProviderCreationResponse>() {
            @DebugLog
            @Override
            public void onSuccess(Response<ProviderCreationResponse> response) {
                try {
                    Log.d("BUG", "--->Success " + operationName + " :" + response.body());

                    Integer newProviderId = null;

                    Realm realm = Realm.getDefaultInstance();
                    try {
                        newProviderId = response.body().getProvider().getIdProvider();
                        realm.beginTransaction();
                        currentProviderToSync.setIdProvider(newProviderId);
                        currentProviderToSync.setAddOffline(false);
                        currentProviderToSync.setEditOffline(false);
                        realm.insertOrUpdate(currentProviderToSync);
                        realm.commitTransaction();
                        Log.d("BUG", "--->Success " + operationName + ", updated local: " + currentProviderToSync);
                    } finally {
                        realm.close();
                        if (oldLocalProviderId != null)
                            updateProviderIdInInvoices(oldLocalProviderId, newProviderId);

                        try {
                            if (currentProviderToSync.getMultimediaProfile().getMultimediaCDN().getUrl() != null) //todo img
                                syncProviderPhoto(currentProviderToSync);
                            else
                                syncNextPendingProvider();
                        }catch (Exception e){
                            syncNextPendingProvider();
                        }


                    }
                } catch (Exception e) {
                    Log.d("BUG", "--->Fail " + operationName + ":" + response);
                    HomeActivity.INSTANCE.syncFailed(operationName + " exception: " + e.getMessage());
                }
            }

            @DebugLog
            @Override
            public void onError(boolean fail, int code, Response<ProviderCreationResponse> response, String errorMessage) {
                Log.d("DETAILS", "--->Fail (" + code + ") " + operationName + ":" + response);

                if (code == 409) {
                    if (errorMessage != null && errorMessage.equals("registered [fullNameProvider]")) {
                        HomeActivity.INSTANCE.syncFailed("No se pudo " + (isAdding ? "crear" : "modificar") + " el "
                                + (currentProviderToSync.isHarvester() ? "cosechador" : "proveedor") + " " + currentProviderToSync.getFullNameProvider()
                                + ". El nombre " + currentProviderToSync.getFullNameProvider() + " ya está registrado\nRespuesta: " + errorMessage);
                    } else {
                        HomeActivity.INSTANCE.syncFailed("No se pudo " + (isAdding ? "crear" : "modificar") + " el "
                                + (currentProviderToSync.isHarvester() ? "cosechador" : "proveedor") + " " + currentProviderToSync.getFullNameProvider()
                                + ". El " + (currentProviderToSync.isHarvester() ? "DNI" : "RUC") + " " + currentProviderToSync.getIdentificationDocProvider()
                                + " ya está registrado\nRespuesta: " + errorMessage);
                    }
                } else if (code == 412) {
                    HomeActivity.INSTANCE.syncFailed("No se pudo " + (isAdding ? "crear" : "modificar") + " el "
                            + (currentProviderToSync.isHarvester() ? "cosechador" : "proveedor") + " " + currentProviderToSync.getFullNameProvider()
                            + " porque falta un atributo. \nRespuesta: " + errorMessage);
                } else {
                    HomeActivity.INSTANCE.syncFailed(operationName + " errorResponse: " + response);
                }
            }

            @DebugLog
            @Override
            public void onInvalidToken() {
                Log.d("DETAILS", "--->" + operationName + " Fail token");
                HomeActivity.INSTANCE.syncFailed(operationName + " onInvalidToken:");
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
            //Log.e("PHOTO", "--->Can't sync image: " + provider.getMultimediaProfile().getMultimediaCDN().getUrl()); //todo img
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
                                //todo brayan img
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
        if (newProviderId == null || newProviderId.equals(oldLocalProviderId)) {
            Log.d("BUG", "--->updateProviderIdInInvoices not needed: " + newProviderId);
        } else {

            Realm realm = Realm.getDefaultInstance();

            final RealmResults<Invoice> existingInvoices = realm.where(Invoice.class)
                    .equalTo("providerId", oldLocalProviderId)
                    .findAll();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    for (Invoice invoice : existingInvoices) {
                        invoice.setProviderId(newProviderId);

                        if (invoice.getProvider() != null) {
                            invoice.getProvider().setIdProvider(newProviderId);
                        }

                        Log.d("BUG", "--->updateProviderIdInInvoices invoice patched: " + invoice);
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
                        Log.d("BUG", "--->updateProviderIdInInvoices invoicePost patched: " + invoicePost);
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

        Log.d("SYNC", "--->Trying to sync " + invoicePostList.size() + " ");
        Log.d("SYNC", "--->Trying to sync these invoice(s) posts: " + invoicePostList);

        final InvoicePost firstInvoicePost = invoicePostList.get(0);
        invoicePostList.remove(firstInvoicePost);

        somethingHasBeenSynced = true;

        Call<CreateInvoiceResponse> call;
        if (firstInvoicePost.getInvoiceId() == -1) {
            Log.d("DETAILS", "---> firstInvoicePost is new: " + firstInvoicePost);
            com.hecticus.eleta.model_new.Invoice invoice = new com.hecticus.eleta.model_new.Invoice(firstInvoicePost, ManagerDB.getProviderById(firstInvoicePost.getProviderId()));
            Gson g = new Gson();
            Log.d("DEBUG new offline", g.toJson(invoice));
            call = invoiceApi.newInvoiceDetail(invoice/*, firstInvoicePost.getProviderId(), firstInvoicePost.getStartDate()*/);
            new ManagerServices<>(call, new ManagerServices.ServiceListener<CreateInvoiceResponse>() {
                @DebugLog
                @Override
                public void onSuccess(Response<CreateInvoiceResponse> response) {
                    try {

                        Log.d("DETAILS", "--->Success saveInvoiceSync Request:" + response.body());
                        //int properInvoiceId = firstInvoice.getInvoicePostLocalId();
                        //String dateSuffix = firstInvoice.getStartDate().endsWith(".0") ? "" : ".0";

                        //ManagerDB.delete(firstInvoice.getId2(), firstInvoice.getStartDate() + dateSuffix, properInvoiceId + "-" + firstInvoice.getStartDate())

                        Realm realm = Realm.getDefaultInstance();

                        List<Invoice> invoiceList1 = realm.where(Invoice.class).findAll();
                        Log.d("DETAILS", "--->" + invoiceList1);


                        final Invoice invoiceInRealm = realm
                                .where(Invoice.class)
                                .equalTo("id2", firstInvoicePost.getInvoiceLocalId())
                                .findFirst();

                        /*HarvestOfDay harvestOfDay = null;

                        if (invoiceInRealm != null) {
                            int properInvoiceId = invoiceInRealm.getInvoiceId() == -1 ? invoiceInRealm.getLocalId() : invoiceInRealm.getInvoiceId();
                            String dateSuffix = firstInvoicePost.getStartDate().endsWith(".0") ? "" : ".0";

                            harvestOfDay = realm.
                                    where(HarvestOfDay.class)
                                    .equalTo("id", properInvoiceId + "-" + firstInvoicePost.getStartDate() + dateSuffix)
                                    .findFirst();

                        }
                        try {
                            if (harvestOfDay != null) {
                                realm.beginTransaction();
                                harvestOfDay.deleteFromRealm();
                                realm.commitTransaction();
                            }

                            if (invoiceInRealm != null) {
                                realm.beginTransaction();
                                invoiceInRealm.deleteFromRealm();
                                realm.commitTransaction();
                            }

                            ManagerDB.findAndDeleteLocalInvoicePost(firstInvoicePost);

                        } finally {
                            realm.close();
                            saveNextInvoice();
                        }*/
                    } catch (Exception e) {
                    /*Gson g = new Gson();
                    Log.d("DEBUG details",g.toJson(response.body()));*/
                        Log.d("DETAILS", "--->Fail saveInvoiceSync: Exception: " + e + " // Response: " + (response == null ? null : response.body()));
                        e.printStackTrace();
                        HomeActivity.INSTANCE.syncFailed("saveInvoiceSync exception: " + e.getMessage());
                    }
                }

                @DebugLog
                @Override
                public void onError(boolean fail, int code, Response<CreateInvoiceResponse> response, String errorMessage) {
                    Log.e("DETAILS", "--->Fail saveInvoiceSync (" + code + "):" + response);
                    HomeActivity.INSTANCE.syncFailed("saveInvoiceSync errorResponse: " + response);
                }

                @DebugLog
                @Override
                public void onInvalidToken() {
                    Log.d("DETAILS", "--->Fail token (saveInvoiceSync)");
                    HomeActivity.INSTANCE.syncFailed("saveInvoiceSync onInvalidToken");
                }
            });
        } else {
            Log.d("DETAILS", "---> firstInvoicePost is an edition: " + firstInvoicePost);
            //todo put
            //call = invoiceApi.updateInvoiceDetail(firstInvoicePost);
        }


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
            syncDeletedOfDay();
            return;
        }
        deleteNextProvider();
    }


    @DebugLog
    public void deleteNextProvider() {
        if (providersList.size() <= 0) {
            syncDeletedOfDay();
            return;
        }

        final Provider firstProvider = providersList.get(0);
        providersList.remove(firstProvider);

        Log.d("DETAILS", "--->deleteNextProvider Provider: " + firstProvider);

        somethingHasBeenSynced = true;

        Call<ResponseBody> call = providersApi.deleteProvider(firstProvider.getIdProvider());

        new ManagerServices<>(call, new ManagerServices.ServiceListener<ResponseBody>() {
            @DebugLog
            @Override
            public void onSuccess(Response<ResponseBody> response) {
                try {

                    Log.d("DETAILS", "--->Success deleteProviderSync:" + response.body());

                    Realm realm = Realm.getDefaultInstance();
                    try {
                        realm.beginTransaction();
                        firstProvider.setDeleteOffline(false);
                        realm.insertOrUpdate(firstProvider);
                        realm.commitTransaction();
                    } finally {
                        realm.close();
                        deleteNextProvider();
                    }
                } catch (Exception e) {
                    Log.d("DETAILS", "--->Fail deleteProviderSync Exception: " + e + "  // Response:" + response);
                    HomeActivity.INSTANCE.syncFailed("deleteProviderSync exception: " + e.getMessage());
                }
            }

            @DebugLog
            @Override
            public void onError(boolean fail, int code, Response<ResponseBody> response, String errorMessage) {
                Log.d("DETAILS", "--->deleteProviderSync onError. Response:" + response);
                if (response.code() == 409)
                    HomeActivity.INSTANCE.syncFailed("deleteProviderSync errorResponse (DELETING_PROVIDER_WITH_OPEN_INVOICES): " + response);
                else
                    HomeActivity.INSTANCE.syncFailed("deleteProviderSync errorResponse: " + response);
            }

            @DebugLog
            @Override
            public void onInvalidToken() {
                Log.d("DETAILS", "--->Fail token");
                HomeActivity.INSTANCE.syncFailed("deleteProviderSync onInvalidToken");
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
            syncProviders();
            return;
        }

        deleteNextInvoice();
    }

    @DebugLog
    private void deleteNextInvoice() {
        if (invoiceList.size() <= 0) {
            Log.d("DETAILS", "--->deleteNextInvoice ended. No more in queue.");
            syncProviders();
            return;
        }

        final Invoice firstInvoice = invoiceList.get(0);
        invoiceList.remove(firstInvoice);

        if (firstInvoice.isAddOffline()) {
            Log.d("DETAILS", "--->deleteNextInvoice firstInvoice IGNORED (Created offline): " + firstInvoice);
            onInvoiceDeleteSyncSuccess(firstInvoice, null);
        } else {

            Log.d("DETAILS", "--->deleteNextInvoice firstInvoice (Not created offline): " + firstInvoice);

            somethingHasBeenSynced = true;

            Call<Message> call = invoiceApi.deleteInvoice(firstInvoice.getInvoiceId());

            new ManagerServices<>(call, new ManagerServices.ServiceListener<Message>() {
                @DebugLog
                @Override
                public void onSuccess(Response<Message> response) {
                    onInvoiceDeleteSyncSuccess(firstInvoice, response);
                }

                @DebugLog
                @Override
                public void onError(boolean fail, int code, Response<Message> response, String errorMessage) {
                    Log.d("DETAILS", "--->Fail deleteInvoiceSync:" + response);
                    HomeActivity.INSTANCE.syncFailed("deleteInvoiceSync errorResponse: " + response);
                }

                @DebugLog
                @Override
                public void onInvalidToken() {
                    Log.d("DETAILS", "--->Fail token");
                    HomeActivity.INSTANCE.syncFailed("deleteInvoiceSync onInvalidToken");
                }
            });
        }
    }

    @DebugLog
    private void onInvoiceDeleteSyncSuccess(Invoice firstInvoice, Response<Message> response) {
        try {
            Log.d("DETAILS", "--->Success deleteInvoiceRequest:" + (response == null ? null : response.body()));
            Realm realm = Realm.getDefaultInstance();
            try {
                realm.beginTransaction();
                firstInvoice.setAddOffline(false);
                firstInvoice.setEditOffline(false);
                firstInvoice.setDeleteOffline(false);
                realm.insertOrUpdate(firstInvoice);
                //firstInvoice.deleteFromRealm();
                realm.commitTransaction();
            } finally {
                realm.close();
                deleteNextInvoice();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("DETAILS", "--->Fail deleteInvoiceSync after success:" + response);
            HomeActivity.INSTANCE.syncFailed("deleteInvoiceSync exception: " + e.getMessage());
        }
    }

    @DebugLog
    public void syncDeletedOfDay() {
        /*ofDayList = new ArrayList<>();

        List<HarvestOfDay> ofDays = Realm.getDefaultInstance()
                .where(HarvestOfDay.class)
                .equalTo("deleteOffline", true)
                .findAllSorted("startDate");

        if (ofDays != null) {
            ofDayList.addAll(Realm.getDefaultInstance().copyFromRealm(ofDays));
        }

        if (ofDayList.isEmpty()) {

            if (somethingHasBeenSynced) {
                onSuccessfulSync();
            } else {
                HomeActivity.INSTANCE.onNothingToSync();
            }
        } else {
            deleteNextOfDay();
        }*/
        onSuccessfulSync(); //todo borrar
    }


    @DebugLog
    public void deleteNextOfDay() {
        onSuccessfulSync(); //todo borrar
        /*if (ofDayList.size() <= 0) {
            onSuccessfulSync();
            return;
        }

        final HarvestOfDay firstOfDay = ofDayList.get(0);
        ofDayList.remove(firstOfDay);
        Log.d("DETAILS", "--->deleteNextOfDay firstInvoice" + firstOfDay);

        Call<InvoiceDetailsResponse> call = invoiceApi.deleteInvoiceDetail(firstOfDay.getInvoiceId() );

        new ManagerServices<>(call, new ManagerServices.ServiceListener<InvoiceDetailsResponse>() {
            @DebugLog
            @Override
            public void onSuccess(Response<InvoiceDetailsResponse> response) {
                try {

                    Realm realm = Realm.getDefaultInstance();
                    try {
                        Log.d("DETAILS", "--->Success deleteOfDaySync (1/3):" + response.body());


                        HarvestOfDay hodToDelete = realm.where(HarvestOfDay.class)
                                .equalTo("id", firstOfDay.getId())
                                .findFirst();


                        if (hodToDelete != null) {
                            Log.d("DETAILS", "--->Success deleteOfDaySync. (2/3) Deleting hodToDelete:" + hodToDelete);
                            realm.beginTransaction();
                            hodToDelete.deleteFromRealm();
                            realm.commitTransaction();
                            Log.d("DETAILS", "--->Success deleteOfDaySync. (3/3) Deleted hodToDelete OK");
                        } else {
                            Log.e("DETAILS", "--->Success deleteOfDaySync. (2/2/3) Can't delete NULL hodToDelete");
                        }

                    } finally {
                        realm.close();
                        deleteNextOfDay();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("DETAILS", "--->Fail deleteOfDaySync:" + response);
                    HomeActivity.INSTANCE.syncFailed("deleteOfDaySync exception: " + e.getMessage());
                }
            }

            @DebugLog
            @Override
            public void onError(boolean fail, int code, Response<InvoiceDetailsResponse> response, String errorMessage) {
                Log.d("DETAILS", "--->Fail deleteOfDaySync:" + response);
                HomeActivity.INSTANCE.syncFailed("deleteOfDaySync errorResponse: " + response);
            }

            @DebugLog
            @Override
            public void onInvalidToken() {
                Log.d("DETAILS", "--->Fail token");
                HomeActivity.INSTANCE.syncFailed("deleteOfDaySync onInvalidToken");
            }
        });*/
    }

    @DebugLog
    private void onSuccessfulSync() {
        //todo nose
        try {
            FileUtils.clearTempImages();
        }catch (Exception e){
        }
        HomeActivity.INSTANCE.syncSuccessful(failedImageUploads);
    }
}


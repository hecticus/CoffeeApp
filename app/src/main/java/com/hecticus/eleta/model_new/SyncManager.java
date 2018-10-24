package com.hecticus.eleta.model_new;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hecticus.eleta.R;
import com.hecticus.eleta.home.HomeActivity;
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
    public void startSync() {
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
            syncInvoices();
            return;
        }

        final Provider currentProviderToSync = providersList.get(0);

        providersList.remove(currentProviderToSync);

        final Integer oldLocalProviderId;

        if (currentProviderToSync.getIdProvider() != null && currentProviderToSync.getIdProvider() < 0) {
            oldLocalProviderId = currentProviderToSync.getIdProvider();
            currentProviderToSync.setIdProvider(null);
        } else {
            oldLocalProviderId = null;
        }

        somethingHasBeenSynced = true;
        Call<ProviderCreationResponse> call;

        final boolean isAdding = currentProviderToSync.isAddOffline();
        final String operationName;

        if (isAdding) {
            operationName = "createProviderSync";
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
                            currentProviderToSync.setIdProvider(newProviderId);
                            currentProviderToSync.setAddOffline(false);
                            currentProviderToSync.setEditOffline(false);
                            realm.insertOrUpdate(currentProviderToSync);
                            realm.commitTransaction();
                        } finally {
                            realm.close();
                            if (oldLocalProviderId != null)
                                updateProviderIdInInvoices(oldLocalProviderId, newProviderId);

                            try {
                                if (currentProviderToSync.getMultimediaProfile().getMultimediaCDN().getUrl() != null)
                                    syncProviderPhoto(currentProviderToSync);
                                else
                                    syncNextPendingProvider();
                            }catch (Exception e){
                                ErrorHandling.syncErrorCodeInServerResponseProcessing(e);
                                syncNextPendingProvider();
                            }


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        //todo
                        ErrorHandling.errorCodeInServerResponseProcessing(e);
                        Log.d("DETAILS", "--->Fail deleteOfDaySync:" + response);
                        HomeActivity.INSTANCE.syncFailed("deleteOfDaySync exception: " + e.getMessage());
                    }
                } else {
                    /*//todo como manejar errores aca si es success
                    Log.d("DETAILS", "--->Fail deleteOfDaySync:" + response);
                    HomeActivity.INSTANCE.syncFailed("deleteOfDaySync errorResponse: " + response);*/
                    //Log.d("DETAILS", "--->Fail (" + code + ") " + operationName + ":" + response);

                    /*if (response.code() == 409) { //todo revisar
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
                    } else if (response.code() == 412) {
                        HomeActivity.INSTANCE.syncFailed("No se pudo " + (isAdding ? "crear" : "modificar") + " el "
                                + (currentProviderToSync.isHarvester() ? "cosechador" : "proveedor") + " " + currentProviderToSync.getFullNameProvider()
                                + " porque falta un atributo. \nRespuesta: " + errorMessage);
                    } else {
                        HomeActivity.INSTANCE.syncFailed(operationName + " errorResponse: " + response);
                    }*/
                }

            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<ProviderCreationResponse> call, @NonNull Throwable t) {
                //todo como manejar errores aca si falla webservice
                ErrorHandling.syncErrorCodeWebServiceFailed(t);
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

        somethingHasBeenSynced = true;

        Call<CreateInvoiceResponse> call;
        if (firstInvoicePost.getInvoiceId() == -1) {
            com.hecticus.eleta.model_new.Invoice invoice = new com.hecticus.eleta.model_new.Invoice(firstInvoicePost, ManagerDB.getProviderById(firstInvoicePost.getProviderId()));

            call = invoiceApi.newInvoiceDetail(invoice);
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

                        try {

                            if (invoiceInRealm != null) {
                                realm.beginTransaction();
                                invoiceInRealm.deleteFromRealm();
                                realm.commitTransaction();
                            }

                            ManagerDB.findAndDeleteLocalInvoicePost(firstInvoicePost);

                        } finally {
                            realm.close();
                            saveNextInvoice();
                        }
                    } catch (Exception e) {
                        ErrorHandling.syncErrorCodeInServerResponseProcessing(e);
                        e.printStackTrace();
                        HomeActivity.INSTANCE.syncFailed("saveInvoiceSync exception: " + e.getMessage());
                    }
                }

                @DebugLog
                @Override
                public void onError(boolean fail, int code, Response<CreateInvoiceResponse> response, String errorMessage) {

                    HomeActivity.INSTANCE.syncFailed("saveInvoiceSync errorResponse: " + response);
                }

                @DebugLog
                @Override
                public void onInvalidToken() {
                    //HomeActivity.INSTANCE.syncFailed("saveInvoiceSync onInvalidToken");
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

        somethingHasBeenSynced = true;

        Call<ResponseBody> call = providersApi.deleteProvider(firstProvider.getIdProvider());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d("DEBUG", "response: " + response.code());
                if (response.code() == 200 || response.code() == 201) {
                    try {

                        Realm realm = Realm.getDefaultInstance();
                        try {

                            Provider provider = realm.where(Provider.class)
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
                            deleteNextProvider();
                        }
                    } catch (Exception e) {
                        ErrorHandling.syncErrorCodeInServerResponseProcessing(e);
                    }
                } else {
                    deleteNextProvider();
                    //Todo indicar que el proveedor no pudo ser eliminado xq posee invoice abierto
                    //HomeActivity.INSTANCE.syncFailed("deleteProviderSync errorResponse (DELETING_PROVIDER_WITH_OPEN_INVOICES): " + response);

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ErrorHandling.syncErrorCodeWebServiceFailed(t);
                t.printStackTrace();
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

        somethingHasBeenSynced = true;
        InvoiceDetail invoiceDetail = new InvoiceDetail(firstInvoiceDetails);
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
                            InvoiceDetails invoice = realm.where(InvoiceDetails.class)
                                    .equalTo("localId", firstInvoiceDetails.getLocalId())
                                    .findFirst();
                            if (invoice != null) {
                                realm.beginTransaction();
                                try {
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
                            addNextInvoiceDetails();
                        }
                    }catch (Exception e) {
                        //todo
                        ErrorHandling.errorCodeInServerResponseProcessing(e);
                        Log.d("DETAILS brayan", "--->Fail invoiceDetails after success:" + response);
                        HomeActivity.INSTANCE.syncFailed("deleteInvoiceSync exception: " + e.getMessage());
                    }

                } else {
                    //todo como manejar errores aca si es success
                    Log.d("DETAILS", "--->Fail deleteInvoiceSync:111" + response);
                    HomeActivity.INSTANCE.syncFailed("deleteInvoiceSync111 errorResponse: " + response);
                }

            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                //todo como manejar errores aca si falla webservice
                ErrorHandling.syncErrorCodeWebServiceFailed(t);
                Log.d("DETAILS", "--->Fail token");
                HomeActivity.INSTANCE.syncFailed("deleteOfDaySync onInvalidToken");
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
                                deleteNextInvoice();
                            }
                        }catch (Exception e) {
                            //todo
                            ErrorHandling.syncErrorCodeInServerResponseProcessing(e);
                            HomeActivity.INSTANCE.syncFailed("deleteInvoiceSync exception: " + e.getMessage());
                        }

                    } else {
                        //todo como manejar errores aca si es success
                        Log.d("DETAILS", "--->Fail deleteInvoiceSync:222" + response);
                        HomeActivity.INSTANCE.syncFailed("deleteInvoiceSync222 errorResponse: " + response);
                    }
                }

                @DebugLog
                @Override
                public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                    //todo como manejar errores aca si falla webservice
                    ErrorHandling.syncErrorCodeWebServiceFailed(t);
                    Log.d("DETAILS", "--->Fail token");
                    HomeActivity.INSTANCE.syncFailed("deleteInvoiceSync onInvalidToken");
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
            //HomeActivity.INSTANCE.syncFailed("deleteInvoiceSync exception: " + e.getMessage()); todo
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
                            editNextInvoiceDetails();
                        }
                    } catch (Exception e) {
                        //todo
                        ErrorHandling.syncErrorCodeInServerResponseProcessing(e);
                        HomeActivity.INSTANCE.syncFailed("editNextInvoiceDetails Sync exception: " + e.getMessage());
                    }
                } else {
                    //todo como manejar errores aca si es success
                    Log.d("DETAILS", "--->editNextInvoiceDetails Sync onError. Response:" + response);
                    if (response.code() == 409)
                        HomeActivity.INSTANCE.syncFailed("editNextInvoiceDetails Sync errorResponse : " + response);
                    else
                        HomeActivity.INSTANCE.syncFailed("editNextInvoiceDetails Sync errorResponse: " + response);
                }
            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                //todo como manejar errores aca si falla webservice
                ErrorHandling.syncErrorCodeWebServiceFailed(t);
                Log.d("DETAILS", "--->Fail token");
                HomeActivity.INSTANCE.syncFailed("editNextInvoiceDetails Sync onInvalidToken");
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
                                InvoiceDetails invoiceDetail = realm.where(InvoiceDetails.class)
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
                                }

                            } finally {
                                realm.close();
                                deleteNextOfDay();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            //todo
                            ErrorHandling.errorCodeInServerResponseProcessing(e);
                            Log.d("DETAILS", "--->Fail deleteOfDaySync:" + response);
                            HomeActivity.INSTANCE.syncFailed("deleteOfDaySync exception: " + e.getMessage());
                        }
                    } else {
                        //todo como manejar errores aca si es success
                        Log.d("DETAILS", "--->Fail deleteOfDaySync:" + response);
                        HomeActivity.INSTANCE.syncFailed("deleteOfDaySync errorResponse: " + response);
                    }

            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<InvoiceDetailsResponse> call, @NonNull Throwable t) {
                //todo como manejar errores aca si falla webservice
                ErrorHandling.syncErrorCodeWebServiceFailed(t);
                Log.d("DETAILS", "--->Fail token");
                HomeActivity.INSTANCE.syncFailed("deleteOfDaySync onInvalidToken");
            }
        });
    }

    @DebugLog
    public void syncCloseIncoice() {
        invoiceClosed = new ArrayList<>();

        List<Invoice> invoiceForClosed = Realm.getDefaultInstance()
                .where(Invoice.class)
                .equalTo("isClosed", true)
                .findAllSorted("invoiceStartDate");

        if (invoiceForClosed != null) {
            invoiceClosed.addAll(Realm.getDefaultInstance().copyFromRealm(invoiceForClosed));
        }

        if (invoiceClosed.isEmpty()) {
            if (somethingHasBeenSynced) {
                onSuccessfulSync();
            } else {
                HomeActivity.INSTANCE.onNothingToSync();
            }
        } else {
            deleteNextCloseInvoice();
        }
    }

    @DebugLog
    public void deleteNextCloseInvoice() {
        if (invoiceClosed.size() <= 0) {
            onSuccessfulSync();
            return;
        }

        final Invoice firstInvoiceClosed = invoiceClosed.get(0);
        invoiceClosed.remove(firstInvoiceClosed);

        com.hecticus.eleta.model_new.Invoice invoice1
                = new com.hecticus.eleta.model_new.Invoice(firstInvoiceClosed,
                ManagerDB.getProviderById(firstInvoiceClosed.getProviderId()),
                new StatusInvoice(12, false, "Cerrada", null));

        Call<Message> call = invoiceApi.closeInvoice(firstInvoiceClosed.getInvoiceId(),invoice1);

        call.enqueue(new Callback<Message>() {
            @DebugLog
            @Override
            public void onResponse(@NonNull Call<Message> call,
                                   @NonNull Response<Message> response) {

                if(response.code()==200 || response.code()==201){
                    try {
                        Realm realm = Realm.getDefaultInstance();
                        try {
                            realm.beginTransaction();
                            firstInvoiceClosed.setClosed(false);
                            realm.insertOrUpdate(firstInvoiceClosed);
                            realm.commitTransaction();
                        } finally {
                            realm.close();
                            deleteNextCloseInvoice();
                        }

                    } catch (Exception e) {
                        //todo
                        ErrorHandling.syncErrorCodeInServerResponseProcessing(e);
                        HomeActivity.INSTANCE.syncFailed("deleteOfDaySync exception: " + e.getMessage());
                    }
                } else {
                    //todo como manejar errores aca si es success
                    Log.d("DETAILS", "--->Fail deleteOfDaySync:" + response);
                    HomeActivity.INSTANCE.syncFailed("deleteOfDaySync errorResponse: " + response);
                }
            }

            @DebugLog
            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                //todo como manejar errores aca si falla webservice
                ErrorHandling.syncErrorCodeWebServiceFailed(t);
                Log.d("DETAILS", "--->Fail token");
                HomeActivity.INSTANCE.syncFailed("deleteOfDaySync onInvalidToken");
            }
        });

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


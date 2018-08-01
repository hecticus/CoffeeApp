package com.hecticus.eleta.provider.detail;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hecticus.eleta.R;
import com.hecticus.eleta.internet.InternetManager;
import com.hecticus.eleta.model_new.MultimediaCDN;
import com.hecticus.eleta.model_new.MultimediaProfile;
import com.hecticus.eleta.model_new.persistence.ManagerDB;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.FileUtils;

import java.util.HashMap;

import hugo.weaving.DebugLog;
import io.realm.Realm;

/**
 * Created by roselyn545 on 16/9/17.
 */

public class ProviderDetailsPresenter implements ProviderDetailsContract.Actions {

    Context context;
    private ProviderDetailsContract.View mView;
    private ProviderDetailsContract.Repository mRepository;
    private Provider currentProvider;
    private int unsavedChangesCount = 0;
    private boolean isForProviderCreation = false;
    private boolean canEdit = false;
    private boolean isHarvester = false;
    private boolean updated = false;
    private boolean errorUpdatingImage = false;

    @DebugLog
    public ProviderDetailsPresenter(Context context, ProviderDetailsContract.View mView, Provider currentProviderParam,
                                    boolean isForProviderCreationParam, boolean canEditParam, boolean isHarvesterParam) {
        this.context = context;
        this.mView = mView;
        mRepository = new ProviderDetailsRepository(this);
        currentProvider = currentProviderParam;
        isForProviderCreation = isForProviderCreationParam;
        canEdit = canEditParam;
        isHarvester = isHarvesterParam;
    }

    @DebugLog
    @Override
    public void initFields() {
        mView.enableEdition(canEdit);
        if (!isForProviderCreation)
            mView.updateFields(currentProvider);
    }

    @Override
    public boolean isUpdated() {
        return updated;
    }

    @Override
    public boolean isCanEdit() {
        return canEdit;
    }

    @Override
    public boolean isHarvester() {
        return isHarvester;
    }

    @DebugLog
    @Override
    public void saveProvider(Provider providerParam, String imagePath, Integer id) {
        unsavedChangesCount = 0;
        if (providerParam != null) {
            if (isForProviderCreation) {
                unsavedChangesCount++;
                providerParam.setIdProvider(null);
                providerParam.setIdentificationDocProviderChange(providerParam.getIdentificationDocProvider());
                mRepository.createProviderRequest(providerParam, imagePath);
            } else {
                HashMap<String, Object> changes = getChanges(providerParam);

                if (changes.isEmpty() && imagePath == null) {
                    mView.showMessage(context.getString(R.string.no_changes));
                } else {
                    if (!InternetManager.isConnected(context) ||
                            currentProvider.getIdProvider() < 0 ||
                            ManagerDB.providerHasOfflineOperation(currentProvider)) {
                        if ((changes.size() > 0) || imagePath != null) {
                            if (imagePath != null) {
                                try {
                                    //providerParam.setPhotoProvider(imagePath);todo img
                                    currentProvider.setMultimediaProfile(new MultimediaProfile("image", new MultimediaCDN("",imagePath)));

                                }catch (Exception e){}
                                }
                            providerParam.setIdProvider(currentProvider.getIdProvider());
                            providerParam.setIdentificationDocProviderChange(currentProvider.getIdentificationDocProvider());
                            mRepository.updateProviderRequest(providerParam, false);
                        }

                    } else {

                        Log.e("DEBUGERROR", "Policia 2");
                        if(id!=null) {
                            try {
                                mView.showWorkingIndicator();
                                mRepository.putImageRequest(imagePath, id);
                            }catch (Exception e){
                                Log.e("DEBUGERROR", e.getMessage());
                            }
                        } else {

                            uploadImage(providerParam, imagePath);
                        }

                        if (changes.size() > 0) {
                            unsavedChangesCount++;
                            mView.showWorkingIndicator();
                            providerParam.setIdProvider(currentProvider.getIdProvider());
                            providerParam.setIdentificationDocProviderChange(currentProvider.getIdentificationDocProvider());
                            if(imagePath==null){
                                mRepository.updateProviderRequest(providerParam, false);
                            }else{
                                mRepository.updateProviderRequest(providerParam, true);
                            }
                            //mRepository.updateProviderRequest(providerParam);
                        }

                    }
                }
            }
        }
    }

    @DebugLog
    @Override
    public HashMap<String, Object> getChanges(Provider maybeModifiedProvider) {

        Log.d("DETAILS", "--->getChanges current: " + currentProvider);

        HashMap<String, Object> changes = new HashMap<>();

        if (!currentProvider.getIdentificationDocProvider().equals(maybeModifiedProvider.getIdentificationDocProvider())) {
            changes.put("identificationDocProvider", maybeModifiedProvider.getIdentificationDocProvider());
        }

        if (!currentProvider.getFullNameProvider().equals(maybeModifiedProvider.getFullNameProvider())) {
            changes.put("fullNameProvider", maybeModifiedProvider.getFullNameProvider());
        }

        if (!currentProvider.getAddressProvider().equals(maybeModifiedProvider.getAddressProvider())) {
            changes.put("addressProvider", maybeModifiedProvider.getAddressProvider());
        }

        if (!currentProvider.getPhoneNumberProvider().equals(maybeModifiedProvider.getPhoneNumberProvider())) {
            changes.put("phoneNumberProvider", maybeModifiedProvider.getPhoneNumberProvider());
        }

        if (!currentProvider.getEmailProvider().equals(maybeModifiedProvider.getEmailProvider())) {
            changes.put("emailProvider", maybeModifiedProvider.getEmailProvider());
        }

        if (!currentProvider.getContactNameProvider().equals(maybeModifiedProvider.getContactNameProvider())) {
            changes.put("contactNameProvider", maybeModifiedProvider.getContactNameProvider());
        }

        return changes;
    }

    @Override
    public void onCancelChangesButtonClicked() {
        undoChanges();
    }

    @DebugLog
    @Override
    public void onCreateError(String message) {
        mView.showMessage(message);
    }

    @DebugLog
    @Override
    public void invalidToken() {
        mView.invalidToken();
    }

    // A negative number (id) is used. i.e: -2.jpg, -3.jpg.
    // If this provider is created offline, this number will match his local id.
    // If provider is created online, this file will be discarded and it the number will not decrease
    // for the next provider.
    @DebugLog
    public String getNextProviderImagePath() {

        Number minExistingProviderLocalId = Realm.getDefaultInstance().where(Provider.class).min("idProvider");

        int nextExistingImageNumber =
                (minExistingProviderLocalId == null || minExistingProviderLocalId.intValue() >= 0) ? -1 :
                        minExistingProviderLocalId.intValue() - 1;

        return FileUtils.getTempFolderPath() + nextExistingImageNumber + ".jpg";
    }

    @Override
    public void onUpdateError(Constants.ErrorType errorType, @Nullable String customErrorString) {
        unsavedChangesCount--;

        if (errorType == Constants.ErrorType.GENERIC_ERROR_DURING_OPERATION) {
            mView.showMessage(context.getString(R.string.error_saving_changes) + (customErrorString == null ? "" : "->" + customErrorString));
        } else if (errorType == Constants.ErrorType.ERROR_UPDATING_IMAGE) {
            errorUpdatingImage = true;
            mView.showMessage(context.getString(R.string.error_updating_image) + (customErrorString == null ? "" : "->" + customErrorString));
        } else if (errorType == Constants.ErrorType.DNI_EXISTING) {
            mView.showMessage(context.getString(R.string.dni_already_exists) + (customErrorString == null ? "" : "->" + customErrorString));
        } else if (errorType == Constants.ErrorType.RUC_EXISTING) {
            mView.showMessage(context.getString(R.string.ruc_already_exists) + (customErrorString == null ? "" : "->" + customErrorString));
        } else if (errorType == Constants.ErrorType.NAME_EXISTING) {
            mView.showMessage(context.getString(R.string.name_already_exists) + (customErrorString == null ? "" : "->" + customErrorString));
        }

        if (unsavedChangesCount == 0) {
            mView.hideWorkingIndicator();

            if (errorUpdatingImage && isForProviderCreation) {
                //We keep the screen open, but now as an edition
                isForProviderCreation = false;
                errorUpdatingImage = false;
            }
        }

    }

    @DebugLog
    @Override
    public void onProviderSaved(Provider providerParam) {
        updated = true;
        if (unsavedChangesCount > 0)
            unsavedChangesCount--;

        if (unsavedChangesCount == 0) {

            //Maybe an image was uploaded before this, so we keep the current image string
            if (currentProvider != null) {
                try {
                    //providerParam.setPhotoProvider(currentProvider.getMultimediaProfile().getMultimediaCDN().getUrl());//currentProvider.getPhotoProvider());todo img
                    providerParam.setMultimediaProfile(new MultimediaProfile("image", new MultimediaCDN("",currentProvider.getMultimediaProfile().getMultimediaCDN().getUrl())));

                }catch (Exception e){}
                }

            currentProvider = providerParam;

            Log.d("DETAILS", "--->No changes left (onProviderSaved)");
            mView.onProviderSaved(providerParam);
        } else {

            //An image update is pending, so we can replace all fields
            currentProvider = providerParam;

            Log.d("DETAILS", "--->Changes left: " + unsavedChangesCount);
        }
    }

    @DebugLog
    @Override
    public void onImageUpdateSuccess(String newImageString) {
        updated = true;
        if (currentProvider == null) {
            currentProvider = new Provider();
        }
        //currentProvider.setPhotoProvider(newImageString);todo img
        currentProvider.setMultimediaProfile(new MultimediaProfile("image", new MultimediaCDN("", newImageString)));//todo brayan img

        unsavedChangesCount--;
        if (unsavedChangesCount == 0) {
            Log.d("DETAILS", "--->No changes left");
            mView.onProviderSaved(currentProvider);
        } else
            Log.d("DETAILS", "--->Changes left: " + unsavedChangesCount);
    }

    /*public void cerrar(){
        mView.hideWorkingIndicator();
        mView.showMessage(context.getString(R.string.provider_saved));
        mView.fin();
    }*/

    @DebugLog
    @Override
    public void undoChanges() {
        mView.updateFields(currentProvider);
    }

    @DebugLog
    @Override
    public void uploadImage(Provider provider, String imagePath) {
        if (imagePath != null) {
            unsavedChangesCount++;
            mView.showWorkingIndicator();

            if (currentProvider == null) {
                Log.d("PHOTO", "--->Saving provider in presenter before uploading image");
                currentProvider = provider;
            }

            mRepository.uploadImageRequest(currentProvider, imagePath);
        } else
            Log.w("PHOTO", "--->No image path to upload");
    }
}

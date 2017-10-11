package com.hecticus.eleta.provider.detail;

import android.content.Context;
import android.util.Log;

import com.hecticus.eleta.R;
import com.hecticus.eleta.model.response.providers.Provider;

import java.util.HashMap;

import hugo.weaving.DebugLog;

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
    public void saveProvider(Provider providerParam, String imagePath) {
        unsavedChangesCount = 0;
        if (providerParam != null) {
            if (isForProviderCreation) {
                unsavedChangesCount++;
                mRepository.createProviderRequest(providerParam, imagePath);
            } else {
                HashMap<String, Object> changes = getChanges(providerParam);

                if (changes.isEmpty() && imagePath == null) {
                    mView.showMessage(context.getString(R.string.no_changes));
                } else {
                    if (changes.size() > 0) {
                        unsavedChangesCount++;
                        mView.showWorkingIndicator();
                        providerParam.setIdProvider(currentProvider.getIdProvider());
                        providerParam.setIdentificationDocProviderChange(currentProvider.getIdentificationDocProvider());
                        mRepository.updateProviderRequest(providerParam);
                    }
                    uploadImage(providerParam, imagePath);
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

    @Override
    public void onUpdateError(int type) {
        unsavedChangesCount--;
        if (unsavedChangesCount == 0) {
            mView.hideWorkingIndicator();
        }

        switch (type) {
            case 0:
                mView.showMessage(context.getString(R.string.error_saving_changes));
                break;
            case 1:
                mView.showMessage(context.getString(R.string.error_updating_image));
                break;
            case 2:
                mView.showMessage(context.getString(R.string.already_exists));
                break;
        }

    }

    @DebugLog
    @Override
    public void onSavedProvider(Provider providerParam) {
        updated = true;
        if (unsavedChangesCount > 0)
            unsavedChangesCount--;

        if (unsavedChangesCount == 0) {

            //Maybe an image was uploaded before this, so we keep the current image string
            if (currentProvider != null) {
                providerParam.setPhotoProvider(currentProvider.getPhotoProvider());
            }

            currentProvider = providerParam;

            Log.d("DETAILS", "--->No changes left (onSavedProvider)");
            mView.onSavedProvider(providerParam);
        } else {

            //An image update is pending, so we can replace all fields
            currentProvider = providerParam;

            Log.d("DETAILS", "--->Changes left: " + unsavedChangesCount);
        }
    }

    @Override
    public void onImageUpdateSuccess(String newImageString) {
        updated = true;
        if (currentProvider == null) {
            currentProvider = new Provider();
        }
        currentProvider.setPhotoProvider(newImageString);

        unsavedChangesCount--;
        if (unsavedChangesCount == 0) {
            Log.d("DETAILS", "--->No changes left");
            mView.onSavedProvider(currentProvider);
        } else
            Log.d("DETAILS", "--->Changes left: " + unsavedChangesCount);
    }

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

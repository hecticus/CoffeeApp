package com.hecticus.eleta.provider.detail;

import android.support.annotation.Nullable;

import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.util.Constants;

import java.util.HashMap;

/**
 * Created by roselyn545 on 16/9/17.
 */

public class ProviderDetailsContract {

    public interface View {

        void showWorkingIndicator();

        void hideWorkingIndicator();

        void onProviderSaved(Provider providerParam);

        void showMessage(String message);

        void enableEdition(boolean enabled);

        Provider getProviderFromFields();

        void updateFields(Provider provider);

        void updateMenuOptions();

        void onClickSaveChangesButtonClicked();

        void onClickSelectImage();

        void returnProvider(Provider provider);

        void invalidToken();

    }

    public interface Actions {

        void initFields();

        boolean isCanEdit();

        boolean isUpdated();

        boolean isHarvester();

        void saveProvider(Provider provider, String imagePath);

        HashMap<String, Object> getChanges(Provider provider);

        void onCancelChangesButtonClicked();

        void onUpdateError(Constants.ErrorType errorType, @Nullable String customErrorString);

        void onProviderSaved(Provider providerModel);

        void undoChanges();

        void uploadImage(Provider provider, String imagePath);

        void onImageUpdateSuccess(String newImageString);

        void onCreateError(String message);

        void invalidToken();

        String getNextProviderImagePath();
    }

    public interface Repository {

        void createProviderRequest(Provider provider, String imagePath);

        void updateProviderRequest(Provider provider);

        void onCreateError(String message);

        void onDataUpdateError(Constants.ErrorType errorType);

        void onImageUpdateError(Provider provider, String previousImageString, @Nullable String errorMessage);

        void onImageUpdateSuccess(String newImageString);

        void onProviderSaved(Provider provider);

        void uploadImageRequest(Provider provider, String newImagePath);

    }
}

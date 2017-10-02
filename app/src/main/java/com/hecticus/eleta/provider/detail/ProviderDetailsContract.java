package com.hecticus.eleta.provider.detail;

import com.hecticus.eleta.model.response.providers.Provider;

import java.util.HashMap;

/**
 * Created by roselyn545 on 16/9/17.
 */

public class ProviderDetailsContract {

    public interface View {

        void showWorkingIndicator();

        void hideWorkingIndicator();

        void onSavedProvider(Provider providerParam);

        void showMessage(String message);

        void enableEdition(boolean enabled);

        Provider getProviderFromFields();

        void updateFields(Provider provider);

        void updateMenuOptions();

        void onClickSaveChangesButtonClicked();

        void onClickSelectImage();

        void returnProvider(Provider provider);

    }

    public interface Actions {

        void initFields();

        boolean isCanEdit();

        boolean isUpdated();

        boolean isHarvester();

        void saveProvider(Provider provider, String imagePath);

        HashMap<String, Object> getChanges(Provider provider);

        void onCancelChangesButtonClicked();

        void onUpdateError(int type);

        void onSavedProvider(Provider providerModel);

        void undoChanges();

        void uploadImage(String imagePath);

        void onImageUpdateSuccess(String newImageString);

        void onCreateError();
    }

    public interface Repository {

        void createProviderRequest(Provider provider, String imagePath);

        void updateProviderRequest(Provider provider);

        void onCreateError();

        void onDataUpdateError();

        void onImageUpdateError(Provider provider, String previousImageString);

        void onImageUpdateSuccess(String newImageString);

        void onSuccessSaveProvider(Provider provider);

        void uploadImageRequest(Provider provider, String newImagePath);

    }
}

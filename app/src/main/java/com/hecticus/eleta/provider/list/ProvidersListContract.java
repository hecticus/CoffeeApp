package com.hecticus.eleta.provider.list;

import com.hecticus.eleta.base.BaseListContract;
import com.hecticus.eleta.base.BaseModel;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.providers.ProvidersListResponse;

import java.util.List;

/**
 * Created by roselyn545 on 16/9/17.
 */

public class ProvidersListContract extends BaseListContract {

    public interface View extends BaseListContract.View {

        void showWorkingIndicator();

        void hideWorkingIndicator();

        void showMessage(String message);

        void showError(String error);

        void onClickAddProvider();

        void onClickSearchProvider();

        void onChangedProviderType(int providerType);

        void goToProviderDetailsView(Provider provider);

        void updateProvidersList(List<Provider> providersList);

        void onClickCancelSearchProvider();

        void refreshList();

        void showDeleteConfirmation(BaseModel model);
    }

    public interface Actions extends BaseListContract.Actions {

        void getInitialData();

        void getProvidersByType(int providerType);

        void refreshProvidersList();

        void cancelSearch();

        void handleSuccessfulSortedProvidersRequest(List<Provider> providersList);

        void handleSuccessfulMixedProvidersRequest(List<Provider> providersList);

        void searchProvidersByName(String name);

        void onProviderDeleted();

        void onError(String error);

        void deleteProvider(BaseModel model);
    }

    public interface Repository {

        void getProvidersOfType(int providerType);

        void onGetProvidersSuccess(List<Provider> providersList);

        void searchProvidersByTypeByName(int type, String name);

        void deleteProvider(Provider provider);

        void onError(String error);

        List<Provider> getCurrentProviders();
    }
}

package com.hecticus.eleta.search_dialog;

import com.hecticus.eleta.base.BaseListContract;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.providers.ProvidersListResponse;

import java.util.List;

/**
 * Created by roselyn545 on 22/9/17.
 */

public class SearchContract extends BaseListContract {

    public interface View  extends BaseListContract.View{

        void showWorkingIndicator();

        void hideWorkingIndicator();

        void showMessage(String message);

        void showError(String error);

        void onClickSearchProvider();

        void onClickCancelSearch();

        void onClickDismissDialog();

        void onProviderSelected(Provider provider);

        void updateProvidersList(List<Provider> providersList);
    }

    public interface Actions extends BaseListContract.Actions {

        boolean isHarvest();

        void getInitialData();

        void searchProvidersByName(String name);

        void refreshProvidersList();

        void handleSuccessfulProvidersRequest(List<Provider> providersList);

        void handleSuccessfulSortedProvidersRequest(List<Provider> providersList);


        void onError(String error);
    }

    public interface Repository {

        void getAllProvidersByType(int type);

        List<Provider> getCurrentProviders();

        void onGetProvidersSuccess(List<Provider> providersList);

        void searchProvidersByTypeByName(int type, String name);

        void onError(String error);
    }
}

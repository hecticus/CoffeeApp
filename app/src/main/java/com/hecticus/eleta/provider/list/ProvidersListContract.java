package com.hecticus.eleta.provider.list;

import com.hecticus.eleta.base.BaseListContract;
import com.hecticus.eleta.model.response.Pager;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.providers.ProvidersListResponse;

import java.util.List;

/**
 * Created by roselyn545 on 16/9/17.
 */

public class ProvidersListContract extends BaseListContract {

    public interface View {

        void showWorkingIndicator();

        void hideWorkingIndicator();

        void showMessage(String message);

        void showError(String error);

        void onClickAddProvider();

        void onClickSearchProvider();

        void onChangedProviderType(int providerType);

        void goToProviderDetailsView(Provider provider);

        void updateProvidersList(List<Provider> providersList);

        //void addMoreProvidersToTheList(List<Provider> providersList);

        void refreshList();
    }

    public interface Actions extends BaseListContract.Actions {

        void getInitialData();

        void getProvidersByType(int providerType);

        void refreshProvidersList();

        void handleSuccessfulProvidersRequest(List<Provider> providersList);

        //void updatePager(Pager pager);

        void onProviderDeleted();

        //void getMoreProviders();

        void onError(String error);

        //boolean canLoadMore();
    }

    public interface Repository {

        void getProvidersOfType(int providerType);

        void onGetProvidersSuccess(ProvidersListResponse providersListResponse);

        void deleteProvider(int providerId);

        void onError(String error);
    }
}

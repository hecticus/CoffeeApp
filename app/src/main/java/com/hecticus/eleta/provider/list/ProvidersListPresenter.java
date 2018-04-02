package com.hecticus.eleta.provider.list;

import android.content.Context;
import android.util.Log;

import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseModel;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 16/9/17.
 */

public class ProvidersListPresenter implements ProvidersListContract.Actions {

    Context context;
    private ProvidersListContract.View mView;
    private ProvidersListContract.Repository mRepository;
    private int currentType = Constants.TYPE_HARVESTER;


    //private int lastPage = Constants.INITIAL_PAGE_IN_PAGER;
    //private int currentPage = Constants.INITIAL_PAGE_IN_PAGER;

    @DebugLog
    public ProvidersListPresenter(Context context, ProvidersListContract.View mView) {
        this.context = context;
        this.mView = mView;
        mRepository = new ProvidersListRepository(this);
    }

    @DebugLog
    @Override
    public void onClickPrintButton(BaseModel model) {
        //TODO
    }

    @DebugLog
    @Override
    public void onClickEditButton(BaseModel clickedProvider) {
        mView.goToProviderDetailsView((Provider) clickedProvider);
    }

    @DebugLog
    @Override
    public void onClickItem(BaseModel clickedProvider) {
        //TODO
        Log.d("TEST","current "+(Provider) clickedProvider);
    }

    @Override
    public void onClickDeleteButton(BaseModel model) {
        mView.showDeleteConfirmation(model);
    }

    @Override
    public void deleteProvider(BaseModel model) {
        mView.showWorkingIndicator();
        mRepository.deleteProvider((Provider) model);
    }

    @Override
    public void invalidToken() {
        mView.invalidToken();
    }

    @DebugLog
    @Override
    public void getInitialData() {
        mView.showWorkingIndicator();
        mRepository.getProvidersOfType(currentType);
    }

    @DebugLog
    @Override
    public void getProvidersByType(int providerType) {
        currentType = providerType;
        mView.showWorkingIndicator();
        mRepository.getProvidersOfType(providerType);
    }

    @DebugLog
    @Override
    public void refreshProvidersList() {
        getInitialData();
    }

    @DebugLog
    @Override
    public void cancelSearch() {
        mView.updateProvidersList(mRepository.getCurrentProviders());
    }

    @DebugLog
    @Override
    public void handleSuccessfulSortedProvidersRequest(List<Provider> providersList) {
        mView.updateProvidersList(providersList);
        mView.hideWorkingIndicator();
    }

    @DebugLog
    @Override
    public void handleSuccessfulMixedProvidersRequest(List<Provider> nonSortableProvidersList) {

        //If nonSortableProvidersList comes from realm, it cannot be properly sorted, so we use a new list.

        List<Provider> sortableProvidersList = new ArrayList<>();
        sortableProvidersList.addAll(nonSortableProvidersList);

        for (int i=0;i<sortableProvidersList.size()-1;i++) {
            for (int j=i+1;j<sortableProvidersList.size();j++){
                String string1 = sortableProvidersList.get(j).getFullNameProvider() != null ? sortableProvidersList.get(j).getFullNameProvider().toLowerCase() : "";
                String string2 = sortableProvidersList.get(i).getFullNameProvider() != null ? sortableProvidersList.get(i).getFullNameProvider().toLowerCase() : "";

                if (string1.compareTo(string2)<0) {
                    Provider aux = sortableProvidersList.get(j);
                    sortableProvidersList.set(j,sortableProvidersList.get(i));
                    sortableProvidersList.set(i,aux);
                }
            }
        }
        handleSuccessfulSortedProvidersRequest(sortableProvidersList);
    }

    @DebugLog
    @Override
    public void searchProvidersByName(String name) {
        if (name.isEmpty()) {
            mView.updateProvidersList(mRepository.getCurrentProviders());
        } else {
            mView.showWorkingIndicator();
            mRepository.searchProvidersByTypeByName(currentType, name);
        }
    }

    @DebugLog
    @Override
    public void onProviderDeleted() {
        mView.hideWorkingIndicator();
        mView.showMessage(context.getString(R.string.provider_deleted_successful));
        refreshProvidersList();
    }

    @DebugLog
    @Override
    public void onError(String error) {
        mView.hideWorkingIndicator();
        mView.showError(error);
    }
}

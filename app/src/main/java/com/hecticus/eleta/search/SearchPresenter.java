package com.hecticus.eleta.search;

import android.content.Context;

import com.hecticus.eleta.base.BaseModel;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.util.Constants;

import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 22/9/17.
 */

public class SearchPresenter implements SearchContract.Actions {

    Context context;
    private SearchContract.View mView;
    private SearchContract.Repository mRepository;

    private int currentType = Constants.TYPE_HARVESTER;

    @DebugLog
    public SearchPresenter(Context context, SearchContract.View mView, int type) {
        this.context = context;
        this.mView = mView;
        currentType = type;
        mRepository = new SearchRepository(this);
    }

    @Override
    public void onClickPrintButton(BaseModel model) {}

    @Override
    public void onClickEditButton(BaseModel model) {}

    @Override
    public void onClickItem(BaseModel model) {
        mView.selectedProvider((Provider) model);
    }

    @Override
    public void onClickDeleteButton(BaseModel model) {}

    @Override
    public boolean isHarvest() {
        return currentType == Constants.TYPE_HARVESTER;
    }

    @Override
    public void getInitialData() {
        mView.showWorkingIndicator();
        mRepository.getProviders(currentType);
    }

    @Override
    public void searchProvidersByName(String name) {
        mView.showWorkingIndicator();
        mRepository.searchProvidersByTypeByName(currentType,name);
    }

    @Override
    public void refreshProvidersList() {
        mView.updateProvidersList(mRepository.getCurrentProviders());
    }

    @Override
    public void handleSuccessfulProvidersRequest(List<Provider> providersList) {
        mView.hideWorkingIndicator();
        mView.updateProvidersList(providersList);
    }

    @Override
    public void onError(String error) {
        mView.hideWorkingIndicator();
        mView.showError(error);
    }
}

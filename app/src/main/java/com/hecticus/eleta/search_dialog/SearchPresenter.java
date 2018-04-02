package com.hecticus.eleta.search_dialog;

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

    @DebugLog
    @Override
    public void onClickPrintButton(BaseModel model) {}

    @DebugLog
    @Override
    public void onClickEditButton(BaseModel model) {}

    @DebugLog
    @Override
    public void onClickItem(BaseModel model) {
        mView.onProviderSelected((Provider) model);
    }

    @DebugLog
    @Override
    public void onClickDeleteButton(BaseModel model) {}

    @DebugLog
    @Override
    public void invalidToken() {
        mView.invalidToken();
    }

    @DebugLog
    @Override
    public boolean isHarvest() {
        return currentType == Constants.TYPE_HARVESTER;
    }

    @DebugLog
    @Override
    public void getInitialData() {
        mView.showWorkingIndicator();
        mRepository.getAllProvidersByType(currentType);
    }

    @DebugLog
    @Override
    public void searchProvidersByName(String name) {
        mView.showWorkingIndicator();
        mRepository.searchProvidersByTypeByName(currentType,name);
    }

    @DebugLog
    @Override
    public void refreshProvidersList() {
        mView.updateProvidersList(mRepository.getCurrentProviders());
    }

    @DebugLog
    @Override
    public void handleSuccessfulProvidersRequest(List<Provider> providersList) {
        /*Collections.sort(providersList, new Comparator<Provider>() {
            @Override
            public int compare(Provider o1, Provider o2) {
                String string1 = o1.getFullNameProvider()!=null?o1.getFullNameProvider().toLowerCase():"";
                String string2 = o2.getFullNameProvider()!=null?o2.getFullNameProvider().toLowerCase():"";
                return string1.compareTo(string2);
            }
        });
        mView.updateProvidersList(providersList);
        mView.hideWorkingIndicator();*/

        for (int i=0;i<providersList.size()-1;i++) {
            for (int j=i+1;j<providersList.size();j++){
                String string1 = providersList.get(j).getFullNameProvider() != null ? providersList.get(j).getFullNameProvider().toLowerCase() : "";
                String string2 = providersList.get(i).getFullNameProvider() != null ? providersList.get(i).getFullNameProvider().toLowerCase() : "";

                if (string1.compareTo(string2)<0) {
                    Provider aux = providersList.get(j);
                    providersList.set(j,providersList.get(i));
                    providersList.set(i,aux);
                }
            }
        }
        mView.updateProvidersList(providersList);
        mView.hideWorkingIndicator();
    }

    @DebugLog
    @Override
    public void handleSuccessfulSortedProvidersRequest(List<Provider> providersList) {
        mView.updateProvidersList(providersList);
        mView.hideWorkingIndicator();
    }

    @DebugLog
    @Override
    public void onError(String error) {
        mView.hideWorkingIndicator();
        mView.showError(error);
    }
}

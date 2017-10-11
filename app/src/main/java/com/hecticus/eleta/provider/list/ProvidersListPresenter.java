package com.hecticus.eleta.provider.list;

import android.content.Context;

import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseModel;
import com.hecticus.eleta.model.response.item.ItemType;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.util.Constants;

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
    }

    @Override
    public void onClickDeleteButton(BaseModel model) {
        mView.showWorkingIndicator();
        Provider provider = (Provider) model;
        mRepository.deleteProvider(provider.getIdProvider());
    }

    @DebugLog
    @Override
    public void getInitialData() {
        mView.showWorkingIndicator();
        //lastPage = Constants.INITIAL_PAGE_IN_PAGER;
        //currentPage = Constants.INITIAL_PAGE_IN_PAGER;
        //mRepository.getProviders(currentPage);//currentPage
        mRepository.getProvidersOfType(currentType);
    }

    @Override
    public void getProvidersByType(int providerType) {
        currentType = providerType;
        mView.showWorkingIndicator();
        mRepository.getProvidersOfType(providerType);
    }

    @Override
    public void refreshProvidersList() {
        getInitialData();
    }

    @Override
    public void cancelSearch() {
        mView.updateProvidersList(mRepository.getCurrentProviders());
    }

    @Override
    public void handleSuccessfulProvidersRequest(List<Provider> providersList) {
        Collections.sort(providersList, new Comparator<Provider>() {
            @Override
            public int compare(Provider o1, Provider o2) {
                String string1 = o1.getFullNameProvider()!=null?o1.getFullNameProvider().toLowerCase():"";
                String string2 = o2.getFullNameProvider()!=null?o2.getFullNameProvider().toLowerCase():"";
                return string1.compareTo(string2);
            }
        });

        //if (currentPage == Constants.INITIAL_PAGE_IN_PAGER) {
        mView.updateProvidersList(providersList);
        /*} else {
            mView.addMoreProvidersToTheList(providersList);
        }*/

        mView.hideWorkingIndicator();

    }

    @Override
    public void searchProvidersByName(String name) {
        if (name.isEmpty()){
            mView.updateProvidersList(mRepository.getCurrentProviders());
        }else {
            mView.showWorkingIndicator();
            mRepository.searchProvidersByTypeByName(currentType, name);
        }
    }

    /*@Override
    public void updatePager(Pager pager) {
        lastPage = pager.getEndIndex();
        currentPage = pager.getPageIndex();
    }*/

    @DebugLog
    @Override
    public void onProviderDeleted() {
        mView.hideWorkingIndicator();
        mView.showMessage(context.getString(R.string.provider_deleted_successful));
        refreshProvidersList();
    }

    /*@Override
    public void getMoreProviders() {
        mView.showWorkingIndicator();
        mRepository.getProviders(++currentPage);
    }*/

    @DebugLog
    @Override
    public void onError(String error) {
        mView.hideWorkingIndicator();
        mView.showError(error);
    }

    /*@Override
    public boolean canLoadMore() {
        return currentPage + 1 <= lastPage;
    }*/
}

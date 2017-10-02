package com.hecticus.eleta.provider.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseFragment;
import com.hecticus.eleta.base.item.GenericListAdapter;
import com.hecticus.eleta.custom_views.CustomEditText;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.provider.detail.ProviderDetailsActivity;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

public class ProvidersListFragment extends BaseFragment implements ProvidersListContract.View {

    private ProvidersListContract.Actions mPresenter;

    @BindView(R.id.provider_list_recyclerview)
    RecyclerView providersRecyclerView;

    @BindView(R.id.custom_header_w_add_action_title_text_view)
    TextView titleTextView;

    @BindView(R.id.custom_header_w_add_action_image)
    ImageView headerImageView;

    @BindView(R.id.provider_list_type_spinner)
    Spinner typeSpinner;

    @BindView(R.id.provider_list_text_edit_provider)
    CustomEditText providerEditText;

    private GenericListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_providers_list, container, false);
            unbinder = ButterKnife.bind(this, rootView);

            mPresenter = new ProvidersListPresenter(getContext(), this);
            initViews();
            mPresenter.getInitialData();
            setHasOptionsMenu(true);
        } else {
            unbinder = ButterKnife.bind(this, rootView);
        }

        return rootView;
    }

    @Override
    public void initViews() {
        setUpRecyclerView();
        providerEditText.init();
        initString();
        headerImageView.setImageResource(R.mipmap.file_text);
        ArrayAdapter providerTypeAdapter = ArrayAdapter.createFromResource(getContext(), R.array.providers_type, android.R.layout.simple_spinner_item);
        typeSpinner.setAdapter(providerTypeAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onChangedProviderType(position == 0 ?
                        Constants.TYPE_HARVESTER :
                        Constants.TYPE_SELLER);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @DebugLog
    private void setUpRecyclerView() {
        providersRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        providersRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new GenericListAdapter(mPresenter, true, false);
        providersRecyclerView.setAdapter(mAdapter);

        /*providersRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition() + 1;
                if (totalItemCount != 0 && progressBar.getVisibility() == View.GONE && mPresenter.canLoadMore() && totalItemCount <= lastVisibleItem + Constants.ITEMS_BEFORE_LOAD_MORE) {
                    mPresenter.getMoreProviders();
                }
            }
        });*/
    }

    @Override
    public void initString() {
        providerEditText.setDescription(getString(R.string.provider));
        titleTextView.setText(getString(R.string.new_provider));
        providerEditText.setHint(getString(R.string.name));
    }

    @Override
    public void showWorkingIndicator() {
        providersRecyclerView.setEnabled(false);
        providersRecyclerView.setAlpha(0.5f);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideWorkingIndicator() {
        providersRecyclerView.setEnabled(true);
        providersRecyclerView.setAlpha(1f);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(mainLinearLayout, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @Override
    public void showError(String error) {
        Snackbar.make(mainLinearLayout, error, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @OnClick(R.id.custom_header_w_add_action_button)
    @Override
    public void onClickAddProvider() {
        Intent intent = new Intent(getActivity(), ProviderDetailsActivity.class);
        intent.putExtra("isForProviderCreation", true);
        intent.putExtra("canEdit", true);
        intent.putExtra("isHarvester", typeSpinner.getSelectedItemPosition() == 0);
        startActivity(intent);
    }

    @OnClick(R.id.provider_list_search_image_button)
    @Override
    public void onClickSearchProvider() {
        //TODO
    }

    @DebugLog
    @Override
    public void onChangedProviderType(int providerType) {
        mPresenter.getProvidersByType(providerType);
    }

    @DebugLog
    @Override
    public void goToProviderDetailsView(Provider selectedProvider) {
        try {
            Intent intent = new Intent(getActivity(), ProviderDetailsActivity.class);
            intent.putExtra("isForProviderCreation", false);
            intent.putExtra("canEdit", true);
            intent.putExtra("isHarvester", selectedProvider.isHarvester());
            intent.putExtra("provider", Util.getGson().toJson(selectedProvider));
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            showMessage(getString(R.string.error));
        }

    }

    @Override
    public void updateProvidersList(List<Provider> providersList) {
        mAdapter.showNewDataSet(providersList);
    }

    /*@Override
    public void addMoreProvidersToTheList(List<Provider> providersList) {
        mAdapter.showMoreDataSet(providersList);
    }*/

    @Override
    public void refreshList() {
        mPresenter.getInitialData();
    }
}

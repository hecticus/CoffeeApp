package com.hecticus.eleta.provider.list;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseFragment;
import com.hecticus.eleta.base.BaseModel;
import com.hecticus.eleta.base.item.GenericListAdapter;
import com.hecticus.eleta.custom_views.CustomEditText;
import com.hecticus.eleta.home.HomeActivity;
import com.hecticus.eleta.internet.InternetManager;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model_new.persistence.ManagerDB;
import com.hecticus.eleta.provider.detail.ProviderDetailsActivity;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import io.realm.Realm;

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

    @BindView(R.id.provider_list_cancel_search_image_button)
    ImageButton cancelSearchImageView;

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
        providerEditText.setSingleLine();
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
    }

    @Override
    public void initString() {
        providerEditText.setDescription(getString(R.string.provider));
        titleTextView.setText(getString(R.string.new_provider));
        providerEditText.setHint(getString(R.string.name));
    }

    @Override
    public void showWorkingIndicator() {
        if (isAdded()) {
            providersRecyclerView.setEnabled(false);
            providersRecyclerView.setClickable(false);
            providersRecyclerView.setAlpha(0.5f);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideWorkingIndicator() {
        if (isAdded()) {
            providersRecyclerView.setEnabled(true);
            providersRecyclerView.setClickable(true);
            providersRecyclerView.setAlpha(1f);
            progressBar.setVisibility(View.GONE);
        }
    }

    @DebugLog
    @Override
    public void showMessage(String message) {
        if (mainLinearLayout != null)
            Snackbar.make(mainLinearLayout, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @Override
    public void showError(String error) {
        if (mainLinearLayout != null)
            Snackbar.make(mainLinearLayout, error, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @DebugLog
    @OnClick(R.id.custom_header_w_add_action_button)
    @Override
    public void onClickAddProvider() {
        Intent intent = new Intent(getActivity(), ProviderDetailsActivity.class);
        intent.putExtra("isForProviderCreation", true);
        intent.putExtra("canEdit", true);
        intent.putExtra("isHarvester", typeSpinner.getSelectedItemPosition() == 0);
        startActivityForResult(intent, Constants.REQUEST_CODE_PROVIDER_CREATION);
    }

    @OnClick(R.id.provider_list_search_image_button)
    @Override
    public void onClickSearchProvider() {
        if (!providerEditText.getText().trim().isEmpty()) {
            cancelSearchImageView.setVisibility(View.VISIBLE);
        } else {
            cancelSearchImageView.setVisibility(View.GONE);
        }
        mPresenter.searchProvidersByName(providerEditText.getText().trim());

    }

    @OnClick(R.id.provider_list_cancel_search_image_button)
    @Override
    public void onClickCancelSearchProvider() {
        providerEditText.setText("");
        cancelSearchImageView.setVisibility(View.GONE);
        mPresenter.cancelSearch();
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
            boolean control = ManagerDB.isProviderOffline(selectedProvider);
            intent.putExtra("control", control);
            if(InternetManager.isConnected(getActivity()) && !control){
                intent.putExtra("provider", Util.getGson().toJson(selectedProvider));
            }else {
                if(selectedProvider.getIdProvider()!=-1) {
                    intent.putExtra("provider", selectedProvider.getIdProvider());
                }
                else {
                    intent.putExtra("providerLocal", selectedProvider.getIdentificationDocProvider());
                }
            }
            //intent.putExtra("provider", selectedProvider.getIdProvider());
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            showMessage(getString(R.string.error));
        }
    }

    @DebugLog
    @Override
    public void updateProvidersList(List<Provider> providersList) {
        mAdapter.showNewDataSet(providersList);
    }

    @DebugLog
    @Override
    public void refreshList() {
        mPresenter.getInitialData();
    }

    @Override
    public void showDeleteConfirmation(final BaseModel model) {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.delete)
                .setMessage(R.string.want_to_delete_provider)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mPresenter.deleteProvider(model);
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void invalidToken() {
        ((HomeActivity) getActivity()).goToLoginActivity();
    }

    @DebugLog
    @Override
    public boolean hasLocationPermissions() {
        //TODO EDWIN
        return false;
    }

    @DebugLog
    @Override
    public void requestLocationPermissions() {
        //TODO EDWIN
    }

    @DebugLog
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE_PROVIDER_CREATION &&
                resultCode == Activity.RESULT_OK) {
            showMessage(getString(R.string.provider_saved));
        }
    }
}

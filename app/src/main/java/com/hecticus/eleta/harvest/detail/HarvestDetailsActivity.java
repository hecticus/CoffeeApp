package com.hecticus.eleta.harvest.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseActivity;
import com.hecticus.eleta.base.BaseEditableModel;
import com.hecticus.eleta.base.DescriptionAndValueModel;
import com.hecticus.eleta.base.item.EditListAdapter;
import com.hecticus.eleta.confirm_purchase.ConfirmDialogFragment;
import com.hecticus.eleta.custom_views.CustomEditText;
import com.hecticus.eleta.custom_views.CustomSpinner;
import com.hecticus.eleta.home.HomeActivity;
import com.hecticus.eleta.model_new.callback.AcceptConfirmInterface;
import com.hecticus.eleta.model_new.callback.SelectedProviderInterface;
import com.hecticus.eleta.model.response.farm.Farm;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.item.ItemType;
import com.hecticus.eleta.model.response.lot.Lot;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.of_day.HarvestsOfDayListActivity;
import com.hecticus.eleta.provider.detail.ProviderDetailsActivity;
import com.hecticus.eleta.search_dialog.SearchDialogFragment;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.GlideApp;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

public class HarvestDetailsActivity extends BaseActivity implements HarvestDetailsContract.View, SelectedProviderInterface, AcceptConfirmInterface {

    @BindView(R.id.harvest_detail_harvester_linear_layour)
    LinearLayout harvesterLinearLayout;

    @BindView(R.id.harvest_detail_text_edit_harvester)
    CustomEditText harvesterEditText;

    @BindView(R.id.harvest_details_items_recyclerview)
    RecyclerView itemsRecyclerView;

    @BindView(R.id.harvest_detail_custom_spinner_farm)
    CustomSpinner farmSpinner;

    @BindView(R.id.harvest_detail_custom_spinner_lot)
    CustomSpinner lotSpinner;

    @BindView(R.id.harvest_detail_text_edit_observations)
    CustomEditText observationsEditText;

    @BindView(R.id.custom_send_button)
    Button sendButton;

    @BindView(R.id.harvest_detail_simple_header)
    LinearLayout simpleHeaderLinearLayour;

    @BindView(R.id.harvest_detail_profile_header)
    LinearLayout profileHeaderLinearLayour;

    @BindView(R.id.custom_profile_header_avatar_image_view)
    ImageView avatarImageView;

    @BindView(R.id.custom_profile_header_name_edit_text)
    TextView fullNameTextViewInProfileHeader;

    @BindView(R.id.custom_profile_header_title_edit_text)
    TextView titleTextViewInProfileHeader;

    @BindView(R.id.custom_header_w_des_description_text_view)
    TextView descriptionTextViewInDescriptionHeader;

    @BindView(R.id.custom_header_w_des_description_image)
    ImageView imageViewInDescriptionHeader;

    @BindView(R.id.custom_header_w_des_title_text_view)
    TextView titleTextViewInDescriptionHeader;

    @BindView(R.id.harvest_detail_button_add)
    ImageButton addImageButton;

    private HarvestDetailsContract.Actions mPresenter;
    private EditListAdapter mAdapter;

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvest_details);

        ButterKnife.bind(this);

        boolean isAdd = getIntent().getBooleanExtra("isAdd", false);
        boolean canEdit = getIntent().getBooleanExtra("canEdit", false);

        List<InvoiceDetails> details = null;
        if (getIntent().getSerializableExtra("details") != null) {
            Type founderListType = new TypeToken<ArrayList<InvoiceDetails>>() {
            }.getType();
            Log.d("DEBUG json", getIntent().getStringExtra("details")); //todo nose
            details = new Gson().fromJson(getIntent().getStringExtra("details"), founderListType);
        }

        Provider provider = null;
        if (getIntent().getStringExtra("provider") != null) {
            provider = new Gson().fromJson(getIntent().getStringExtra("provider"), Provider.class);
        }

        mPresenter = new HarvestDetailsPresenter(this, this, isAdd, canEdit, provider, details);
        initViews();
        mPresenter.initFields();

    }

    @Override
    public void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        harvesterEditText.initWithTypeAndDescription(CustomEditText.Type.TEXT, getString(R.string.harvester));
        harvesterEditText.getEditText().setFocusable(false);
        harvesterEditText.setHint(getString(R.string.dni_or_name));
        harvesterEditText.getEditText().setOnClickListener(new View.OnClickListener() {
            @DebugLog
            @Override
            public void onClick(View view) {
                showHarvesterSearchDialog();
            }
        });

        observationsEditText.initWithTypeAndDescription(CustomEditText.Type.MULTILINE, getString(R.string.observations));

        if (mPresenter.isAdd()) {
            profileHeaderLinearLayour.setVisibility(View.GONE);
            imageViewInDescriptionHeader.setImageResource(R.mipmap.icon_grano);
        } else {
            harvesterLinearLayout.setVisibility(View.GONE);
            simpleHeaderLinearLayour.setVisibility(View.GONE);
            addImageButton.setVisibility(View.GONE);
        }

        farmSpinner.initWithDescription(getString(R.string.farm));
        farmSpinner.get().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mPresenter.getLotsByFarm(farmSpinner.getSelectedItem().getItemId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        lotSpinner.initWithDescription(getString(R.string.lot));
        lotSpinner.setBackground();

        setUpRecyclerView();
        initString();
    }

    @DebugLog
    private void setUpRecyclerView() {
        itemsRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        itemsRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new EditListAdapter(true, false);
        itemsRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initString() {
        sendButton.setText(getString(R.string.save));
        if (mPresenter.isAdd()) {
            titleTextViewInDescriptionHeader.setText(getString(R.string.new_harvest));
            descriptionTextViewInDescriptionHeader.setText(getString(R.string.create));
        } else {
            titleTextViewInProfileHeader.setText(getString(R.string.harvest_of_day));
            fullNameTextViewInProfileHeader.setText("");//TODO
        }
    }

    @Override
    public void showWorkingIndicator() {
        mainLinearLayout.setEnabled(false);
        mainLinearLayout.setAlpha(0.5f);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideWorkingIndicator() {
        mainLinearLayout.setEnabled(true);
        mainLinearLayout.setAlpha(1f);
        progressBar.setVisibility(View.GONE);
    }

    @DebugLog
    @Override
    public void handleSuccessfulUpdate() {
        if (mPresenter.isAdd()) {
            Intent mIntent = new Intent(HarvestDetailsActivity.this, HomeActivity.class);
            mIntent.putExtra("reloadHarvests", true);
            startActivity(mIntent);
        } else {
            Intent intent = new Intent(this, HarvestsOfDayListActivity.class);
            intent.putExtra("reload", true);
            setResult(RESULT_OK, intent);
            startActivity(intent);
        }
        Log.d("OFFLINE", "--->handleSuccessfulUpdate isAdd:" + mPresenter.isAdd());
        finish();
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(mainLinearLayout, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @Override
    public void enableEdition(boolean enabled) {
        harvesterEditText.setEnable(enabled);
        observationsEditText.setEnable(enabled);
    }

    @DebugLog
    @OnClick(R.id.custom_send_button)
    @Override
    public void onClickSaveChangesButton() {
        mPresenter.onSaveChanges(
                (Lot) lotSpinner.getSelectedItem(),
                mAdapter.getItemsValues(),
                observationsEditText.getText());

    }

    @DebugLog
    @OnClick(R.id.harvest_detail_button_add)
    @Override
    public void onClickAddHarvesterButton() {
        Intent intent = new Intent(this, ProviderDetailsActivity.class);
        intent.putExtra("isForProviderCreation", true);
        intent.putExtra("canEdit", true);
        intent.putExtra("isHarvester", true);
        intent.putExtra("fromProvidersList", false);
        startActivityForResult(intent, Constants.REQUEST_CODE_PROVIDER_CREATION);
    }

    @Override
    public void updateFarms(List<Farm> farmsList, int selectedId) {
        Gson g = new Gson();
        Log.d("DEBUGFFFFFFFARM", g.toJson(farmsList));
        farmSpinner.setValuesAndSelect(farmsList, selectedId);
    }

    @Override
    public void updateLots(List<Lot> lotsList, int selectedId) {
        lotSpinner.setValuesAndSelect(lotsList, selectedId);
    }

    @Override
    public void updateItems(List<ItemType> itemTypeList) {
        if (itemTypeList != null && itemTypeList.size() % 2 != 0)
            observationsEditText.setBackground();

        mAdapter.showNewDataSet(itemTypeList);
    }

    @Override
    public void updateProvider(String provider) {
        harvesterEditText.setText(provider);
    }

    @Override
    public void loadHeader(String providerName, String imageUrl) {
        fullNameTextViewInProfileHeader.setText(providerName);
        if (imageUrl != null) {
            GlideApp
                    .with(this)
                    .load(imageUrl)
                    .error(R.mipmap.placeholder_avatar)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(avatarImageView);
        }
    }

    @Override
    public void loadObservation(String observation) {
        observationsEditText.setText(observation);
    }

    @Override
    public void invalidToken() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("invalidToken", true);
        startActivity(intent);
        finish();
    }

    @DebugLog
    @Override
    public void showDialogConfirmation() {
        Bundle args = new Bundle();
        args.putInt("providerType", Constants.TYPE_HARVESTER);
        args.putString("provider", harvesterEditText.getText());
        args.putString("farm", farmSpinner.getSelectedItem().getItemReadableDescription());
        args.putString("lot", lotSpinner.getSelectedItem().getItemReadableDescription());
        args.putString("observation", observationsEditText.getText());

        List<DescriptionAndValueModel> descriptionAndValueModelList = new ArrayList<>();
        for (BaseEditableModel model : mAdapter.getValues()) {
            if (!model.getInputValue().isEmpty()) {
                descriptionAndValueModelList.add(new DescriptionAndValueModel(model.getReadableDescription(), model.getInputValue()));
            }
        }
        args.putSerializable("descriptions", (Serializable) descriptionAndValueModelList);

        ConfirmDialogFragment mConfirmDialogFragment = new ConfirmDialogFragment();

        mConfirmDialogFragment.setArguments(args);
        mConfirmDialogFragment.show(getSupportFragmentManager(), "confirm");
    }

    @DebugLog
    private void showHarvesterSearchDialog() {
        SearchDialogFragment dialog = new SearchDialogFragment();
        Bundle args = new Bundle();
        args.putInt("providerType", Constants.TYPE_HARVESTER);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "search");
    }

    @DebugLog
    @Override
    public void onProviderSelected(Provider provider) {
        if (provider == null)
            return;

        mPresenter.onProviderSelected(provider);
    }

    @DebugLog
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_PROVIDER_CREATION && resultCode == RESULT_OK) {
            if (data.getStringExtra("provider") != null) {
                showMessage(getResources().getString(R.string.provider_saved));
                Provider provider = new Gson().fromJson(data.getStringExtra("provider"), Provider.class);
                mPresenter.onProviderSelected(provider);
            }
        }
    }

    @DebugLog
    @Override
    public void onAcceptConfirm() {
        mPresenter.acceptSave();
    }
}

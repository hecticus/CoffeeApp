package com.hecticus.eleta.purchases.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.hecticus.eleta.internet.InternetManager;
import com.hecticus.eleta.model_new.callback.AcceptConfirmInterface;
import com.hecticus.eleta.model_new.callback.SelectedProviderInterface;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.item.ItemType;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model.response.purity.Purity;
import com.hecticus.eleta.model.response.store.Store;
import com.hecticus.eleta.model_new.persistence.ManagerDB;
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

public class PurchaseDetailsActivity extends BaseActivity implements PurchaseDetailsContract.View, SelectedProviderInterface, AcceptConfirmInterface {

    @BindView(R.id.purchase_details_provider_linear_layour)
    LinearLayout purchaseLinearLayout;

    @BindView(R.id.purchase_details_text_edit_provider)
    CustomEditText providerEditText;

    @BindView(R.id.purchase_detail_text_edit_amount)
    CustomEditText amountEditText;

    @BindView(R.id.purchase_detail_text_edit_price)
    CustomEditText priceEditText;

    @BindView(R.id.purchase_detail_text_edit_dispatcher)
    CustomEditText dispatcherEditText;

    @BindView(R.id.purchase_details_text_edit_observations)
    CustomEditText observationsEditText;

    @BindView(R.id.purchase_detail_custom_spinner_items)
    CustomSpinner itemsSpinner;

    @BindView(R.id.purchase_detail_custom_spinner_store)
    CustomSpinner storeSpinner;

    @BindView(R.id.purchase_details_purity_recyclerview)
    RecyclerView purityRecyclerView;

    @BindView(R.id.custom_send_button)
    Button sendButton;

    @BindView(R.id.purchase_details_checkbox_freight)
    CheckBox freightCheckBox;

    @BindView(R.id.purchase_details_simple_header)
    LinearLayout simpleHeaderLinearLayour;

    @BindView(R.id.purchase_details_profile_header)
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

    @BindView(R.id.purchase_details_button_add)
    ImageButton addImageButton;

    private PurchaseDetailsContract.Actions mPresenter;
    private EditListAdapter puritiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_details);

        ButterKnife.bind(this);

        boolean isAdd = getIntent().getBooleanExtra("isAdd", false);
        boolean canEdit = getIntent().getBooleanExtra("canEdit", false);
        boolean invoiceHasOfflineOperation = getIntent().getBooleanExtra("invoiceHasOfflineOperation", false);

        List<InvoiceDetails> details = new ArrayList<>(); //null;
        /*if (getIntent().getSerializableExtra("details") != null) {
            Type founderListType = new TypeToken<ArrayList<InvoiceDetails>>() {
            }.getType();
            Log.d("DEBUG json", getIntent().getStringExtra("details")); //todo nose
            details = new Gson().fromJson(getIntent().getStringExtra("details"), founderListType);
        }*/
        //details.add(new Gson().fromJson(getIntent().getStringExtra("details"), InvoiceDetails.class));
        try{
            if(InternetManager.isConnected(this) && !getIntent().getBooleanExtra("control", false)){
                if (getIntent().getStringExtra("details") != null) {
                    details.add(new Gson().fromJson(getIntent().getStringExtra("details"), InvoiceDetails.class));
                }
            }else {
                if (getIntent().getIntExtra("details", -1) != -1) {
                    details.add(ManagerDB.getInvoiceDetailById(getIntent().getIntExtra("details", -1)));
                    Log.d("DEBUG", "lote"+ String.valueOf(details.get(0).getLotId()));
                    details.get(0).setStore(ManagerDB.getStoreById(details.get(0).getStoreId()));
                    details.get(0).setItemType(new ItemType(details.get(0).getItemTypeId()));
                } else {
                    details.add(ManagerDB.getInvoiceDetailByIdLocal(getIntent().getIntExtra("detailsLocal", -1)));
                    //Log.d("DEBUG", "lote"+ String.valueOf(details.get(0).getLotId()));
                    details.get(0).setStore(ManagerDB.getStoreById(details.get(0).getStoreId()));
                    details.get(0).setItemType(new ItemType(details.get(0).getItemTypeId()));
                }
            }
        }catch (Exception e){

        }

        Provider provider = null;
        if (getIntent().getStringExtra("provider") != null) {
            provider = new Gson().fromJson(getIntent().getStringExtra("provider"), Provider.class);
        }

        mPresenter = new PurchaseDetailsPresenter(this, this, isAdd, canEdit,
                invoiceHasOfflineOperation, provider, details);
        initViews();
        mPresenter.initFields();
    }

    @Override
    public void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        providerEditText.initWithTypeAndDescription(CustomEditText.Type.TEXT, getString(R.string.provider));
        providerEditText.getEditText().setFocusable(false);
        providerEditText.setHint(getString(R.string.ruc_or_name));
        providerEditText.getEditText().setOnClickListener(new View.OnClickListener() {
            @DebugLog
            @Override
            public void onClick(View view) {
                showProviderSearchDialog();
            }
        });

        amountEditText.initWithTypeAndDescription(CustomEditText.Type.DECIMAL, getString(R.string.amount));
        amountEditText.setBackground();
        priceEditText.initWithTypeAndDescription(CustomEditText.Type.DECIMAL, getString(R.string.price));
        dispatcherEditText.initWithTypeAndDescription(CustomEditText.Type.TEXT, getString(R.string.dispatched_by));

        observationsEditText.initWithTypeAndDescription(CustomEditText.Type.MULTILINE, getString(R.string.observations));


        if (mPresenter.isAdd()) {
            profileHeaderLinearLayour.setVisibility(View.GONE);
            imageViewInDescriptionHeader.setImageResource(R.mipmap.package2);
        } else {
            purchaseLinearLayout.setVisibility(View.GONE);
            simpleHeaderLinearLayour.setVisibility(View.GONE);
            addImageButton.setVisibility(View.GONE);
        }

        storeSpinner.initWithDescription(getString(R.string.gathering));
        itemsSpinner.initWithDescription(getString(R.string.type));

        setUpRecyclerView();
        freightCheckBox.setChecked(true);

        initString();
    }

    @DebugLog
    private void setUpRecyclerView() {
        purityRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        purityRecyclerView.setLayoutManager(linearLayoutManager);

        puritiesAdapter = new EditListAdapter(true, true, false);

        purityRecyclerView.setAdapter(puritiesAdapter);
    }

    @Override
    public void initString() {
        sendButton.setText(getString(R.string.save));
        if (mPresenter.isAdd()) {
            titleTextViewInDescriptionHeader.setText(getString(R.string.new_purchase));
            descriptionTextViewInDescriptionHeader.setText(getString(R.string.create));
        } else {
            titleTextViewInProfileHeader.setText(getString(R.string.purchase_of_day));
            fullNameTextViewInProfileHeader.setText(getString(R.string.name));//TODO
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

    @Override
    public void handleSuccessfulUpdate() {
        if (mPresenter.isAdd()) {
            Intent mIntent = new Intent(PurchaseDetailsActivity.this, HomeActivity.class);
            mIntent.putExtra("reloadPurchases", true);
            startActivity(mIntent);
        } else {
            Intent intent = new Intent();
            intent.putExtra("reload", true);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(mainLinearLayout, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @Override
    public void enableEdition(boolean enabled) {
        providerEditText.setEnable(enabled);
        observationsEditText.setEnable(enabled);
    }

    @DebugLog
    @OnClick(R.id.custom_send_button)
    @Override
    public void onClickSaveChangesButton() {
        Log.d("DEBUG itemId", "desde Activity"+String.valueOf(itemsSpinner.getSelectedItem().getItemId()));
        mPresenter.onSaveChanges(
                (Store) storeSpinner.getSelectedItem(),
                freightCheckBox.isChecked(),
                itemsSpinner.getSelectedItem().getItemId(),
                amountEditText.getText(),
                priceEditText.getText(),
                puritiesAdapter.getPuritiesValues(),
                dispatcherEditText.getText(),
                observationsEditText.getText()
        );
    }

    @DebugLog
    @OnClick(R.id.purchase_details_button_add)
    @Override
    public void onClickAddProviderButton() {
        Intent intent = new Intent(this, ProviderDetailsActivity.class);
        intent.putExtra("isForProviderCreation", true);
        intent.putExtra("canEdit", true);
        intent.putExtra("isHarvester", false);
        intent.putExtra("fromProvidersList", false);
        startActivityForResult(intent, Constants.REQUEST_CODE_PROVIDER_CREATION);
    }

    @Override
    public void updateStores(List<Store> stores, int selectedId) {
        storeSpinner.setValuesAndSelect(stores, selectedId);
    }

    @Override
    public void updateItems(List<ItemType> itemTypeList, int selectedId) {
        /*Gson g = new Gson();
        Log.d("DEBUG listItems id:"+String.valueOf(selectedId), g.toJson(itemTypeList));*/
        itemsSpinner.setValuesAndSelect(itemTypeList, selectedId);

    }

    @DebugLog
    @Override
    public void updatePurities(List<Purity> puritiesList) {
        if (puritiesList != null && puritiesList.size() % 2 != 0) {
            observationsEditText.setBackground();
        } else {
            dispatcherEditText.setBackground();
        }

        puritiesAdapter.showNewDataSet(puritiesList);
    }

    @Override
    public void updateProvider(String provider) {
        providerEditText.setText(provider);
    }

    @Override
    public void loadHeader(String providerName, String imageUrl) {
        fullNameTextViewInProfileHeader.setText(providerName);
        if (imageUrl != null) {
            GlideApp
                    .with(this)
                    .load(imageUrl)
                    .error(R.mipmap.picture)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(avatarImageView);
        }
    }

    @DebugLog
    @Override
    public void loadFields(boolean freight, String amount, String price, String dispatcher, String observation) {
        freightCheckBox.setChecked(freight);
        amountEditText.setText(amount);
        priceEditText.setText(price);
        dispatcherEditText.setText(dispatcher);
        observationsEditText.setText(observation);
    }

    @Override
    public void invalidToken() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("invalidToken", true);
        startActivity(intent);
        finish();
    }

    @Override
    public void showDialogConfirmation() {
        ConfirmDialogFragment dialog = new ConfirmDialogFragment();
        Bundle args = new Bundle();
        args.putInt("providerType", Constants.TYPE_SELLER);
        args.putString("provider", providerEditText.getText());
        args.putString("store", storeSpinner.getSelectedItem().getItemReadableDescription());
        args.putBoolean("freight", freightCheckBox.isChecked());
        args.putString("type", itemsSpinner.getSelectedItem().getItemReadableDescription());
        args.putString("amount", amountEditText.getText());
        args.putString("price", priceEditText.getText());
        args.putString("dispatch_by", dispatcherEditText.getText());
        args.putString("observation", observationsEditText.getText());

        List<DescriptionAndValueModel> descriptionAndValueModelList = new ArrayList<>();

        for (BaseEditableModel model : puritiesAdapter.getValues()) {
            if (!model.getInputValue().isEmpty()) {
                descriptionAndValueModelList.add(new DescriptionAndValueModel(model.getReadableDescription(), model.getInputValue()));
            }
        }
        args.putSerializable("descriptions", (Serializable) descriptionAndValueModelList);

        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "confirm");
    }

    @DebugLog
    private void showProviderSearchDialog() {
        SearchDialogFragment dialog = new SearchDialogFragment();
        Bundle args = new Bundle();
        args.putInt("providerType", Constants.TYPE_SELLER);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "search");
    }

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

    @Override
    public void onAcceptConfirm() {
        mPresenter.onSaveConfirmedInDialog();
    }
}

package com.hecticus.eleta.of_day;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseActivity;
import com.hecticus.eleta.base.BaseDetailModel;
import com.hecticus.eleta.base.item.TwoColumnsGenericListAdapter;
import com.hecticus.eleta.custom_views.CustomButtonWBorderAndImage;
import com.hecticus.eleta.home.HomeActivity;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.model_new.persistence.ManagerDB;
import com.hecticus.eleta.print.PrintPreviewActivity;
import com.hecticus.eleta.purchases.detail.PurchaseDetailsActivity;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.GlideApp;
import com.hecticus.eleta.util.PermissionUtil;
import com.hecticus.eleta.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 27/9/17.
 */

public class PurchasesOfDayListActivity extends BaseActivity implements InvoicesOfDayListContract.View {

    private InvoicesOfDayListContract.Actions mPresenter;

    @BindView(R.id.harvests_of_day_list_recyclerview)
    RecyclerView harvestsRecyclerView;

    @BindView(R.id.custom_profile_header_name_edit_text)
    TextView headerNameTextView;

    @BindView(R.id.custom_profile_header_title_edit_text)
    TextView headerTitleTextView;

    @BindView(R.id.custom_profile_header_avatar_image_view)
    ImageView profileImageView;

    @BindView(R.id.harvests_of_day_print_invoice)
    CustomButtonWBorderAndImage printInvoiceButton;

    @BindView(R.id.harvests_of_day_make_puchase)
    CustomButtonWBorderAndImage makePurchaseButtom;

    private TwoColumnsGenericListAdapter mAdapter;

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoices_of_day_list);
        ButterKnife.bind(this);

        Invoice initialInvoice = null;
        /*if (getIntent().getIntExtra("invoice",-1) > -1){  // getIntent().getStringExtra("invoice") != null) {
            Log.d("DEBUG", String.valueOf(getIntent().getIntExtra("invoice", -1)));
            //todo nose
            //todo invoice
            initialInvoice = ManagerDB.getInvoiceById(getIntent().getIntExtra("invoice",-1));/*new Gson().fromJson(getIntent().getStringExtra("invoice"), Invoice.class);*/
        //}*/
        if(getIntent().getBooleanExtra("control", false)) {
            if (getIntent().getIntExtra("invoice", -1) > -1) {//getIntent().getStringExtra("invoice") != null) {//
                //Log.d("HarvestsOfDayListAct", "--->Invoice json after intent: \n" + getIntent().getStringExtra("invoice"));
                //todo nose
                //todo invoice
                initialInvoice = ManagerDB.getInvoiceById(getIntent().getIntExtra("invoice", -1));//new Gson().fromJson(getIntent().getStringExtra("invoice"), Invoice.class);
                //Log.d("DEBUG Prueba", getIntent().getStringExtra("invoice"));
                //initialInvoice = new Gson().fromJson(getIntent().getStringExtra("invoice"), Invoice.class);

                Log.d("HarvestsOfDayListAct", "--->Invoice class rebuilt: \n" + initialInvoice.toString());
            } else
                Log.e("HarvestsOfDayListAct", "--->No invoice sent to HarvestsOfDayActivity");
        } else {
            if (getIntent().getStringExtra("invoice") != null) {//getIntent().getIntExtra("invoice", -1) > -1) {//
                //Log.d("HarvestsOfDayListAct", "--->Invoice json after intent: \n" + getIntent().getStringExtra("invoice"));
                //todo nose
                //todo invoice
                //initialInvoice = ManagerDB.getInvoiceById(getIntent().getIntExtra("invoice", -1));//new Gson().fromJson(getIntent().getStringExtra("invoice"), Invoice.class);
                //Log.d("DEBUG Prueba", getIntent().getStringExtra("invoice"));
                initialInvoice = new Gson().fromJson(getIntent().getStringExtra("invoice"), Invoice.class);

                Log.d("HarvestsOfDayListAct", "--->Invoice class rebuilt: \n" + initialInvoice.toString());
            } else
                Log.e("HarvestsOfDayListAct", "--->No invoice sent to HarvestsOfDayActivity");
        }
        mPresenter = new InvoicesOfDayListPresenter(this, this, initialInvoice, false);
        initViews();
    }

    @DebugLog
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mPresenter.getInitialData();
    }

    @DebugLog
    @Override
    public void showWorkingIndicator() {
        harvestsRecyclerView.setEnabled(false);
        harvestsRecyclerView.setAlpha(0.5f);
        progressBar.setVisibility(View.VISIBLE);
    }

    @DebugLog
    @Override
    public void hideWorkingIndicator() {
        harvestsRecyclerView.setEnabled(true);
        harvestsRecyclerView.setAlpha(1f);
        progressBar.setVisibility(View.GONE);
    }

    @DebugLog
    @Override
    public void showMessage(String message) {
        if (mainLinearLayout != null)
            Snackbar.make(mainLinearLayout, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @DebugLog
    @Override
    public void showError(String error) {
        if (mainLinearLayout != null)
            Snackbar.make(mainLinearLayout, error, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @DebugLog
    @OnClick(R.id.harvests_of_day_print_invoice)
    @Override
    public void onClickPrintButton() {
        mPresenter.onClickPrintButton();
    }

    @OnClick(R.id.harvests_of_day_make_puchase)
    @Override
    public void onClickCMakePurchase() {
        mPresenter.closeInvoice();
    }

    @DebugLog
    @Override
    public void closedInvoice() {
        mAdapter.setShowActions(false);
        makePurchaseButtom.setVisibility(View.INVISIBLE);
    }

    @DebugLog
    @Override
    public void goToHarvestOrPurchaseDetailsView(Provider provider, /*List<*/InvoiceDetails/*>*/ detailsList, boolean invoiceHasOfflineOperation) {
        try {
            Intent intent = new Intent(this, PurchaseDetailsActivity.class);
            intent.putExtra("details", Util.getGson().toJson(detailsList));
            intent.putExtra("provider", Util.getGson().toJson(provider));
            intent.putExtra("isAdd", false);
            intent.putExtra("canEdit", true);
            intent.putExtra("invoiceHasOfflineOperation", invoiceHasOfflineOperation);
            startActivityForResult(intent, 1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @DebugLog
    @Override
    public void updateHarvestsOrPurchasesList(List<InvoiceDetails> harvestsList) {
        mAdapter.showNewDataSet(harvestsList);
    }

    @DebugLog
    @Override
    public void initHeader(String name, String imageUrl) {
        headerNameTextView.setText(name);
        if (imageUrl != null) {
            GlideApp
                    .with(this)
                    .load(imageUrl)
                    .error(R.mipmap.placeholder_avatar)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(profileImageView);
        }
    }

    @DebugLog
    @Override
    public void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpRecyclerView();

        printInvoiceButton.initWithTypeAndText(CustomButtonWBorderAndImage.Type.PRINT, getString(R.string.print_invoice));
        makePurchaseButtom.initWithTypeAndText(CustomButtonWBorderAndImage.Type.CHECK, getString(R.string.make_purchase));

        if (mPresenter.isCurrentClosedInvoice()) {
            makePurchaseButtom.setVisibility(View.INVISIBLE);
        }

        initString();
    }

    @DebugLog
    private void setUpRecyclerView() {
        harvestsRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        harvestsRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new TwoColumnsGenericListAdapter(mPresenter, !mPresenter.isCurrentClosedInvoice());
        harvestsRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void initString() {
        headerNameTextView.setText("");
        headerTitleTextView.setText(getString(R.string.purchase_of_day));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            doBack();
        return true;
    }

    @DebugLog
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data.getBooleanExtra("reload", false)) {
                    mPresenter.refreshHarvestsList();
                }
            }
        }
    }

    @DebugLog
    @Override
    public void doBack() {
        if (mPresenter.needReloadMainList()) {
            Intent mIntent = new Intent(PurchasesOfDayListActivity.this, HomeActivity.class);
            mIntent.putExtra("reloadPurchases", true);
            startActivity(mIntent);
        }
        finish();
    }

    @DebugLog
    @Override
    public void showHarvestPrintPreview(String textToPrint, String textToShow) {
        Intent intent = new Intent(this, PrintPreviewActivity.class);
        intent.putExtra(Constants.PRINT_TEXT_FOR_ZPL, textToPrint);
        intent.putExtra(Constants.PRINT_TEXT_FOR_PREVIEW, textToShow);
        startActivity(intent);
    }

    @Override
    public void showDeleteConfirmation(final BaseDetailModel model) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete)
                .setMessage(R.string.want_to_delete_harvest)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mPresenter.deleteHarvestOrPurchase(model);
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @DebugLog
    @Override
    public void onBackPressed() {
        if (mPresenter.needReloadMainList()) {
            Intent mIntent = new Intent(PurchasesOfDayListActivity.this, HomeActivity.class);
            mIntent.putExtra("reloadPurchases", true);
            startActivity(mIntent);
            finish();
        } else
            super.onBackPressed();
    }

    @DebugLog
    @Override
    public void invalidToken() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("invalidToken", true);
        startActivity(intent);
        finish();
    }

    @DebugLog
    @Override
    public void finishWithErrorMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        finish();
    }


    @DebugLog
    @Override
    public boolean hasLocationPermissions() {
        return PermissionUtil.isPermissionGranted(this, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    @DebugLog
    @Override
    public void requestLocationPermissions() {
        PermissionUtil.requestLocationPermission(this);
    }

    @DebugLog
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
package com.hecticus.eleta.of_day;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseActivity;
import com.hecticus.eleta.base.item.TwoColumnsGenericListAdapter;
import com.hecticus.eleta.custom_views.CustomButtonWBorderAndImage;
import com.hecticus.eleta.harvest.detail.HarvestDetailsActivity;
import com.hecticus.eleta.model.response.harvest.HarvestOfDay;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.model.response.invoice.InvoiceDetails;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.util.GlideApp;
import com.hecticus.eleta.util.Util;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

public class HarvestsOfDayListActivity extends BaseActivity implements InvoicesOfDayListContract.View{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoices_of_day_list);
        ButterKnife.bind(this);

        Invoice initialInvoice = null;
        if (getIntent().getStringExtra("invoice") != null) {
            initialInvoice = new Gson().fromJson(getIntent().getStringExtra("invoice"), Invoice.class);
        }
        mPresenter = new InvoicesOfDayListPresenter(this, this, initialInvoice);
        initViews();
        mPresenter.getInitialData();
    }

    @Override
    public void showWorkingIndicator() {
        harvestsRecyclerView.setEnabled(false);
        harvestsRecyclerView.setAlpha(0.5f);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideWorkingIndicator() {
        harvestsRecyclerView.setEnabled(true);
        harvestsRecyclerView.setAlpha(1f);
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

    @OnClick(R.id.harvests_of_day_print_invoice)
    @Override
    public void onClickPrintInvoice() {
        //TODO
    }

    @OnClick(R.id.harvests_of_day_make_puchase)
    @Override
    public void onClickCMakePurchase() {
        //TODO
    }

    @Override
    public void goToHarvestDetail(Provider provider, List<InvoiceDetails> detailsList) {
        try {
            Intent intent = new Intent(this, HarvestDetailsActivity.class);
            intent.putExtra("details", (Serializable) detailsList);
            intent.putExtra("provider", Util.getGson().toJson(provider));
            intent.putExtra("isAdd",false);
            intent.putExtra("canEdit",true);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateHarvestsList(List<HarvestOfDay> harvestsList) {
        mAdapter.showNewDataSet(harvestsList);
    }

    /*@Override
    public void addMoreHarvestsToTheList(List<HarvestOfDay> harvestsList) {
        mAdapter.showMoreDataSet(harvestsList);
    }*/

    @Override
    public void refreshList() {
        mPresenter.getInitialData();
    }

    @Override
    public void initHeader(String name, String imageUrl) {
        headerNameTextView.setText(name);
        if (imageUrl != null){
            GlideApp
                    .with(this)
                    .load(imageUrl)
                    .error(R.mipmap.picture)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(profileImageView);
        }
    }

    @Override
    public void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpRecyclerView();

        printInvoiceButton.initWithTypeAndText(CustomButtonWBorderAndImage.Type.PRINT,getString(R.string.print_invoice));
        makePurchaseButtom.initWithTypeAndText(CustomButtonWBorderAndImage.Type.CHECK,getString(R.string.make_purchase));

        initString();
    }

    @DebugLog
    private void setUpRecyclerView() {
        harvestsRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        harvestsRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new TwoColumnsGenericListAdapter(mPresenter);
        harvestsRecyclerView.setAdapter(mAdapter);

        /*harvestsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition() + 1;
                if (totalItemCount != 0 && progressBar.getVisibility() == View.GONE && mPresenter.canLoadMore() && totalItemCount <= lastVisibleItem + Constants.ITEMS_BEFORE_LOAD_MORE) {
                    mPresenter.getMoreHarvests();
                }
            }
        });*/
    }

    @Override
    public void initString() {
        headerNameTextView.setText("");
        headerTitleTextView.setText(getString(R.string.harvest_of_day));
    }
}

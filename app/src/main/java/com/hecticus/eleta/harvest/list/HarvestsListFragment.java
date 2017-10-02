package com.hecticus.eleta.harvest.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hecticus.eleta.PrintActivity;
import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseFragment;
import com.hecticus.eleta.base.item.GenericListAdapter;
import com.hecticus.eleta.harvest.detail.HarvestDetailsActivity;
import com.hecticus.eleta.of_day.HarvestsOfDayListActivity;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 15/9/17.
 */

public class HarvestsListFragment extends BaseFragment implements HarvestsListContract.View{

    private HarvestsListContract.Actions mPresenter;

    @BindView(R.id.harvest_list_recyclerview)
    RecyclerView harvestsRecyclerView;

    @BindView(R.id.custom_header_w_add_action_title_text_view)
    TextView titleTextView;

    private GenericListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_harvests_list, container, false);
            unbinder = ButterKnife.bind(this, rootView);

            mPresenter = new HarvestsListPresenter(getContext(), this);
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
        initString();
    }

    @DebugLog
    private void setUpRecyclerView() {
        harvestsRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        harvestsRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new GenericListAdapter(mPresenter, false, false);
        harvestsRecyclerView.setAdapter(mAdapter);

        harvestsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition() + 1;
                if (totalItemCount != 0 && progressBar.getVisibility() == View.GONE && mPresenter.canLoadMore() && totalItemCount <= lastVisibleItem + Constants.ITEMS_BEFORE_LOAD_MORE) {
                    mPresenter.getMoreHarvests();
                }
            }
        });
    }

    @Override
    public void initString() {
        titleTextView.setText(getString(R.string.new_harvest));
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

    @OnClick(R.id.custom_header_w_add_action_button)
    @Override
    public void onClickAddHarvest() {
        Intent intent = new Intent(getActivity(), HarvestDetailsActivity.class);
        intent.putExtra("isAdd",true);
        intent.putExtra("canEdit",true);
        startActivity(intent);
    }

    @Override
    public void goToHarvestsListByHarvester(Invoice invoice) {
        try {
            Intent intent = new Intent(getActivity(), HarvestsOfDayListActivity.class);
            intent.putExtra("invoice", Util.getGson().toJson(invoice));
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateHarvestsList(List<Invoice> invoicesList) {
        mAdapter.showNewDataSet(invoicesList);
    }

    @Override
    public void addMoreHarvestsToTheList(List<Invoice> invoicesList) {
        mAdapter.showMoreDataSet(invoicesList);
    }

    @Override
    public void refreshList() {
        mPresenter.getInitialData();
    }

    @Override
    public void printHarvest(String text) {
        Intent intent = new Intent(getActivity(), PrintActivity.class);
        intent.putExtra("text",text);
        startActivity(intent);
    }
}
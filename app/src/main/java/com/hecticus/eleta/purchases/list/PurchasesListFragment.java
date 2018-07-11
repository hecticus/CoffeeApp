package com.hecticus.eleta.purchases.list;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseFragment;
import com.hecticus.eleta.base.BaseModel;
import com.hecticus.eleta.base.item.GenericListAdapter;
import com.hecticus.eleta.home.HomeActivity;
import com.hecticus.eleta.model.response.invoice.Invoice;
import com.hecticus.eleta.of_day.PurchasesOfDayListActivity;
import com.hecticus.eleta.print.PrintPreviewActivity;
import com.hecticus.eleta.purchases.detail.PurchaseDetailsActivity;
import com.hecticus.eleta.util.Constants;
import com.hecticus.eleta.util.PermissionUtil;
import com.hecticus.eleta.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

public class PurchasesListFragment extends BaseFragment implements PurchasesListContract.View {

    private PurchasesListContract.Actions mPresenter;

    @BindView(R.id.purchases_list_recyclerview)
    RecyclerView purchasesRecyclerView;

    @BindView(R.id.custom_header_w_add_action_title_text_view)
    TextView titleTextView;

    @BindView(R.id.custom_header_w_add_action_image)
    ImageView headerImageView;

    private GenericListAdapter mAdapter;

    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_puchases_list, container, false);
            unbinder = ButterKnife.bind(this, rootView);

            mPresenter = new PurchasesListPresenter(getContext(), this);
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
        headerImageView.setImageResource(R.mipmap.package2);
    }

    @DebugLog
    private void setUpRecyclerView() {
        purchasesRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        purchasesRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new GenericListAdapter(mPresenter, false, false);
        purchasesRecyclerView.setAdapter(mAdapter);

        purchasesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition() + 1;
                if (totalItemCount != 0 && progressBar.getVisibility() == View.GONE && mPresenter.canLoadMore() && totalItemCount <= lastVisibleItem + Constants.ITEMS_BEFORE_LOAD_MORE) {
                    mPresenter.getMorePurchases();
                }
            }
        });
    }

    @Override
    public void initString() {
        titleTextView.setText(getString(R.string.new_purchase));
    }

    @Override
    public void showWorkingIndicator() {
        if (isAdded()) {
            purchasesRecyclerView.setEnabled(false);
            purchasesRecyclerView.setClickable(false);
            purchasesRecyclerView.setAlpha(0.5f);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideWorkingIndicator() {
        if (isAdded()) {
            purchasesRecyclerView.setEnabled(true);
            purchasesRecyclerView.setClickable(true);
            purchasesRecyclerView.setAlpha(1f);
            progressBar.setVisibility(View.GONE);
        }
    }

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

    @OnClick(R.id.custom_header_w_add_action_button)
    @Override
    public void onClickAddPurchase() {
        Intent intent = new Intent(getActivity(), PurchaseDetailsActivity.class);
        intent.putExtra("isAdd", true);
        intent.putExtra("canEdit", true);
        startActivity(intent);
    }

    @Override
    public void goToPurchasesListByProvider(Invoice invoice) {
        //try {
            Intent intent = new Intent(getActivity(), PurchasesOfDayListActivity.class);
            intent.putExtra("invoice", invoice.getId());
            startActivity(intent);
        /*} catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    @DebugLog
    @Override
    public void updatePurchasesList(List<Invoice> invoicesList) {
        if (isAdded()) {
            mAdapter.showNewDataSet(invoicesList);
        }
    }

    @DebugLog
    @Override
    public void addMorePurchasesToTheList(List<Invoice> invoicesList) {
        if (isAdded()) {
            mAdapter.showMoreDataSetX(invoicesList);
        }
    }

    @DebugLog
    @Override
    public void refreshList() {
        mPresenter.getInitialData();
    }

    @Override
    public void printPurchase(String textToPrint, String textToShow) {
        Intent intent = new Intent(getActivity(), PrintPreviewActivity.class);
        intent.putExtra(Constants.PRINT_TEXT_FOR_ZPL, textToPrint);
        intent.putExtra(Constants.PRINT_TEXT_FOR_PREVIEW, textToShow);
        startActivity(intent);
    }

    @DebugLog
    @Override
    public void showDeleteConfirmation(final BaseModel model) {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.delete)
                .setMessage(R.string.want_to_delete_purchase)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mPresenter.deletePurchase(model);
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
        return PermissionUtil.isPermissionGranted(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
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
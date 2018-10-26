package com.hecticus.eleta.confirm_purchase;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.hecticus.eleta.R;
import com.hecticus.eleta.base.DescriptionAndValueModel;
import com.hecticus.eleta.base.item.DoubleTextViewListAdapter;
import com.hecticus.eleta.custom_views.CustomDobleTextView;
import com.hecticus.eleta.model_new.callback.AcceptConfirmInterface;
import com.hecticus.eleta.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 14/10/17.
 */

public class ConfirmDialogFragment extends DialogFragment {

    @BindView(R.id.dialog_confirm_recycler_view)
    RecyclerView descriptionsRecyclerView;

    @BindView(R.id.dialog_confirm_linear_layout_harvester)
    LinearLayout harvesterLinearLayout;

    @BindView(R.id.dialog_confirm_linear_layout_provider)
    LinearLayout providerLinearLayout;


    @BindView(R.id.custom_doble_text_view_name_provider)
    CustomDobleTextView nameProviderDobleTextView;

    @BindView(R.id.custom_doble_text_view_farm)
    CustomDobleTextView farmDobleTextView;

    @BindView(R.id.custom_doble_text_view_lot)
    CustomDobleTextView lotDobleTextView;

    @BindView(R.id.custom_doble_text_view_store)
    CustomDobleTextView storeDobleTextView;

    @BindView(R.id.custom_doble_text_view_freight)
    CustomDobleTextView freightDobleTextView;

    @BindView(R.id.custom_doble_text_view_type)
    CustomDobleTextView typeDobleTextView;

    @BindView(R.id.custom_doble_text_view_amount)
    CustomDobleTextView amountDobleTextView;

    @BindView(R.id.custom_doble_text_view_price)
    CustomDobleTextView priceDobleTextView;

    @BindView(R.id.custom_doble_text_view_dispatch_by)
    CustomDobleTextView dispatchDobleTextView;

    @BindView(R.id.custom_doble_text_view_observation)
    CustomDobleTextView observationDobleTextView;

    private AcceptConfirmInterface mCallback;
    private DoubleTextViewListAdapter mAdapter;

    private int providerType = Constants.TYPE_HARVESTER;

    private String provider = null;
    private String farm = null;
    private String lot = null;
    private String store = null;
    private boolean freight = false;
    private String type = null;
    private String amount = null;
    private String price = null;
    private String dispatch_by = null;
    private String observation = null;
    private List<DescriptionAndValueModel> descriptionAndValueModelList = null;

    @DebugLog
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_confirm, null);
        builder.setView(view);
        ButterKnife.bind(this, view);

        providerType = getArguments().getInt("providerType", Constants.TYPE_HARVESTER);
        if (providerType == Constants.TYPE_HARVESTER) {
            builder.setMessage("Confirmar cosecha");
        }else {
            builder.setMessage("Confirmar compra");
        }

        provider = getArguments().getString("provider", "");
        farm = getArguments().getString("farm", "");
        lot = getArguments().getString("lot", "");
        store = getArguments().getString("store", "");
        freight = getArguments().getBoolean("freight", false);
        type = getArguments().getString("type", "");
        amount = getArguments().getString("amount", "");
        price = getArguments().getString("price", "");
        dispatch_by = getArguments().getString("dispatch_by", "");
        observation = getArguments().getString("observation", "");

        if (getArguments().getSerializable("descriptions") != null) {
            descriptionAndValueModelList = (List<DescriptionAndValueModel>) getArguments().getSerializable("descriptions");
        }

        initViews();
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof Activity) {
                mCallback = (AcceptConfirmInterface) context;
            }
        } catch (ClassCastException e) {
            Log.d("TEST", "Activity doesn't implement the SelectedDoctorInterface");
        }
    }

    public void onResume() {
        super.onResume();
        Rect displayRectangle = new Rect();
        Window window = getDialog().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        //window.setLayout((int) (displayRectangle.width() * 0.9f), (int) (displayRectangle.height() * 0.8f));
        window.setGravity(Gravity.CENTER);
    }

    private void initViews() {
        setUpRecyclerView();
        initString();
    }

    private void setUpRecyclerView() {
        descriptionsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        descriptionsRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new DoubleTextViewListAdapter();
        descriptionsRecyclerView.setAdapter(mAdapter);
    }

    public void initString() {
        if (providerType == Constants.TYPE_HARVESTER) {
            providerLinearLayout.setVisibility(View.GONE);
            nameProviderDobleTextView.initWithdDescriptionAndValue(getString(R.string.harvester),provider);
            farmDobleTextView.initWithdDescriptionAndValue(getString(R.string.farm),farm);
            lotDobleTextView.initWithdDescriptionAndValue(getString(R.string.lot),lot);
            dispatchDobleTextView.setVisibility(View.GONE);
            //load items
        }else{
            harvesterLinearLayout.setVisibility(View.GONE);
            nameProviderDobleTextView.initWithdDescriptionAndValue(getString(R.string.provider),provider);
            storeDobleTextView.initWithdDescriptionAndValue(getString(R.string.gathering),store);
            freightDobleTextView.initWithdDescriptionAndValue(getString(R.string.freight),freight?"Sí":"No");
            typeDobleTextView.initWithdDescriptionAndValue(getString(R.string.type),type);
            amountDobleTextView.initWithdDescriptionAndValue(getString(R.string.amount),amount);
            priceDobleTextView.initWithdDescriptionAndValue(getString(R.string.price),price);
            dispatchDobleTextView.initWithdDescriptionAndValue(getString(R.string.dispatched_by),dispatch_by);

            //load purities
        }

        mAdapter.showNewDataSet(descriptionAndValueModelList);
        observationDobleTextView.initWithdDescriptionAndValue(getString(R.string.observations),observation);
    }

    @DebugLog
    @OnClick(R.id.dialog_save_add_another)
    public void onClickAddAnother() {
        mCallback.onAddAnother();
        getDialog().dismiss();
    }

    @DebugLog
    @OnClick(R.id.dialog_confirm_accept_button)
    public void onClickConfirmButton() {
        mCallback.onAcceptConfirm();
        dismiss();
    }

    @DebugLog
    @OnClick(R.id.dialog_confirm_cancel_button)
    public void onClickDismissDialog() {
        getDialog().dismiss();
    }

}

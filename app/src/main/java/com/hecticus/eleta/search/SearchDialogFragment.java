package com.hecticus.eleta.search;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.hecticus.eleta.R;
import com.hecticus.eleta.base.item.GenericListAdapter;
import com.hecticus.eleta.custom_views.CustomEditText;
import com.hecticus.eleta.model.callback.SelectedProviderInterface;
import com.hecticus.eleta.model.response.providers.Provider;
import com.hecticus.eleta.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
/**
 * Created by Edwin on 2017-09-18.
 */

public class SearchDialogFragment extends DialogFragment implements SearchContract.View{

    @BindView(R.id.dialog_search_recycler_view)
    RecyclerView providersRecyclerView;

    @BindView(R.id.custom_search_bar_custom_text_edit)
    CustomEditText providerEditText;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.relative_layout)
    RelativeLayout mainRelativeLayout;


    private SearchContract.Actions mPresenter;
    private GenericListAdapter mAdapter;
    private SelectedProviderInterface mCallback;

    @DebugLog
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_search, null);
        builder.setView(view);
        builder.setMessage(R.string.app_name);
        ButterKnife.bind(this, view);

        int providerType = getArguments().getInt("providerType", Constants.TYPE_HARVESTER);
        mPresenter = new SearchPresenter(getContext(), this, providerType);
        initViews();
        mPresenter.getInitialData();
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof Activity) {
                mCallback = (SelectedProviderInterface) context;
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
        window.setLayout((int) (displayRectangle.width() * 0.9f), (int) (displayRectangle.height() * 0.8f));
        window.setGravity(Gravity.CENTER);

    }

    public void initViews() {
        setUpRecyclerView();
        providerEditText.init();
        initString();
    }

    @DebugLog
    private void setUpRecyclerView() {
        providersRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        providersRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new GenericListAdapter(mPresenter, false, true);
        providersRecyclerView.setAdapter(mAdapter);
    }

    public void initString() {
        if (mPresenter.isHarvest()) {
            providerEditText.setDescription(getString(R.string.dni_or_name));
        }else{
            providerEditText.setDescription(getString(R.string.ruc_or_name));
        }
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
        Snackbar.make(mainRelativeLayout, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();

    }

    @Override
    public void showError(String error) {
        Snackbar.make(mainRelativeLayout, error, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @OnClick(R.id.custom_search_bar_search_button)
    @Override
    public void onClickSearchProvider() {
        mPresenter.searchProvidersByName(providerEditText.getText());
    }

    @OnClick(R.id.custom_search_bar_cancel_button)
    @Override
    public void onClickCancelSearch() {
        providerEditText.setText("");
        mPresenter.refreshProvidersList();
    }

    @OnClick(R.id.cancel_button_dialog)
    @Override
    public void onClickDismissDialog() {
        getDialog().dismiss();
    }

    @Override
    public void selectedProvider(Provider provider) {
        mCallback.onProviderSelected(provider);
        dismiss();
    }

    @Override
    public void updateProvidersList(List<Provider> providersList) {
        mAdapter.showNewDataSet(providersList);
    }
}

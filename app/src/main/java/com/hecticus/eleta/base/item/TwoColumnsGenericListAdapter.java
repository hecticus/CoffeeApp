package com.hecticus.eleta.base.item;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseDetailListContract;
import com.hecticus.eleta.base.BaseListContract;
import com.hecticus.eleta.base.BaseDetailModel;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 18/9/17.
 */

public class TwoColumnsGenericListAdapter extends RecyclerView.Adapter<TwoColumnsGenericItemViewHolder> {

    private List<? extends BaseDetailModel> list;
    private BaseDetailListContract.Actions mPresenter;

    private boolean showActions = true;

    @DebugLog
    public TwoColumnsGenericListAdapter(BaseDetailListContract.Actions mPresenterParam, boolean showActions) {
        list = new ArrayList<>();
        mPresenter = mPresenterParam;
        this.showActions = showActions;
    }

    @Override
    public TwoColumnsGenericItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_generic_list_w_two_columns, parent, false);
        return new TwoColumnsGenericItemViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final TwoColumnsGenericItemViewHolder genericItemViewHolder, final int position) {

        genericItemViewHolder.getHeaderTextView().setText(list.get(position).getReadableHeader());
        genericItemViewHolder.getFirstColumnTextView().setText(list.get(position).getReadableFirstInfo());
        genericItemViewHolder.getSecondColumnTextView().setText(list.get(position).getReadableSecondInfo());

        if (position % 2 == 0){
            genericItemViewHolder.getItemWholeLinearLayout().setBackgroundResource(R.color.colorBackgroundPairItem);
        }else{
            genericItemViewHolder.getItemWholeLinearLayout().setBackgroundResource(R.color.colorBackgroundOddItem);
        }

        if (showActions) {
            genericItemViewHolder.getButtonsLinearLayout().setVisibility(View.VISIBLE);

            genericItemViewHolder.getEditImageButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.onClickEditButton(list.get(position));
                }
            });

            genericItemViewHolder.getDeleteImageButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.onClickDeleteButton(list.get(position));
                }
            });

            genericItemViewHolder.getItemWholeLinearLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.onClickItem(list.get(position));
                }
            });
        }else {
            genericItemViewHolder.getButtonsLinearLayout().setVisibility(View.GONE);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @DebugLog
    public void showNewDataSet(List<? extends BaseDetailModel> listParam) {
        list = listParam;
        notifyDataSetChanged();
    }

    @DebugLog
    public void showMoreDataSet(List<? extends BaseDetailModel> listParam) {
        List aux = new ArrayList<>();
        aux.addAll(listParam);
        list.addAll(aux);
        notifyDataSetChanged();
    }

    @DebugLog
    public void clearDataSet() {
        list = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setShowActions(boolean showActions) {
        this.showActions = showActions;
        notifyDataSetChanged();
    }
}
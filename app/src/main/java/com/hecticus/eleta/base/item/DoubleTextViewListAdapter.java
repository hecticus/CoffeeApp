package com.hecticus.eleta.base.item;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hecticus.eleta.R;
import com.hecticus.eleta.base.DescriptionAndValueModel;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 14/10/17.
 */

public class DoubleTextViewListAdapter extends RecyclerView.Adapter<DobleTextViewItemViewHolder> {

    private List<DescriptionAndValueModel> list;

    private boolean showActions = true;

    @DebugLog
    public DoubleTextViewListAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public DobleTextViewItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doble_text_view, parent, false);
        return new DobleTextViewItemViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final DobleTextViewItemViewHolder genericItemViewHolder, final int position) {

        genericItemViewHolder.getDescriptionTextView().setText(list.get(position).getDescription());
        genericItemViewHolder.getValueTextView().setText(list.get(position).getValue());
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
    public void showNewDataSet(List<DescriptionAndValueModel> listParam) {
        list = listParam;
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

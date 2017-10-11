package com.hecticus.eleta.base.item;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hecticus.eleta.R;
import com.hecticus.eleta.base.BaseEditableModel;
import com.hecticus.eleta.model.response.item.ItemType;
import com.hecticus.eleta.model.response.purity.Purity;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 25/9/17.
 */

public class EditListAdapter extends RecyclerView.Adapter<EditItemViewHolder> {

    private List<? extends BaseEditableModel> list;
    private boolean canEdit = false;
    private boolean cellPairWithBg = false;

    @DebugLog
    public EditListAdapter(boolean canEditParam, boolean cellPairWithBgParam) {
        list = new ArrayList<>();
        canEdit = canEditParam;
        cellPairWithBg = cellPairWithBgParam;
    }

    @Override
    public EditItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_list, parent, false);
        return new EditItemViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final EditItemViewHolder genericItemViewHolder, final int position) {

        genericItemViewHolder.getDescriptionTextView().setText(list.get(position).getReadableDescription());
        genericItemViewHolder.getValueEditText().setText(list.get(position).getInputValue());

        if (cellPairWithBg) {
            if (position % 2 == 0) {
                genericItemViewHolder.getItemWholeLinearLayout().setBackgroundResource(R.color.colorBackgroundPairItem);
            } else {
                genericItemViewHolder.getItemWholeLinearLayout().setBackgroundResource(R.color.colorBackgroundOddItem);
            }
        } else {
            if (position % 2 == 0) {
                genericItemViewHolder.getItemWholeLinearLayout().setBackgroundResource(R.color.colorBackgroundOddItem);
            } else {
                genericItemViewHolder.getItemWholeLinearLayout().setBackgroundResource(R.color.colorBackgroundPairItem);
            }
        }

        genericItemViewHolder.getValueEditText().setEnabled(canEdit);

        if (canEdit) {
            genericItemViewHolder.getValueEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    list.get(position).setInputValue(charSequence+"");
                }

                @Override
                public void afterTextChanged(Editable editable) {}
            });
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
    public void showNewDataSet(List<? extends BaseEditableModel> listParam) {
        list = listParam;
        notifyDataSetChanged();
    }

    @DebugLog
    public List<? extends BaseEditableModel> getValues() {
        return list;
    }

    public List<ItemType> getItemsValues() {
        return (List<ItemType>)(Object)list;
    }

    public List<Purity> getPuritiesValues() {
        return (List<Purity>)(Object)list;
    }

}

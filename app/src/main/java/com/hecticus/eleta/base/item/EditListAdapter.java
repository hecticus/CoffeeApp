package com.hecticus.eleta.base.item;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
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
    private boolean isHarvest = false;

    @DebugLog
    public EditListAdapter(boolean canEditParam, boolean cellPairWithBgParam, boolean isHarvestParam) {
        list = new ArrayList<>();
        canEdit = canEditParam;
        isHarvest = isHarvestParam;
        cellPairWithBg = cellPairWithBgParam;
    }

    @Override
    public EditItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_list, parent, false);
        return new EditItemViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final EditItemViewHolder genericItemViewHolder, final int position) {
        if(canEdit && isHarvest) {
            if (!list.get(position).getInputValue().equals("")) {
                genericItemViewHolder.getDescriptionTextView().setText(list.get(position).getReadableDescription());
                genericItemViewHolder.getValueEditText().setText(list.get(position).getInputValue());
            } else {
                genericItemViewHolder.getDescriptionTextView().setText(list.get(position).getReadableDescription());
                genericItemViewHolder.getValueEditText().setText(list.get(position).getInputValue());
            }
        } else {
            genericItemViewHolder.getDescriptionTextView().setText(list.get(position).getReadableDescription());
            genericItemViewHolder.getValueEditText().setText(list.get(position).getInputValue());
        }
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
            Log.d("DEBUG1110000", new Gson().toJson(list));
            genericItemViewHolder.getValueEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Log.d("DEBUG1111111", new Gson().toJson(list));
                    list.get(position).setInputValue(charSequence + "");
                    Log.d("DEBUG11112222", new Gson().toJson(list));
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
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
    public void showNewDataSet(List<? extends BaseEditableModel> listParam, Boolean control) {
        list = listParam;
        if(control){
            for(int i = 0; i<list.size(); i++){
                list.get(i).setInputValue("");
            }
        }
        notifyDataSetChanged();
    }

    /*@DebugLog
    public void showAddAnother(List<? extends BaseEditableModel> listParam) {
        list = new ArrayList<>(listParam);
        for(int i = 0; i<list.size(); i++){
            list.get(i).setInputValue("");
        }
        notifyDataSetChanged();
    }*/

    @DebugLog
    public List<? extends BaseEditableModel> getValues() {
        return list;
    }

    public List<ItemType> getItemsValues() {
        return (List<ItemType>) list;
    }

    public List<Purity> getPuritiesValues() {
        return (List<Purity>) list;
    }

}

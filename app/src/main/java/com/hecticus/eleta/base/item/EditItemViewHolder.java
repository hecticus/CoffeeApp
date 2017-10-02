package com.hecticus.eleta.base.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hecticus.eleta.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by roselyn545 on 25/9/17.
 */

public class EditItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_edit_description_text_view)
    TextView descriptionTextView;

    @BindView(R.id.item_edit_value_edit_text)
    EditText valueEditText;

    @BindView(R.id.item_whole_linearlayout)
    LinearLayout itemWholeLinearLayout;

    public EditItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getDescriptionTextView() {
        return descriptionTextView;
    }

    public void setDescriptionTextView(TextView descriptionTextView) {
        this.descriptionTextView = descriptionTextView;
    }

    public EditText getValueEditText() {
        return valueEditText;
    }

    public void setValueEditText(EditText valueEditText) {
        this.valueEditText = valueEditText;
    }

    public LinearLayout getItemWholeLinearLayout() {
        return itemWholeLinearLayout;
    }

    public void setItemWholeLinearLayout(LinearLayout itemWholeLinearLayout) {
        this.itemWholeLinearLayout = itemWholeLinearLayout;
    }
}

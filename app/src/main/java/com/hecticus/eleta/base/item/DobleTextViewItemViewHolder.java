package com.hecticus.eleta.base.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hecticus.eleta.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by roselyn545 on 14/10/17.
 */

public class DobleTextViewItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_doble_text_view_description)
    TextView descriptionTextView;

    @BindView(R.id.item_doble_text_view_value)
    TextView valueTextView;

    public DobleTextViewItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getDescriptionTextView() {
        return descriptionTextView;
    }

    public void setDescriptionTextView(TextView descriptionTextView) {
        this.descriptionTextView = descriptionTextView;
    }

    public TextView getValueTextView() {
        return valueTextView;
    }

    public void setValueTextView(TextView valueTextView) {
        this.valueTextView = valueTextView;
    }
}

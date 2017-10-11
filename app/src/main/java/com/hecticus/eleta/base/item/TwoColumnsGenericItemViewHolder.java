package com.hecticus.eleta.base.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hecticus.eleta.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by roselyn545 on 18/9/17.
 */

public class TwoColumnsGenericItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_list_header_text_view)
    TextView headerTextView;

    @BindView(R.id.item_list_first_column_text_view)
    TextView firstColumnTextView;

    @BindView(R.id.item_list_second_column_text_view)
    TextView secondColumnTextView;

    @BindView(R.id.item_list_edit_image_button)
    ImageButton editImageButton;

    @BindView(R.id.item_list_delete_image_button)
    ImageButton deleteImageButton;

    @BindView(R.id.item_list_buttons_container_view)
    LinearLayout buttonsLinearLayout;

    @BindView(R.id.item_whole_linearlayout)
    LinearLayout itemWholeLinearLayout;

    public TwoColumnsGenericItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getHeaderTextView() {
        return headerTextView;
    }

    public void setHeaderTextView(TextView headerTextView) {
        this.headerTextView = headerTextView;
    }

    public TextView getFirstColumnTextView() {
        return firstColumnTextView;
    }

    public void setFirstColumnTextView(TextView firstColumnTextView) {
        this.firstColumnTextView = firstColumnTextView;
    }

    public TextView getSecondColumnTextView() {
        return secondColumnTextView;
    }

    public void setSecondColumnTextView(TextView secondColumnTextView) {
        this.secondColumnTextView = secondColumnTextView;
    }

    public ImageButton getEditImageButton() {
        return editImageButton;
    }

    public void setEditImageButton(ImageButton editImageButton) {
        this.editImageButton = editImageButton;
    }

    public ImageButton getDeleteImageButton() {
        return deleteImageButton;
    }

    public void setDeleteImageButton(ImageButton deleteImageButton) {
        this.deleteImageButton = deleteImageButton;
    }

    public LinearLayout getButtonsLinearLayout() {
        return buttonsLinearLayout;
    }

    public void setButtonsLinearLayout(LinearLayout buttonsLinearLayout) {
        this.buttonsLinearLayout = buttonsLinearLayout;
    }

    public LinearLayout getItemWholeLinearLayout() {
        return itemWholeLinearLayout;
    }

    public void setItemWholeLinearLayout(LinearLayout itemWholeLinearLayout) {
        this.itemWholeLinearLayout = itemWholeLinearLayout;
    }
}

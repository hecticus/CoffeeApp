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
 * Created by roselyn545 on 15/9/17.
 */

public class GenericItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_list_description_text_view)
    TextView descriptionTextView;

    @BindView(R.id.item_list_print_image_button)
    ImageButton printImageButton;

    @BindView(R.id.item_list_edit_image_button)
    ImageButton editImageButton;

    @BindView(R.id.item_list_delete_image_button)
    ImageButton deleteImageButton;

    @BindView(R.id.item_list_buttons_container_view)
    LinearLayout containerButtonsView;

    @BindView(R.id.item_whole_linearlayout)
    LinearLayout itemWholeLinearLayout;

    public GenericItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getDescriptionTextView() {
        return descriptionTextView;
    }

    public void setDescriptionTextView(TextView descriptionTextView) {
        this.descriptionTextView = descriptionTextView;
    }

    public ImageButton getPrintImageButton() {
        return printImageButton;
    }

    public void setPrintImageButton(ImageButton printImageButton) {
        this.printImageButton = printImageButton;
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

    public LinearLayout getContainerButtonsView() {
        return containerButtonsView;
    }

    public void setContainerButtonsView(LinearLayout containerButtonsView) {
        this.containerButtonsView = containerButtonsView;
    }

    public LinearLayout getItemWholeLinearLayout() {
        return itemWholeLinearLayout;
    }

    public void setItemWholeLinearLayout(LinearLayout itemWholeDoctorLinearLayout) {
        this.itemWholeLinearLayout = itemWholeDoctorLinearLayout;
    }
}

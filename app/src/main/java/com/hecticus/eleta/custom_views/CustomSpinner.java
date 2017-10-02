package com.hecticus.eleta.custom_views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hecticus.eleta.R;
import com.hecticus.eleta.model.ItemSpinnerInterface;

import java.util.List;

/**
 * Created by roselyn545 on 25/9/17.
 */

public class CustomSpinner extends LinearLayout {

    public enum Type {
        PASSWORD, TEXT, EMAIL, DATE, DNI, NUMERIC, PHONE, MULTILINE
    }

    TextView descriptionTextView;
    Spinner spinner;

    public CustomSpinner(Context context) {
        super(context);
    }

    public CustomSpinner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSpinner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(){
        descriptionTextView = (TextView) findViewById(R.id.custom_spinner_description_text_view);
        spinner = (Spinner) findViewById(R.id.custom_spinner_spinner);
    }

    public void initWithDescription(String description){
        init();
        setDescription(description);
    }

    public void setDescription(String text){
        descriptionTextView.setText(text);
    }

    public void setBackground(){
        setBackgroundResource(R.color.colorBackgroundPairItem);
    }

    public void setValues(List<? extends ItemSpinnerInterface> items){
        ArrayAdapter<ItemSpinnerInterface> adapter = new ArrayAdapter<ItemSpinnerInterface>(getContext(), android.R.layout.simple_spinner_item);
        adapter.addAll(items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void setValuesAndSelect(List<? extends ItemSpinnerInterface> items, int selectedId){
        setValues(items);

        if (selectedId == -1){
            return;
        }

        for (ItemSpinnerInterface item:items) {
            if (item.getItemId() == selectedId){
                spinner.setSelection(items.indexOf(item));
                return;
            }
        }
    }

    public Spinner get(){
        return spinner;
    }

    public ItemSpinnerInterface getSelectedItem(){
        return (ItemSpinnerInterface)spinner.getSelectedItem();
    }
}

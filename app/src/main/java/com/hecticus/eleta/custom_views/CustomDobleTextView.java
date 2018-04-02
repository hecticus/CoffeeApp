package com.hecticus.eleta.custom_views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hecticus.eleta.R;

/**
 * Created by roselyn545 on 14/10/17.
 */

public class CustomDobleTextView extends LinearLayout {

    TextView descriptionTextView;
    TextView valueTextView;

    public CustomDobleTextView(Context context) {
        super(context);
    }

    public CustomDobleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDobleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(){
        descriptionTextView = (TextView) findViewById(R.id.custom_doble_text_view_description);
        valueTextView = (TextView) findViewById(R.id.custom_doble_text_view_value);
    }

    public void initWithdDescriptionAndValue(String description, String value){
        init();
        setDescription(description);
        setValue(value);
    }

    public void initWithDescription(String description){
        init();
        setDescription(description);
    }

    public void setTextColor(int resId){
        descriptionTextView.setTextColor(getResources().getColor(resId));
        valueTextView.setTextColor(getResources().getColor(resId));
    }

    public void setDescription(String text){
        descriptionTextView.setText(text);
    }

    public void setValue(String text){
        valueTextView.setText(text);
    }

}

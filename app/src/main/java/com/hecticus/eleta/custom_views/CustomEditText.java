package com.hecticus.eleta.custom_views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hecticus.eleta.R;

/**
 * Created by roselyn545 on 15/9/17.
 */

public class CustomEditText extends LinearLayout {

    public enum Type {
        PASSWORD, TEXT, EMAIL, DATE, DNI, NUMERIC, PHONE, MULTILINE, DECIMAL
    }

    TextView descriptionTextView;
    EditText editText;

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(){
        descriptionTextView = (TextView) findViewById(R.id.custom_edit_text_description_text_view);
        editText = (EditText) findViewById(R.id.custom_edit_text_internal);
    }

    public void initWithType(Type type){
        init();

        switch (type) {
            case PASSWORD:
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case PHONE:
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case NUMERIC:
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case EMAIL:
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
                break;
            case DATE:
                editText.setInputType(InputType.TYPE_CLASS_DATETIME);
                break;
            case DNI:
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case DECIMAL:
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            case MULTILINE:
                editText.setSingleLine(false);
                editText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                editText.setMaxLines(3);
                break;
            default:
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        }
    }

    public void initWithTypeAndDescription(Type type, String description){
        initWithType(type);
        setDescription(description);
    }

    public void initWithDescription(String description){
        init();
        setDescription(description);
    }

    public void setBackground(){
        setBackgroundResource(R.color.colorBackgroundPairItem);
    }

    public void setTextColor(int resId){
        descriptionTextView.setTextColor(getResources().getColor(resId));
        editText.setTextColor(getResources().getColor(resId));

    }

    public void setText(String text){
        editText.setText(text);
    }

    public void setHint(String text){
        editText.setHint(text);
    }

    public void setDescription(String text){
        descriptionTextView.setText(text);
    }

    public String getText(){
        return editText.getText().toString();
    }

    public void hideDescription(){
        descriptionTextView.setVisibility(GONE);
    }

    public void setEnable(boolean enabled){
        editText.setEnabled(enabled);
    }

    public EditText getEditText(){
        return editText;
    }

    public void setSingleLine(){
        editText.setSingleLine();
    }
}

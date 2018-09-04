package com.hecticus.eleta.custom_views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
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
        PASSWORD, TEXT, EMAIL, DATE, DNI, NUMERIC, PHONE, MULTILINE, DECIMAL, RUC
    }

    TextView descriptionTextView;
    EditText editText;
    private int textlength = 0;
    private boolean listenForChanges = true;

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

        editText.setSingleLine(true);
        editText.setMaxLines(1);
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
            case RUC:
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                initEditTextRuc();
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
        listenForChanges = false;
        editText.setText(text);
        listenForChanges = true;
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

    private void initEditTextRuc(){
        editText.setHint("XXXXX-YYY-ZZZZ DV NN");
        editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});
        editText.addTextChangedListener(new TextWatcher()
        {

            public void afterTextChanged(Editable s)
            {
                String text = editText.getText().toString();
                int aux = textlength;
                textlength = editText.getText().length();

                if (!listenForChanges)
                    return;

                if (aux<=textlength) {
                    if (text.endsWith(" ") || text.endsWith("-"))
                        return;

                    if (textlength == 5 || textlength == 9/*7*/) {
                        editText.setText(new StringBuilder(text).append("-").toString());
                        editText.setSelection(editText.getText().length());
                    } else if (textlength == 6 || textlength == 10/*8*/) {
                        editText.setText(new StringBuilder(text).insert(textlength-1,"-").toString());
                        editText.setSelection(editText.getText().length());
                    } else if (textlength == 14/*12*/) {
                        editText.setText(new StringBuilder(text).append(" DV ").toString());
                        editText.setSelection(editText.getText().length());
                    } else if (textlength == 15/*13*/) {
                        editText.setText(new StringBuilder(text).insert(textlength-1," DV ").toString());
                        editText.setSelection(editText.getText().length());
                    }
                } else {
                    if (text.endsWith("-")) {
                        listenForChanges = false;
                        editText.setText(new StringBuilder(text).deleteCharAt(textlength-1).toString());
                        listenForChanges = true;
                        editText.setSelection(editText.getText().length());
                    } else if (text.endsWith(" ")) {
                        listenForChanges = false;
                        editText.setText(new StringBuilder(text).delete(textlength-4,textlength).toString());
                        listenForChanges = true;
                        editText.setSelection(editText.getText().length());
                    } else if (text.endsWith("V")) {
                        listenForChanges = false;
                        editText.setText(new StringBuilder(text).delete(textlength-3,textlength).toString());
                        listenForChanges = true;
                        editText.setSelection(editText.getText().length());
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) { }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) { }

        });
    }
}

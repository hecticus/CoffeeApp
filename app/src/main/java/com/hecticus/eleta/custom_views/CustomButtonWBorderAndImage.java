package com.hecticus.eleta.custom_views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hecticus.eleta.R;

/**
 * Created by roselyn545 on 18/9/17.
 */

public class CustomButtonWBorderAndImage extends LinearLayout {

    public enum Type {
        PRINT, CHECK
    }

    TextView descriptionTextView;
    ImageView imageView;

    public CustomButtonWBorderAndImage(Context context) {
        super(context);
    }

    public CustomButtonWBorderAndImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomButtonWBorderAndImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(){
        descriptionTextView = (TextView) findViewById(R.id.custom_send_button_text_view);
        imageView = (ImageView) findViewById(R.id.custom_send_button_image_view);
    }

    public void initWithType(Type type){
        init();

        switch (type) {
            case PRINT:
                imageView.setImageResource(R.mipmap.print);
                break;
            default:
                imageView.setImageResource(R.mipmap.check);
        }
    }

    public void initWithTypeAndText(Type type, String text){
        initWithType(type);
        setText(text);

    }

    public void setText(String text){
        descriptionTextView.setText(text);
    }

    public void setEnable(boolean enabled){
        setEnabled(enabled);
    }

}

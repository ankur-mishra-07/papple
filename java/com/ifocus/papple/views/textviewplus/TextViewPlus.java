package com.ifocus.papple.views.textviewplus;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ifocus.papple.R;

/**
 * Created by Abhishek on 12-01-2016.
 */
public class TextViewPlus extends TextView {

    public TextViewPlus(Context context) {
        super(context);
    }

    public TextViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);

        setCustomFont(context, a);
        setUnderLine(a);

        a.recycle();
    }

    public TextViewPlus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);

        setCustomFont(context, a);
        setUnderLine(a);

        a.recycle();
    }

    private void setCustomFont(Context context, TypedArray a){
        String customFont = a.getString(R.styleable.TextViewPlus_customFont);

        if(customFont != null && !customFont.equals(""))
            setCustomFont(context, customFont);

    }

    public boolean setCustomFont(Context context, String asset){

        Typeface typeface = null;

        try {
            typeface = Typefaces.get(context, asset);
        }catch (Exception e){
            e.printStackTrace();
        }

        setTypeface(typeface);

        return true;
    }

    private void setUnderLine(TypedArray a){
        boolean isUnderlined = a.getBoolean(R.styleable.TextViewPlus_underlined, false);
        if(isUnderlined){
            setPaintFlags(getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
    }
}

package com.ifocus.papple.views.gradientview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.ifocus.papple.R;


/**
 * Created by Abhishek on 12-01-2016.
 */
public class GradientView extends View {
    private final int defStartColor = Color.DKGRAY;
    private final int defEndColor = Color.BLACK;
    private final float defGradientRadius = 0f;
    private final float defCenterX = 0.5f;
    private final float defCenterY = 0.5f;
    private final float defGradRadScreenPercentage = 0.5f;

    private int startColor;
    private int endColor;
    private float gradientRadius;
    private float centerX;
    private float centerY;
    private float gradRadScreenPercentage;

    private Paint paint;

    public GradientView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GradientView);

        try{
            startColor = a.getColor(R.styleable.GradientView_startColor, defStartColor);
            endColor = a.getColor(R.styleable.GradientView_endColor, defEndColor);
            gradientRadius = a.getFloat(R.styleable.GradientView_gradientRadius, defGradientRadius);
            centerX = a.getFraction(R.styleable.GradientView_centerX, 1, 1, defCenterX);
            centerY = a.getFraction(R.styleable.GradientView_centerY, 1, 1, defCenterY);
            gradRadScreenPercentage = a.getFraction(R.styleable.GradientView_gradRadScreenPercentage, 1, 1, defGradRadScreenPercentage);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            a.recycle();
        }

        init();
    }

    private void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        float maxSize = Math.max(getMeasuredWidth(), getMeasuredHeight()) * gradRadScreenPercentage;
        if(gradientRadius == 0f){
            gradientRadius = maxSize;
        }
        RadialGradient rGradient = new RadialGradient(
                getMeasuredWidth() * centerX,
                getMeasuredHeight() * centerY,
                gradientRadius,
                startColor,
                endColor,
                Shader.TileMode.MIRROR
        );
        paint.setDither(true);
        paint.setShader(rGradient);

        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);

    }


    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
        invalidate();
    }

    public int getStartColor() {
        return startColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
        invalidate();
    }

    public int getEndColor() {
        return endColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
        invalidate();
    }

    public float getGradientRadius() {
        return gradientRadius;
    }

    public void setGradientRadius(float gradientRadius) {
        this.gradientRadius = gradientRadius;
        invalidate();
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
        invalidate();
    }

    public float getGradRadScreenPercentage() {
        return gradRadScreenPercentage;
    }

    public void setGradRadScreenPercentage(float gradRadScreenPercentage) {
        this.gradRadScreenPercentage = gradRadScreenPercentage;
        invalidate();
    }
}

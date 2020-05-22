package com.example.lyfuelgas.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DrawView extends View {


    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getViewSize(100, widthMeasureSpec);
        int height = getViewSize(100, heightMeasureSpec);
        if (width < height) {
            height = width;
        } else {
            width = height;
        }
        setMeasuredDimension(width, height);
    }

    private int getViewSize(int defaultSize, int measureSpec) {
        int resultSize = defaultSize;
        int size = MeasureSpec.getSize(measureSpec);
        int mode = MeasureSpec.getMode(measureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                resultSize = size;
                break;
            case MeasureSpec.AT_MOST:
                resultSize = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                resultSize = defaultSize;
                break;

        }
        return resultSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       // canvas.drawRect(new Rect(-100, -100, 0, 0), new Paint());//缩放了
        //半径
        int r = getMeasuredHeight() / 2;
        canvas.drawCircle(r, r, r,new Paint());
    }
}

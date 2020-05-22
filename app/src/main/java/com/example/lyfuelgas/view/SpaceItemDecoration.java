package com.example.lyfuelgas.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by QiuYan on 2018/8/27.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration{
    private int mSpace;

    public SpaceItemDecoration(Context context, int dpValue) {
        mSpace = dp2px(context,dpValue);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = mSpace;
    }

    /**
     * dp to px转换
     * @param context
     * @param dpValue
     * @return
     */
    private int dp2px(Context context, int dpValue){
        int pxValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
        return pxValue;
    }
}

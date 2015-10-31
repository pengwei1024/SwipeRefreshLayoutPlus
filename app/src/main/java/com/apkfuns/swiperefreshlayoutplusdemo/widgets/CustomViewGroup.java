package com.apkfuns.swiperefreshlayoutplusdemo.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by pengwei08 on 15/10/28.
 */
public class CustomViewGroup extends ViewGroup {

    public CustomViewGroup(Context context) {
        this(context, null);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("tag", String.format("%d, %d", widthMeasureSpec, heightMeasureSpec));
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureWidth = 0;
        if (measureWidthMode == MeasureSpec.AT_MOST || measureWidthMode == MeasureSpec.EXACTLY) {
            measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureHeight = 0;
        if (measureHeightMode == MeasureSpec.AT_MOST || measureHeightMode == MeasureSpec.EXACTLY) {
            measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        Log.d("tag2", String.format("%d, %d", measureWidth, measureHeight));
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int totalHeight = 0;
        for (int i = 0; i < getChildCount(); ++i) {
            View child = getChildAt(i);
            child.layout(l, totalHeight, child.getMeasuredWidth(), totalHeight + child.getMeasuredHeight());
            totalHeight += child.getMeasuredHeight();
        }
    }
}

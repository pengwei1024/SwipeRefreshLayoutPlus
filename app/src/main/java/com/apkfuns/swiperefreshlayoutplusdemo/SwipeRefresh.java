package com.apkfuns.swiperefreshlayoutplusdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * Created by pengwei08 on 15/10/28.
 */
public class SwipeRefresh extends ViewGroup {

    // 触发移动事件的最小距离
    private int mTouchSlop;
    private View mTarget;

    public SwipeRefresh(Context context) {
        this(context, null);
    }

    public SwipeRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    /**
     * 确认子元素
     */
    private void ensureTarget() {
        if (mTarget == null) {
            for (int i = 0; i < getChildCount(); ++i) {
                View child = getChildAt(i);
                mTarget = child;
                break;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

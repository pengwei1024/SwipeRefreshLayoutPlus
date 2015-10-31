package com.apkfuns.swiperefreshlayoutplusdemo;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
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
            if (mTarget == null) {
                throw new IllegalArgumentException("can't find Scrollable Child within SwipeRefreshLayoutPlus!");
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ensureTarget();
        mTarget.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop()
                - getPaddingBottom(), MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mTarget.layout(l, t, r, b);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureTarget();
        int action = MotionEventCompat.getActionMasked(ev);
        return super.onInterceptTouchEvent(ev);
    }
}

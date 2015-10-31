package com.apkfuns.swiperefreshlayoutplusdemo;

import android.content.Context;
import android.graphics.Color;
import android.hardware.display.DisplayManager;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.apkfuns.swiperefreshlayoutplusdemo.widgets.CircleImageView;
import com.apkfuns.swiperefreshlayoutplusdemo.widgets.MaterialProgressDrawable;

/**
 * Created by pengwei08 on 15/10/28.
 */
public class SwipeRefresh extends ViewGroup {

    // 触发移动事件的最小距离
    private int mTouchSlop;
    private View mTarget;

    private CircleImageView mCircleView;
    private MaterialProgressDrawable mProgress;
    private RelativeLayout headLayout;
    private int headWidth, headHeight;

    public SwipeRefresh(Context context) {
        this(context, null);
    }

    float density = 1f;

    public SwipeRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        createProgressView();
        headLayout = new RelativeLayout(getContext());
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        headWidth = display.getWidth();
        headHeight = (int) (displayMetrics.density * 50);
        headLayout.setBackgroundColor(Color.YELLOW);
        addView(headLayout);
    }

    // Default background for the progress spinner
    private static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
    private static final int CIRCLE_DIAMETER = 40;

    private void createProgressView() {
        mCircleView = new CircleImageView(getContext(), CIRCLE_BG_LIGHT, CIRCLE_DIAMETER / 2);
        mProgress = new MaterialProgressDrawable(getContext(), this);
//        mProgress.setBackgroundColor(CIRCLE_BG_LIGHT);
        mProgress.setBackgroundColor(Color.RED);
        mCircleView.setImageDrawable(mProgress);
        mCircleView.setVisibility(View.GONE);
        addView(mCircleView);
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

    int mCircleWidth;
    int mCircleHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ensureTarget();
        mTarget.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop()
                - getPaddingBottom(), MeasureSpec.EXACTLY));
        mCircleView.measure(MeasureSpec.makeMeasureSpec(mCircleWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(mCircleHeight, MeasureSpec.EXACTLY));
        headLayout.measure(MeasureSpec.makeMeasureSpec(headWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(headHeight, MeasureSpec.EXACTLY));
    }

    int mCurrentTargetOffsetTop;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        if (getChildCount() == 0) {
            return;
        }
        if (mTarget == null) {
            ensureTarget();
        }
        if (mTarget == null) {
            return;
        }
        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop();
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();
        mTarget.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
        int circleWidth = mCircleView.getMeasuredWidth();
        int circleHeight = mCircleView.getMeasuredHeight();
        Log.d("tag", String.format("%d,%d,%d,%d", width, mCurrentTargetOffsetTop, circleWidth, circleHeight));
        mCircleView.layout((width / 2 - circleWidth / 2), mCurrentTargetOffsetTop,
                (width / 2 + circleWidth / 2), mCurrentTargetOffsetTop + circleHeight);

        headLayout.layout((width / 2 - headWidth / 2), mCurrentTargetOffsetTop,
                (width / 2 + headWidth / 2), mCurrentTargetOffsetTop + headHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureTarget();
        int action = MotionEventCompat.getActionMasked(ev);
        return super.onInterceptTouchEvent(ev);
    }
}

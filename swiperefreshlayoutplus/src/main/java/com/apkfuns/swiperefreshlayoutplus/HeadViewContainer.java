package com.apkfuns.swiperefreshlayoutplus;

import android.content.Context;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

/**
 * Created by ZhengHaibo 莫川.
 * 下拉刷新布局头部的容器
 */
public class HeadViewContainer extends RelativeLayout {

    private Animation.AnimationListener mListener;

    protected HeadViewContainer(Context context) {
        super(context);
    }

    public void setAnimationListener(Animation.AnimationListener listener) {
        mListener = listener;
    }

    @Override
    public void onAnimationStart() {
        super.onAnimationStart();
        if (mListener != null) {
            mListener.onAnimationStart(getAnimation());
        }
    }

    @Override
    public void onAnimationEnd() {
        super.onAnimationEnd();
        if (mListener != null) {
            mListener.onAnimationEnd(getAnimation());
        }
    }
}
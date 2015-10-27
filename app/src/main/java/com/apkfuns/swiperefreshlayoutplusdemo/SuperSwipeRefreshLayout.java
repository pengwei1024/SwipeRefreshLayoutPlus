package com.apkfuns.swiperefreshlayoutplusdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengwei08 on 15/10/25.
 */
public class SuperSwipeRefreshLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener {

    private Context mContext;
    private OnLoadMoreListener mOnLoadMoreListener;
    private int mTouchSlop;
    private ListView mListView;
    private int mYDown;
    private int mLastY;
    private boolean isLoading = false;
    private View mLoadMoreView;
    private View mRefreshView;
    private List<AbsListView.OnScrollListener> onScrollListeners;

    public SuperSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public SuperSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.superSwipeRefreshLayout_attr);
            int loadMoreView = typedArray.getResourceId(R.styleable.superSwipeRefreshLayout_attr_mLoadMoreView,
                    R.layout.view_normal_refresh_footer);
            int refreshView = typedArray.getResourceId(R.styleable.superSwipeRefreshLayout_attr_mRefreshView,
                    R.layout.view_normal_refresh_header);
            mLoadMoreView = LayoutInflater.from(mContext).inflate(loadMoreView, null, false);
            mRefreshView = LayoutInflater.from(mContext).inflate(refreshView, null, false);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mListView == null) {
            getListView();
        }
    }

    /**
     * 获取ListView
     */
    private void getListView() {
        int childCount = getChildCount();
        if (childCount > 0) {
            View childView = getChildAt(0);
            if (childView instanceof ListView) {
                mListView = ((ListView) childView);
                mListView.setOnScrollListener(this);
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (onScrollListeners != null) {
            for (AbsListView.OnScrollListener listener : onScrollListeners) {
                listener.onScrollStateChanged(view, scrollState);
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (onScrollListeners != null) {
            for (AbsListView.OnScrollListener listener : onScrollListeners) {
                listener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        }
        if (canLoad()) {
            loadData();
        }
    }

    /**
     * 增加滚动监听
     *
     * @param mListener
     */
    public void addOnScrollListener(AbsListView.OnScrollListener mListener) {
        if (onScrollListeners == null) {
            onScrollListeners = new ArrayList<>();
        }
        if (mListener != null) {
            onScrollListeners.add(mListener);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mYDown = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                if (canLoad()) {
                    loadData();
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 加载数据
     */
    private void loadData() {
        if (mOnLoadMoreListener != null) {
            setLoading(true);
            mOnLoadMoreListener.onLoadMore();
        }
    }

    /**
     * 设置加载状态
     *
     * @param loading
     */
    private void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            mListView.addFooterView(mLoadMoreView);
        } else {
            mListView.removeFooterView(mLoadMoreView);
            mYDown = 0;
            mLastY = 0;
        }
    }

    /**
     * 是否加载
     *
     * @return
     */
    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
    }

    /**
     * 是否到了底部
     *
     * @return
     */
    private boolean isBottom() {
        if (mListView != null && mListView.getAdapter() != null) {
            return mListView.getLastVisiblePosition() == mListView.getAdapter().getCount() - 1;
        }
        return false;
    }

    /**
     * 是否上拉操作
     *
     * @return
     */
    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mOnLoadMoreListener = listener;
    }

    /**
     * 加载更多的回调接口
     */
    public static interface OnLoadMoreListener {
        void onLoadMore();
    }

    /**
     * 清理垃圾
     */
    public void onDestroy() {
        onScrollListeners.clear();
        onScrollListeners = null;
        mOnLoadMoreListener = null;
    }
}

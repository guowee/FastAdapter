package com.missile.fastadapter.refresh.impl;


import android.animation.ValueAnimator;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.missile.fastadapter.refresh.inf.RefreshContent;
import com.missile.fastadapter.refresh.listener.CoordinatorLayoutListener;

public class RefreshContentWrapper implements RefreshContent, CoordinatorLayoutListener, ValueAnimator.AnimatorUpdateListener {

    protected View mContentView;
    protected View mRealContentView;
    protected View mScrollableView;

    protected boolean mEnableRefresh = true;
    protected boolean mEnableLoadMore = true;

    protected int mLastSpinner = 0;


    public RefreshContentWrapper(View view) {
        this.mContentView = mRealContentView = mScrollableView = view;
    }


    @Override
    public View getView() {
        return mContentView;
    }

    @Override
    public View getScrollableView() {
        return mScrollableView;
    }

    @Override
    public void onActionDown(MotionEvent event) {

    }

    @Override
    public boolean canRefresh() {
        return mEnableRefresh;
    }

    @Override
    public boolean canLoadMore() {
        return mEnableLoadMore;
    }

    @Override
    public void onCoordinatorUpdate(boolean enableRefresh, boolean enableLoadMore) {
        mEnableLoadMore = enableLoadMore;
        mEnableRefresh = enableRefresh;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        int value = (int) valueAnimator.getAnimatedValue();
        if (mScrollableView instanceof AbsListView) {
            scrollListBy((AbsListView) mScrollableView, value - mLastSpinner);
        } else {
            mScrollableView.scrollBy(0, value - mLastSpinner);
        }

        mLastSpinner = value;
    }

    private void scrollListBy(AbsListView listView, int y) {
        if (Build.VERSION.SDK_INT >= 19) {
            // Call the framework version directly
            listView.scrollListBy(y);
        } else if (listView instanceof ListView) {
            // provide backport on earlier versions
            final int firstPosition = listView.getFirstVisiblePosition();
            if (firstPosition == ListView.INVALID_POSITION) {
                return;
            }
            //noinspection UnnecessaryLocalVariable
            final ViewGroup listGroup = listView;
            final View firstView = listGroup.getChildAt(0);
            if (firstView == null) {
                return;
            }
            final int newTop = firstView.getTop() - y;
            ((ListView) listView).setSelectionFromTop(firstPosition, newTop);
        } else {
            listView.smoothScrollBy(y, 0);
        }
    }


}

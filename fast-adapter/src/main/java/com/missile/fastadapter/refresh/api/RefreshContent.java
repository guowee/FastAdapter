package com.missile.fastadapter.refresh.api;


import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;

public interface RefreshContent {

    View getView();

    View getScrollableView();

    void onActionDown(MotionEvent e);

    void setUpComponent(RefreshKernel kernel, View fixedHeader, View fixedFooter);

    void setScrollBoundaryDecider(ScrollBoundaryDecider boundary);

    void setEnableLoadMoreWhenContentNotFull(boolean enable);

    void moveSpinner(int spinner, int headerTranslationViewId, int footerTranslationViewId);

    boolean canRefresh();

    boolean canLoadMore();

    ValueAnimator.AnimatorUpdateListener scrollContentWhenFinished(int spinner);

}

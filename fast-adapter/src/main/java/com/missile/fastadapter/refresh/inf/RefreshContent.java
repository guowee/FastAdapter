package com.missile.fastadapter.refresh.inf;


import android.view.MotionEvent;
import android.view.View;

public interface RefreshContent {

    View getView();

    View getScrollableView();

    void onActionDown(MotionEvent event);

    boolean canRefresh();

    boolean canLoadMore();

    void setUpComponent(RefreshKernel kernel, View fixedHeader, View fixedFooter);

    void setScrollBoundaryDecider(ScrollBoundaryDecider boundary);

    void setEnableLoadMoreWhenContentNotFull(boolean enable);

}

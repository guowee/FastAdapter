package com.missile.fastadapter.refresh.inf;


import android.view.View;
import android.view.ViewGroup;

import com.missile.fastadapter.refresh.constant.RefreshState;
import com.missile.fastadapter.refresh.listener.OnLoadMoreListener;
import com.missile.fastadapter.refresh.listener.OnRefreshListener;

public interface RefreshLayout {

    ViewGroup getLayout();

    RefreshState getState();

    RefreshLayout setRefreshHeader(RefreshHeader header);

    RefreshLayout setRefreshHeader(RefreshHeader header, int width, int height);

    RefreshLayout setRefreshContent(View contentView);

    RefreshLayout setRefreshContent(View contentView, int width, int height);

    RefreshLayout setRefreshFooter(RefreshFooter footer);

    RefreshLayout setRefreshFooter(RefreshFooter footor, int width, int height);

    RefreshLayout setFooterHeight(float dp);

    RefreshLayout setHeaderHeight(float dp);

    RefreshLayout setOnRefreshListener(OnRefreshListener listener);

    RefreshLayout setOnLoadMoreListener(OnLoadMoreListener listener);

    /**
     * 设置滚动边界判断器
     *
     * @return
     */
    RefreshLayout setScrollBoundaryDecider(ScrollBoundaryDecider boundaryDecider);

}

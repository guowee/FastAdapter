package com.missile.fastadapter.refresh.api;

import android.view.View;

/**
 * 滚动边界
 */
public interface ScrollBoundaryDecider {
    /**
     * 根据内容视图状态判断是否可以开始下拉刷新
     *
     * @param contentView
     * @return
     */

    boolean canRefresh(View contentView);

    /**
     * 根据内容视图状态判断是否可以开始上拉加载
     *
     * @param contentView
     * @return
     */
    boolean canLoadMore(View contentView);
}

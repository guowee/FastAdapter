package com.missile.fastadapter.refresh.api;


public interface RefreshFooter extends RefreshInternal {
    /**
     * 数据全部加载完，不再触发加载功能
     *
     * @param noMoreData
     * @return
     */
    boolean setNoMoreData(boolean noMoreData);
}

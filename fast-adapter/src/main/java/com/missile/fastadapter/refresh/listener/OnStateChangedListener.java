package com.missile.fastadapter.refresh.listener;


import com.missile.fastadapter.refresh.api.RefreshLayout;
import com.missile.fastadapter.refresh.constant.RefreshState;

public interface OnStateChangedListener {
    void onStateChanged(RefreshLayout layout, RefreshState oldState, RefreshState newState);
}

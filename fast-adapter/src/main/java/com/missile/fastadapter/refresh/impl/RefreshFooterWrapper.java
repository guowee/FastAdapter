package com.missile.fastadapter.refresh.impl;


import android.view.View;

import com.missile.fastadapter.refresh.constant.RefreshState;
import com.missile.fastadapter.refresh.constant.SpinnerStyle;
import com.missile.fastadapter.refresh.api.RefreshFooter;
import com.missile.fastadapter.refresh.api.RefreshLayout;
import com.missile.fastadapter.refresh.internal.InternalAbstract;

public class RefreshFooterWrapper extends InternalAbstract implements RefreshFooter {


    public RefreshFooterWrapper(View wrapper) {
        super(wrapper);
    }

    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        return mWrappedInternal instanceof RefreshFooter && ((RefreshFooter) mWrappedInternal).setNoMoreData(noMoreData);
    }
}

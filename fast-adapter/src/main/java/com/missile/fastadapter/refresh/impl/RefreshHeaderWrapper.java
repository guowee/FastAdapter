package com.missile.fastadapter.refresh.impl;


import android.view.View;

import com.missile.fastadapter.refresh.constant.RefreshState;
import com.missile.fastadapter.refresh.constant.SpinnerStyle;
import com.missile.fastadapter.refresh.api.RefreshHeader;
import com.missile.fastadapter.refresh.api.RefreshLayout;
import com.missile.fastadapter.refresh.internal.InternalAbstract;

public class RefreshHeaderWrapper extends InternalAbstract implements RefreshHeader {

    public RefreshHeaderWrapper(View wrapper) {
        super(wrapper);
    }
}

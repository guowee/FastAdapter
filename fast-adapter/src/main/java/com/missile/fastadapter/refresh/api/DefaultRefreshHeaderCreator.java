package com.missile.fastadapter.refresh.api;


import android.content.Context;

public interface DefaultRefreshHeaderCreator {
    RefreshHeader createRefreshHeader(Context context, RefreshLayout layout);
}

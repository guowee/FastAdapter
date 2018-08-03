package com.missile.fastadapter;


import android.support.annotation.Nullable;

import java.util.List;

public abstract class MultipleItemRvAdapter<T, V extends BaseViewHolder> extends BaseQuickAdapter<T, V> {

    public MultipleItemRvAdapter(@Nullable List<T> data) {
        super(data);
    }
}

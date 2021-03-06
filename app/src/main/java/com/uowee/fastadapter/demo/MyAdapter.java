package com.uowee.fastadapter.demo;

import android.support.annotation.Nullable;

import com.missile.fastadapter.BaseQuickAdapter;
import com.missile.fastadapter.BaseViewHolder;
import com.uowee.fastadapter.demo.entity.HomeItem;

import java.util.List;


public class MyAdapter extends BaseQuickAdapter<HomeItem, BaseViewHolder> {

    public MyAdapter(@Nullable List<HomeItem> data) {
        super(data);
    }

    public MyAdapter(int layoutResId) {
        super(layoutResId);
    }

    public MyAdapter(int layoutResId, @Nullable List<HomeItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, HomeItem item) {
        holder.setText(R.id.content, item.getContent());
        holder.setImageResource(R.id.icon, item.getResId());
        holder.addOnClickListener(R.id.icon);

    }
}

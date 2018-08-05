package com.uowee.fastadapter.demo;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.missile.fastadapter.BaseQuickAdapter;
import com.missile.fastadapter.BaseViewHolder;

import java.util.List;


public class PullToRefreshAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {
    public PullToRefreshAdapter(@Nullable List<Status> data) {
        super(data);
    }

    public PullToRefreshAdapter(int layoutResId) {
        super(layoutResId);
    }

    public PullToRefreshAdapter(int layoutResId, @Nullable List<Status> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Status item) {
        holder.setImageResource(R.id.img, R.mipmap.ic_banner_default);
        holder.setText(R.id.tweetName, "Yesterday once more");
        ((TextView) holder.getView(R.id.tweetText)).setText("Come back clearly to me");

    }


}

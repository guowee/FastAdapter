package com.uowee.fastadapter.demo;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.missile.fastadapter.BaseQuickAdapter;
import com.missile.fastadapter.BaseViewHolder;
import com.uowee.fastadapter.demo.entity.Movie;

import java.util.List;


public class PullToRefreshAdapter extends BaseQuickAdapter<Movie, BaseViewHolder> {
    public PullToRefreshAdapter(@Nullable List<Movie> data) {
        super(data);
    }

    public PullToRefreshAdapter(int layoutResId) {
        super(layoutResId);
    }

    public PullToRefreshAdapter(int layoutResId, @Nullable List<Movie> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Movie item) {
        holder.setText(R.id.lmi_title, item.filmName)
                .setText(R.id.lmi_actor, item.actors)
                .setText(R.id.lmi_grade, item.grade)
                .setText(R.id.lmi_describe, item.shortinfo);
        Glide.with(mContext).load(item.picaddr).into((ImageView) holder.getView(R.id.lmi_avatar));
        holder.addOnClickListener(R.id.movie_item);

    }


}

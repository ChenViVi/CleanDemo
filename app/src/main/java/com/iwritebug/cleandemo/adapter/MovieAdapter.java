package com.iwritebug.cleandemo.adapter;

import android.content.Context;

import com.iwritebug.cleandemo.R;
import com.iwritebug.viewmodel.MovieView;

import java.util.List;

public class MovieAdapter extends BaseAdapter<MovieView> {

    public MovieAdapter(Context context, List<MovieView> data) {
        super(context, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, MovieView item, int position) {
        holder.loadImage(R.id.ivImg, item.getPoster());
    }

    @Override
    protected int getItemViewLayoutId(int viewType) {
        return R.layout.item_movie;
    }
}

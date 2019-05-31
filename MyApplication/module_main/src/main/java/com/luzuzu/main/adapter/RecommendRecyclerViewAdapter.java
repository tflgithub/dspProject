package com.luzuzu.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luzuzu.main.R;
import com.luzuzu.main.bean.VideoBean;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

import java.util.List;

/**
 * Created by fula on 2019/5/16.
 */

public class RecommendRecyclerViewAdapter extends RecyclerArrayAdapter<VideoBean> {

    public RecommendRecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        holder.itemView.setTag(position);
    }

    public class VideoViewHolder extends BaseViewHolder<VideoBean> {

        private ImageView thumb;

        VideoViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_video_recommend);
            thumb = getView(R.id.main_video_thumb);
            addOnClickListener(R.id.main_tv_comment_num);
            addOnClickListener(R.id.main_tv_share);
            addOnClickListener(R.id.main_tv_dz_num);
            addOnClickListener(R.id.player_container);
        }

        @Override
        public void setData(final VideoBean data) {
            Glide.with(getContext())
                    .load(data.getThumb()).into(thumb);
            setText(R.id.main_tv_title, data.getTitle());
        }
    }
}

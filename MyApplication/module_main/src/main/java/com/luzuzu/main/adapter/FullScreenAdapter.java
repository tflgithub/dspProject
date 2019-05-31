package com.luzuzu.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.luzuzu.main.R;
import com.luzuzu.main.bean.VideoBean;
import com.luzuzu.main.widget.customerDialog.CommentDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fula on 2019/5/22.
 */

public class FullScreenAdapter extends RecyclerView.Adapter<FullScreenAdapter.VideoViewHolder> {

    private List<VideoBean> videos = new ArrayList<>();
    private Context context;

    public FullScreenAdapter(Context context, List<VideoBean> videos) {
        this.videos = videos;
        this.context = context;
    }

    public void setVideos(List<VideoBean> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_full_screen, parent, false);
        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoBean videoBean = videos.get(position);
//        Glide.with(context)
//                .load(videoBean.getThumb())
//                .into(holder.thumb);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView main_btn_like;
        private TextView main_like_num, main_comment_num;

        public VideoViewHolder(View itemView) {
            super(itemView);
            main_btn_like = itemView.findViewById(R.id.main_btn_like);
            main_like_num = itemView.findViewById(R.id.main_like_num);
            main_comment_num = itemView.findViewById(R.id.main_comment_num);
            main_btn_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (main_btn_like.isSelected()) {
                        main_btn_like.setSelected(false);
                        main_like_num.setText(Integer.valueOf(main_like_num.getText().toString()) - 1 + "");
                    } else {
                        main_btn_like.setSelected(true);
                        main_like_num.setText(Integer.valueOf(main_like_num.getText().toString()) + 1 + "");
                    }
                }
            });
            main_comment_num.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new CommentDialog(context, R.style.custom_dialog).show();

                }
            });
        }
    }
}

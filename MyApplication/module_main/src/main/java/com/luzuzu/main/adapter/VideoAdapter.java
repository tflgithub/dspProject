package com.luzuzu.main.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.luzuzu.library.utils.image.ImageUtils;
import com.luzuzu.main.R;
import com.luzuzu.main.bean.VideoBean;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

/**
 * Created by fula on 2019/5/24.
 */

public class VideoAdapter extends RecyclerArrayAdapter<VideoBean> {

    public VideoAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }


    class ViewHolder extends BaseViewHolder<VideoBean> {
        private ImageView thumb;

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_video);
            thumb = getView(R.id.main_video_thumb);
            addOnClickListener(R.id.main_video_thumb);
        }

        @Override
        public void setData(VideoBean data) {
            super.setData(data);
            ImageUtils.loadImageRound(thumb, data.getThumb(), 5);
        }
    }
}

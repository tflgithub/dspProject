package com.luzuzu.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import com.luzuzu.library.utils.image.ImageUtils;
import com.luzuzu.main.R;
import com.luzuzu.main.bean.FollowBean;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

/**
 * Created by fula on 2019/5/21.
 */

public class FollowAdapter extends RecyclerArrayAdapter<FollowBean> {

    public FollowAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }


    class ViewHolder extends BaseViewHolder<FollowBean> {

        private ImageView avatar;
        private Button btn;

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_follow);
            avatar = getView(R.id.iv_follow_avatar);
            btn=getView(R.id.main_up_man_btn_follow);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            addOnClickListener(R.id.iv_follow_avatar);
        }

        @Override
        public void setData(FollowBean data) {
            ImageUtils.loadImageCircle(avatar, data.avatar);
            setText(R.id.tv_follow_nick_name, data.nickName);
            setText(R.id.tv_follow_num, data.followNum + "人关注");
        }
    }
}

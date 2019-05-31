package com.luzuzu.main.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.luzuzu.library.utils.image.ImageUtils;
import com.luzuzu.main.R;
import com.luzuzu.main.bean.CommentBean;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

/**
 * Created by fula on 2019/5/19.
 */

public class CommentAdapter extends RecyclerArrayAdapter<CommentBean> {


    public CommentAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(parent);
    }


    class CommentHolder extends BaseViewHolder<CommentBean> {
        private ImageView avatar;
        private TextView name;
        private TextView num;
        private TextView content;

        CommentHolder(ViewGroup parent) {
            super(parent, R.layout.adapter_comment);
            avatar = getView(R.id.main_comment_avatar);
            name = getView(R.id.main_comment_name);
            num = getView(R.id.main_comment_praise_num);
            content = getView(R.id.main_comment_content);
        }

        @Override
        public void setData(CommentBean data) {
            ImageUtils.loadImageCircle(avatar, data.getUserIcon());
            name.setText(data.getUserName());
            num.setText(data.getPraiseNum()+"");
            content.setText(data.getContent());
        }
    }
}

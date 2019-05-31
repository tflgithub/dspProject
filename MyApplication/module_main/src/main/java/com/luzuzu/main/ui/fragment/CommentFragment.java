package com.luzuzu.main.ui.fragment;
import android.animation.ObjectAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.luzuzu.library.base.mvp.BaseFragment;
import com.luzuzu.main.R;
import com.luzuzu.main.adapter.CommentAdapter;
import com.luzuzu.main.bean.CommentBean;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CommentFragment extends BaseFragment {

    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.fragment_comment_close)
    ImageView mClose;
    @BindView(R.id.fragment_comment_num)
    TextView mCommentNum;
    @BindView(R.id.fragment_comment_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.fragment_comment_root)
    RelativeLayout mRoot;
    private List<CommentBean> mList;
    private CommentAdapter mAdapter;
    private onCloseClickListener onCloseClickListener;

    @Override
    public int getContentView() {
        return R.layout.fragment_comment;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initListener() {
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCloseClickListener != null) {
                    onCloseClickListener.closeCommentFragment();
                }
            }
        });
    }

    @Override
    public void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            CommentBean bean = new CommentBean();
            bean.setId(String.valueOf(i));
            bean.setUserName("我就是个开发仔" + i);
            bean.setContent("大佬不要再秀了，学不动啦......");
            bean.setPraiseNum(12138);
            mList.add(bean);
        }
//        ObjectAnimator animator = ObjectAnimator.ofFloat(mRoot, "translationY", mRoot.getHeight(), 0,mRoot.getHeight());
//        animator.setDuration(DURATION);
//        animator.setInterpolator(new LinearInterpolator());
//        animator.start();
//        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
//        mAdapter = new CommentAdapter(getContext());
//        mRecycler.setAdapter(mAdapter);
    }


    public void setOnCloseClickListener(CommentFragment.onCloseClickListener onCloseClickListener) {
        this.onCloseClickListener = onCloseClickListener;
    }

    public interface onCloseClickListener {
        void closeCommentFragment();
    }

    public void closeFragment() {
//        ObjectAnimator animator = ObjectAnimator.ofFloat(mRoot, "translationY", 0,mRoot.getHeight());
//        animator.setInterpolator(new LinearInterpolator());
//        animator.setDuration(DURATION);
//        animator.start();
    }

}

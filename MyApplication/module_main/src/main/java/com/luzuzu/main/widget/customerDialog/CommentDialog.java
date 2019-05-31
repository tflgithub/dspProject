package com.luzuzu.main.widget.customerDialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luzuzu.library.utils.PxUtils;
import com.luzuzu.library.widget.BaseDialog;
import com.luzuzu.main.R;
import com.luzuzu.main.adapter.CommentAdapter;
import com.luzuzu.main.bean.CommentBean;

import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fula on 2019/5/25.
 */

public class CommentDialog extends BaseDialog {

    @BindView(R.id.comment_recycler)
    YCRefreshView ycRefreshView;
    private CommentAdapter mAdapter;

    public CommentDialog(Context context, int resId) {
        super(context, resId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCreateView = View.inflate(getContext(), R.layout.comment_dialog, null);
        setContentView(mCreateView);
        ButterKnife.bind(this);
        mAdapter = new CommentAdapter(getContext());
        ycRefreshView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ycRefreshView.setLayoutManager(linearLayoutManager);
        //设置没有数据
        mAdapter.setNoMore(R.layout.view_recycle_no_more, new RecyclerArrayAdapter.OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {
                if (NetworkUtils.isConnected()) {
                    mAdapter.resumeMore();
                } else {
                    ToastUtils.showShort("网络不可用");
                }
            }

            @Override
            public void onNoMoreClick() {
                if (NetworkUtils.isConnected()) {
                    mAdapter.resumeMore();
                } else {
                    ToastUtils.showShort("网络不可用");
                }
            }
        });
        //加载更多
        mAdapter.setMore(R.layout.view_recycle_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                if (NetworkUtils.isConnected()) {
                    if (mAdapter.getAllData().size() > 0) {
//                        page++;
//                        presenter.getNews(num,page);
                    } else {
                        mAdapter.pauseMore();
                    }
                } else {
                    mAdapter.pauseMore();
                    ToastUtils.showShort("网络不可用");
                }
            }

            @Override
            public void onMoreClick() {
            }
        });
        List<CommentBean> mList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            CommentBean bean = new CommentBean();
            bean.setId(String.valueOf(i));
            bean.setUserName("我就是个开发仔" + i);
            bean.setContent("可不可以放过我......");
            bean.setPraiseNum(12138);
            mList.add(bean);
        }
        mAdapter.addAll(mList);
    }


    private InputDialog inputDialog;

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = PxUtils.dp2px(getContext(), 448);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    @OnClick({R.id.main_comment_dialog_close, R.id.main_et_input_container, R.id.main_smile_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_comment_dialog_close:
                dismiss();
                break;
            case R.id.main_et_input_container:
                new InputDialog(mContext, R.style.inputDialog, false).show();
                break;
            case R.id.main_smile_btn:
                new InputDialog(mContext, R.style.inputDialog, true).show();
                break;
        }
    }

    @Override
    public void dismiss() {
        inputDialog = null;
        super.dismiss();
    }
}

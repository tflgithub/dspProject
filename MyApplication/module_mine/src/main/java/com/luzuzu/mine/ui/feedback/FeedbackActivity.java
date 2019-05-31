package com.luzuzu.mine.ui.feedback;

import android.support.v7.widget.Toolbar;

import com.luzuzu.library.base.mvp.BaseActivity;
import com.luzuzu.mine.R;
import com.luzuzu.mine.R2;

import butterknife.BindView;

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.mine_toolbar)
    Toolbar toolbar;

    @Override
    public int getContentView() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initView() {
        toolbar.setTitle("反馈");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}

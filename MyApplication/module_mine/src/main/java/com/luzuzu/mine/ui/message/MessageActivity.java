package com.luzuzu.mine.ui.message;

import android.support.v7.widget.Toolbar;

import com.luzuzu.library.base.mvp.BaseActivity;
import com.luzuzu.mine.R;
import com.luzuzu.mine.R2;


import butterknife.BindView;

public class MessageActivity extends BaseActivity {


    @BindView(R2.id.mine_toolbar)
    Toolbar toolbar;

    @Override
    public int getContentView() {
        return R.layout.activity_message;
    }

    @Override
    public void initView() {
        toolbar.setTitle("消息");
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

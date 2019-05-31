package com.luzuzu.mine.ui.settting;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.luzuzu.library.base.config.AppConfig;
import com.luzuzu.library.base.mvp.BaseActivity;
import com.luzuzu.library.widget.MultipleItemView;
import com.luzuzu.mine.R;
import com.luzuzu.mine.R2;
import com.luzuzu.mine.widget.CustomDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.mine_toolbar)
    Toolbar toolbar;

    @BindView(R.id.mine_miv_auto_push)
    MultipleItemView autoPush;

    @BindView(R.id.mine_miv_auto_play)
    MultipleItemView autoPlay;

    @BindView(R.id.mine_miv_clear_cache)
    MultipleItemView cacheSize;

    @Override
    public int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void initListener() {
        autoPush.getRightIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoPush.getRightIcon().isSelected()) {
                    autoPush.getRightIcon().setSelected(false);
                } else {
                    autoPush.getRightIcon().setSelected(true);
                }
            }
        });

        autoPlay.getRightIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoPlay.getRightIcon().isSelected()) {
                    autoPlay.getRightIcon().setSelected(false);
                } else {
                    autoPlay.getRightIcon().setSelected(true);
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.mine_setting_login_out, R2.id.mine_miv_clear_cache})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_setting_login_out:
                new CustomDialog(this, new String[]{"提示", "确认退出登录?", null, null}, new CustomDialog.onButtonListener() {
                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onOk() {
                        AppConfig.INSTANCE.setLogin(false);
                        ToastUtils.showShort("退出登录");
                        finish();
                    }
                }).show();
                break;
            case R.id.mine_miv_clear_cache:
                new CustomDialog(this, new String[]{"提示", "确认删除缓存?", null, null}, new CustomDialog.onButtonListener() {
                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onOk() {
                        cacheSize.setRightText("0 M");
                    }
                }).show();
                break;
        }
    }
}

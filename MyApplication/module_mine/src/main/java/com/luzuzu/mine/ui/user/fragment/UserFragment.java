package com.luzuzu.mine.ui.user.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.luzuzu.library.arouter.ARouterConstant;
import com.luzuzu.library.arouter.ARouterUtils;
import com.luzuzu.library.base.config.AppConfig;
import com.luzuzu.library.base.mvp.BaseFragment;
import com.luzuzu.library.bean.Event;
import com.luzuzu.library.constant.EventAction;
import com.luzuzu.mine.R;
import com.luzuzu.mine.R2;
import com.luzuzu.mine.ui.account.LoginActivity;
import com.luzuzu.mine.ui.feedback.FeedbackActivity;
import com.luzuzu.mine.ui.message.MessageActivity;
import com.luzuzu.mine.ui.settting.SettingActivity;
import com.luzuzu.mine.ui.user.UserEditActivity;
import com.luzuzu.mine.ui.user.mvp.presenter.UserFragmentPresenter;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ARouterConstant.USER_MAIN_FRAGMENT)
public class UserFragment extends BaseFragment<UserFragmentPresenter> {

    @BindView(R2.id.mine_user_avatar)
    ImageView ivHead;

    public static final String URL = "http://lc-ethuey9k.cn-n1.lcfile.com/a7b4c9f141a6ef18b198.jpg";

    @Override
    public int getContentView() {
        return R.layout.fragment_user;
    }

    @Override
    public void initView(View view) {
        ARouterUtils.injectFragment(this);
//        ImageUtils.loadImageCircle(ivHead, URL);
        displayInfo();
        new Handler();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    private void displayInfo() {
        if (AppConfig.INSTANCE.isLogin()) {
            ToastUtils.showShort("登录成功");
        }
    }

    @Override
    public void onEventBus(Event event) {
        super.onEventBus(event);
        if (TextUtils.equals(event.getAction(), EventAction.EVENT_LOGIN_SUCCESS)) {
            displayInfo();
        }
    }


    @Override
    protected boolean regEvent() {
        return true;
    }


    @OnClick({R2.id.mine_user_avatar, R2.id.mine_miv_message, R2.id.mine_miv_feedback, R2.id.mine_miv_setting, R2.id.mine_user_update_mobile, R2.id.mine_edit_base_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_miv_setting:
                startActivity(SettingActivity.class);
                break;
            case R.id.mine_user_avatar:
                startActivity(LoginActivity.class);
                break;
            case R.id.mine_miv_feedback:
                startActivity(FeedbackActivity.class);
                break;
            case R.id.mine_miv_message:
                startActivity(MessageActivity.class);
                break;
            case R.id.mine_user_update_mobile:
                // ARouterUtils.navigation(ARouterConstant.ACTIVITY_LOGIN, bundle1);
                break;
            case R.id.mine_edit_base_info:
                startActivity(UserEditActivity.class);
                break;
        }

    }
}

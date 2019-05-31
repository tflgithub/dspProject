package com.luzuzu.mine.ui.account;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.luzuzu.library.arouter.ARouterConstant;
import com.luzuzu.library.arouter.ARouterUtils;
import com.luzuzu.library.base.mvp.BaseActivity;
import com.luzuzu.mine.R;
import com.luzuzu.mine.ui.account.fragment.LoginFragment;

import butterknife.OnClick;

@Route(path = ARouterConstant.ACTIVITY_LOGIN)
public class LoginActivity extends BaseActivity {

    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        ARouterUtils.injectActivity(this);
        LoginFragment fragment = (LoginFragment) ARouterUtils.getFragment(ARouterConstant.FRAGMENT_LOGIN);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.mine_login_container, fragment);
        transaction.commit();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.mine_close_btn})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_close_btn:
                finish();
                break;
        }
    }

}

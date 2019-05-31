package com.luzuzu.mine.ui.account.mvp.presenter;

import android.app.Activity;

import com.luzuzu.library.base.config.AppConfig;
import com.luzuzu.mine.ui.account.LoginActivity;
import com.luzuzu.mine.ui.account.mvp.contract.LoginContract;


/**
 * Created by a10105 on 2019/3/11.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private Activity activity;

    private LoginContract.View mView;

    public LoginPresenter(LoginContract.View view) {
        this.mView =view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        if (activity != null) {
            activity = null;
        }
    }

    @Override
    public void bindActivity(LoginActivity activity) {
        this.activity=activity;
    }

    @Override
    public void login() {
        //登录
        AppConfig.INSTANCE.setLogin(true);
        mView.loginSuccess();
    }
}

package com.luzuzu.mine.ui.account.mvp.contract;

import com.luzuzu.library.base.mvp.BasePresenter;
import com.luzuzu.library.base.mvp.BaseView;
import com.luzuzu.mine.ui.account.LoginActivity;

/**
 * Created by a10105 on 2019/3/11.
 */

public interface LoginContract {

    interface  View extends BaseView{

        void loginSuccess();
    }

    interface Presenter extends BasePresenter{

        void bindActivity(LoginActivity activity);

        void login();
    }
}

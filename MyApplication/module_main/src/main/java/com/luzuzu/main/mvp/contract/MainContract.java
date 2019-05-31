package com.luzuzu.main.mvp.contract;
import com.luzuzu.library.base.mvp.BasePresenter;
import com.luzuzu.library.base.mvp.BaseView;

/**
 * Created by fula on 2019/3/11.
 */

public interface MainContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {

        void locationPermissionsTask();
    }
}

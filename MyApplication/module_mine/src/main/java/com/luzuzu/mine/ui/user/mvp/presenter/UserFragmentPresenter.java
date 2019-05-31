package com.luzuzu.mine.ui.user.mvp.presenter;

import android.app.Activity;

import com.luzuzu.mine.ui.user.mvp.contract.UserFragmentContract;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by a10105 on 2019/3/11.
 */

public class UserFragmentPresenter  implements UserFragmentContract.Presenter {

    private CompositeDisposable mCompositeDisposable;
    private Activity activity;
    public UserFragmentPresenter(UserFragmentContract.View view) {
        this.activity = (Activity) view;
    }

    @Override
    public void subscribe() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
        if (activity != null) {
            activity = null;
        }
    }
}

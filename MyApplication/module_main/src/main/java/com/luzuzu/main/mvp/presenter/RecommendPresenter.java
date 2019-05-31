package com.luzuzu.main.mvp.presenter;

import com.blankj.utilcode.util.NetworkUtils;
import com.luzuzu.main.api.ConstantApi;
import com.luzuzu.main.api.RecommendModel;
import com.luzuzu.main.bean.VideoBean;
import com.luzuzu.main.mvp.contract.RecommendContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fula on 2019/5/19.
 */

public class RecommendPresenter implements RecommendContract.Presenter {

    private RecommendContract.View mView;

    public RecommendPresenter(RecommendContract.View view) {
        this.mView = view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void getVideo(int num, final int page) {

//        RecommendModel model = RecommendModel.getInstance();
//        model.getVideo(ConstantApi.TX_KEY, num, page)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<VideoBean>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(VideoBean videoBean) {
//                        if (page == 1) {
//                            if (videoBean != null && videoBean.() != null
//                                    && weChatBean.getNewslist().size() > 0) {
//                                mView.setView(weChatBean.getNewslist());
//                            } else {
//                                mView.setEmptyView();
//                            }
//                        } else {
//                            if (weChatBean != null && weChatBean.getNewslist() != null
//                                    && weChatBean.getNewslist().size() > 0) {
//                                mView.setViewMore(weChatBean.getNewslist());
//                            } else {
//                                mView.stopMore();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if (NetworkUtils.isConnected()) {
//                            mView.setErrorView();
//                        } else {
//                            mView.setNetworkErrorView();
//                        }
//                    }
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }
}

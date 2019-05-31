package com.luzuzu.main.mvp.contract;

import com.luzuzu.library.base.mvp.BasePresenter;
import com.luzuzu.library.base.mvp.BaseView;
import com.luzuzu.main.bean.VideoBean;

import java.util.List;

/**
 * Created by fula on 2019/5/19.
 */

public interface RecommendContract {


    interface View extends BaseView {
        void setErrorView();
        void setNetworkErrorView();
        void setView(List<VideoBean> videoList);
        void setEmptyView();
        void setViewMore(List<VideoBean> videoList);
        void stopMore();
    }


    interface Presenter extends BasePresenter {

        void getVideo(int num, int page);
    }
}

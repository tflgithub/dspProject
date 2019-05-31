package com.luzuzu.main.api;

import com.luzuzu.library.http.RetrofitWrapper;
import com.luzuzu.main.bean.VideoBean;

import io.reactivex.Observable;

/**
 * Created by fula on 2019/5/19.
 */

public class RecommendModel {

    private static RecommendModel model;

    private RecommendApi mApiService;

    private RecommendModel() {
        mApiService = RetrofitWrapper
                .getInstance(ConstantApi.TX_HTTP)
                .create(RecommendApi.class);
    }

    public static RecommendModel getInstance(){
        if(model == null) {
            model = new RecommendModel();
        }
        return model;
    }

    public Observable<VideoBean> getVideo(String key , int num , int page) {
        return mApiService.getVideo(key, num, page);
    }

}

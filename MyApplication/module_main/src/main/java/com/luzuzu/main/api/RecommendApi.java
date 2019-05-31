package com.luzuzu.main.api;

import com.luzuzu.main.bean.VideoBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by fula on 2019/5/19.
 */

public interface RecommendApi {

    @GET("wxnew/")
    Observable<VideoBean> getVideo(@Query("key") String key,
                                        @Query("num") int num,
                                        @Query("page") int page);

}

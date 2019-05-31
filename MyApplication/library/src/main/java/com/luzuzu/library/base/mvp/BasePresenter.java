package com.luzuzu.library.base.mvp;


/**
 * Created by a10105 on 2019/3/7.
 */

public interface BasePresenter {

    //绑定数据
    void subscribe();
    //解除绑定
    void unSubscribe();
}

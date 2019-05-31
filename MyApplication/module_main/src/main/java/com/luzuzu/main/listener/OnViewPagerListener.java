package com.luzuzu.main.listener;

import android.view.View;

/**
 * Created by fula on 2019/5/22.
 */

public interface OnViewPagerListener {

    /*初始化完成*/
    void onInitComplete(View view);

    /*选中的监听以及判断是否滑动到底部*/
    void onPageSelected(int position, boolean isBottom,View view);

}


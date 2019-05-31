package com.luzuzu.library.listener;

import android.view.View;

import java.util.Calendar;

/**
 * Created by fula on 2019/3/7.
 * 重复点击监听
 */

public abstract class NoDoubleClickListener implements View.OnClickListener  {

    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        int MIN_CLICK_DELAY_TIME = 1000;
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    protected abstract void onNoDoubleClick(View view);
}

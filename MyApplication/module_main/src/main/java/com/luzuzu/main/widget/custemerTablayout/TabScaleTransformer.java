package com.luzuzu.main.widget.custemerTablayout;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/**
 * Created by fula on 2019/5/18.
 */

public class TabScaleTransformer implements ViewPager.PageTransformer{

    private SlidingScaleTabLayout slidingScaleTabLayout;

    private PagerAdapter pagerAdapter;

    private float textSelectSize;

    private float textUnSelectSize;

    public TabScaleTransformer(SlidingScaleTabLayout slidingScaleTabLayout, PagerAdapter pagerAdapter,
                               float textSelectSize, float textUnSelectSize) {
        this.slidingScaleTabLayout = slidingScaleTabLayout;
        this.pagerAdapter = pagerAdapter;
        this.textSelectSize = textSelectSize;
        this.textUnSelectSize = textUnSelectSize;
    }

    @Override
    public void transformPage(@NonNull View view, final float position) {
        final TextView currentTab = slidingScaleTabLayout.getTitle(pagerAdapter.getItemPosition(view));
        if (currentTab == null) {
            return;
        }
        // 必须要在View调用post更新样式，否则可能无效
        currentTab.post(new Runnable() {
            @Override
            public void run() {
                if (position >= -1 && position <= 1) { // [-1,1]
                    currentTab.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSelectSize - Math.abs((textSelectSize - textUnSelectSize) * position));
                } else {
                    currentTab.setTextSize(TypedValue.COMPLEX_UNIT_PX, textUnSelectSize);
                }
            }
        });
    }

}

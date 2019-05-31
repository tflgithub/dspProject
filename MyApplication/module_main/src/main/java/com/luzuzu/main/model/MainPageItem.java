package com.luzuzu.main.model;

import android.view.View;

/**
 * Created by fula on 2019/5/19.
 */

public class MainPageItem {

    public MainPageItem(View view, String title) {
        this.view = view;
        this.title = title;
    }

    public View view;

    public String title;
}

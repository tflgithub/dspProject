package com.luzuzu.main.widget.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.luzuzu.main.R;
import com.luzuzu.videoplayer.ui.StandardVideoController;

/**
 * Created by fula on 2019/5/22.
 */

public class FullScreenController extends StandardVideoController {

    public FullScreenController(@NonNull Context context) {
        super(context);
    }

    public FullScreenController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FullScreenController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        super.initView();
        mGestureDetector = new GestureDetector(getContext(), new MyGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (mShowing) {
                    hide();
                } else {
                    show();
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_play) {
            doPauseResume();
        }
    }
}

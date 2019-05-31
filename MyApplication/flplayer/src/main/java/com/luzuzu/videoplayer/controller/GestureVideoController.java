package com.luzuzu.videoplayer.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.luzuzu.videoplayer.util.PlayerUtils;

/**
 * Created by fula on 2019/5/23.
 */

public abstract class GestureVideoController extends BaseVideoController {

    protected GestureDetector mGestureDetector;
    protected boolean mIsGestureEnabled;


    public GestureVideoController(@NonNull Context context) {
        super(context);
    }

    public GestureVideoController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GestureVideoController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        super.initView();
        mGestureDetector = new GestureDetector(getContext(), new MyGestureListener());
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }


    protected float mBrightness;

    protected int mPosition;

    protected boolean mNeedSeek;

    protected class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        private boolean mFirstTouch;
        private boolean mChangePosition;

        @Override
        public boolean onDown(MotionEvent e) {
            if (!mIsGestureEnabled || PlayerUtils.isEdge(getContext(), e)) return super.onDown(e);
            mBrightness = PlayerUtils.scanForActivity(getContext()).getWindow().getAttributes().screenBrightness;
            mFirstTouch = true;
            mChangePosition = false;
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (mShowing) {
                hide();
            } else {
                show();
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (!mIsGestureEnabled || PlayerUtils.isEdge(getContext(), e1)) return super.onScroll(e1, e2, distanceX, distanceY);
            float deltaX = e1.getX() - e2.getX();
            if (mFirstTouch) {
                mChangePosition = Math.abs(distanceX) >= Math.abs(distanceY);
                mFirstTouch = false;
            }
            if (mChangePosition) {
                slideToChangePosition(deltaX);
            }
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean detectedUp = event.getAction() == MotionEvent.ACTION_UP;
        if (!mGestureDetector.onTouchEvent(event) && detectedUp) {
            if (mNeedSeek) {
                mMediaPlayer.seekTo(mPosition);
                mNeedSeek = false;
            }
        }
        return super.onTouchEvent(event);
    }

    protected void slideToChangePosition(float deltaX) {
        hide();
        deltaX = -deltaX;
        int width = getMeasuredWidth();
        int duration = (int) mMediaPlayer.getDuration();
        int currentPosition = (int) mMediaPlayer.getCurrentPosition();
        int position = (int) (deltaX / width * 120000 + currentPosition);
        if (position > duration) position = duration;
        if (position < 0) position = 0;
        mPosition = position;
        mNeedSeek = true;
    }
}

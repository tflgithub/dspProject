package com.luzuzu.main.widget.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.luzuzu.main.R;
import com.luzuzu.videoplayer.player.IjkVideoView;
import com.luzuzu.videoplayer.ui.StandardVideoController;
/**
 * Created by fula on 2019/5/24.
 */

public class RotateInFullscreenController extends StandardVideoController {

    public RotateInFullscreenController(@NonNull Context context) {
        super(context);
    }

    public RotateInFullscreenController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateInFullscreenController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        super.initView();
        mGestureDetector = new GestureDetector(getContext(), new MyGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (!mMediaPlayer.isFullScreen()) {
                    mMediaPlayer.startFullScreen();
                    return true;
                }
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
    public void setPlayerState(int playerState) {
        super.setPlayerState(playerState);
        switch (playerState) {
            case IjkVideoView.PLAYER_FULL_SCREEN:
                getThumb().setVisibility(GONE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
         if (i == R.id.iv_play) {
            doPauseResume();
        } else if (i == R.id.thumb) {
            mMediaPlayer.start();
            mMediaPlayer.startFullScreen();
        }
    }

    @Override
    public boolean onBackPressed() {
        if (mMediaPlayer.isFullScreen()) {
            mMediaPlayer.stopFullScreen();
            return true;
        }
        return super.onBackPressed();
    }
}

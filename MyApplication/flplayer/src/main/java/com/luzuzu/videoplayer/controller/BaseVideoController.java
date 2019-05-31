package com.luzuzu.videoplayer.controller;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.luzuzu.videoplayer.R;
import com.luzuzu.videoplayer.player.BaseIjkVideoView;
import com.luzuzu.videoplayer.player.IjkVideoView;
import com.luzuzu.videoplayer.widget.StatusView;
import java.util.Formatter;
import java.util.Locale;

/**
 * Created by fula on 2019/5/16.
 *
 * 控制基类
 */

public abstract class BaseVideoController extends FrameLayout {

    protected View mControllerView;//控制器视图
    protected MediaPlayerControl mMediaPlayer;//播放器
    protected boolean mShowing;//控制器是否处于显示状态
    protected int mDefaultTimeout = 4000;
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;
    protected int mCurrentPlayState;
    protected StatusView mStatusView;

    public BaseVideoController(Context context) {
        this(context, null);
    }

    public BaseVideoController(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public BaseVideoController(Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    protected void initView() {
        mControllerView = LayoutInflater.from(getContext()).inflate(getLayoutId(), this);
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        mStatusView = new StatusView(getContext());
        setClickable(true);
        setFocusable(true);
    }

    /**
     * 设置控制器布局文件，子类必须实现
     */
    protected abstract int getLayoutId();


    /**
     * 显示
     */
    public void show() {
    }

    /**
     * 隐藏
     */
    public void hide() {
    }

    public void setPlayState(int playState) {
        mCurrentPlayState = playState;
        hideStatusView();
        switch (playState) {
            case IjkVideoView.STATE_ERROR:
                mStatusView.setMessage(getResources().getString(R.string.flplayer_error_message));
                mStatusView.setButtonTextAndAction(getResources().getString(R.string.flplayer_retry), new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideStatusView();
                        mMediaPlayer.replay(false);
                    }
                });
                this.addView(mStatusView, 0);
                break;
        }
    }

    public void showStatusView() {
        this.removeView(mStatusView);
        mStatusView.setMessage(getResources().getString(R.string.flplayer_wifi_tip));
        mStatusView.setButtonTextAndAction(getResources().getString(R.string.flplayer_continue_play), new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideStatusView();
                BaseIjkVideoView.IS_PLAY_ON_MOBILE_NETWORK = true;
                mMediaPlayer.start();
            }
        });
        this.addView(mStatusView);
    }

    public void hideStatusView() {
        this.removeView(mStatusView);
    }

    public void setPlayerState(int playerState) {
    }

    protected void doPauseResume() {
        if (mCurrentPlayState == IjkVideoView.STATE_BUFFERING) return;
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        } else {
            mMediaPlayer.start();
        }
    }

    protected Runnable mShowProgress = new Runnable() {
        @Override
        public void run() {
            int pos = setProgress();
            if (mMediaPlayer.isPlaying()) {
                postDelayed(mShowProgress, 1000 - (pos % 1000));
            }
        }
    };

    protected final Runnable mFadeOut = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    protected int setProgress() {
        return 0;
    }


    protected String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        post(mShowProgress);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(mShowProgress);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            post(mShowProgress);
        }
    }

    /**
     * 改变返回键逻辑，用于activity
     */
    public boolean onBackPressed() {
        return false;
    }

    public void setMediaPlayer(MediaPlayerControl mediaPlayer) {
        this.mMediaPlayer = mediaPlayer;
    }
}

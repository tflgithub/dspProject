package com.luzuzu.videoplayer.ui;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.luzuzu.videoplayer.R;
import com.luzuzu.videoplayer.controller.GestureVideoController;
import com.luzuzu.videoplayer.player.IjkVideoView;
import com.luzuzu.videoplayer.util.L;
import com.luzuzu.videoplayer.util.PlayerUtils;

/**
 * Created by fula on 2019/5/16.
 * <p>
 * 点播、直播控制器
 */

public class StandardVideoController extends GestureVideoController implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    protected TextView mTotalTime, mCurrTime;
    protected LinearLayout mBottomContainer;
    protected SeekBar mVideoProgress;
    private boolean mIsDragging;
    private ProgressBar mBottomProgress;
    private ImageView mPlayButton;
    private ProgressBar mLoadingProgress;
    private ImageView mThumb;
    private Animation mShowAnim = AnimationUtils.loadAnimation(getContext(), R.anim.flplayer_anim_alpha_in);
    private Animation mHideAnim = AnimationUtils.loadAnimation(getContext(), R.anim.flplayer_anim_alpha_out);

    public StandardVideoController(@NonNull Context context) {
        this(context, null);
    }

    public StandardVideoController(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StandardVideoController(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.flplayer_layout_standard_controller;
    }

    @Override
    protected void initView() {
        super.initView();
        mBottomContainer = mControllerView.findViewById(R.id.bottom_container);
        mVideoProgress = mControllerView.findViewById(R.id.seekBar);
        mVideoProgress.setOnSeekBarChangeListener(this);
        mTotalTime = mControllerView.findViewById(R.id.total_time);
        mCurrTime = mControllerView.findViewById(R.id.curr_time);
        mThumb = mControllerView.findViewById(R.id.thumb);
        mThumb.setOnClickListener(this);
        mPlayButton = mControllerView.findViewById(R.id.iv_play);
        mPlayButton.setOnClickListener(this);
        mLoadingProgress = mControllerView.findViewById(R.id.loading);
        mBottomProgress = mControllerView.findViewById(R.id.bottom_progress);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_play || i == R.id.thumb) {
            doPauseResume();
        }
    }

    @Override
    public void setPlayState(int playState) {
        super.setPlayState(playState);
        switch (playState) {
            case IjkVideoView.STATE_IDLE:
                L.e("STATE_IDLE");
                hide();
                mBottomProgress.setProgress(0);
                mBottomProgress.setSecondaryProgress(0);
                mVideoProgress.setProgress(0);
                mVideoProgress.setSecondaryProgress(0);
                mBottomProgress.setVisibility(View.GONE);
                mLoadingProgress.setVisibility(View.GONE);
                mThumb.setVisibility(View.VISIBLE);
                break;
            case IjkVideoView.STATE_PLAYING:
                L.e("STATE_PLAYING");
                post(mShowProgress);
                mPlayButton.setSelected(true);
                mLoadingProgress.setVisibility(View.GONE);
                mThumb.setVisibility(View.GONE);
                break;
            case IjkVideoView.STATE_PAUSED:
                L.e("STATE_PAUSED");
                mPlayButton.setSelected(false);
                break;
            case IjkVideoView.STATE_PREPARING:
                L.e("STATE_PREPARING");
                mLoadingProgress.setVisibility(View.VISIBLE);
                mThumb.setVisibility(View.VISIBLE);
                break;
            case IjkVideoView.STATE_PREPARED:
                L.e("STATE_PREPARED");
                mLoadingProgress.setVisibility(GONE);
                break;
            case IjkVideoView.STATE_ERROR:
                L.e("STATE_ERROR");
                mLoadingProgress.setVisibility(View.GONE);
                mThumb.setVisibility(View.GONE);
                mBottomProgress.setVisibility(View.GONE);
                break;
            case IjkVideoView.STATE_BUFFERING:
                L.e("STATE_BUFFERING");
                mLoadingProgress.setVisibility(View.VISIBLE);
                mThumb.setVisibility(View.GONE);
                mPlayButton.setSelected(mMediaPlayer.isPlaying());
                break;
            case IjkVideoView.STATE_BUFFERED:
                L.e("STATE_BUFFERED");
                mLoadingProgress.setVisibility(View.GONE);
                mThumb.setVisibility(View.GONE);
                mPlayButton.setSelected(mMediaPlayer.isPlaying());
                break;
            case IjkVideoView.STATE_PLAYBACK_COMPLETED:
                L.e("STATE_PLAYBACK_COMPLETED");
                hide();
                removeCallbacks(mShowProgress);
                mThumb.setVisibility(View.VISIBLE);
                mBottomProgress.setProgress(0);
                mBottomProgress.setSecondaryProgress(0);
                break;
        }
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mIsDragging = true;
        removeCallbacks(mShowProgress);
        removeCallbacks(mFadeOut);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        long duration = mMediaPlayer.getDuration();
        long newPosition = (duration * seekBar.getProgress()) / mVideoProgress.getMax();
        mMediaPlayer.seekTo((int) newPosition);
        mIsDragging = false;
        post(mShowProgress);
        show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) {
            return;
        }
        long duration = mMediaPlayer.getDuration();
        long newPosition = (duration * progress) / mVideoProgress.getMax();
        if (mCurrTime != null)
            mCurrTime.setText(stringForTime((int) newPosition));
    }

    @Override
    public void hide() {
        if (mShowing) {
            hideAllViews();
            mShowing = false;
        }
    }

    private void hideAllViews() {
        mBottomContainer.setVisibility(View.GONE);
        mBottomContainer.startAnimation(mHideAnim);
    }

    private void show(int timeout) {
        if (!mShowing) {
            showAllViews();
            mShowing = true;
        }
        removeCallbacks(mFadeOut);
        if (timeout != 0) {
            postDelayed(mFadeOut, timeout);
        }
    }

    private void showAllViews() {
        mBottomContainer.setVisibility(View.VISIBLE);
        mBottomContainer.startAnimation(mShowAnim);
    }

    @Override
    public void show() {
        show(mDefaultTimeout);
    }

    @Override
    protected int setProgress() {
        if (mMediaPlayer == null || mIsDragging) {
            return 0;
        }
        int position = (int) mMediaPlayer.getCurrentPosition();
        int duration = (int) mMediaPlayer.getDuration();
        if (mVideoProgress != null) {
            if (duration > 0) {
                mVideoProgress.setEnabled(true);
                int pos = (int) (position * 1.0 / duration * mVideoProgress.getMax());
                mVideoProgress.setProgress(pos);
                mBottomProgress.setProgress(pos);
            } else {
                mVideoProgress.setEnabled(false);
            }
            int percent = mMediaPlayer.getBufferedPercentage();
            if (percent >= 95) { //解决缓冲进度不能100%问题
                mVideoProgress.setSecondaryProgress(mVideoProgress.getMax());
                mBottomProgress.setSecondaryProgress(mBottomProgress.getMax());
            } else {
                mVideoProgress.setSecondaryProgress(percent * 10);
                mBottomProgress.setSecondaryProgress(percent * 10);
            }
        }

        if (mTotalTime != null)
            mTotalTime.setText(stringForTime(duration));
        if (mCurrTime != null)
            mCurrTime.setText(stringForTime(position));
        return position;
    }

    @Override
    public boolean onBackPressed() {
        Activity activity = PlayerUtils.scanForActivity(getContext());
        if (activity == null) return super.onBackPressed();
        if (mMediaPlayer.isFullScreen()) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mMediaPlayer.stopFullScreen();
            return true;
        }
        return super.onBackPressed();
    }

    public ImageView getThumb() {
        return mThumb;
    }

}

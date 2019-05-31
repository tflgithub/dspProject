package com.luzuzu.main.widget.controller;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.luzuzu.main.R;
import com.luzuzu.main.listener.ControllerListener;
import com.luzuzu.videoplayer.controller.BaseVideoController;
import com.luzuzu.videoplayer.player.IjkVideoView;
import com.luzuzu.videoplayer.util.PlayerUtils;

/**
 * Created by fula on 2019/5/16.
 *
 * 广告控制器
 */

public class AdController extends BaseVideoController implements View.OnClickListener {

    protected TextView adTime, adDetail;
    protected ImageView back, playButton;
    protected ControllerListener listener;

    public AdController(@NonNull Context context) {
        super(context);
    }

    public AdController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_ad_controller;
    }


    @Override
    protected void initView() {
        super.initView();
        adTime = mControllerView.findViewById(R.id.ad_time);
        adDetail = mControllerView.findViewById(R.id.ad_detail);
        adDetail.setText("了解详情>");
        back = mControllerView.findViewById(R.id.back);
        back.setVisibility(GONE);
        playButton = mControllerView.findViewById(R.id.iv_play);
        playButton.setOnClickListener(this);
        adTime.setOnClickListener(this);
        adDetail.setOnClickListener(this);
        back.setOnClickListener(this);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onAdClick();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
            onBackPressed();
        } else if (id == R.id.ad_detail) {
            if (listener != null) listener.onAdClick();
        } else if (id == R.id.ad_time) {
            if (listener != null) listener.onSkipAd();
        } else if (id == R.id.iv_play) {
            doPauseResume();
        }
    }
    @Override
    public void setPlayState(int playState) {
        super.setPlayState(playState);
        switch (playState) {
            case IjkVideoView.STATE_PLAYING:
                post(mShowProgress);
                playButton.setSelected(true);
                break;
            case IjkVideoView.STATE_PAUSED:
                playButton.setSelected(false);
                break;
        }
    }

    @Override
    public void setPlayerState(int playerState) {
        super.setPlayerState(playerState);
        switch (playerState) {
            case IjkVideoView.PLAYER_NORMAL:
                back.setVisibility(GONE);
                break;
            case IjkVideoView.PLAYER_FULL_SCREEN:
                back.setVisibility(VISIBLE);
                break;
        }
    }

    @Override
    protected int setProgress() {
        if (mMediaPlayer == null) {
            return 0;
        }
        int position = (int) mMediaPlayer.getCurrentPosition();
        int duration = (int) mMediaPlayer.getDuration();
        if (adTime != null)
            adTime.setText(String.format("%s | 跳过", (duration - position) / 1000));
        return position;
    }

    @Override
    public boolean onBackPressed() {
        if (mMediaPlayer.isFullScreen()) {
            PlayerUtils.scanForActivity(getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mMediaPlayer.stopFullScreen();
            setPlayerState(IjkVideoView.PLAYER_NORMAL);
            return true;
        }
        return super.onBackPressed();
    }

    public void setControllerListener(ControllerListener listener) {
        this.listener = listener;
    }

}

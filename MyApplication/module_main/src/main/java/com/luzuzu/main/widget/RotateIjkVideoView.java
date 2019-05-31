package com.luzuzu.main.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.luzuzu.main.util.VideoCacheManager;
import com.luzuzu.videoplayer.player.IjkVideoView;
import com.luzuzu.videoplayer.util.PlayerUtils;

import java.io.File;

/**
 * Created by fula on 2019/5/16.
 */
public class RotateIjkVideoView extends IjkVideoView {

    protected HttpProxyCacheServer mCacheServer;
    protected int mBufferedPercentage;
    protected boolean mIsCacheEnabled = true; //默认打开缓存

    public RotateIjkVideoView(@NonNull Context context) {
        super(context);
    }

    public RotateIjkVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateIjkVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HttpProxyCacheServer getCacheServer() {
        return VideoCacheManager.getProxy(getContext().getApplicationContext());
    }


    @Override
    protected void startPrepare(boolean needReset) {
        if (TextUtils.isEmpty(mCurrentUrl) && mAssetFileDescriptor == null) return;
        if (needReset) mMediaPlayer.reset();
        if (mAssetFileDescriptor != null) {
            mMediaPlayer.setDataSource(mAssetFileDescriptor);
        } else if (mIsCacheEnabled && !mCurrentUrl.startsWith("file://")) { //本地文件不能缓存
            mCacheServer = getCacheServer();
            String proxyPath = mCacheServer.getProxyUrl(mCurrentUrl);
            mCacheServer.registerCacheListener(cacheListener, mCurrentUrl);
            if (mCacheServer.isCached(mCurrentUrl)) {
                mBufferedPercentage = 100;
            }
            mMediaPlayer.setDataSource(proxyPath, mHeaders);
        } else {
            mMediaPlayer.setDataSource(mCurrentUrl, mHeaders);
        }
        mMediaPlayer.prepareAsync();
        setPlayState(STATE_PREPARING);
        setPlayerState(isFullScreen() ? PLAYER_FULL_SCREEN : PLAYER_NORMAL);
    }

    /**
     * 开启缓存后，返回是缓存的进度
     */
    @Override
    public int getBufferedPercentage() {
        return mIsCacheEnabled ? mBufferedPercentage : super.getBufferedPercentage();
    }

    @Override
    public void release() {
        super.release();
        if (mCacheServer != null) {
            mCacheServer.unregisterCacheListener(cacheListener);
            mCacheServer = null;
        }
    }

    /**
     * 是否打开缓存，默认打开
     */
    public void setCacheEnabled(boolean isEnabled) {
        mIsCacheEnabled = isEnabled;
    }

    /**
     * 缓存监听
     */
    private CacheListener cacheListener = new CacheListener() {
        @Override
        public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {
            mBufferedPercentage = percentsAvailable;
        }
    };

    @Override
    public void startFullScreen() {
        Activity activity = PlayerUtils.scanForActivity(getContext());
        if (activity == null) return;
        if (mIsFullScreen) return;
        PlayerUtils.hideActionBar(getContext());
        this.removeView(mPlayerContainer);
        this.addView(mHideNavBarView);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ViewGroup contentView = activity
                .findViewById(android.R.id.content);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.addView(mPlayerContainer, params);
        mIsFullScreen = true;
        setPlayerState(PLAYER_FULL_SCREEN);
    }

    @Override
    public void stopFullScreen() {
        Activity activity = PlayerUtils.scanForActivity(getContext());
        if (activity == null) return;
        if (!mIsFullScreen) return;
        if (mVideoController != null) mVideoController.hide();
        PlayerUtils.showActionBar(getContext());
        ViewGroup contentView = activity
                .findViewById(android.R.id.content);
        contentView.removeView(mPlayerContainer);
        this.removeView(mHideNavBarView);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(mPlayerContainer, params);
        mIsFullScreen = false;
        setPlayerState(PLAYER_NORMAL);
    }
}

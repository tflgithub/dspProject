package com.luzuzu.videoplayer.controller;

import android.graphics.Bitmap;

/**
 * Created by fula on 2019/5/16.
 */

public interface MediaPlayerControl {

    void start();

    void pause();

    long getDuration();

    long getCurrentPosition();

    void seekTo(long pos);

    boolean isPlaying();

    int getBufferedPercentage();

    void startFullScreen();

    void stopFullScreen();

    boolean isFullScreen();

    void setMute(boolean isMute);

    boolean isMute();

    void setScreenScale(int screenScale);

    void setSpeed(float speed);

    long getTcpSpeed();

    void replay(boolean resetPosition);

    void setMirrorRotation(boolean enable);

    Bitmap doScreenShot();

    int[] getVideoSize();

    void setRotation(float rotation);
}

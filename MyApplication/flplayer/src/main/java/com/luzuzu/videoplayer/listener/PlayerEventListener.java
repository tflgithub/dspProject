package com.luzuzu.videoplayer.listener;

/**
 * Created by fula on 2019/5/16.
 */

public interface PlayerEventListener {

    void onError();

    void onCompletion();

    void onInfo(int what, int extra);

    void onPrepared();

    void onVideoSizeChanged(int width, int height);
}

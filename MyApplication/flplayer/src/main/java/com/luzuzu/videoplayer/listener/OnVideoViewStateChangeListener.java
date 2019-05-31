package com.luzuzu.videoplayer.listener;

/**
 * Created by fula on 2019/5/16.
 */

public interface OnVideoViewStateChangeListener {
    void onPlayerStateChanged(int playerState);
    void onPlayStateChanged(int playState);
}

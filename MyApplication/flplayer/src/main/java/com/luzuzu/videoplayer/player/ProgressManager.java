package com.luzuzu.videoplayer.player;

/**
 * Created by fula on 2019/5/16.
 */

public abstract class ProgressManager {

    public abstract void saveProgress(String url, long progress);

    public abstract long getSavedProgress(String url);
}

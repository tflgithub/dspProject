package com.luzuzu.main.util;

import com.blankj.utilcode.util.Utils;
import com.luzuzu.videoplayer.player.IjkVideoView;

/**
 * Created by fula on 2019/5/21.
 * 无缝播放
 */

public class SeamlessPlayHelper {

    private IjkVideoView mIjkVideoView;

    private static SeamlessPlayHelper instance;

    private SeamlessPlayHelper() {
        mIjkVideoView = new IjkVideoView(Utils.getApp());
    }

    public static SeamlessPlayHelper getInstance() {
        if (instance == null) {
            synchronized (SeamlessPlayHelper.class) {
                if (instance == null) {
                    instance = new SeamlessPlayHelper();
                }
            }
        }
        return instance;
    }

    public IjkVideoView getIjkVideoView() {
        return mIjkVideoView;
    }
}

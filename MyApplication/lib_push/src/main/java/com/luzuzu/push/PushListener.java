package com.luzuzu.push;

import android.content.Context;

/**
 * Created by fula on 2019/5/15.
 */

public interface PushListener {

    void onPush(Context context, String content);
}

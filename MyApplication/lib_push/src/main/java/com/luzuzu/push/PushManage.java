package com.luzuzu.push;

import android.content.Context;

/**
 * Created by fula on 2019/5/15.
 */

public class PushManage {

    public static void push(Context context, String content) {
        if (PushClient.getInstance().mListener != null) {
            PushClient.getInstance().mListener.onPush(context, content);
        }
    }
}

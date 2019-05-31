package com.luzuzu.push;
import android.content.Context;
import java.util.Random;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by fula on 2019/5/15.
 */

public class PushClient {

    Context mContext;
    PushListener mListener;

    private static PushClient instance;

    private PushClient() {

    }

    public static PushClient getInstance() {
        if (instance == null) {
            synchronized (PushClient.class) {
                if (instance == null) {
                    instance = new PushClient();
                }
            }
        }
        return instance;
    }

    /**
     * Push init
     *
     * @param context context
     * @return
     */
    public PushClient init(Context context) {
        this.mContext = context;
        // JPush
        JPushInterface.setDebugMode(true);
        JPushInterface.init(context);
        return PushClient.this;
    }

    /**
     * set push alias
     *
     * @param alias alias
     */
    public PushClient setAlias(String alias) {
        JPushInterface.setAlias(mContext, new Random().nextInt(), alias);
        return PushClient.this;
    }

    /**
     * set push listener to receive message
     *
     * @param listener
     * @return
     */
    public PushClient setListener(PushListener listener) {
        this.mListener = listener;
        return PushClient.this;
    }
}

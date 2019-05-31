package com.luzuzu.library.arouter;
import android.content.Context;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.luzuzu.library.base.config.AppConfig;
/**
 * Created by fula on 2019/3/12.
 */
@Interceptor(priority = 1, name = "登录拦截")
public class LoginInterceptor implements
        IInterceptor {

    private Context mContext;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String group=postcard.getGroup();
        LogUtils.d("当前模块是:"+group);
        if (group.equals("order")) {
            boolean isLogin = AppConfig.INSTANCE.isLogin();
            if (isLogin) {
                callback.onContinue(postcard);
            } else {//没有登录,注意需要传入context
                ARouter.getInstance().build(ARouterConstant.ACTIVITY_LOGIN).navigation(mContext);
            }
        } else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
        // 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
        this.mContext = context;
    }
}

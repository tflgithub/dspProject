package com.luzuzu.library.arouter;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.luzuzu.library.base.mvp.BaseFragment;
import com.luzuzu.library.utils.LogUtils;

/**
 * Created by fula on 2019/3/7.
 * 路由转跳工具
 */

public class ARouterUtils {

    /**
     * 在activity中添加
     * @param activity              activity
     */
    public static void injectActivity(FragmentActivity activity){
        if (activity==null){
            return;
        }
        ARouter.getInstance().inject(activity);
    }

    /**
     * 在fragment中添加
     * @param fragment              fragment
     */
    public static void injectFragment(Fragment fragment){
        if (fragment==null){
            return;
        }
        ARouter.getInstance().inject(fragment);
    }

    /**
     * 销毁资源
     */
    public static void destroy(){
        LogUtils.i("销毁路由资源");
        ARouter.getInstance().destroy();
    }

    /**
     * 根据path返回Fragment
     *
     * @param path path
     * @return fragment
     */
    public static BaseFragment getFragment(String path) {
        return (BaseFragment) ARouter.getInstance()
                .build(path)
                .navigation();
    }

    /**
     * 简单的跳转页面
     * @param string                string目标界面对应的路径
     */
    public static void navigation(String string){
        if (string==null){
            return;
        }
        ARouter.getInstance()
                .build(string)
                .navigation();
    }

    /**
     * 简单的跳转页面
     * @param string                string目标界面对应的路径
     */
    public static void navigationGroup(String string , String group){
        if (string==null){
            return;
        }
        ARouter.getInstance()
                .build(string , group)
                .navigation();
    }

    /**
     * 简单的跳转页面
     * @param uri                   uri
     */
    public static void navigation(Uri uri){
        if (uri==null){
            return;
        }
        ARouter.getInstance()
                .build(uri)
                .navigation();
    }


    /**
     * 携带参数跳转页面
     * @param path                  path目标界面对应的路径
     * @param bundle                bundle参数
     */
    public static void navigation(String path , Bundle bundle){
        if (path==null || bundle==null){
            return;
        }
        ARouter.getInstance()
                .build(path)
                .with(bundle)
                .navigation();
    }


    /**
     * 跨模块实现ForResult返回数据（activity中使用）,在fragment中使用不起作用
     * 携带参数跳转页面
     * @param path                  path目标界面对应的路径
     * @param bundle                bundle参数
     */
    public static void navigation(String path , Bundle bundle , Activity context , int code){
        if (path==null){
            return;
        }
        if (bundle==null){
            ARouter.getInstance()
                    .build(path)
                    .navigation(context,code);
        }else {
            ARouter.getInstance()
                    .build(path)
                    .with(bundle)
                    .navigation(context,code);
        }
    }

    /**
     * 使用绿色通道(跳过所有的拦截器)
     * @param path                  path目标界面对应的路径
     * @param green                 是否使用绿色通道
     */
    public static void navigation(String path , boolean green){
        if (path==null){
            return;
        }
        if (green){
            ARouter.getInstance()
                    .build(path)
                    .greenChannel()
                    .navigation();
        }else {
            ARouter.getInstance()
                    .build(path)
                    .navigation();
        }
    }
}

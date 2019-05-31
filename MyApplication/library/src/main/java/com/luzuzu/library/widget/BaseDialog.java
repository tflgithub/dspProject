package com.luzuzu.library.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by fula on 2019/5/17.
 */

public abstract class BaseDialog extends Dialog {

    public View mCreateView;
    public Context mContext;
    public int mScreenWidth;//屏幕宽  
    public int mScreenHeight;//屏幕高  
    public int mDensity;//单位像素  
    public Animation mExitAnim;//退出动画
    public Animation mEnterAnim;//进入动画

    public BaseDialog(Context context, int resId) {
        super(context, resId);
        init(context);
    }

    public BaseDialog(Context context) {
        super(context);
        init(context);
    }


    private void init(Context context) {
        mContext = context;
        //计算屏幕的宽高像素  
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        mScreenHeight = metrics.heightPixels;
        mScreenWidth = metrics.widthPixels;
        mDensity = (int) metrics.density;
    }

    @Override
    public void show() {
        super.show();
        enterAnimation();//进入动画
    }

    //进入动画
    private void enterAnimation() {
        if (mEnterAnim == null) {
            mEnterAnim = new TranslateAnimation(1, 0, 1, 0, 1, 1, 1, 0);
            mEnterAnim.setDuration(500);
        }
        mCreateView.startAnimation(mEnterAnim);
    }

    //退出动画
    private void exitAnimation() {
        if (mExitAnim == null) {
            mExitAnim = new TranslateAnimation(1, 0, 1, 0, 1, 0, 1, 1);
            mExitAnim.setDuration(500);
            mExitAnim.setAnimationListener(
                    new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            dismissDialog(); //动画完成执行关闭  
                        }
                    });
        }
        mCreateView.startAnimation(mExitAnim);
    }


    private void dismissDialog() {
        super.dismiss();
    }

    @Override
    public void dismiss() {
        exitAnimation();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            dismiss();
        }
        return super.onKeyDown(keyCode, event);
    }
}

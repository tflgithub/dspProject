package com.luzuzu.library.utils.animation;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by fula on 2019/3/7.
 * 补间动画工具类
 */

public class AnimationsUtils {

    /**
     * 旋转 Rotate
     */
    public static Animation getRotateAnimation(float fromDegrees, float toDegrees, long durationMillis) {
        RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(durationMillis);
        rotate.setFillAfter(true);
        return rotate;
    }

    /**
     * 透明度 Alpha
     */
    public static Animation getAlphaAnimation(float fromAlpha, float toAlpha, long durationMillis) {
        AlphaAnimation alpha = new AlphaAnimation(fromAlpha, toAlpha);
        alpha.setDuration(durationMillis);          //设置持续时间
        alpha.setFillAfter(true);                   //动画结束后保留结束状态
        //alpha.setInterpolator(new AccelerateInterpolator());        //添加差值器
        return alpha;
    }

    /**
     * 缩放 Scale
     */
    public static Animation getScaleAnimation(float scaleXY, long durationMillis) {
        ScaleAnimation scale = new ScaleAnimation(1.0f, scaleXY, 1.0f, scaleXY, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(durationMillis);
        //scale.setFillAfter(true);
        return scale;
    }


    /**
     * 位移 Translate
     */
    public static Animation getTranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, long durationMillis) {
        TranslateAnimation translate = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        translate.setDuration(durationMillis);
        translate.setFillAfter(true);
        return translate;
    }

    /**
     * 点击控件缩放动画
     * @param scaleXY
     * @param durationMillis
     * @return
     */
    public static Animation clickAnimation(float scaleXY, long durationMillis) {
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(getScaleAnimation(scaleXY, durationMillis));
        set.setDuration(durationMillis);
        return set;
    }
}

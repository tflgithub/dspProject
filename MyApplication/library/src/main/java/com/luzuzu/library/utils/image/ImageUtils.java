package com.luzuzu.library.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.luzuzu.library.utils.transformation.GlideRoundTransform;

/**
 * Created by fula on 2019/3/7.
 * 图片工具类
 */

public class ImageUtils {

    /**
     * 加载网络图片
     *
     * @param url       url
     * @param imageView imageView
     * @param imageView transformation 转换器
     */
    public static void loadImage(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions()
                        //.placeholder(R.drawable.ic_loading_image)
                        .error(new ColorDrawable(Color.WHITE))
                        .fallback(new ColorDrawable(Color.RED)))
                .into(imageView);
    }

    /**
     * 加载圆形
     *
     * @param url       url
     * @param imageView imageView
     */
    public static void loadImageCircle(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.circleCropTransform()
                        //.placeholder(R.drawable.ic_loading_image)
                        .error(new ColorDrawable(Color.WHITE))
                        .fallback(new ColorDrawable(Color.RED)))
                .into(imageView);
    }


    /**
     * 加载圆角图片
     * @param imageView
     * @param url
     * @param radius
     */
    public static void loadImageRound(ImageView imageView, String url,int radius) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(imageView.getContext(),radius));
        Glide.with(imageView.getContext())
                .load(url)
                .apply(myOptions)
                        //.placeholder(R.drawable.ic_loading_image)
                .into(imageView);
    }

}

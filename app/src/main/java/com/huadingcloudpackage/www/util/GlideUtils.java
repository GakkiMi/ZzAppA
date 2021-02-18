package com.huadingcloudpackage.www.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.widget.GlideCircleBorderTransform;

;

public class GlideUtils {
    /**
     * 10圆角
     *
     * @param context ：
     * @param imgUrl  ：
     * @param view    ：
     */
    public static void loadConner10Img(Context context, String imgUrl, ImageView view) {
//        RequestOptions o = new RequestOptions().transform(new CenterCropRoundCornerTransform(10)).placeholder(R.mipmap.logo440).error(R.mipmap.logo440);
//        Glide.with(context).load(imgUrl).apply(o).into(view);
    }


    /**
     * 加载圆形图片
     *
     * @param context ：
     * @param imgUrl  ：
     * @param view    ：
     */
    public static void showRoundImg(Context context, String imgUrl, ImageView view) {
        RequestOptions requestOptions = new RequestOptions().circleCrop().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher);
        Glide.with(context).load(imgUrl).apply(requestOptions).into(view);
    }


    /**
     * 加载普通图片
     *
     * @param context ：
     * @param imgUrl  ：
     * @param view    ：
     */
    public static void showRoundImgCenterCrop(Context context, String imgUrl, ImageView view) {
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.mipmap.icon_loading_fail).error(R.mipmap.icon_loading_fail);
        Glide.with(context).load(imgUrl).apply(requestOptions).into(view);
    }



    /**
     * 普通的图片加载
     *
     * @param context ：
     * @param imgUrl  ：
     * @param view    ：
     */
    public static void showGildeImg(Context context, String imgUrl, ImageView view) {
        RequestOptions o = new RequestOptions()
                .placeholder(R.mipmap.icon_loading_fail)    //加载成功之前占位图
                .error(R.mipmap.icon_loading_fail)    //加载错误之后的错误图
                .centerInside()
                .skipMemoryCache(false)    //使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);    //只缓存最终的图片
        Glide.with(context).load(imgUrl).apply(o).into(view);
    }


    /**
     * 圆角图片加载
     *
     * @param context        上下文
     * @param imgUrl         图片url
     * @param view           展示的view
     * @param roundingRadius 圆角大小
     */
    public static void showRoudnGildeImg(Context context, String imgUrl, ImageView view, int roundingRadius) {
        RoundedCorners corners = new RoundedCorners(roundingRadius);
        RequestOptions o = new RequestOptions()
                .placeholder(R.mipmap.icon_login_portrait)    //加载成功之前占位图
                .error(R.mipmap.icon_login_portrait)    //加载错误之后的错误图
                .centerInside()
                .skipMemoryCache(false)    //使用内存缓存
                .transform(corners)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);    //只缓存最终的图片
        Glide.with(context).load(imgUrl).apply(o).into(view);
    }


    /**
     * 圆角带边框图片加载
     *
     * @param context        上下文
     * @param imgUrl         图片url
     * @param view           展示的view
     * @param borderWidth    边框宽度
     * @param borderColor    边框颜色
     * @param cornerRadius   圆角大小
     * @param showCornerType 要展示的任意圆角的类型
     */
    public static void showRoundBorderGildeImg(Context context, String imgUrl, ImageView view, float borderWidth, int borderColor, int cornerRadius, int showCornerType) {
        boolean isCache = true;
        GlideCircleBorderTransform circleBorderTransform = new GlideCircleBorderTransform(borderWidth, borderColor, cornerRadius, showCornerType);

        RequestBuilder<Drawable> transforms = Glide
                .with(context)
                .load(R.mipmap.icon_loading_fail)
                .transform(circleBorderTransform);

        Glide.with(context)
                .load(imgUrl)
                .thumbnail(transforms)
                .diskCacheStrategy(isCache ? DiskCacheStrategy.RESOURCE : DiskCacheStrategy.NONE)   //只缓存最终的图片
                .skipMemoryCache(isCache ? false : true)
                .transform(circleBorderTransform)
                .into(view);


//        GlideCircleBorderTransform circleBorderTransform = new GlideCircleBorderTransform(borderWidth, borderColor, cornerRadius, showCornerType);
//        RequestOptions o = new RequestOptions()
//                .placeholder(R.mipmap.icon_loading_fail)    //加载成功之前占位图
//                .error(R.mipmap.icon_loading_fail)    //加载错误之后的错误图
//                .transform(new CenterInside(), circleBorderTransform)
//                .diskCacheStrategy(isCache ? DiskCacheStrategy.RESOURCE : DiskCacheStrategy.NONE)   //只缓存最终的图片
//                .skipMemoryCache(isCache ? false : true);   //使用内存缓存
//        Glide.with(context).load(imgUrl).apply(o).into(view);
    }


}

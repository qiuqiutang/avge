package com.example.lyfuelgas.common.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.lyfuelgas.R;

/**
 * Created by QiuYan on 2018/8/23.
 */

public class GlideUtils {
    /**
     * Glide特点
     * 使用简单
     * 可配置度高，自适应程度高
     * 支持常见图片格式 Jpg png gif webp
     * 支持多种数据源  网络、本地、资源、Assets 等
     * 高效缓存策略    支持Memory和Disk图片缓存 默认Bitmap格式采用RGB_565内存使用至少减少一半
     * 生命周期集成   根据Activity/Fragment生命周期自动管理请求
     * 高效处理Bitmap  使用Bitmap Pool使Bitmap复用，主动调用recycle回收需要回收的Bitmap，减小系统回收压力
     * 这里默认支持Context，Glide支持Context,Activity,Fragment，FragmentActivity
     */


    //默认加载
    public static void loadImageView(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).into(mImageView);
    }


    //默认加载还默认图片
    public static void loadImageView(Context mContext,int placeResId, String path, ImageView mImageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeResId)
                .error(placeResId)
                .diskCacheStrategy(DiskCacheStrategy.ALL);;
        Glide.with(mContext).load(path).apply(options).into(mImageView);
    }

    //默认加载
    public static void loadImageView(Context mContext, int resId, ImageView mImageView) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)    //缓存所有版本的图像
                ;
        Glide.with(mContext).load(resId).apply(options).into(mImageView);
    }

    //加载指定大小
    public static void loadImageViewSize(Context mContext, String path, int width, int height, ImageView mImageView) {
        RequestOptions options = new RequestOptions()
                .override(width, height);
        Glide.with(mContext).load(path).apply(options).into(mImageView);
    }

    //设置加载中以及加载失败图片
    public static void loadImageViewError(Context mContext, String path, ImageView mImageView, int errorImageView) {
        RequestOptions options = new RequestOptions()
                .error(errorImageView);
        Glide.with(mContext).load(path).apply(options).into(mImageView);
    }

    //设置加载中以及加载失败图片
    public static void loadImageViewLoding(Context mContext, String path, ImageView mImageView, int lodingImage, int errorImageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(lodingImage)
                .error(errorImageView)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(mContext).load(path).apply(options).into(mImageView);
    }

    //设置加载中以及加载失败图片并且指定大小
    public static void loadImageViewLodingSize(Context mContext, String path, int width, int height, ImageView mImageView, int lodingImage, int errorImageView) {
        RequestOptions options = new RequestOptions()
                .override(width, height)
                .placeholder(lodingImage)
                .error(errorImageView);
        Glide.with(mContext).load(path).apply(options).into(mImageView);
    }

    //设置跳过内存缓存
    public static void loadImageViewCache(Context mContext, String path, ImageView mImageView) {
        RequestOptions options = new RequestOptions()
                .skipMemoryCache(true);
        Glide.with(mContext).load(path).apply(options).into(mImageView);
    }

    //设置下载优先级
    public static void loadImageViewPriority(Context mContext, String path, ImageView mImageView) {
        RequestOptions options = new RequestOptions()
                .priority(Priority.NORMAL);
        Glide.with(mContext).load(path).apply(options).into(mImageView);
    }


    //加载圆形图片
    public static void loadCircleImage(final Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.img_avator)
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)    //缓存所有版本的图像
                ;
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(options) //设置缓存
                .into(imageView);
    }

    //加载圆形图片带默认头像
    public static void loadCircleImage(final Context context, int resId, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(resId)
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)    //缓存所有版本的图像
                ;
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(options)
                .into(imageView);
    }


    //加载圆角图片
    public static void loadRoundedImage(final Context context, String url, final ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.img_avator)
                .transform(new GlideRoundTransform(DensityUtils.dp2px(10), GlideRoundTransform.CornerType.ALL))
                .diskCacheStrategy(DiskCacheStrategy.ALL)    //缓存所有版本的图像
                ;
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 策略解说：
     * <p>
     * all:缓存源资源和转换后的资源
     * <p>
     * none:不作任何磁盘缓存
     * <p>
     * source:缓存源资源
     * <p>
     * result：缓存转换后的资源
     */

    //设置缓存策略
    public static void loadImageViewDiskCache(Context mContext, String path, ImageView mImageView) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)    //缓存所有版本的图像
                ;
        Glide.with(mContext).load(path).apply(options).into(mImageView);
    }

    /**
     * api也提供了几个常用的动画：比如crossFade()
     */


    /**
     * 会先加载缩略图
     */

    //设置缩略图支持
    public static void loadImageViewThumbnail(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).thumbnail(0.1f).into(mImageView);
    }

    /**
     * api提供了比如：centerCrop()、fitCenter()等
     */

    //设置动态转换
    public static void loadImageViewCrop(Context mContext, String path, ImageView mImageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop();
        Glide.with(mContext).load(path).apply(options).into(mImageView);
    }

    //设置动态GIF加载方式
    public static void loadImageViewDynamicGif(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).asGif().load(path).into(mImageView);
    }

    //设置静态GIF加载方式
    public static void loadImageViewStaticGif(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).asBitmap().load(path).into(mImageView);
    }

    //设置监听的用处 可以用于监控请求发生错误来源，以及图片来源 是内存还是磁盘

    //设置监听请求接口
    public static void loadImageViewListener(Context mContext, String path, ImageView mImageView, RequestListener<Drawable> requstlistener) {
        Glide.with(mContext).load(path).listener(requstlistener).into(mImageView);
    }

    //项目中有很多需要先下载图片然后再做一些合成的功能，比如项目中出现的图文混排

    //设置要加载的内容
    public static void loadImageViewContent(Context mContext, String path, SimpleTarget<Drawable> simpleTarget) {
        RequestOptions options = new RequestOptions()
                .centerCrop();
        Glide.with(mContext).load(path).apply(options).into(simpleTarget);
    }

    //清理磁盘缓存
    public static void GuideClearDiskCache(Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext).clearDiskCache();
    }

    //清理内存缓存
    public static void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }



}

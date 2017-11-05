package com.djtenacity.imageloader.loader;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.djtenacity.imageloader.cache.BitmapCache;
import com.djtenacity.imageloader.config.DisplayConfig;
import com.djtenacity.imageloader.policy.LoadPolicy;
import com.djtenacity.imageloader.request.BitmapRequest;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/11/2
 */
public abstract class AbstractLoader implements Loader {
    //拿到用户自定义配置的缓存策略
    private BitmapCache bitmapCache = SimpleImageLoader.getInstance().getConfig().getBitmapCache();

    //拿到显示配置
    private DisplayConfig displayConfig = SimpleImageLoader.getInstance().getConfig().getDisplayConfig();

    @Override
    public void loadImage(BitmapRequest request) {
        Log.w("loadImage","AbstractLoader request");
        //从缓存中取到bitmap
        Bitmap bitmap = bitmapCache.get(request);
        if (bitmap == null) {
            //显示默认加载中图片
            showLoadingImage(request);
            //开始真正的加载图片
            bitmap = onLoad(request);
            //缓存图片
            cacheBitmap(request, bitmap);
        }
        deliveryToUIThread(request,bitmap);
    }

    /**
     * 交给主线程显示
     * @param request
     * @param bitmap
     */
    protected void deliveryToUIThread(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        if(imageView!=null)
        {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    updateImageView(request, bitmap);
                }

            });
        }

    }
    private void updateImageView(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        //加载正常  防止图片错位
        if(bitmap != null && imageView.getTag().equals(request.getImageUrl())){
            imageView.setImageBitmap(bitmap);
        }
        //有可能加载失败
        if(bitmap == null && displayConfig!=null&&displayConfig.failImage!=-1){
            imageView.setImageResource(displayConfig.failImage);
        }
        //监听
        //回调 给圆角图片  特殊图片进行扩展
        if(request.imageListener != null){
            request.imageListener.onComplete(imageView, bitmap, request.getImageUrl());
        }
    }
    /**
     * 缓存图片
     */
    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {
        if (request != null && bitmap != null) {
            synchronized (AbstractLoader.class)
            {
                bitmapCache.put(request,bitmap);
            }
        }

    }

    //显示默认加载中图片
    private void showLoadingImage(BitmapRequest request) {
        if (hasLoadingPlaceHolder()) {
            final ImageView imageView = request.getImageView();
            if (imageView != null) {
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(displayConfig.loadingImage);
                    }
                });
            }
        }
    }
    //抽象的加载策略
    protected abstract Bitmap onLoad(BitmapRequest request);

    protected boolean hasLoadingPlaceHolder() {
        return (displayConfig != null && displayConfig.loadingImage > 0);
    }

}

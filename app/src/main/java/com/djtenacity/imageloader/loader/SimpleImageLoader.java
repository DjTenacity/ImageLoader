package com.djtenacity.imageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.djtenacity.imageloader.config.DisplayConfig;
import com.djtenacity.imageloader.config.ImageLoaderConfig;
import com.djtenacity.imageloader.request.BitmapRequest;
import com.djtenacity.imageloader.request.RequestQueue;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/11/2
 */
public class SimpleImageLoader {
    // 配置文件
    private ImageLoaderConfig config;

    //请求队列
    private RequestQueue requestQueue;

    //单例
    private static volatile SimpleImageLoader mInstance;

    private SimpleImageLoader() {

    }

    private SimpleImageLoader(ImageLoaderConfig config) {
        this.config = config;
        requestQueue = new RequestQueue(config.getThreadCount());
        //开启请求队列
        requestQueue.start();
    }

    public static SimpleImageLoader getInstance(ImageLoaderConfig config) {
        if (mInstance == null) {
            synchronized (SimpleImageLoader.class) {
                mInstance = new SimpleImageLoader(config);
            }
        }
        return mInstance;
    }

    /**
     * 第二次获取单例
     */
    public static SimpleImageLoader getInstance() {
        if (mInstance == null) {
            throw new UnsupportedOperationException("没有初始化");
        }
        return mInstance;
    }

    /**
     * 暴露获取图片
     *
     * @param imageView the image view
     * @param uri       file开头
     */
    public void displayImage(ImageView imageView, String uri) {
        displayImage(imageView, uri, null, null);
    }

    /**
     * 重载
     *
     * @param imageView the image view
     * @param uri       the uri
     * @param config    the config
     * @param listener  the listener
     */
    public void displayImage(ImageView imageView, String uri,
                             DisplayConfig config, ImageListener listener) {
        //实例化一个请求
        BitmapRequest bitmapRequest = new BitmapRequest(imageView, uri, listener, config);
        //添加到队列里面
        requestQueue.addRequest(bitmapRequest);
        requestQueue.start();
    }

    public static interface ImageListener {
        void onComplete(ImageView imageView, Bitmap bitmap, String uri);
    }

    /**
     * 拿到全局配置
     */
    public ImageLoaderConfig getConfig() {
        return config;
    }
}

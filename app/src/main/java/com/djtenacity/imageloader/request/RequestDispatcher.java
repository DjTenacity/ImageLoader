package com.djtenacity.imageloader.request;

import android.util.Log;

import com.djtenacity.imageloader.loader.Loader;
import com.djtenacity.imageloader.loader.LoaderManager;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * Comment:转发器
 * <p>
 * 请求转发线程,不断从请求队列中获取请求
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/11/2
 */
public class RequestDispatcher extends Thread {
    /**
     * 请求队列
     */
    private BlockingQueue<BitmapRequest> mRequestQueue;

    public RequestDispatcher(BlockingQueue<BitmapRequest> mRequestQueue) {
        this.mRequestQueue = mRequestQueue;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                //阻塞式函数
                BitmapRequest request = mRequestQueue.take();
                /**
                 * 处理请求对象
                 */
                String scheme = parseSchema(request.getImageUrl());
                //获取loader加载器
                Loader loader = LoaderManager.getInstance().getLoader(scheme);
                loader.loadImage(request);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private String parseSchema(String imageUrl) {
        //网络图片:http开头  本地 file://开头

        if (imageUrl.contains("://")) {
            return imageUrl.split("://")[0];
        } else {
            Log.i("parseSchema", "不支持此类型");
        }

        return null;
    }
}

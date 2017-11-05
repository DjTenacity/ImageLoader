package com.djtenacity.imageloader.cache;

import android.graphics.Bitmap;

import com.djtenacity.imageloader.request.BitmapRequest;

/**
 * Comment: 缓存策略
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/11/2
 */
public interface BitmapCache {
    /**
     * 缓存bitmap
     */
    void put(BitmapRequest request, Bitmap bitmap);


    /**通过请求取bitmap*/
    Bitmap get(BitmapRequest request);

    /**移除*/
    void remove(BitmapRequest request);


}

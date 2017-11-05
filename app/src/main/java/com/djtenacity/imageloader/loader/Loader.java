package com.djtenacity.imageloader.loader;

import com.djtenacity.imageloader.request.BitmapRequest;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/11/2
 */
public interface Loader {

    /**加载图片*/
    void loadImage(BitmapRequest request);
}

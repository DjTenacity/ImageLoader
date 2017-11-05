package com.djtenacity.imageloader.loader;

import android.graphics.Bitmap;

import com.djtenacity.imageloader.request.BitmapRequest;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/11/4
 */
public class NullLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        return null;
    }
}

package com.djtenacity.imageloader.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Comment:图片解码器
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/11/4
 */
public abstract class BitmapDecoder {

    public Bitmap decodeBitmap(int reqWidth, int reqHeight) {
        //初始化options
        BitmapFactory.Options options = new BitmapFactory.Options();

        //true--->获取边界信息,只需要读取读片宽高信息,而不去将整张图片加载到内存中,优化的一种方式
        //自适应的前提是去先加载图片的宽和高
        options.inJustDecodeBounds = true;
        //图片要读两次

        //计算图片的缩放比例饿
        caculateSapleSizeWithOption(options, reqWidth, reqHeight);

        return decodeBitmapWithOption(options);

    }

    /**
     * 计算图片缩放的比例
     */
    private void caculateSapleSizeWithOption(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        //计算缩放的比例
        //图片的原始宽高
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;
        //  reqWidth   ImageView的  宽
        if (width > reqWidth || height > reqHeight) {
            //宽高的缩放比例
            int heightRatio = Math.round((float) height / (float) reqHeight);
            int widthRatio = Math.round((float) width / (float) reqWidth);

            //有的图是长图、有的是宽图
            inSampleSize = Math.max(heightRatio, widthRatio);
        }

        //全景图
        //当inSampleSize为2，图片的宽与高变成原来的1/2
        //options.inSampleSize = 2
        options.inSampleSize = inSampleSize;

        //每个像素2个字节
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //Bitmap占用内存  true
        options.inJustDecodeBounds = false;
        //当系统内存不足时可以回收Bitmap
        options.inPurgeable = true;
        options.inInputShareable = true;


    }

    public abstract Bitmap decodeBitmapWithOption(BitmapFactory.Options options);

}

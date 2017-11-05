package com.djtenacity.imageloader.request;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.djtenacity.imageloader.config.DisplayConfig;
import com.djtenacity.imageloader.loader.SimpleImageLoader;
import com.djtenacity.imageloader.policy.LoadPolicy;
import com.djtenacity.imageloader.utils.MD5Utils;

import java.lang.ref.SoftReference;
import java.util.Comparator;

/**
 * Comment:请求
 * implements Comparator排序
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/11/2
 */
public class BitmapRequest implements Comparable<BitmapRequest> {
    //持有ImageView的软引用
    private SoftReference<ImageView> softImageView;
    //图片路径
    private String imageUrl;
    //MD 5的图片路径,让其非法字符合法化
    private String imageUriMD5;
    private DisplayConfig displayConfig;
    //下载完成监听
    public SimpleImageLoader.ImageListener imageListener;

    public BitmapRequest(ImageView imageView, String imageUrl,
                         SimpleImageLoader.ImageListener imageListener, DisplayConfig displayConfig) {
        this.imageUrl = imageUrl;
        //加密成16进制
        this.imageUriMD5 = MD5Utils.toMD5(imageUrl);
        this.imageListener = imageListener;
        this.softImageView = new SoftReference<ImageView>(imageView);
        //设置可见的image的Tag,要下载的图片路径
        imageView.setTag(imageUrl);
        if (displayConfig != null) {
            this.displayConfig = displayConfig;
        }
    }

    //加载策略
    private LoadPolicy loadPolicy = SimpleImageLoader.getInstance().getConfig().getLoadPolicy();
    /**
     * 优先级编号
     */
    private int serialNo;

    /**
     * 排序,处理优先级问题
     */
    @Override
    public int compareTo(@NonNull BitmapRequest o) {
        return loadPolicy.compareto(o, this);
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public ImageView getImageView() {
        return softImageView.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BitmapRequest that = (BitmapRequest) o;

        if (serialNo != that.serialNo) return false;
        return loadPolicy != null ? loadPolicy.equals(that.loadPolicy) : that.loadPolicy == null;
    }

    @Override
    public int hashCode() {
        int result = loadPolicy != null ? loadPolicy.hashCode() : 0;
        result = 31 * result + serialNo;
        return result;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUriMD5() {
        return imageUriMD5;
    }

    public void setImageUriMD5(String imageUriMD5) {
        this.imageUriMD5 = imageUriMD5;
    }


}

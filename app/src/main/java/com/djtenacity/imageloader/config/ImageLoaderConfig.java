package com.djtenacity.imageloader.config;

import com.djtenacity.imageloader.cache.BitmapCache;
import com.djtenacity.imageloader.cache.DoubleCache;
import com.djtenacity.imageloader.cache.MemoryCache;
import com.djtenacity.imageloader.policy.LoadPolicy;
import com.djtenacity.imageloader.policy.ReversePolicy;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/11/2
 */
public class ImageLoaderConfig {

    //缓存策略
    private BitmapCache bitmapCache=new MemoryCache();

    //加载策略
    private LoadPolicy loadPolicy =new ReversePolicy();

    //默认线程数
    private int threadCount = Runtime.getRuntime().availableProcessors();
    //显示的配置
    private DisplayConfig displayConfig=new DisplayConfig();

    //图片
    private ImageLoaderConfig() {

    }

    /**
     * 建造者模式
     * 和AlterDialog建造者过程类似
     **/
    public static class Builder {
        private ImageLoaderConfig config;

        public Builder() {
            config = new ImageLoaderConfig();
        }

        /**
         * 设置缓存策略
         **/
        public Builder setCachePolicy(BitmapCache bitmapCache) {
            config.bitmapCache = bitmapCache;
            return this;
        }

        /**
         * 设置加载策略
         */
        public Builder setLoadPolicy(LoadPolicy loadPolicy) {
            config.loadPolicy = loadPolicy;
            return this;
        }

        /**
         * 设置线程个数
         */
        public Builder setThreadCount(int count) {
            config.threadCount = count;
            return this;
        }

        /**
         * 设置加载中的图片
         */
        public Builder setLoadingImage(int resID) {
            config.displayConfig.loadingImage = resID;
            return this;
        }

        /**
         * 设置加载中的图片
         */
        public Builder setFailImage(int resID) {
            config.displayConfig.failImage = resID;
            return this;
        }

        public ImageLoaderConfig build() {
            return config;
        }

    }

    public BitmapCache getBitmapCache() {
        return bitmapCache;
    }

    public void setBitmapCache(BitmapCache bitmapCache) {
        this.bitmapCache = bitmapCache;
    }

    public LoadPolicy getLoadPolicy() {
        return loadPolicy;
    }

    public void setLoadPolicy(LoadPolicy loadPolicy) {
        this.loadPolicy = loadPolicy;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public DisplayConfig getDisplayConfig() {
        return displayConfig;
    }

    public void setDisplayConfig(DisplayConfig displayConfig) {
        this.displayConfig = displayConfig;
    }
}

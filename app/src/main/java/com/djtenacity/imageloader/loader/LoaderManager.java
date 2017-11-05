package com.djtenacity.imageloader.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * Comment: 系统服务的
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/11/4
 */
public class LoaderManager {
    //缓存所有支持的loader类型
    private Map<String, Loader> mLoaderMap = new HashMap<>();

    private static LoaderManager mInstance = new LoaderManager();

    private LoaderManager() {
        register("http", new UrlLoader());
        register("https", new UrlLoader());
        register("file", new LocalLoader());
    }

    public static LoaderManager getInstance() {
        return mInstance;
    }

    private void register(String scheme, Loader loader) {
        mLoaderMap.put(scheme, loader);
    }

    public Loader getLoader(String scheme) {
        if (mLoaderMap.containsKey(scheme)) {
            return mLoaderMap.get(scheme);
        }
        return new NullLoader();
    }

}

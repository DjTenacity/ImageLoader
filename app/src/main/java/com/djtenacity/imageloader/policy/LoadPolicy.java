package com.djtenacity.imageloader.policy;

import com.djtenacity.imageloader.request.BitmapRequest;

/**
 * Comment: 加载策略
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/11/2
 */
public interface LoadPolicy {
    /**
     * 两个BitmapRequest进行优先级比较
     */
    int compareto(BitmapRequest request1, BitmapRequest request2);
}

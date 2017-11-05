package com.djtenacity.imageloader.policy;

import com.djtenacity.imageloader.request.BitmapRequest;

/**
 * Comment: 后进先出
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/11/2
 */
public class ReversePolicy implements LoadPolicy {
    @Override
    public int compareto(BitmapRequest request1, BitmapRequest request2) {
        return request2.getSerialNo() - request1.getSerialNo();
    }
}

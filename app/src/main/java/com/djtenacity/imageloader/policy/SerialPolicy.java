package com.djtenacity.imageloader.policy;

import com.djtenacity.imageloader.request.BitmapRequest;

/**
 * Comment:先进先出
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/11/2
 */
public class SerialPolicy implements LoadPolicy {
    @Override
    public int compareto(BitmapRequest request1, BitmapRequest request2) {
        return request1.getSerialNo() - request2.getSerialNo();

    }
}

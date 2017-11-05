package com.djtenacity.imageloader.request;

import android.util.Log;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/11/2
 */
public class RequestQueue {
    /**
     * 阻塞式队列,
     * PriorityBlockingQueue源码
     * <p>
     * 多线程共享
     * 生产效率和消费效率相差太远
     * <p>
     * 使用优先级队列
     * 优先级搞得队列优先被消费
     * <p>
     * 每一个产品都有编号
     * 上一次volley是链表队列
     */
    private BlockingQueue<BitmapRequest> mRequestQueue = new PriorityBlockingQueue<BitmapRequest>();

    /**
     * 转发器的数量
     */
    private int threadCount;
    //i++  ++i 能 1 ,不能 2  线程安全
    private AtomicInteger i = new AtomicInteger(0);

    /**
     * 一组转发器
     */
    private RequestDispatcher[] requestDispatcher;

    public RequestQueue(int threadCount) {
        this.threadCount = threadCount;
    }

    /**
     * 添加请求对象
     **/
    public void addRequest(BitmapRequest request) {

        Log.w("loadImage","addRequest request");

        //判断请求队列是否包含请求
        if (!mRequestQueue.contains(request)) {
            //给请求进行编号
            request.setSerialNo(i.incrementAndGet());
            mRequestQueue.add(request);
        } else {
            Log.i("RequestQueue", "请求已经存在 ,编号:" + request.getSerialNo());
        }
    }

    /**
     * 开启请求
     */
    public void start() {
        //开启之前先停止(安全)
        stop();
        startDispatchers();
    }

    /**
     * 开启转发器
     */
    private void startDispatchers() {
        requestDispatcher = new RequestDispatcher[threadCount];
        for (int i = 0; i < threadCount; i++) {//默认CPU个数
            RequestDispatcher p=new RequestDispatcher(mRequestQueue);
            requestDispatcher[i]=p;
            requestDispatcher[i].start();
        }
    }

    /**
     * 停止请求
     */
    public void stop() {

    }
}

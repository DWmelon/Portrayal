package com.portrayal.open.bean;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PortrayalBeanFactory {

    /**
     * 对象缓存池
     */
    private static BlockingQueue<PageSBean> mCachePageQueue;

    /**
     * 从对象缓存池中获取对象
     * 避免对象的频繁创建和销毁，导致内存抖动
     * @return
     */
    public static PageSBean obtain(){
        if (mCachePageQueue == null){
            mCachePageQueue = new LinkedBlockingQueue<>();
            return new PageSBean();
        }
        if (mCachePageQueue.isEmpty()){
            return new PageSBean();
        }
        PageSBean pageSBean;
        synchronized(PortrayalBeanFactory.class){
            if (mCachePageQueue.isEmpty()){
                pageSBean = new PageSBean();
            }else {
                pageSBean = mCachePageQueue.poll();
                pageSBean.reset();
            }
        }
        return pageSBean;
    }

    /**
     * 将废弃对象放入缓存队列
     * @param beans
     */
    public static void enqueue(LinkedBlockingQueue<PageSBean> beans){
        synchronized(PortrayalBeanFactory.class){
            mCachePageQueue.addAll(beans);
        }
    }

}

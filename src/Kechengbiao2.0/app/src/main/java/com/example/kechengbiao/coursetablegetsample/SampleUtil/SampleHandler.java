package com.example.kechengbiao.coursetablegetsample.SampleUtil;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

//https://blog.csdn.net/baidu_28741289/article/details/48137007
/** @brief 小工具，用WeakReference，防止消息循环里循环引用导致内存泄漏*/
public abstract class SampleHandler<T extends AppCompatActivity> extends android.os.Handler {
    WeakReference<T> weakReference;
    /** @brief 存入活动的引用*/
    public SampleHandler(T t) {
        this.weakReference = new WeakReference<>(t);
    }
    /** @brief 获取活动的引用*/
    public T getT(){
        return weakReference.get();
    }
    @Override
    public abstract void  handleMessage(Message msg) ;
}

//https://www.jianshu.com/p/e914cda1b5fe
//https://blog.csdn.net/weixin_40291968/article/details/80610212
//Handler的作用：它把消息发送给Looper管理的MessageQueue，并负责处理Looper分发给他的消息。

//Handler,在主线程



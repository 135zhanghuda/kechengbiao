package com.example.kechengbiao.coursetablegetsample.SampleUtil.OkHttp;

import android.os.Handler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/** @brief 小工具，用于简单指定网络响应的处理方式，并发送回活动 */
public class SampleOkhttpCallback extends SampleOkhttpCallbackOnResponse {
    //Handler handler;
    int what;
    Mode mode;
    boolean printf=false;
    public SampleOkhttpCallback(Handler handler, int what, Mode mode) {
        super(handler);
        //this.handler = handler;
        this.what = what;
        this.mode = mode;
    }
    public SampleOkhttpCallback(Handler handler, int what, Mode mode,int a) {
        super(handler);
        //this.handler = handler;
        this.what = what;
        this.mode = mode;
        printf=true;
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (printf){
            System.out.println(call);
            System.out.println(call.request());
            deal(response,mode,handler,what,0);
        }else {
            deal(response,mode,handler,what);
        }
    }
}



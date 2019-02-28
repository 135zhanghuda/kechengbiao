package com.example.kechengbiao.coursetablegetsample.SampleUtil.OkHttp;

import okhttp3.Callback;
import okhttp3.OkHttpClient;

/** @brief 小工具，用于复用Okhttp的线程池
 *
 * @verbatim
 * 共用一个OkHttpClient.Builder，复用Okhttp的线程池
 * 自动设置SimpleCookieJar()管理Cookie
 * @endverbatim
 */
public class SampleOkhttp {
    static OkHttpClient.Builder clientBuilder=new OkHttpClient.Builder()
            .cookieJar(new SimpleCookieJar());
    public static void asyncSend(okhttp3.Request request,okhttp3.RequestBody requestBody, Callback callback){
        if (requestBody != null){
            request=request.newBuilder().post(requestBody).build();
        }
        if(clientBuilder==null){
            clientBuilder=new OkHttpClient.Builder()
                    .cookieJar(new SimpleCookieJar());
        }
        clientBuilder.build().newCall(request).enqueue(callback);
    }
    public static void asyncSend(okhttp3.Request request, Callback callback){
        asyncSend(request,null,callback);
    }
}

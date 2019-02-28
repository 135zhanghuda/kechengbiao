package com.example.kechengbiao.coursetablegetsample.SampleUtil.OkHttp;

import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/** @brief OkHttp小工具，用了okhttp3.CookieJar接口，用于管理Cookie */
public class SimpleCookieJar implements CookieJar {
    private final ArrayMap<String , List<Cookie>> cookieStore = new ArrayMap<>();
    boolean cout=false;

    public SimpleCookieJar() {}
    /** @brief 用于调试的构建方法，取出Cookie时打印一遍 */
    public SimpleCookieJar(int i) {
        cout=true;
    }

    /** @brief OkHttp自动调用，存入Cookie */
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.put(url.host(), cookies);
    }
    /** @brief OkHttp自动调用，取出Cookie */
    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url.host());
        if(cout && cookies != null){
            for (Cookie cook:cookies
                    ) {
                System.out.println(cook);
            }
        }
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }
}
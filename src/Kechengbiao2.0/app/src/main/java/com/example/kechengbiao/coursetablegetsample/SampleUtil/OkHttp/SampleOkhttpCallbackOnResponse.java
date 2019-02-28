package com.example.kechengbiao.coursetablegetsample.SampleUtil.OkHttp;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/** @brief 小工具，将连接失败的消息发回活动
 *
 * @verbatim
 * 连接失败的错误号要提前设置好，最终没下定决心用public final static int，但这样错误号也不该被反复设置成不同值
 * 合并了小工具：将网页响应内容按指定方式处理发回活动
 * @endverbatim
 */
public abstract class SampleOkhttpCallbackOnResponse implements Callback {
    public static int what_errorInfo=-10;
    Handler handler;
    public SampleOkhttpCallbackOnResponse(Handler handler) {
        this.handler = handler;
    }
    @Override
    public void onFailure(Call call, IOException e) {
        System.out.println(call.request().headers());
        System.out.println(call.request().body().toString());
        try {
            deal(null,Mode.RESPONSE,handler,what_errorInfo);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        e.printStackTrace();
    }
    /** @brief 必须重写的方法，处理获得的网络响应
     *
     * @verbatim
     * 实际主要功能是为了在okhttp的线程，直接继续发送网络请求，
     * 不要发消息回主线程了
     * @endverbatim
     */
    public abstract void onResponse(Call call, Response response) throws IOException ;

    /** @brief Response处理方式 */
    public enum Mode{RESPONSE,STRING,BYTES,STRINGandRESPONSE,BYTESandRESPONSE}

    /** @brief 将网页响应内容按指定方式处理发回消息窗体 */
    public static void deal(Response response, Mode mode, Handler handler, int what) throws IOException {
        Message message = handler.obtainMessage();
        message.what=what;
        switch (mode){
            case RESPONSE:{
                message.obj=response;
                break;
            }
            case STRING:{
                message.obj=response.body().string();
                break;
            }
            case BYTES:{
                message.obj=response.body().bytes();
                break;
            }
            case STRINGandRESPONSE:{
                message.obj=new Object[]{response.body().string(),response};
                break;
            }
            case BYTESandRESPONSE:{
                message.obj=new Object[]{response.body().bytes(),response};
                break;
            }
            default:
                throw new IOException("SampleOkhttpCallback:被要求用未知的处理模式处理Response");
        }
        handler.sendMessage(message);
    }
    /** @brief 用于调试 */
    public static void deal(Response response, Mode mode, Handler handler, int what,int a) throws IOException{
        System.out.println("headers:"+response.headers());
        System.out.println("mode:"+mode);
        System.out.println("what:"+what);
        System.out.println("response:"+response.toString());
        System.out.println("message:"+response.message());
        System.out.println("responseurl:"+response.request().url());
        deal(response,mode,handler,what);
    }
}

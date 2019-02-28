package com.example.kechengbiao.coursetablegetsample.AcademicAffairsOffice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kechengbiao.R;
import com.example.kechengbiao.coursetablegetsample.SampleUtil.Html.SampleJsoup;
import com.example.kechengbiao.coursetablegetsample.SampleUtil.OkHttp.SampleOkhttp;
import com.example.kechengbiao.coursetablegetsample.SampleUtil.OkHttp.SampleOkhttpCallback;
import com.example.kechengbiao.coursetablegetsample.SampleUtil.OkHttp.SampleOkhttpCallbackOnResponse;
import com.example.kechengbiao.coursetablegetsample.SampleUtil.SampleHandler;
import com.example.kechengbiao.coursetablegetsample.SampleUtil.SampleSharedPreferencesLand;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Response;


/** @brief 登陆教务网活动
 *
 * @verbatim
 * 只用于登录浙江连农林大学的教务网拉取课表，处理相关网络接的响应
 * 此活动可用SharedPreferences保存账号与密码
 * @endverbatim
 */
public class LandAAOActivity extends AppCompatActivity {

    SampleSharedPreferencesLand sampleSharedPreferencesLand;
    ImageView verificatinImg;
    TextView verificatinCode;
    TextView htmlText;

    Spinner spinner_xuenian;
    Spinner spinner_xueqi;
    String[] xuenian=new String[]{"2018-2019","2017-2018","2016-2017"};
    String[] xueqi=new String[]{"1","2"};//new String[]{"1","2","3"}
    int xuenian_position=0,xueqi_position=0;
    String now_xuenian, now_xueqi;


    String imgaUrl="http://115.236.84.162/CheckCode.aspx";
    String landUrl="http://115.236.84.162/default2.aspx";
    //String Land__VIEWSTATE=null;
    //String Land__EVENTVALIDATION=null;

    String mainurl="http://115.236.84.162/xs_main.aspx?xh=201605010316";
    String mainurl1="http://115.236.84.162/xs_main.aspx?xh=";
    boolean isAreadyLand=false;

    String tableadress="http://115.236.84.162/xskbcx.aspx?xh=201605010316&gnmkdm=N121603";
    String tableadress1="http://115.236.84.162/xskbcx.aspx?gnmkdm=N121603&xh=";
    String Table__VIEWSTATE=null;
    String Table__EVENTVALIDATION=null;


    okhttp3.Request.Builder hostRequestBuilder= new okhttp3.Request.Builder()
            .addHeader("Referer","http://115.236.84.162")
            .addHeader("Content-Type","application/x-www-form-urlencoded");



    String returnHtml;


    final static int VERIFICATIN_IMG =0;
    final static int LAND=1;
    final static int COURSETABLE =2;
    final static int EXIT =-1;
    final static int okhttp_link_error =-2;


    MyHandler handler=new MyHandler(this);
    /** @brief 用WeakReference，防止消息循环里循环引用导致内存泄漏，继承于SampleHandler< LandAAOActivity >*/
    static class MyHandler extends SampleHandler<LandAAOActivity> {
        public MyHandler(LandAAOActivity landAAOActivity) {
            super(landAAOActivity);
        }
        @Override
        public void handleMessage(Message msg) {
            LandAAOActivity activity=getT();
            if(activity != null){
                switch (msg.what){
                    case VERIFICATIN_IMG:{
                        byte[] Picture = (byte[]) msg.obj;
                        Bitmap bitmap = BitmapFactory.decodeByteArray(Picture, 0, Picture.length);
                        activity.verificatinImg.setImageBitmap(bitmap);
                        break;
                    }
                    case LAND:{
                        Object[] objects=(Object[])msg.obj;
//                    htmlText.setText((String)objects[0]);
                        Response response=(Response)objects[1];
                        HttpUrl httpUrl=response.request().url();
                        if("/xs_main.aspx".equals(httpUrl.encodedPath())){
//                        System.out.println("登陆成功");
                            activity.htmlText.setText("登陆成功");
                            String use;
                            use=activity.sampleSharedPreferencesLand.edituser.getText().toString();
                            activity.tableadress=activity.tableadress1+use;
                            activity.mainurl=activity.mainurl1+use;
                            activity.isAreadyLand=true;
                            activity.get_course_table();
                        }else{
//                        System.out.println("登陆失败");
                            activity.htmlText.setText("登陆失败");
                        }
                        break;
                    }
                    case COURSETABLE:{
                        activity.returnHtml=(String)msg.obj;
//                        activity.htmlText.setText((String)msg.obj);

                        String xnd=activity.xuenian[activity.xuenian_position];
                        String xqd=activity.xueqi[activity.xueqi_position];
                        Intent intent=new Intent();
                        intent.putExtra("html",activity.returnHtml);
                        intent.putExtra("xn",xnd);
                        intent.putExtra("xq",xqd);
                        activity.setResult(RESULT_OK,intent);
                        activity.onBackPressed();
                        break;
                    }
                    case EXIT:{
                        activity.htmlText.setText((String)msg.obj);
                        break;
                    }
                    case okhttp_link_error:
                        activity.htmlText.setText("连接失败");
                        break;
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_a_a_o);

        SampleOkhttpCallbackOnResponse.what_errorInfo=okhttp_link_error;

        sampleSharedPreferencesLand=new SampleSharedPreferencesLand(
                this
                ,"LandInfo"
                ,R.id.layout_land_login_user
                ,R.id.layout_land_login_pass
                ,R.id.layout_land_save_pass
                ,R.id.layout_land_auto_landing
                ,R.id.layout_land_hide
        );
        sampleSharedPreferencesLand.init();
        findViewById(R.id.layout_land_forget).setVisibility(View.INVISIBLE);
        findViewById(R.id.linear_autoland).setVisibility(View.INVISIBLE);


        verificatinImg=(ImageView)findViewById(R.id.Img_verificatinCode);
        verificatinCode=(TextView)findViewById(R.id.Edit_verificatinCode);
        htmlText=(TextView)findViewById(R.id.htmlText);

        spinner_xuenian=(Spinner)findViewById(R.id.spiner_xuenian);
        spinner_xueqi=(Spinner)findViewById(R.id.spiner_xueqi);

        spinner_xuenian.setAdapter(
                new ArrayAdapter<String>(
                        this,R.layout.support_simple_spinner_dropdown_item,
                        xuenian
                )
        );
        spinner_xueqi.setAdapter(
                new ArrayAdapter<String>(
                        this,R.layout.support_simple_spinner_dropdown_item,
                        xueqi
                )
        );
        spinner_xuenian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                xuenian_position=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        spinner_xueqi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                xueqi_position=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        RefreshVerificatinImg();
        verificatinImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshVerificatinImg();
            }
        });
        findViewById(R.id.Button_course_table).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_course_table();
            }
        });
    }
//    @Override
//    /** @brief 原本用于告诉教务网退出了，由于教务网更新，功能变成异常退出，所以功能取消了*/
//    public void onBackPressed() {
//        exit();
//        super.onBackPressed();
//    }




    void RefreshVerificatinImg(){
        SampleOkhttp.asyncSend(
                hostRequestBuilder.url(imgaUrl).build(),
                new SampleOkhttpCallback(handler,VERIFICATIN_IMG,SampleOkhttpCallback.Mode.BYTES));
    }
    void land(){
        htmlText.setText("Landing");
        SampleOkhttp.asyncSend(hostRequestBuilder.url(landUrl).build(),
                new SampleOkhttpCallbackOnResponse(handler) {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string=response.body().string();
                        String __VIEWSTATE,__EVENTVALIDATION;
                        __VIEWSTATE=SampleJsoup.getValue(string, "input[name=__VIEWSTATE]");
                        __EVENTVALIDATION=SampleJsoup.getValue(string, "input[name=__EVENTVALIDATION]");
                        if(__EVENTVALIDATION==null){
                            __EVENTVALIDATION="";
                        }

                        String use;
                        String password;
                        String yanzhengma;
                        use=sampleSharedPreferencesLand.edituser.getText().toString();
                        password=sampleSharedPreferencesLand.editpass.getText().toString();
                        yanzhengma=verificatinCode.getText().toString();
                        sampleSharedPreferencesLand.save();

                        okhttp3.RequestBody requestBody=new FormBody.Builder()
                                .add("__VIEWSTATE",__VIEWSTATE)
                                .add("__EVENTVALIDATION",__EVENTVALIDATION)
                                .add("txtUserName",use)
                                .add("TextBox2",password)
                                .add("txtSecretCode",yanzhengma)
                                //.add("RadioButtonList1","%D1%A7%C9%FA")
                                .add("Button1","")
                                .build();
                        SampleOkhttp.asyncSend(
                                hostRequestBuilder.url(landUrl).build(),
                                requestBody,
                                new SampleOkhttpCallback(handler,LAND,SampleOkhttpCallback.Mode.STRINGandRESPONSE));
                    }
                });
    }

    void get_Table__VIEWSTATE(){
        SampleOkhttp.asyncSend(
                hostRequestBuilder.url(tableadress).build(),
                new SampleOkhttpCallbackOnResponse(handler) {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string=response.body().string();
                        Table__VIEWSTATE=SampleJsoup.getValue(string, "input[name=__VIEWSTATE]");
                        Table__EVENTVALIDATION=SampleJsoup.getValue(string, "input[name=__EVENTVALIDATION]");
                        if(Table__EVENTVALIDATION==null){
                            Table__EVENTVALIDATION="";
                        }

                        Document document = Jsoup.parse(string);
                        Elements element= document.select("option[selected]");
                        now_xuenian =element.get(0).text();
                        now_xueqi =element.get(1).text();
                        if (Table__VIEWSTATE != null){
                            get_course_table();
                        }
                    }
                });
    }
    void get_course_table(){
        if(!isAreadyLand){
            land();
            return;
        }
        if(Table__VIEWSTATE == null){
            get_Table__VIEWSTATE();
            return;
        }
        String xnd;
        String xqd;
        xnd=xuenian[xuenian_position];
        xqd=xueqi[xueqi_position];

        if (xnd.equals(now_xuenian) && xqd.equals(now_xueqi)){
            SampleOkhttp.asyncSend(
                    hostRequestBuilder.url(tableadress).build(),
                    new SampleOkhttpCallback(handler,COURSETABLE,SampleOkhttpCallback.Mode.STRING));
        }else {
            SampleOkhttp.asyncSend(
                    hostRequestBuilder.url(tableadress).build(),
                    new FormBody.Builder()
                            .add("__VIEWSTATE",Table__VIEWSTATE)
                            .add("__EVENTVALIDATION",Table__EVENTVALIDATION)
                            //Table__VIEWSTATE 包含了当前页面学期与学年信息，同学年学期会导致无课程数据返回
                            .add("xnd",xnd)
                            .add("xqd",xqd)
                            .build(),
                    new SampleOkhttpCallback(handler,COURSETABLE,SampleOkhttpCallback.Mode.STRING));
        }
    }
    void exit(){
        if (isAreadyLand) {
            SampleOkhttp.asyncSend(
                    hostRequestBuilder.url(mainurl).build(),
                    new FormBody.Builder().add("__EVENTTARGET","likTc").build(),
                    new SampleOkhttpCallback(handler,EXIT,SampleOkhttpCallback.Mode.STRING));
        }
    }
}


package com.example.kechengbiao.coursetablegetsample.SampleUtil;

/** @brief 小工具，用于处理String*/
public class SampleString {
    public static final String CharBase_Digital="零一二三四五六七八九";
    public static final String CharBase_Week="#一二三四五六日";
    public static final String CharBase_Week2="#一二三四五六天";

    public static int find(String string,char c,int i){
        int index=0;
        while(i != 0){
            index=string.indexOf(c,index);
            --i;
            if(i!=0){
                ++index;
            }
        }
        return index;
    }
    public static String cut(String string,int a,int b){
        return new String(string.toCharArray(),a,b-a+1);
    }
    public static String cut(String string,char start,char end){
        int a=string.indexOf(start);
        int b=string.indexOf(end);
        return cut(string,a,b);
    }
}

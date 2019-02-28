package com.example.kechengbiao.coursetablegetsample.SampleUtil.Html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/** @brief 小工具，简单的把Jsoup封装成我常用的几个功能 */
public class SampleJsoup {
    public static String getValue(String html,String str1) {
        if (null != html) {
            Document document = Jsoup.parse(html);
            Element element= document.select(str1).first();
            if (element != null){
                return element.attr("value");
            }
        }
        return null;
    }
    public static String getText(String html,String str1) {
        if (null != html) {
            Document document = Jsoup.parse(html);
            return document.select(str1).text();
        }
        return null;
    }
    public static String getTexthtml(String html,String str1) {
        if (null != html) {
            Document document = Jsoup.parse(html);
            return document.select(str1).html();
        }
        return null;
    }
    public static String getTexthtml(String html,String str1,int i) {
        if (null != html) {
            Document document = Jsoup.parse(html);
            return document.select(str1).get(i).html();
        }
        return null;
    }
    public static List<String> getTexthtmls(String html,String str1) {
        if (null != html) {
            Document document = Jsoup.parse(html);
            List<String> stringList=new ArrayList<>();
            Elements elements=document.select(str1);
            for (int i = 0; i < elements.size(); i++) {
                stringList.add(elements.get(i).html());
            }
            return stringList;
        }
        return null;
    }
}

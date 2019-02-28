package com.example.kechengbiao.coursetablegetsample.SampleUtil.Html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


//https://www.cnblogs.com/frankyou/p/6047664.html
//https://www.cnblogs.com/Lowp/archive/2012/09/22/2698574.html
//http://www.runoob.com/java/java-string-split.html
//https://blog.csdn.net/caijunfen/article/details/70571160
//https://blog.csdn.net/hsuxu/article/details/7823211
//http://www.runoob.com/java/java-regular-expressions.html

public class SampleReplace {
    public static String Replace(String patt,String input,String replacement){

        // 创建一个正则表达式模式，用以匹配一个单词（\b=单词边界）
//        String patt = "\\bfavor\\b";

        // 用于测试的输入字符串
//        String input = "Do me a favor? Fetch my favorites.AAA favor BBB";

        // 从正则表达式实例中运行方法并查看其如何运行
        Pattern r = Pattern.compile(patt);
        Matcher m = r.matcher(input);
        return m.replaceAll(replacement);
    }
}

package com.example.kechengbiao.coursetablegetsample.AcademicAffairsOffice;


import com.example.kechengbiao.coursetablegetsample.SampleUtil.Html.SampleJsoup;
import com.example.kechengbiao.coursetablegetsample.SampleUtil.Html.SampleReplace;
import com.example.kechengbiao.coursetablegetsample.SampleUtil.Html.SampleSAX;
import com.example.kechengbiao.coursetablegetsample.SampleUtil.SampleString;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/** @brief 从html/xml解析出课表信息 */
public class CourseDataDeal {

    /** @brief 从html/xml解析出课表信息
     *
     * @verbatim
     * 输入html，学年xn，学期xq，输出List < Course_html >
     * 制作期间遇到pull对中文的不支持，SAX对"&nbsp;"的不支持
     * @endverbatim
     */
    public static List<Course_html> parse(String string, final String xn, final String xq){
//        List<String> tables=SampleJsoup.getTexthtmls(string,"table");
//        int i=1;
//        for (String str:tables
//             ) {
//            System.out.println("table"+i+":"+str);
//            ++i;
//        }
        String s10=SampleJsoup.getTexthtml(string,"table",1);
//        System.out.println(s10);
        String s11=SampleReplace.Replace("<br>",s10,";;");
        s11=SampleReplace.Replace("<td align=\"Center\">&nbsp;</td>|<td class=\"noprint\" align=\"Center\">&nbsp;</td>|<td align=\"Center\" width=\"7%\">&nbsp;</td>|<td class=\"noprint\" align=\"Center\" width=\"7%\">&nbsp;</td>|<tr>|</tr>",s11,"");
        //<td align="Center">&nbsp;</td>
        //<td align="Center" width="7%">&nbsp;</td>
        //<td class="noprint" align="Center">&nbsp;</td>
        //<td class="noprint" align="Center" width="7%">&nbsp;</td>
        //<tr>|</tr>


        s11=SampleReplace.Replace("<td align=\"center\">&nbsp;</td>|<td class=\"noprint\" align=\"center\">&nbsp;</td>|<td align=\"center\" width=\"7%\">&nbsp;</td>|<td class=\"noprint\" align=\"center\" width=\"7%\">&nbsp;</td>",s11,"");
        //<td align="center">&nbsp;</td>
        //<td align="center" width="7%">&nbsp;</td>
        //<td class="noprint" align="center">&nbsp;</td>
        //<td class="noprint" align="center" width="7%">&nbsp;</td>
        s11=SampleReplace.Replace(">\\s{1,}<",s11,">\n<");
//        System.out.println(s11);
        final List<Course_html> courseLis= new ArrayList<>();
        SampleSAX.parseXMLWithSAX("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+s11
                ,new DefaultHandler(){
                    @Override
                    public void characters(char[] ch, int start, int length) throws SAXException {
                        String[] strs=new String(ch,start,length).split(";;");
                        if (strs.length>=2){
                            Course_html course=new Course_html();
                            course.xn=xn;
                            course.xq=xq;
                            course.coursename=strs[0];
                            course.time=strs[1];
                            if (strs.length>=3)
                                course.teacher=strs[2];
                            if (strs.length>=4)
                                course.classroom=strs[3];
                            if (strs.length>=5)
                                course.examtime=strs[4];
                            if (strs.length>=6)
                                course.examroom=strs[5];
                            courseLis.add(course);
                        }
                    }
                }
        );
        return  courseLis;
    }

    /** @brief 进一步加工，从(String)Course_html.time解析出数字数据 */
    public static List<Course_html> parse_time(List<Course_html> courseList){
        for (Course_html course:courseList
                ) {
//            course.week;
//            course.todaystart;
//            course.len;
//            course.weekstart;
//            course.weekend;
//            course.isBiweekly;
            //周四第10,11节{第2-16周|双周}
            //周五第3,4,5节{第1-16周}

            course.week=SampleString.CharBase_Week.indexOf( course.time.charAt(1) );
//            System.out.println("week:"+course.week+":"+course.time.charAt(1));

            String str1=new String(course.time.toCharArray()
                    ,3,course.time.indexOf('节')-3);
//            System.out.println(str1);
            String[] str_1=str1.split(",");
            course.todaystart=Integer.parseInt(str_1[0]);
            course.len=str_1.length;
//            System.out.println(course.todaystart+","+course.len);

            String str2=SampleString.cut(course.time
                    ,SampleString.find(course.time,'第',2)
                    ,course.time.length()-2);
//            System.out.println(str2);
            String[] str_2=str2.split("\\|");
            course.isBiweekly= (str_2.length == 2);
            String  str_2_1=SampleString.cut(str_2[0]
                    ,1
                    ,str_2[0].length()-2);
            String[]  str_2_1_=str_2_1.split("-");
            course.weekstart=Integer.parseInt(str_2_1_[0]);
            course.weekend=Integer.parseInt(str_2_1_[1]);
//            System.out.println(course.weekstart+","+course.weekend+","+course.isBiweekly);


//            for (int i = 2; i < course.time.length(); i++) {
//                course.time.charAt(i);
//            }
//            System.out.println("------------");
        }
        return courseList;
    }
    /** @brief 格式化显示Course_html，用于临时检查*/
    public static String showString(List<Course_html> courses){
        StringBuilder stringBuilder=new StringBuilder();
        for (Course_html course:courses
                ) {
            stringBuilder
                    .append("coursename:"+course.coursename).append('\n')
                    .append("time:"+course.time).append('\n')
                    .append("week:"+course.week).append('\n')
                    .append("todaystart:"+course.todaystart).append('\n')
                    .append("len:"+course.len).append('\n')
                    .append("weekstart:"+course.weekstart).append('\n')
                    .append("weekend:"+course.weekend).append('\n')
                    .append("isBiweekly:"+course.isBiweekly).append('\n')
                    .append("teacher:"+course.teacher).append('\n')
                    .append("classroom:"+course.classroom).append('\n')
                    .append("examtime:"+course.examtime).append('\n')
                    .append("examroom:"+course.examroom).append('\n')
                    .append("xn:"+course.xn).append('\n')
                    .append("xq:"+course.xq).append('\n')
                    .append("---------------").append('\n');
        }
        return stringBuilder.toString();
    }
}

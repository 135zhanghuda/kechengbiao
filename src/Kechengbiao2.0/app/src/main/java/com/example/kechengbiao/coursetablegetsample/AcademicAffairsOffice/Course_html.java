package com.example.kechengbiao.coursetablegetsample.AcademicAffairsOffice;

/** @brief 从教务网上获取课程数据后，用于存储的javabean
 *
 * @verbatim
 * 分别存入课程名、上课时间、老师、上课教室、考试时间、考试教室、学年、学期，
 * 没有对应数据，则为空值。
 *
 * 之后用CourseDataDeal.parse_time()对上课时间time解析，
 * 解析成星期几、第几节上课、上几节、第几周开始、第几周结束、是否是单双周。
 * @endverbatim
 */
public class Course_html {
    /** @brief 课程名 */
    public String coursename;
    /** @brief 上课时间 */
    public String time;
    /** @brief 老师 */
    public String teacher;
    /** @brief 上课教室 */
    public String classroom;
    /** @brief 考试时间 */
    public String examtime;
    /** @brief 考试教室 */
    public String examroom;
    /** @brief 学年 */
    public String xn;
    /** @brief 学期 */
    public String xq;

    /** @brief 星期几 */
    public int week;
    /** @brief 第几节上课 */
    public int todaystart;
    /** @brief 上几节 */
    public int len;
    /** @brief 第几周开始 */
    public int weekstart;
    /** @brief 第几周结束 */
    public int weekend;
    /** @brief 是否是单双周 */
    public boolean isBiweekly;
}

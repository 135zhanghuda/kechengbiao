package com.example.kechengbiao;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kechengbiao.coursetablegetsample.AcademicAffairsOffice.CourseDataDeal;
import com.example.kechengbiao.coursetablegetsample.AcademicAffairsOffice.Course_html;
import com.example.kechengbiao.coursetablegetsample.AcademicAffairsOffice.LandAAOActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //星期几
//    private int int_day;
    private RelativeLayout[] days=new RelativeLayout[7];
    //第几周
    private  int num;//week

    public HashMap<Course ,View> courseViewHashMap=new HashMap<>();


    public int colors[]={
            Color.rgb(245, 245, 245),//0
            //0 245 255
            //144 238 144
            // 0 238 0
            // 70 130 180
            // 238 220 130
            Color.rgb(0,245,255),//1
            Color.rgb(144, 238, 144),//2
            Color.rgb(0, 238, 0),//3
            Color.rgb(70, 130, 180),//4
            Color.rgb(238, 220, 130),//5
            //238 201 0
            // 205 85 85
            // 205 170 125
            // 255 52 179
            // 144 238 144
            Color.rgb(238, 201, 0),//6
            Color.rgb(205, 85, 85),//7
            Color.rgb(205, 170, 125),//8
            Color.rgb(255, 52, 179),//9
            Color.rgb(144, 238, 144)};//10
    public HashMap<String ,Integer> course_color=new HashMap<>();

    private void setCourseColor(Course course) {
        View view=courseViewHashMap.get(course);
        if(view==null){
            return;
        }
        if( !(num>=course.getStartweek()&&num<=course.getEndweek()) )
            view.setBackgroundColor(colors[0]);
        else
            view.setBackgroundColor(colors[course_color.get(course.getCourseName())]);
    }

    //SQLite Helper类
    private DatabaseHelper databaseHelper = new DatabaseHelper
            (this, "mycourse.db", null, 5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //工具条
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button delete=(Button) findViewById(R.id.delete_courses);
        Button gongjuxiang=(Button) findViewById(R.id.gongjuxiang);
        Button dennglu=(Button) findViewById(R.id.denglu);
        gongjuxiang.setOnClickListener(this);
        dennglu.setOnClickListener(this);

        final Spinner sp = (Spinner) findViewById(R.id.spin);
        num=Integer.parseInt((String)sp.getSelectedItem());
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                num=Integer.parseInt((String)sp.getSelectedItem());
                for (Course course1:databaseHelper.courseList
                        ) {
                    setCourseColor(course1);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        days[0]=(RelativeLayout) findViewById(R.id.monday);
        days[1] = (RelativeLayout) findViewById(R.id.tuesday);
        days[2] = (RelativeLayout) findViewById(R.id.wednesday);
        days[3] = (RelativeLayout) findViewById(R.id.thursday);
        days[4] = (RelativeLayout) findViewById(R.id.friday);
        days[5] = (RelativeLayout) findViewById(R.id.saturday);
        days[6] = (RelativeLayout) findViewById(R.id.weekday);

        //从数据库读取数据
        //使用从数据库读取出来的课程信息来加载课程表视图
        databaseHelper.loadData();
        for (Course course : databaseHelper.courseList) {
            //createLeftView(course);
            createCourseView(course);
        }
    }



    final int LandAAO=2016;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == 0 && data != null) {
            Course course = (Course) data.getSerializableExtra("course");//获取从AddCourseActivity传过来的数据
            //存储数据到数据库
            databaseHelper.saveData(course);

            //创建课程表左边视图(节数)
            //createLeftView(course);
            //创建课程表视图
            createCourseView(course);
        }

        String html,xn,xq;
        switch (requestCode){
            case LandAAO:
                if(RESULT_OK!=resultCode){
                    System.out.println("requestCode isn'n RESULT_OK");
                    return;
                }
                html=data.getStringExtra("html");
                xn=data.getStringExtra("xn");
                xq=data.getStringExtra("xq");
                List<Course_html> courseList=CourseDataDeal.parse(html,xn,xq);
                courseList=CourseDataDeal.parse_time(courseList);

                List<Course> courses=new ArrayList<>();
                for (Course_html course_html:courseList
                        ) {
                    courses.add(new Course(
                            course_html.coursename,course_html.teacher,course_html.classroom,
                            course_html.week,
                            course_html.todaystart,course_html.todaystart+course_html.len -1 ,
                            course_html.weekstart,course_html.weekend,
                            (course_html.isBiweekly)? 1 : 0
                    ));
                }
                for (Course course:courses
                        ) {
                    databaseHelper.saveData(course);

                    //创建课程表左边视图(节数)
                    //createLeftView(course);
                    //创建课程表视图
                    createCourseView(course);
                }
            default:
                break;
        }
    }

    //创建课程节数的卡片视图
    /*private void createLeftView(Course course) {
        int maxClassNumber = 0;//课程的最大节数
        int number = 1; //课程表左侧的当前节数
        int len = course.getEnd();
        if (len > maxClassNumber) {
            LinearLayout classNumberLayout = (LinearLayout) findViewById(R.id.class_number_layout);
            View view;
            TextView text;
            for (int i = 0; i < len-maxClassNumber; i++) {

                view = LayoutInflater.from(this).inflate(R.layout.left_view, null);
                //布局填充器，将目标布局填充到当前Activity中
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(110,180);
                //LayoutParams类是用于子视图向父视图传达自己的意愿
                view.setLayoutParams(params);

                text = view.findViewById(R.id.class_number_text);
                text.setText("" + number++);
                classNumberLayout.addView(view);
            }
            maxClassNumber = len;
        }
    }*/
    //创建课程的卡片视图
    private void createCourseView(final Course course) {
        if ((course.getDay() < 1 && course.getDay() > 7) || course.getStart() > course.getEnd()){
            Toast.makeText(this, "日期或时间格式错误", Toast.LENGTH_LONG).show();
            return;
        }
        final RelativeLayout day=days[course.getDay()-1];
        int height = 180;
        final View view = LayoutInflater.from(this).inflate(R.layout.course_card, null);
        //加载单个课程布局

        courseViewHashMap.put(course,view);


        view.setY(height * (course.getStart()-1));
        //设置开始高度,即第几节课开始
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT,(course.getEnd()-course.getStart()+1)*height - 8);
        //设置布局高度,即跨多少节课
        view.setLayoutParams(params);

        course_color.put(course.getCourseName(),(int)(1+Math.random()*10));
        setCourseColor(course);

        TextView text = view.findViewById(R.id.text_view);
        Button delete=view.findViewById(R.id.delete_courses);
        Button change=view.findViewById(R.id.change_courses);

        //显示课程名
        text.setText(course.getCourseName() + "\n" + course.getTeacher() + "\n" + course.getClassRoom());
        day.addView(view);
        day.getTouchables();//day.getFocusables();
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                view.setVisibility(View.GONE);//先隐藏
                day.removeView(view);//再移除课程视图

                databaseHelper.removeData(course);
                courseViewHashMap.remove(course);
            }
        });
        change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                view.setVisibility(View.GONE);//先隐藏
                day.removeView(view);//再移除课程视图

                Intent intent = new Intent(MainActivity.this, ChangeActivity.class);
                intent.putExtra("course", course);


                databaseHelper.removeData(course);
                courseViewHashMap.remove(course);


                startActivityForResult(intent ,0);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.denglu:
                Intent intent =new Intent(MainActivity.this,LandAAOActivity.class);
                startActivityForResult(intent,LandAAO);
                break;
            case R.id.gongjuxiang:
                Intent m1 =new Intent(MainActivity.this,gongjuactivity.class);
                startActivity(m1);
                break;
            default:
                break;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_courses:
              Intent intent = new Intent(MainActivity.this, AddCourseActivity.class);
               startActivityForResult(intent, 0);
                //Intent intent =new Intent(MainActivity.this,LandAAOActivity.class);
                //startActivityForResult(intent,LandAAO);
                break;
        }
        return true;
    }

}


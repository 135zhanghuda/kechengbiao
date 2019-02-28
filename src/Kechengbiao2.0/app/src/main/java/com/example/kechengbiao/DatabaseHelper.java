package com.example.kechengbiao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    public ArrayList<Course> courseList= new ArrayList<>();//保持数据同步,虽然不一定有必要
    private Context mContext;
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table course("+
                "id integer primary key autoincrement," +
                "course_name text," +
                "teacher text," +
                "class_room text," +
                "day integer," +
                "start integer," +
                "ends integer," +
                "startweek integer," +
                "endweek integer," +
                "single integer)");
    }

    public void removeData(Course course){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.execSQL("delete from course where id = ?",
                new String[] {course.getId()+""});
        courseList.remove(course);
    }
    //保存数据到数据库
    public int saveData(Course course) {
        SQLiteDatabase sqLiteDatabase =  getWritableDatabase();
        sqLiteDatabase.execSQL("insert into course(course_name, teacher, class_room, day, start, ends,startweek,endweek,single) " +
                "values(?, ?, ?, ?, ?, ?,?,?,?)", new String[] {course.getCourseName(), course.getTeacher(),
                course.getClassRoom(), course.getDay()+"", course.getStart()+"", course.getEnd()+"",course.getStartweek()+"",course.getEndweek()+"",course.getSingle()+""});

        Cursor cursor = sqLiteDatabase.rawQuery("select last_insert_rowid() from course",null);
        cursor.moveToFirst();
        int int_id = cursor.getInt(0);
//        System.out.println("saveData:int_id:"+int_id);
        cursor.close();
        course.setId(int_id);
        courseList.add(course);
        return int_id;
    }
    //从数据库加载数据
    public ArrayList<Course> loadData() {
        courseList= new ArrayList<>();
//        ArrayList<Course> courseList = new ArrayList<>(); //课程列表
        SQLiteDatabase sqLiteDatabase =  getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("course", null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                courseList.add(new Course(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("course_name")),
                        cursor.getString(cursor.getColumnIndex("teacher")),
                        cursor.getString(cursor.getColumnIndex("class_room")),
                        cursor.getInt(cursor.getColumnIndex("day")),
                        cursor.getInt(cursor.getColumnIndex("start")),
                        cursor.getInt(cursor.getColumnIndex("ends")),
                        cursor.getInt(cursor.getColumnIndex("startweek")),
                        cursor.getInt(cursor.getColumnIndex("endweek")),
                        cursor.getInt(cursor.getColumnIndex("single"))));

            } while(cursor.moveToNext());
        }
        cursor.close();
        return courseList;
    }
    public ArrayList<Course> loadtodayData(int i) {
        courseList= new ArrayList<>();
//        ArrayList<Course> courseList = new ArrayList<>(); //课程列表
        SQLiteDatabase sqLiteDatabase =  getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("course", null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                if(i==cursor.getInt(cursor.getColumnIndex("day"))){
                courseList.add(new Course(

                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("course_name")),
                        cursor.getString(cursor.getColumnIndex("teacher")),
                        cursor.getString(cursor.getColumnIndex("class_room")),
                        cursor.getInt(cursor.getColumnIndex("day")),
                        cursor.getInt(cursor.getColumnIndex("start")),
                        cursor.getInt(cursor.getColumnIndex("ends")),
                        cursor.getInt(cursor.getColumnIndex("startweek")),
                        cursor.getInt(cursor.getColumnIndex("endweek")),
                        cursor.getInt(cursor.getColumnIndex("single"))));}

            } while(cursor.moveToNext());
        }
        cursor.close();
        return courseList;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           db.execSQL("drop table if exists course");
    }
}

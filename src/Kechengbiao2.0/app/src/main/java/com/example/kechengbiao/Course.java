package com.example.kechengbiao;

import java.io.Serializable;


public class Course implements Serializable {

    private  int id;

    private String courseName;
    private String teacher;
    private String classRoom;
    private int day;//week
    private int start;//todaystart
    private int ends;//todaystart+len
    private int startweek;//weekstart
    private int endweek;//weekend
    private int single;//isBiweekly
    public Course(int id,String courseName, String teacher, String classRoom, int day, int start, int ends,int startweek,int endweek,int single) {
        this.id=id;
        this.courseName = courseName;
        this.teacher = teacher;
        this.classRoom = classRoom;
        this.day = day;
        this.start = start;
        this.ends = ends;
        this.startweek=startweek;
        this.endweek=endweek;
        this.single=single;
    }
    public Course(String courseName, String teacher, String classRoom, int day, int start, int ends,int startweek,int endweek,int single) {
        this.courseName = courseName;
        this.teacher = teacher;
        this.classRoom = classRoom;
        this.day = day;
        this.start = start;
        this.ends = ends;
        this.startweek=startweek;
        this.endweek=endweek;
        this.single=single;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return ends;
    }

    public void setEnd(int end) {
        this.ends = end;
    }
    public int getEndweek() {
        return endweek;
    }

    public void setEndweek(int endweek) {
        this.endweek = endweek;
    }
    public int getStartweek() {
        return startweek;
    }

    public void setStartweek(int endweek) {
        this.startweek = startweek;
    }
    public int getSingle() {
        return single;
    }

    public void setSingle(int single) {
        this.single = single;
    }



    public static Course transform(String courseName,String teacher,String classRoom,
                                   String day,
                                   String start,String ends,
                                   String startweek,String endweek,
                                   String single
    ){
        if (courseName.equals("") || day.equals("") || start.equals("") || ends.equals("")) {
            return null;
        }
        else {
            int int_day=Integer.parseInt(day);
            int int_start=Integer.parseInt(start);
            int int_ends=Integer.parseInt(ends);

            int int_startweek=1;
            int int_endweek=16;
            int int_single=0;
            if(!startweek.equals(""))
                int_startweek=Integer.parseInt(startweek);
            if(!endweek.equals(""))
                int_endweek=Integer.parseInt(endweek);
            if(!single.equals(""))
                int_single=Integer.parseInt(single);
            return new Course(courseName, teacher, classRoom,
                    int_day, int_start, int_ends,int_startweek,int_endweek,int_single);
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


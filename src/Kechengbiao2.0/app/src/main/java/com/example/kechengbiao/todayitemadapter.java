package com.example.kechengbiao;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class todayitemadapter extends RecyclerView.Adapter<todayitemadapter.ViewHolder> {
    private List<Course>  mCourseList;

    static  class ViewHolder extends RecyclerView.ViewHolder {
        TextView start;
        TextView end;
        TextView coursename;
        TextView classname;

        public ViewHolder(View view) {
            super(view);
            start = (TextView) view.findViewById(R.id.kaishi);
            end = (TextView) view.findViewById(R.id.jieshu);
            coursename = (TextView) view.findViewById(R.id.kechengming);
            classname = (TextView) view.findViewById(R.id.jiaoshi);
        }
    }
        public todayitemadapter(List<Course> courseList)
        {
            mCourseList=courseList;
        }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Course course=mCourseList.get(position);
        holder.start.setText(String.valueOf(course.getStart()));
        holder.end.setText(String.valueOf(course.getEnd()));
        holder.coursename.setText(String.valueOf(course.getCourseName()));
        holder.classname.setText(String.valueOf(course.getClassRoom()));
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }
}


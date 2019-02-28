package com.example.kechengbiao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.kechengbiao.R.id.class_room;
import static com.example.kechengbiao.R.id.course_name;


public class AddCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);


        final EditText inputCourseName = (EditText) findViewById(course_name);
        final EditText inputTeacher = (EditText) findViewById(R.id.teacher_name);
        final EditText inputClassRoom = (EditText) findViewById(class_room);
        final EditText inputDay = (EditText) findViewById(R.id.week);
        final EditText inputStart = (EditText) findViewById(R.id.classes_begin);
        final EditText inputEnd = (EditText) findViewById(R.id.classes_ends);
        final EditText inputStartweek = (EditText) findViewById(R.id.start_week);
        final EditText inputEndweek = (EditText) findViewById(R.id.end_week);
        final EditText inputsingle = (EditText) findViewById(R.id.single);
        Button okButton = (Button) findViewById(R.id.button);
        Button noButton = (Button) findViewById(R.id.cancel);
        noButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AddCourseActivity.this, MainActivity.class);
                finish();
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = inputCourseName.getText().toString();
                String teacher = inputTeacher.getText().toString();
                String classRoom = inputClassRoom.getText().toString();
                String day = inputDay.getText().toString();
                String start = inputStart.getText().toString();
                String ends = inputEnd.getText().toString();
                String startweek = inputStartweek.getText().toString();
                String endweek = inputEndweek.getText().toString();
                String single = inputsingle.getText().toString();

                Course course = Course.transform(courseName, teacher, classRoom,
                        day,
                        start, ends,
                        startweek, endweek,
                        single);
                if (course == null) {
                    Toast.makeText(AddCourseActivity.this, "基本课程信息未填写", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(AddCourseActivity.this, MainActivity.class);
                intent.putExtra("course", course);
                setResult(0, intent);
                finish();
            }
        });
    }
}


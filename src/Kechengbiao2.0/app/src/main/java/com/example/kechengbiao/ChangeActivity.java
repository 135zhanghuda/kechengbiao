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

public class ChangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        final EditText inputCourseName = (EditText) findViewById(course_name);
        final EditText inputTeacher = (EditText) findViewById(R.id.teacher_name);
        final EditText inputClassRoom = (EditText) findViewById(class_room);
        final EditText inputDay = (EditText) findViewById(R.id.week);
        final EditText inputStart = (EditText) findViewById(R.id.classes_begin);
        final EditText inputEnd = (EditText) findViewById(R.id.classes_ends);
        final EditText inputStartweek = (EditText) findViewById(R.id.start_week);
        final EditText inputEndweek = (EditText) findViewById(R.id.end_week);
        final EditText inputSingle = (EditText) findViewById(R.id.single);

        Button okButton = (Button) findViewById(R.id.button);

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
                String single = inputSingle.getText().toString();

                Course course = Course.transform(courseName, teacher, classRoom,
                        day,
                        start, ends,
                        startweek, endweek,
                        single);
                if (course == null) {
                    Toast.makeText(ChangeActivity.this, "基本课程信息未填写", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(ChangeActivity.this, MainActivity.class);
                intent.putExtra("course", course);
                setResult(0, intent);
                finish();
            }
        });

        Intent intent = getIntent();
        Course course = (Course) intent.getSerializableExtra("course");
        inputCourseName.setText(course.getCourseName());
        inputTeacher.setText(course.getTeacher());
        inputClassRoom.setText(course.getClassRoom());
        inputDay.setText(String.valueOf(course.getDay()));
        inputStart.setText(String.valueOf(course.getStart()));
        inputEnd.setText(String.valueOf(course.getEnd()));
        inputStartweek.setText(String.valueOf(course.getStartweek()));
        inputEndweek.setText(String.valueOf(course.getEndweek()));
        inputSingle.setText(String.valueOf(course.getSingle()));
    }
}

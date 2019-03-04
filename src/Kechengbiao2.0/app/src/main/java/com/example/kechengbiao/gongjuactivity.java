package com.example.kechengbiao;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class gongjuactivity extends AppCompatActivity implements View.OnClickListener{
private List<Course> courseList=new ArrayList<>();
    private int todayweek;
    private DatabaseHelper databaseHelper = new DatabaseHelper
            (this, "mycourse.db", null, 5);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gongjuactivity);
        Calendar c = Calendar.getInstance();
        todayweek=c.get(Calendar.DAY_OF_WEEK);
        todayweek--;
        TextView text;
        text=(TextView)findViewById(R.id.xingqiji);
        text.setText(String.valueOf(todayweek));
        Log.d("gongjuactivity" ,"it is "+todayweek);
        courseList=databaseHelper.loadtodayData(todayweek);
        Button returnmain=(Button)findViewById(R.id.returnmain);
        returnmain.setOnClickListener(this);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.gongjurecyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        todayitemadapter adapter=new todayitemadapter(courseList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.returnmain:
//                Intent intent=new Intent(gongjuactivity.this,MainActivity.class);
//                startActivity(intent);
                finish();
                break;
        }
    }

}

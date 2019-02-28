package com.example.kechengbiao.coursetablegetsample.SampleUtil;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

/** @brief 小工具，用于复用登陆界面的设置*/
public class SampleSharedPreferencesLand {
//        R.layout.layout_land;

    public SharedPreferences pref;
    // (Context mBase).getSharedPreferences
    //    getApplicationContext();
    public EditText edituser;
    public EditText editpass;
    public CheckBox checksave;
    public CheckBox checkauto;
    public CheckBox checkhide;

    public SampleSharedPreferencesLand(
            AppCompatActivity appCompatActivity,
            String SharedPreferencesName,
            int id_user,
            int id_password,
            int id_save_pass,
            int id_auto_landing
            //,int id_land_forget
            //,View.OnClickListener listener_forget//去注册
            //,int id_land_landing
            ,int id_hide_pass
    ){
        pref =appCompatActivity.getSharedPreferences(SharedPreferencesName,appCompatActivity.MODE_PRIVATE);
        edituser=(EditText) appCompatActivity.findViewById(id_user);
        editpass=(EditText) appCompatActivity.findViewById(id_password);
        checksave=(CheckBox) appCompatActivity.findViewById(id_save_pass);
        checkauto=(CheckBox) appCompatActivity.findViewById(id_auto_landing);
        checkhide=(CheckBox) appCompatActivity.findViewById(id_hide_pass);

        checkhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkhide.isChecked()){
                    editpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    editpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


//        checkauto.set
    }
    public void init(){
        editpass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        checksave.setChecked(   pref.getBoolean("save_pass",false)      );
        checkauto.setChecked(   pref.getBoolean("auto_landing",false)   );
        edituser.setText(       pref.getString("login_user","")         );
        if(checksave.isChecked()){
            editpass.setText(   pref.getString("login_pass","")         );
        }
        //appCompatActivity.findViewById(id_land_forget).setOnClickListener(listener_forget);
//        appCompatActivity.findViewById(id_land_forget).OnClickListener;
    }
    public void save(){
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("save_pass", checksave.isChecked());
        editor.putBoolean("auto_landing", checkauto.isChecked());
        editor.putString("login_user", edituser.getText().toString());
        if (checksave.isChecked()) {
            editor.putString("login_pass", editpass.getText().toString());
        } else {
            editor.remove("login_pass");
        }
        editor.apply();
    }
}

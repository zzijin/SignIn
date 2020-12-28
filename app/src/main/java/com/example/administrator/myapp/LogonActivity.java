package com.example.administrator.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LogonActivity extends AppCompatActivity implements View.OnClickListener{
    public EditText et_user, et_password;
    public String strpw;
    public int  userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);
        et_user = findViewById(R.id.logon_user);
        et_password = findViewById(R.id.logon_password);
        findViewById(R.id.logon_bt).setOnClickListener(this);

       // initData();

    }

//    public  void  initData(){
//        SocketApplication socketApplication=(SocketApplication)getApplication();
//        SocketClient socketClient=socketApplication.getSocketClient();
//        InfoManager infoManager=socketApplication.getInfoManager();
//
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logon_bt: logOn();break;
        }
    }

    //登录点击事件
    public void logOn(){
        if(et_user.getText().toString().equals("")||et_password.getText().toString().equals("")){
            Toast.makeText(LogonActivity.this, "请输入用户id或密码", Toast.LENGTH_SHORT).show();
        }else {
            userid=Integer.parseInt(et_user.getText().toString());
            strpw=et_password.getText().toString();
            Intent intent=new Intent(LogonActivity.this, MainActivity.class);
            intent.putExtra("myID",userid);
            Toast.makeText(LogonActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }
    }
}

package com.example.administrator.myapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.administrator.myapp.Info.MyInfo;
import com.example.administrator.myapp.client.SocketApplication;
import com.example.administrator.myapp.fragment.FindFragment;
import com.example.administrator.myapp.fragment.HeadFragment;
import com.example.administrator.myapp.fragment.MeFragment;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView  navigation;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private HeadFragment head;
    private FindFragment find;
    private MeFragment me;
    private int myID;
    private SocketApplication socketApplication;
    private InfoManager infoManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.fragment,find);
                    transaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    transaction.replace(R.id.fragment,head);
                    transaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    transaction.replace(R.id.fragment,me);
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        socketApplication =(SocketApplication) getApplication();
        infoManager=socketApplication.getInfoManager();

        Intent intent=getIntent();
        myID=intent.getIntExtra("myID",0);
        navigation =findViewById(R.id.navigation);
        head = new HeadFragment();
        find = new FindFragment();
        me = new MeFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment,find);
        transaction.commit();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        accessPermission();
    }

    private void accessPermission(){
        while (!checkGPSPermission()){
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (!checkWRPermission()){
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        SocketConnCheckThread socketConnCheckThread=new SocketConnCheckThread();
        socketConnCheckThread.start();
    }

    private void getPermission(String[] PERMISSIONS_STORAGE){
        ActivityCompat.requestPermissions(MainActivity.this,PERMISSIONS_STORAGE, PERMISSIONS_STORAGE.length);
    }

    private boolean checkWRPermission(){
        String[] PERMISSIONS_STORAGE = {
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};    //请求状态码
        //判断是否已经赋予权限
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            getPermission(PERMISSIONS_STORAGE);
            return false;
        }
        else {
            return true;
        }
    }

    private boolean checkGPSPermission(){
        String[] PERMISSIONS_STORAGE = {
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION};    //请求状态码
        //判断是否已经赋予权限
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            getPermission(PERMISSIONS_STORAGE);
            return false;
        }
        else {
            return true;
        }
    }

    class SocketConnCheckThread extends Thread{
        @Override
        public void run() {
            while (true){
                if(!socketApplication.getConnStatus()){
                    socketApplication.startClientSocket();
                }
                else break;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(infoManager.getMyLoginStatus()!=1){
                Intent intent=new Intent(MainActivity.this,LoginMyAccountActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        }
    }


}

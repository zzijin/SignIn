package com.example.administrator.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.administrator.myapp.fragment.FindFragment;
import com.example.administrator.myapp.fragment.HeadFragment;
import com.example.administrator.myapp.fragment.MeFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView  navigation;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private HeadFragment head;
    private FindFragment find;
    private MeFragment me;
    private int myID;

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
        Intent intent=getIntent();
        myID=intent.getIntExtra("myID",0);
        navigation =findViewById(R.id.navigation);
        head = new HeadFragment(myID);
        find = new FindFragment();
        me = new MeFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment,find);
        transaction.commit();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}

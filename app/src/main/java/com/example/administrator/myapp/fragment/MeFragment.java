package com.example.administrator.myapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapp.InfoManager;
import com.example.administrator.myapp.LoginMyAccountActivity;
import com.example.administrator.myapp.R;
import com.example.administrator.myapp.client.SocketApplication;

import java.util.ArrayList;
import java.util.List;

public class MeFragment extends Fragment implements View.OnClickListener {
    private ImageView notice,activities,wait,userpro;
    private TextView username,myName;
    private InfoManager infoManager;
    private MeFragmentItem meFragmentItem;
    private int myStatus=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_me, container, false);
        username=view.findViewById(R.id.me_username);
        myName=view.findViewById(R.id.text_my_name);
        userpro=view.findViewById(R.id.login);
        notice=view.findViewById(R.id.me_notice);
        activities=view.findViewById(R.id.me_activities);
        wait=view.findViewById(R.id.me_wait);
        onViewCreated(view,savedInstanceState);
        userpro.setOnClickListener(this);
        notice.setOnClickListener(this);
        wait.setOnClickListener(this);
        activities.setOnClickListener(this);
        showFragment(R.layout.fragment_me_item1);
       return view;
    }

    public void showFragment(int myresource){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        meFragmentItem=new MeFragmentItem(myresource);
        transaction.replace(R.id.me_item_fragment,meFragmentItem);
        transaction.commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SocketApplication socketApplication = (SocketApplication) getActivity().getApplication();
        infoManager = socketApplication.getInfoManager();
        myStatus=infoManager.getMyLoginStatus();
        if (myStatus==1){
            username.setText("UID:"+infoManager.getMyInfo().getMyID());
            myName.setText(infoManager.getMyInfo().getMyName());
            userpro.setImageResource(R.drawable.ic_account);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_notice:{showFragment(R.layout.fragment_me_item1);};break;
            case R.id.me_activities:{showFragment(R.layout.fragment_me_item2);};break;
            case R.id.me_wait:{showFragment(R.layout.fragment_me_item3);};break;
            case R.id.login:{
                if (myStatus!=1){
                    startActivity(new Intent(getActivity(), LoginMyAccountActivity.class));
                    getActivity().finish();
                }
            };break;
    }
    }
}

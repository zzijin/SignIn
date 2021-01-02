package com.example.administrator.myapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.myapp.InfoManager;
import com.example.administrator.myapp.R;
import com.example.administrator.myapp.baidumap.createactivity;
import com.example.administrator.myapp.client.SocketApplication;


public class FindFragment extends Fragment {
    Button vCreateNewActivity;
    InfoManager infoManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_find, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SocketApplication socketApplication=(SocketApplication)getActivity().getApplication();
        infoManager=socketApplication.getInfoManager();
        vCreateNewActivity=view.findViewById(R.id.find_btn);
        vCreateNewActivity.setOnClickListener(cCreateNewActivity);
    }

    View.OnClickListener cCreateNewActivity =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(infoManager.getMyLoginStatus()==1){
                Intent intent=new Intent(getActivity(), createactivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
            }
        }
    };

}

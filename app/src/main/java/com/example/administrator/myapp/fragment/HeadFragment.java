package com.example.administrator.myapp.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.administrator.myapp.Info.CheckInActivityInfo;
import com.example.administrator.myapp.Info.MyInfo;
import com.example.administrator.myapp.Info.UserInfo;
import com.example.administrator.myapp.InfoManager;
import com.example.administrator.myapp.R;
import com.example.administrator.myapp.adapter.HeadAdapter;
import com.example.administrator.myapp.client.SocketApplication;
import com.example.administrator.myapp.client.SocketClient;
import com.example.administrator.myapp.cls.Sign;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class HeadFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ListView listView;
    private List<Sign> list=new ArrayList<>();
    private List<CheckInActivityInfo> mychecklist=new ArrayList<>();
    private List<UserInfo> userInfoslist=new ArrayList<>();
    private List<MyInfo> myInfoslist=new ArrayList<>();
    private MyInfo myInfo;
    private int myID;
    @SuppressLint("ValidFragment")
    public HeadFragment(int myID){
        this.myID=myID;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_head, container, false);
        listView=view.findViewById(R.id.head_list);
        BaseAdapter adapter=new HeadAdapter(getContext(),mychecklist);
        onActivityCreated(savedInstanceState);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SocketApplication socketApplication=(SocketApplication)getActivity().getApplication();
        SocketClient socketClient=socketApplication.getSocketClient();
        InfoManager infoManager=socketApplication.getInfoManager();
        userInfoslist=infoManager.getUserInfoList();
         myInfo=infoManager.getMyInfo();
 //       myInfoslist=infoManager.getMyInfoList();
 //      mychecklist=infoManager.getCheckInActivityInfoList();
      for (int j=0;j<=myInfo.getJoinActivity().size();j++){
               mychecklist.add(infoManager.getCheckInActivityInfoByActivityID(myInfo.getJoinActivity().get(j)));
           }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

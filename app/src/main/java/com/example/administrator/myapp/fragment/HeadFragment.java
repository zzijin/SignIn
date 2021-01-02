package com.example.administrator.myapp.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.example.administrator.myapp.LoginMyAccountActivity;
import com.example.administrator.myapp.MainActivity;
import com.example.administrator.myapp.R;
import com.example.administrator.myapp.adapter.HeadAdapter;
import com.example.administrator.myapp.baidumap.information;
import com.example.administrator.myapp.baidumap.sign;
import com.example.administrator.myapp.client.SocketApplication;
import com.example.administrator.myapp.client.SocketClient;
import com.example.administrator.myapp.cls.RadioGroup;
import com.example.administrator.myapp.cls.Sign;
import com.example.administrator.myapp.configuration.MessageNameConfiguration;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class HeadFragment extends Fragment implements AdapterView.OnItemClickListener,RadioGroup.OnCheckedChangeListener{
    private RadioGroup rg;
    private ListView listView;
    private List<Sign> list=new ArrayList<>();
    private List<CheckInActivityInfo> mychecklist=new ArrayList<>();
    private List<MyInfo> myInfoslist=new ArrayList<>();
    private MyInfo myInfo;
    private int myID;
    private SocketApplication socketApplication;
    private  SocketClient socketClient;
    private InfoManager infoManager;
    private BaseAdapter adapter;
    private Thread thread1,thread2;
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
        rg=view.findViewById(R.id.rgTab);
        rg.setOnCheckedChangeListener(this);
        adapter=new HeadAdapter(getContext(),mychecklist);
        onActivityCreated(savedInstanceState);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        socketApplication=(SocketApplication)getActivity().getApplication();
        socketClient=socketApplication.getSocketClient();
        infoManager=socketApplication.getInfoManager();
        myInfo=infoManager.getMyInfo();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      Intent itemItent=new Intent(getActivity(), sign.class);
      itemItent.putExtra(MessageNameConfiguration.ACTIVITY_ID,
              mychecklist.get(position).getActivityID());
      Log.i(" 我的活动信息列表","跳转活动详情界面-ID:"+itemItent.getIntExtra("activityID",0)
      +"-index:"+position);
      startActivity(itemItent);
    }

    @Override
    public void onCheckedChanged(int checkedId) {
        switch (checkedId){
            case R.id.managedactivities:{
                thread1=new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            if (myInfo.getManagedActivities()==null) {
                                while (true) {
                                    Thread.sleep(5);
                                    if (myInfo.getManagedActivities()!=null) break;
                                }
                            }
                            Log.i("我的活动列表","我管理的列表:"+myInfo.getManagedActivities());
                            refreshListView(myInfo.getManagedActivities());
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                    }
                };
                thread1.start();
            }break;
            case R.id.joinedactivities:{
                thread1=new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            if (myInfo.getJoinedActivities()==null) {
                                while (true) {
                                    Thread.sleep(5);
                                    if (myInfo.getJoinedActivities()!=null) break;
                                }
                            }
                            Log.i("我的活动列表","我加入的列表:"+myInfo.getJoinedActivities());
                            refreshListView(myInfo.getJoinedActivities());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread1.start();
            }break;
            case R.id.initiatoractivities:{
                thread1=new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            if (myInfo.getInitiatorActivities()==null) {
                                while (true) {
                                    Thread.sleep(5);
                                    if (myInfo.getInitiatorActivities()!=null) break;
                                }
                            }
                            Log.i("我的活动列表","我发起的列表:"+myInfo.getInitiatorActivities());
                            refreshListView(myInfo.getInitiatorActivities());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread1.start();
            }break;
        }
    }

    private void changeListView(){
        ((MainActivity) getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                adapter=new HeadAdapter(getContext(),mychecklist);
                listView.setAdapter(adapter);
            }
        });
    }

    //刷新ListView
    public void refreshListView(final List<Integer> myactivities){
        thread1.interrupt();
        mychecklist.clear();
        if (myactivities.size()>0){
            for (int i=0;i<myactivities.size();i++){
                if (infoManager.uiGetActivityInfo(myactivities.get(i))==null){
                    final int finalI = i;
                    thread2=new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            try {
                                if (infoManager.uiGetActivityInfo(myactivities.get(finalI))==null) {
                                    while (true) {
                                        Thread.sleep(5);
                                        if (infoManager.uiGetActivityInfo(myactivities.get(finalI))!=null) break;
                                    }
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    thread2.start();
                }
                    //thread2.interrupt();
                    mychecklist.add(infoManager.uiGetActivityInfo(myactivities.get(i)));
            }
        }
        changeListView();
    }
}

package com.example.administrator.myapp.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.myapp.Info.CheckInActivityInfo;
import com.example.administrator.myapp.InfoManager;
import com.example.administrator.myapp.R;
import com.example.administrator.myapp.adapter.ActivityListAdapter;
import com.example.administrator.myapp.client.SocketApplication;
import com.example.administrator.myapp.cls.RadioGroup;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class HeadFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{
    private InfoManager infoManager;
    private ListView activityListView;
    private ActivityListAdapter activityListAdapter;
    private RadioGroup radioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_head, container, false);
        activityListView=view.findViewById(R.id.list_view_activity);
        radioGroup=view.findViewById(R.id.rgTab);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SocketApplication socketApplication=(SocketApplication)getActivity().getApplication();
        infoManager=socketApplication.getInfoManager();
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(int checkedId) {
        ///////闪退
        Log.i("相关活动UI","活动列表");
        switch (checkedId) {
            case R.id.joinedactivities: {
                ArrayList<CheckInActivityInfo> joinedActivity = new ArrayList<>();
                for (int i = 0; i < infoManager.getMyInfo().getJoinedActivities().size(); i++) {
                    CheckInActivityInfo checkInActivityInfo = infoManager.uiGetActivityInfo(infoManager.getMyInfo().getJoinedActivities().get(i));
                    if (checkInActivityInfo != null) {
                        joinedActivity.add(checkInActivityInfo);
                    }
                }
                Log.i("相关活动UI","加入活动列表:"+joinedActivity.size());
                if(joinedActivity.size()>0){
                    activityListAdapter = new ActivityListAdapter(getContext(), joinedActivity, R.layout.fragment_head_list, infoManager.getMyInfo().getMyID());
                    activityListView.setAdapter(activityListAdapter);
                }
            }
            break;
            case R.id.managedactivities: {
                ArrayList<CheckInActivityInfo> joinedActivity = new ArrayList<>();
                for (int i = 0; i < infoManager.getMyInfo().getJoinedActivities().size(); i++) {
                    CheckInActivityInfo checkInActivityInfo = infoManager.uiGetActivityInfo(infoManager.getMyInfo().getManagedActivities().get(i));
                    if (checkInActivityInfo != null) {
                        joinedActivity.add(checkInActivityInfo);
                    }
                }
                Log.i("相关活动UI","管理活动列表:"+joinedActivity.size());
                if(joinedActivity.size()>0){
                    activityListAdapter = new ActivityListAdapter(getContext(), joinedActivity, R.layout.fragment_head_list, infoManager.getMyInfo().getMyID());
                    activityListView.setAdapter(activityListAdapter);
                }
            }
            break;
            case R.id.initiatoractivities: {
                ArrayList<CheckInActivityInfo> joinedActivity = new ArrayList<>();
                for (int i = 0; i < infoManager.getMyInfo().getJoinedActivities().size(); i++) {
                    CheckInActivityInfo checkInActivityInfo = infoManager.uiGetActivityInfo(infoManager.getMyInfo().getInitiatorActivities().get(i));
                    if (checkInActivityInfo != null) {
                        joinedActivity.add(checkInActivityInfo);
                    }
                }
                Log.i("相关活动UI","发起活动列表:"+joinedActivity.size());
                if(joinedActivity.size()>0){
                    activityListAdapter = new ActivityListAdapter(getContext(), joinedActivity, R.layout.fragment_head_list, infoManager.getMyInfo().getMyID());
                    activityListView.setAdapter(activityListAdapter);
                }
            }
            break;
        }
    }
}

package com.example.administrator.myapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.administrator.myapp.InfoManager;
import com.example.administrator.myapp.R;
import com.example.administrator.myapp.adapter.HeadAdapter;
import com.example.administrator.myapp.client.SocketApplication;
import com.example.administrator.myapp.client.SocketClient;
import com.example.administrator.myapp.cls.Sign;

import java.util.ArrayList;
import java.util.List;

public class HeadFragment extends Fragment {
    private ListView listView;
    private List<Sign> list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_head, container, false);
        listView=view.findViewById(R.id.head_list);
        initData();
       BaseAdapter adapter=new HeadAdapter(list,getContext());
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SocketApplication socketApplication=(SocketApplication)getActivity().getApplication();
        SocketClient socketClient=socketApplication.getSocketClient();
        InfoManager infoManager=socketApplication.getInfoManager();
    }

    private void initData() {
        list.clear();
        for (int i=1;i<=7;i++) {
            Sign sign=new Sign("活动："+i,"赵煦仁","主题","地址",R.mipmap.head_pro);
            list.add(sign);
        }
    }
}

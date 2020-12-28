package com.example.administrator.myapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.administrator.myapp.R;
import com.example.administrator.myapp.adapter.MeAdapter;
import com.example.administrator.myapp.cls.MeMenu;

import java.util.ArrayList;
import java.util.List;

public class MeFragment extends Fragment {
    private ListView listView;
    private List<MeMenu> list=new ArrayList<>();
    private static final String[] str={"我的关注","我的话题","活动记录","问题反馈","系统设置","安全中心","关于应用"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_me, container, false);
        listView=view.findViewById(R.id.me_listview);
        initData();
        BaseAdapter adapter=new MeAdapter(list,getContext());
        listView.setAdapter(adapter);
       return view;
    }

    private void initData() {
        list.clear();
        for (int i=0;i<7;i++) {
            MeMenu mm=new MeMenu(str[i]+i,R.mipmap.me_pro);
            list.add(mm);
        }
    }
}

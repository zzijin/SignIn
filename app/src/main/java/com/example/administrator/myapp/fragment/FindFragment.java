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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.myapp.InfoManager;
import com.example.administrator.myapp.R;
import com.example.administrator.myapp.baidumap.createactivity;
import com.example.administrator.myapp.baidumap.information;
import com.example.administrator.myapp.client.SocketApplication;
import com.example.administrator.myapp.configuration.MessageNameConfiguration;


public class FindFragment extends Fragment {
    Button vCreateNewActivity;
    InfoManager infoManager;
    EditText et_search;
    ImageView searchbtn;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_find, container, false);
        et_search=view.findViewById(R.id.find_search);
        onViewCreated(view,savedInstanceState);
        view.findViewById(R.id.find_search_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (infoManager.uiGetActivityInfo(Integer.valueOf(et_search.getText().toString()))!=null){
                    Intent intent=new Intent(getActivity(),information.class);
                    intent=intent.putExtra(MessageNameConfiguration.ACTIVITY_ID,Integer.valueOf(et_search.getText().toString()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"此活动暂不存在",Toast.LENGTH_SHORT).show();
                }
            }
        });
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

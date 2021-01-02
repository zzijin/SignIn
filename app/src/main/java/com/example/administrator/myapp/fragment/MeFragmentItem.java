package com.example.administrator.myapp.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class MeFragmentItem extends Fragment {
private int myresource;
    @SuppressLint("ValidFragment")
    public MeFragmentItem(int myresource){
        this.myresource=myresource;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(myresource, container, false);
        return view;
    }


}

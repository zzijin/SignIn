package com.example.administrator.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.myapp.R;
import com.example.administrator.myapp.ViewHolder;
import com.example.administrator.myapp.cls.Sign;

import java.util.List;

public class HeadAdapter extends BaseAdapter {
    private List<Sign> list;
    private Context ctx;

    public HeadAdapter(List<Sign> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(ctx).inflate(R.layout.fragment_head_list, null);
            viewHolder.sign = list.get(position);
            viewHolder.headpro = view.findViewById(R.id.head_list_pro);
            viewHolder.headpro.setImageResource(viewHolder.sign.getProfile());
            viewHolder.headna = view.findViewById(R.id.head_list_hdname);
            viewHolder.headna.setText(viewHolder.sign.getSignname());
            viewHolder.headsp = view.findViewById(R.id.head_list_time);
            viewHolder.headsp.setText(viewHolder.sign.getSponsor());
        } else {
            viewHolder = (ViewHolder) view.getTag();

        }
        return view;
    }

}

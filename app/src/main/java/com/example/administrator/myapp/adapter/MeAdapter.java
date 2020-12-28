package com.example.administrator.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.myapp.R;
import com.example.administrator.myapp.ViewHolder;
import com.example.administrator.myapp.cls.MeMenu;

import java.util.List;

public class MeAdapter extends BaseAdapter
{
    private List<MeMenu> list;
    protected Context ctx;
    public  MeAdapter(List<MeMenu> list, Context ctx) {
        this.list=list;
        this.ctx=ctx;
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
        if(view==null){
            viewHolder = new ViewHolder();
            view= LayoutInflater.from(ctx).inflate(R.layout.fragment_me_list,null);
            viewHolder.mm=list.get(position);
            viewHolder.metv=view.findViewById(R.id.me_list_tv);
            viewHolder.mepro=view.findViewById(R.id.me_list_pro);
            viewHolder.mepro.setImageResource(viewHolder.mm.getProfile());
            viewHolder.metv.setText(viewHolder.mm.getItem());

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }
        return view;
    }
}

package com.example.administrator.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.myapp.Info.CheckInActivityInfo;
import com.example.administrator.myapp.R;
import com.example.administrator.myapp.ViewHolder;

import java.util.List;

public class HeadAdapter extends BaseAdapter {
    private List<CheckInActivityInfo> checkInActivityInfosList;
    private Context ctx;

    public HeadAdapter(Context ctx, List<CheckInActivityInfo> checkInActivityInfosList) {
        this.ctx = ctx;
        this.checkInActivityInfosList=checkInActivityInfosList;
    }

    @Override
    public int getCount() {
        return checkInActivityInfosList.size();
    }

    @Override
    public Object getItem(int position) {
        return checkInActivityInfosList.get(position);
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
            viewHolder.checkInActivityInfo=checkInActivityInfosList.get(position);
            viewHolder.headpro = view.findViewById(R.id.head_list_pro);
            viewHolder.headpro.setImageResource(R.drawable.ic_activity_cover_defult);
            viewHolder.headna = view.findViewById(R.id.head_list_hdname);
            viewHolder.headna.setText("活动主题："+viewHolder.checkInActivityInfo.getActivityTheme());
            viewHolder.headsp = view.findViewById(R.id.head_list_time);
            viewHolder.headsp.setText("活动ID"+viewHolder.checkInActivityInfo.getActivityID()+"");
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        return view;
    }

}

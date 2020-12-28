package com.example.administrator.myapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.myapp.ViewHolder;
import com.example.administrator.myapp.cls.Sign;

import java.util.List;


public class MyAdapter extends BaseAdapter {
    private List<Sign> list;
    private Context ctx;
    public MyAdapter(List<Sign> list, Context ctx) {
        this.list=list;
        this.ctx=ctx;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
//        if(view==null){
////            viewHolder = new ViewHolder();
////            view= LayoutInflater.from(ctx).inflate(R.layout.stu_ad_view,null);
////            viewHolder.stu=list.get(position);
////            ImageView profile=view.findViewById(R.id.profile);
////            profile.setImageResource(viewHolder.stu.getProfile());
////            TextView nickname=view.findViewById(R.id.nickname);
////            nickname.setText(viewHolder.stu.getNickname());
////            TextView nickid=view.findViewById(R.id.nickid);
////            nickid.setText(viewHolder.stu.getNickid());
////            viewHolder.sx=view.findViewById(R.id.stu_sx);
////            viewHolder.yw=view.findViewById(R.id.stu_yw);
////            viewHolder.yy=view.findViewById(R.id.stu_yy);
////            viewHolder.btn=view.findViewById(R.id.stu_btn);
//        }else{
//            viewHolder = (ViewHolder)view.getTag();
//        }
//

//        final ViewHolder finalViewHolder = viewHolder;
//        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {
//                  finalViewHolder.sx.setText("数学："+finalViewHolder.stu.getSx());
//                  finalViewHolder.yw.setText("语文："+finalViewHolder.stu.getYw());
//                  finalViewHolder.yy.setText("英语："+finalViewHolder.stu.getYy());
//              }
//          });
        return view;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

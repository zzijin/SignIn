package com.example.administrator.myapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapp.Info.CheckInActivityInfo;
import com.example.administrator.myapp.R;
import com.example.administrator.myapp.baidumap.sign;
import com.example.administrator.myapp.configuration.MessageNameConfiguration;

import java.util.ArrayList;

public class ActivityListAdapter extends ArrayAdapter<CheckInActivityInfo> {
    private int resourceId;
    private int myID;

    public ActivityListAdapter(Context context,ArrayList<CheckInActivityInfo> checkInActivityInfos, int textViewResourceId, int myID){
        super(context,textViewResourceId,checkInActivityInfos);
        this.resourceId=textViewResourceId;
        this.myID=myID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view  = LayoutInflater.from(getContext()).inflate(resourceId, null);
        final CheckInActivityInfo checkInActivityInfo=getItem(position);
        ImageView activityCover=view.findViewById(R.id.item_activity_cover);
        TextView activityTheme=view.findViewById(R.id.item_activity_theme);
        TextView activityInitiator=view.findViewById(R.id.item_activity_initiator);
        TextView activitySignInStatus=view.findViewById(R.id.item_activity_sign_in_status);
        activityTheme.setText("活动主题:"+checkInActivityInfo.getActivityTheme());
        activityInitiator.setText("活动发起人:"+checkInActivityInfo.getActivityInitiatorID());
        activitySignInStatus.setText("签到状态:不可用");
        for(int i=0;i<checkInActivityInfo.getActivityParticipant().size();i++){
            if(checkInActivityInfo.getActivityParticipant().get(i).userID==this.myID){
                activitySignInStatus.setText("签到状态:"+checkInActivityInfo.getActivityParticipant().get(i).clockIn);
            }
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), sign.class);
                intent.putExtra(MessageNameConfiguration.ACTIVITY_ID,checkInActivityInfo.getActivityID());
                getContext().startActivity(intent);
            }
        });
        return view;
    }
}

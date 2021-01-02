package com.example.administrator.myapp.baidumap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.administrator.myapp.Info.CheckInActivityInfo;
import com.example.administrator.myapp.InfoManager;
import com.example.administrator.myapp.R;
import com.example.administrator.myapp.client.SocketApplication;
import com.example.administrator.myapp.configuration.MessageNameConfiguration;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class information extends AppCompatActivity {
    TextView activity_title,activity_location,time_start,time_signstart,code_invite;
    InfoManager infoManager;
    int activityID;
    GeoCoder mSearch;
    String code;
    Button btn_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        SocketApplication socketApplication = (SocketApplication) getApplication();
        infoManager = socketApplication.getInfoManager();

        activity_title = findViewById(R.id.show_activity_title);
        activity_location = findViewById(R.id.show_actiivity_location);
        time_start = findViewById(R.id.show_time_start);
        time_signstart = findViewById(R.id.show_signtime_start);
        code_invite = findViewById(R.id.code_invite);
        btn_join = findViewById(R.id.btn_join);
        btn_join.setOnClickListener(join);

        Intent getid = getIntent();
        activityID = getid.getIntExtra(MessageNameConfiguration.ACTIVITY_ID, -1);
        if (activityID == -1) {
            finish();
            return;
        }

        if(infoManager.getActivityIDInMyJoinActivityList(activityID)||infoManager.getActivityIDInMyMangedActivityList(activityID)||
                infoManager.getActivityIDInMyInitiatorActivityList(activityID)){
            setJoin();
            return;
        }

        getActivityInfo();

        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                Log.i("address", "" + reverseGeoCodeResult.getAddress());
                activity_location.setText(reverseGeoCodeResult.getAddress());
            }
        });
        //下面是传入对应的经纬度

        getInformation();
    }


    public void getInformation() {

    }

    public void getActivityInfo() {
        GetActivityInfoThread getActivityInfoThead = new GetActivityInfoThread();
        getActivityInfoThead.start();
    }

    public void setView(final CheckInActivityInfo checkInActivityInfo) {
        ((information) this).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity_title.setText("活动主题："+checkInActivityInfo.getActivityEndTime());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    time_start.setText("活动时间："+simpleDateFormat.parse(checkInActivityInfo.getActivityStartTime())+"-"+simpleDateFormat.parse(checkInActivityInfo.getActivityEndTime()));
                    time_signstart.setText("签到时间："+simpleDateFormat.parse(checkInActivityInfo.getActivityCheckInStartTime())+"-"+simpleDateFormat.parse(checkInActivityInfo.getActivityCheckInEndTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(checkInActivityInfo.getActivityCheckInLatitude(), checkInActivityInfo.getActivityCheckInLongitude())));
            }
        });
    }

    class GetActivityInfoThread extends Thread {
        @Override
        public void run() {
            while (true) {
                CheckInActivityInfo checkInActivityInfo = infoManager.uiGetActivityInfo(activityID);
                if (checkInActivityInfo != null) {
                    setView(checkInActivityInfo);
                    break;
                }
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void JoinStatus() {
        GetJoinStatus getJoinStatus = new GetJoinStatus();
        getJoinStatus.start();
    }

    public void setJoin() {
        ((information) this).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(information.this, sign.class);
                intent.putExtra(MessageNameConfiguration.ACTIVITY_ID, activityID);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }

    public void setJoinfail() {
        ((information) this).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "加入失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class GetJoinStatus extends Thread {
        @Override
        public void run() {
            while (true) {
                if (infoManager.uiGetJoinActivityStatus(activityID) == 1) {
                    setJoin();
                    break;
                }
                if (infoManager.uiGetJoinActivityStatus(activityID) == -1 || infoManager.uiGetJoinActivityStatus(activityID) == -2) {
                    setJoinfail();
                    break;
                }
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    View.OnClickListener join = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            code = code_invite.getText().toString();

            if(infoManager.uiJoinActivity(activityID,code)){
                JoinStatus();
            }
            else {
                Toast.makeText(getApplicationContext(),"加入失败",Toast.LENGTH_SHORT).show();
            }
        }
    };
}
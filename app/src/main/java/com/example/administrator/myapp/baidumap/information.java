package com.example.administrator.myapp.baidumap;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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

public class information extends AppCompatActivity {
    TextView activity_title,activity_location,time_start,time_end,time_signstart,time_signend,remark;
    String title,starttime,endtime,signstart,signend;
    LatLng latLng;
    InfoManager infoManager;
    int activityID;
    GeoCoder mSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_information);
        SocketApplication socketApplication=(SocketApplication)getApplication();
        infoManager=socketApplication.getInfoManager();

        activityID=0;

        activity_title = findViewById(R.id.show_activity_title);
        activity_location = findViewById(R.id.show_actiivity_location);
        time_start = findViewById(R.id.show_time_start);
        time_end = findViewById(R.id.show_time_end);
        time_signstart = findViewById(R.id.show_signtime_start);
        time_signend = findViewById(R.id.show_signtime_end);

        getActivityInfo();

        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                Log.i("address",""+reverseGeoCodeResult.getAddress());
                activity_location.setText(reverseGeoCodeResult.getAddress());
            }
        });
        //下面是传入对应的经纬度

        getInformation();
    }

    public void btn_join(View view) {
        Intent intent=new Intent(information.this,sign.class);
        startActivity(intent);
    }
    public void getInformation(){
        activity_title.setText("活动主题");
        activity_location.setText("活动位置");
        time_start.setText("开始时间");
        time_end.setText("结束时间");
        time_signstart.setText("签到开始");
        time_signend.setText("签到结束");
    }

    public void getActivityInfo(){
        GetActivityInfoThread getActivityInfoThead=new GetActivityInfoThread();
        getActivityInfoThead.start();
    }

    public void setView(CheckInActivityInfo checkInActivityInfo){

    }

    class GetActivityInfoThread extends Thread{
        @Override
        public void run() {
            while (true){
                CheckInActivityInfo checkInActivityInfo = infoManager.uiGetActivityInfo(activityID);
                if (checkInActivityInfo!=null){
                    title = checkInActivityInfo.getActivityTheme();
                    starttime = checkInActivityInfo.getActivityStartTime();
                    endtime = checkInActivityInfo.getActivityEndTime();
                    signstart = checkInActivityInfo.getActivityCheckInStartTime();
                    signend = checkInActivityInfo.getActivityCheckInStartTime();
                    mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(checkInActivityInfo.getActivityCheckInLatitude(),checkInActivityInfo.getActivityCheckInLongitude())));
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
}
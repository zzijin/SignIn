package com.example.administrator.myapp.baidumap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.administrator.myapp.Info.CheckInActivityInfo;
import com.example.administrator.myapp.InfoManager;
import com.example.administrator.myapp.R;
import com.example.administrator.myapp.client.SocketApplication;
import com.example.administrator.myapp.client.SocketClient;
import com.example.administrator.myapp.configuration.MessageNameConfiguration;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class sign extends AppCompatActivity {
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    LocationClient mLocationClient;
    Boolean isBackLoc=true;
    double accuracy;
    double Latitude;
    double Longitude;
    double Altitude;
    String address,value_information;
    TextView mylocation,information;
    LatLng mylatlng, destinationlatlng;
    InfoManager infoManager;
    int activityID;

    GeoCoder mSearch;
    Date start,end;
    Button btn;
    MapStatus mMapStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        SocketApplication demoApplication=(SocketApplication) getApplication();

        SocketClient socketClient=demoApplication.getSocketClient();
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mylocation = findViewById(R.id.mylocation);
        information = findViewById(R.id.information);
        btn = findViewById(R.id.btn_sign);
        Intent id = getIntent();
        activityID = id.getIntExtra(MessageNameConfiguration.ACTIVITY_ID
                ,-1);
        if(id.getStringExtra("identity") != "加入者"){
            btn.setVisibility(View.INVISIBLE);
        }
        GeoCoder mSearch;
        mBaiduMap.setMyLocationEnabled(true);
        getLocation();
        getInformation();
        getActivityInfo();

        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                Log.i("address",""+reverseGeoCodeResult.getAddress());
                information.setText(value_information+reverseGeoCodeResult.getAddress());
            }
        });

        btn.setOnClickListener(btnSign);

    }

    public void getLocation(){
        mLocationClient = new LocationClient(this);

//通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setScanSpan(1000);

//设置locationClientOption
        mLocationClient.setLocOption(option);

//注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
//开启地图定位图层
        mLocationClient.start();
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null){
                return;
            }

            accuracy=location.getRadius();
            Latitude=location.getLatitude();
            Longitude=location.getLongitude();
            Altitude=location.getAltitude();
            address=location.getAddrStr();
            mylocation.setText("我的位置："+address+"("+Latitude+","+Longitude+")");
            mylatlng=new LatLng(Latitude,Longitude);
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection())
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            mBaiduMap.setMyLocationData(locData);
            if(isBackLoc){
                isBackLoc=false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }

    public void Range(){
//构造CircleOptions对象
        CircleOptions mCircleOptions = new CircleOptions().center(destinationlatlng)
                .radius(100)
                .fillColor(0xAA0000FF) //填充颜色
                .stroke(new Stroke(5, 0xAA00ff00)); //边框宽和边框颜色

//在地图上显示圆
        Overlay mCircle = mBaiduMap.addOverlay(mCircleOptions);
    }

    @Override
    protected void onResume() {
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        super.onResume();
    }
    @Override
    protected void onPause() {
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    public void getActivityInfo(){
        GetActivityInfoThread getActivityInfoThead=new GetActivityInfoThread();
        getActivityInfoThead.start();
    }
    public void getInformation(){
        information.setText("活动主题："+"\n活动地址："+"\n时间：");
    }
    public void setView(final CheckInActivityInfo checkInActivityInfo){
        ((sign) this).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    start=simpleDateFormat.parse(checkInActivityInfo.getActivityCheckInStartTime());
                    end=simpleDateFormat.parse(checkInActivityInfo.getActivityCheckInEndTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                destinationlatlng = new LatLng(checkInActivityInfo.getActivityCheckInLatitude(),checkInActivityInfo.getActivityCheckInLongitude());
                Range();
                mMapStatus = new MapStatus.Builder()
                        .target(mylatlng)//设定中心点坐标 ,要移动到的点
                        .zoom(getRank(DistanceUtil.getDistance(mylatlng, destinationlatlng))) //设置级别，放大地图到13倍
                        .build();

                //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                //改变地图状态
                mBaiduMap.setMapStatus(mMapStatusUpdate);
                value_information = "活动主题："+checkInActivityInfo.getActivityTheme()+
                        "\n开始时间：" + checkInActivityInfo.getActivityStartTime() +
                        "\n签到时间：" + checkInActivityInfo.getActivityCheckInStartTime() + "——" + checkInActivityInfo.getActivityCheckInEndTime() +
                        "\n活动地点：";
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(checkInActivityInfo.getActivityCheckInLatitude(),checkInActivityInfo.getActivityCheckInLongitude())));
            }
        });
    }

    class GetActivityInfoThread extends Thread{
        @Override
        public void run() {
            while (true){
                CheckInActivityInfo checkInActivityInfo = infoManager.uiGetActivityInfo(activityID);
                if (checkInActivityInfo!=null){
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

    View.OnClickListener btnSign = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(System.currentTimeMillis()<=start.getTime()){
                Toast.makeText(getApplicationContext(),"未到签到时间",Toast.LENGTH_SHORT).show();
            }
            else if (System.currentTimeMillis()>=end.getTime()){
                Toast.makeText(getApplicationContext(),"迟到",Toast.LENGTH_SHORT).show();
            }
            else if(DistanceUtil.getDistance(mylatlng, destinationlatlng)>100){
                Toast.makeText(getApplicationContext(),"还未到达目的地",Toast.LENGTH_SHORT).show();
            }
            else if(DistanceUtil.getDistance(mylatlng, destinationlatlng)<=100&&System.currentTimeMillis()>=start.getTime()&&System.currentTimeMillis()<=end.getTime()){
                Log.i("distance", String.valueOf(DistanceUtil.getDistance(mylatlng, destinationlatlng)));
                Toast.makeText(getApplicationContext(),"签到成功",Toast.LENGTH_SHORT).show();
                btn.setEnabled(false);
            }
        }
    };

    public int getRank(double distance){
        int rank = 10;
        if(distance<=20){
            rank = 19;
        }
        else if(distance<=50){
            rank = 18;
        }
        else if(distance<=100){
            rank = 17;
        }
        else if(distance<=200){
            rank = 16;
        }
        else if(distance<=500){
            rank = 15;
        }
        else if(distance<=1000){
            rank = 14;
        }
        else if(distance<=2000){
            rank = 13;
        }
        else if(distance<=5000){
            rank = 12;
        }
        else if(distance<=10000){
            rank = 11;
        }
        return rank;
    }
}


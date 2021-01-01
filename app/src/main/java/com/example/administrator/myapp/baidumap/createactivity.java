package com.example.administrator.myapp.baidumap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;


import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.administrator.myapp.Info.MyInfo;
import com.example.administrator.myapp.InfoManager;
import com.example.administrator.myapp.R;
import com.example.administrator.myapp.client.SocketApplication;

import java.math.BigDecimal;
import java.util.Calendar;

public class createactivity extends AppCompatActivity {
    EditText activity_title,activity_location,time_year,time_mouth,time_day,time_start,time_end,signtime_start,signtime_end,city,keyword,code_invite;
    String value_title,value_signstart,value_signend,value_start,value_end,value_city,value_keyword;
    Button search;
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    LocationClient mLocationClient;
    LinearLayout show_timepicker;
    TimePicker timepicker;
    Boolean isBackLoc=true;
    double accuracy;
    double Latitude,point_latitude;
    double Longitude,point_longitude;
    String address;
    Calendar calendar = Calendar.getInstance();
    int t,value_code,hour,minute;
    InfoManager infoManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceateactivity);
        SocketApplication socketApplication=(SocketApplication)getApplication();

        infoManager=socketApplication.getInfoManager();

        activity_title = findViewById(R.id.activity_title);
        activity_location = findViewById(R.id.activity_location);

        code_invite = findViewById(R.id.code_invite);
        time_year = findViewById(R.id.time_year);
        time_mouth = findViewById(R.id.time_month);
        time_day = findViewById(R.id.time_day);
        time_start = findViewById(R.id.time_start);
        time_end = findViewById(R.id.time_end);
        signtime_start = findViewById(R.id.signtime_start);
        signtime_end = findViewById(R.id.signtime_end);
        show_timepicker = findViewById(R.id.show_timepicker);
        timepicker = findViewById(R.id.timepicker);
        city = findViewById(R.id.city);
        keyword = findViewById(R.id.keyword);
        search = findViewById(R.id.search);

        timepicker.setIs24HourView(true);
        time_start.setInputType(InputType.TYPE_NULL);
        time_end.setInputType(InputType.TYPE_NULL);
        signtime_start.setInputType(InputType.TYPE_NULL);
        signtime_end.setInputType(InputType.TYPE_NULL);

        mMapView = (MapView) findViewById(R.id.create_map);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);

        getLocation();

        mBaiduMap.setOnMapClickListener(listener);
        city.addTextChangedListener(iscity);
        time_start.setOnClickListener(setstarttime);
        time_end.setOnClickListener(setendtime);
        signtime_start.setOnClickListener(setsignstarttime);
        signtime_end.setOnClickListener(setsignendtime);
        timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hour=i;
                minute=i1;
            }
        });
    }

    public void btn_createactivity(View view) {
        value_title = activity_title.getText().toString();
        value_code = Integer.valueOf(code_invite.getText().toString());
        value_signstart = timetostring(signtime_start.getText().toString());
        value_signend = timetostring(signtime_end.getText().toString());
        value_start = timetostring(time_start.getText().toString());
        value_end = timetostring(time_end.getText().toString());
        if(value_title.length() > 20){
            Toast.makeText(getApplicationContext(),"活动主题名过长",Toast.LENGTH_SHORT);
        }
        else if (code_invite.getText().toString().length() > 8){
            Toast.makeText(getApplicationContext(),"邀请码过长",Toast.LENGTH_SHORT);
        }
        else if(activity_title.getText() == null || activity_location.getText() == null || time_year.getText() == null || time_mouth.getText() == null || time_day.getText() == null || time_start.getText() == null || time_end.getText() == null || signtime_start.getText() == null || signtime_start.getText() == null || code_invite.getText() == null){
            Toast.makeText(getApplicationContext(),"不能为空",Toast.LENGTH_SHORT);
        }
        else {
            infoManager.uiRegisterActivityInfo(infoManager.getMyInfo().getMyID(),value_title,point_longitude,point_latitude,value_code,value_signstart,value_signend,value_start,value_end);
        }
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

    public void settime(View view) {
        if(t==0){
            time_start.setText(hour+":"+minute);
        }
        if(t==1){
            time_end.setText(hour+":"+minute);
        }
        if(t==2){
            signtime_start.setText(hour+":"+minute);
        }
        if(t==3){
            signtime_end.setText(hour+":"+minute);
        }
        show_timepicker.setVisibility(View.INVISIBLE);
    }

    public void search(View view) {
        // 通过GeoCoder的实例方法得到GerCoder对象
        GeoCoder geoCoder=GeoCoder.newInstance();//根据某个key寻找地理位置的坐标，这个key可以是地址还可以是ip地址
        // 得到GenCodeOption对象
        GeoCodeOption geoCodeOption=new GeoCodeOption();//地理编码请求参数
        value_city = city.getText().toString();
        value_keyword = keyword.getText().toString();
        geoCodeOption.address(value_keyword);
        geoCodeOption.city(value_city); // 这里必须设置城市，否则会报错
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            //将具体的地址转化为坐标
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                if (geoCodeResult==null||geoCodeResult.error!=SearchResult.ERRORNO.NO_ERROR){
                    Toast.makeText(getApplicationContext(),"检索错误",Toast.LENGTH_SHORT).show();
                }else {
                    // 得到具体地址的坐标
                    //LatLng是存储经纬度坐标值的类，单位角度。
                    LatLng latLng=geoCodeResult.getLocation();//使用传入的经纬度构造LatLng 对象，一对经纬度值代表地球上一个地点。
                    Log.i("经度", String.valueOf(latLng));
                    // 得到一个标记的控制器
                    //MarkerOptions设置 Marker 覆盖物的图标，相同图案的 icon 的 marker 最好使用同一个 BitmapDescriptor 对象以节省内存空间。
                    MarkerOptions markerOptions = new MarkerOptions();
                    BitmapDescriptor mbitmapDescriptor = BitmapDescriptorFactory  //BitmapDescriptor用户自定义图标
                            .fromResource(R.drawable.mark_location);
                    // 设置标记的图标
                    markerOptions.icon(mbitmapDescriptor);
                    // 设置标记的坐标
                    markerOptions.position(latLng);
                    // 添加标记
                    mBaiduMap.addOverlay(markerOptions);//意思是添加覆盖物
                    // 设置地图跳转的参数
                    latLng = new LatLng(latLng.latitude-0.01515,latLng.longitude);

                    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
                            .newLatLngZoom(latLng, 15);
                    // 设置进行地图跳转

                    mBaiduMap.setMapStatus(mMapStatusUpdate);

                }
            }
            // 这个方法是将坐标转化为具体地址
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

            }
        });

        // 这句话必须写，否则监听事件里面的都不会执行
        geoCoder.geocode(geoCodeOption);
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
            address=location.getAddrStr();
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
                LatLng ll = new LatLng(location.getLatitude()-0.0022,
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    BaiduMap.OnMapClickListener listener = new BaiduMap.OnMapClickListener() {
        /**
         * 地图单击事件回调函数
         *
         * @param point 点击的地理坐标
         */
        @Override
        public void onMapClick(LatLng point) {
            point_latitude=point.latitude;
            point_longitude=point.longitude;
            final BigDecimal latitude=new BigDecimal(point_latitude);
            final BigDecimal longitude=new BigDecimal(point_longitude);

            GeoCoder mSearch = GeoCoder.newInstance();
            mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                @Override
                public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

                }
                @Override
                public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                    Log.i("address",""+reverseGeoCodeResult.getAddress());
                    activity_location.setText(reverseGeoCodeResult.getAddress()+"\n("+latitude.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue() + ","
                            +longitude.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue()+")");
                }
            });
            //下面是传入对应的经纬度
            mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(point));
            //先清除图层
            mBaiduMap.clear();
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.mark_location);
//构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
//在地图上添加Marker，并显示
            mBaiduMap.addOverlay(option);
            CircleOptions mCircleOptions = new CircleOptions().center(point)
                    .radius(200)
                    .fillColor(0xAA0000FF) //填充颜色
                    .stroke(new Stroke(5, 0xAA00ff00)); //边框宽和边框颜色
            //在地图上显示圆
            Overlay mCircle = mBaiduMap.addOverlay(mCircleOptions);
            mBaiduMap.addOverlay(option);
        }

        /**
         * 地图内 Poi 单击事件回调函数
         *
         * @param mapPoi 点击的 poi 信息
         */
        @Override
        public void onMapPoiClick(MapPoi mapPoi) {

        }
    };

    View.OnClickListener setstarttime=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            show_timepicker.setVisibility(View.VISIBLE);
            timepicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            timepicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
            hour=calendar.get(Calendar.HOUR_OF_DAY);
            minute=calendar.get(Calendar.MINUTE);
            t=0;
        }
    };
    View.OnClickListener setendtime=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            show_timepicker.setVisibility(View.VISIBLE);
            timepicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            timepicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
            hour=calendar.get(Calendar.HOUR_OF_DAY);
            minute=calendar.get(Calendar.MINUTE);
            t=1;
        }
    };
    View.OnClickListener setsignstarttime=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            show_timepicker.setVisibility(View.VISIBLE);
            timepicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            timepicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
            hour=calendar.get(Calendar.HOUR_OF_DAY);
            minute=calendar.get(Calendar.MINUTE);
            t=2;
        }
    };
    View.OnClickListener setsignendtime=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            show_timepicker.setVisibility(View.VISIBLE);
            timepicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            timepicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
            hour=calendar.get(Calendar.HOUR_OF_DAY);
            minute=calendar.get(Calendar.MINUTE);
            t=3;
        }
    };
    public String timetostring(String time){
        time=time_year+"-"+time_mouth+"-"+time_day+" "+time_start+":00";
        //SimpleDateFormat tts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //time=tts.parse(time);
        return time;
    }
    TextWatcher iscity = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(city == null){
                search.setEnabled(false);
            }
            else search.setEnabled(true);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
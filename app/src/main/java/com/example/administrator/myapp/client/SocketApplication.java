package com.example.administrator.myapp.client;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.example.administrator.myapp.InfoManager;


public class SocketApplication extends Application {
    SocketClient mSocketClient;
    InfoManager infoManager;
    @Override
    public void onCreate() {
        super.onCreate();

        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        infoManager =new InfoManager();
        mSocketClient=new SocketClient(infoManager);
        infoManager.setSocketClient(mSocketClient);
    }

    public void startClientSocketThread(){
        StartClientSocketThread startClientSocketThread=new StartClientSocketThread();
        startClientSocketThread.start();
    }

    public class StartClientSocketThread extends Thread{
        @Override
        public void run() {
            while (!mSocketClient.startClientSocket()){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取socket接口
     * @return
     */
    public SocketClient getSocketClient() {
        return mSocketClient;
    }

    /**
     * 获取应用数据包
     * @return
     */
    public InfoManager getInfoManager() {
        return infoManager;
    }
}

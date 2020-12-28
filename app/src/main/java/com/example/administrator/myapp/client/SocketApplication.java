package com.example.administrator.myapp.client;

import android.app.Application;

import com.example.administrator.myapp.InfoManager;


public class SocketApplication extends Application {
    SocketClient mSocketClient;
    InfoManager infoManager;
    @Override
    public void onCreate() {
        super.onCreate();

        infoManager =new InfoManager();
        mSocketClient=new SocketClient(infoManager);
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

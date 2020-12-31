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

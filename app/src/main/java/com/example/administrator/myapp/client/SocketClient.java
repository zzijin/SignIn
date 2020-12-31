package com.example.administrator.myapp.client;

import android.util.Log;

import com.example.administrator.myapp.InfoManager;
import com.example.administrator.myapp.client.configuration.SocketConfiguration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 功能明确：连接服务器、关闭服务器、向activity提供发送消息方法、存放该app的所有与服务器相关的数据
 * 是连接activity与conn的中间类
 */
public class SocketClient {
    /**
     * Socket
     */
    private Socket mSocket;
    /**
     * 连接成功服务类
     */
    private Conn conn;
    private InfoManager infoManager;

    public SocketClient(InfoManager infoManager){
        this.infoManager = infoManager;
        startClientSocket();
    }

    public boolean startClientSocket(){
        Log.i("Socket连接","开始连接 ");
        mSocket=new Socket();
        try {
            mSocket.connect(new InetSocketAddress(SocketConfiguration.IP,SocketConfiguration.PORT),500);
            conn=new Conn(this,mSocket, infoManager);
            Log.i("Socket连接","连接正式服务器");
        } catch (IOException e) {
            Log.i("Socket连接","连接正式服务器失败");
            try {
                mSocket.connect(new InetSocketAddress(SocketConfiguration.IP_TEST,SocketConfiguration.PORT_TEST),500);
                conn=new Conn(this,mSocket, infoManager);
                Log.i("Socket连接","连接备用服务器");
            } catch (IOException ex) {
                ex.printStackTrace();
                Log.i("Socket连接","连接备用服务器失败");
                return false;
            }
        }
        return true;
    }

    /**
     * 向服务器发送关闭连接请求，在收到回复后再关闭
     * @return
     */
    public boolean endClientSocket(){

        return false;
    }

    /**
     * 心跳包接收超时后判断为网络异常，关闭连接并尝试重新连接
     */
    public void disconnect(){

    }

    /**
     * 向服务器发送消息(使用此方法发送消息)(可使用JsonGenerated类的sendMessage方法发送)
     * @param type 类别
     * @param data 数据
     * @return 是否发送成功
     */
    public boolean sendMessage(int type,byte[] data){
        PackData packData=new PackData(type,data);
        return conn.sendMessage(packData.getPackData());
    }

}

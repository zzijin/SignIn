package com.example.administrator.myapp.client;

import android.util.Log;

import com.example.administrator.myapp.InfoManager;
import com.example.administrator.myapp.client.configuration.ClientMessageTypeConfiguration;
import com.example.administrator.myapp.client.configuration.SocketConfiguration;
import com.example.administrator.myapp.client.tool.ConvertTypeTool;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * 功能明确：(连接成功后的) 向服务器发送数据/请求、接收/拆解/处理/执行服务器发来的数据/请求/命令
 *
 */
public class Conn {
    /**
     * readBuff大小
     */
    private int buffSize;
    /**
     * socket连接汇总类
     */
    private SocketClient mSocketClient;
    /**
     * socket连接
     */
    private Socket mSocket;
    /**
     * 缓存数据，从socket输入流读取到的数据
     */
    private byte[] readBuff;
    /**
     * readBuff已使用大小,指示接收数据时将数据装入readBuff时的起始位置及将
     */
    private int buffStartIndex;
    /**
     * 拆包类
     */
    private UnpackData unpackData;
    /**
     * 服务器连接状态
     */
    private boolean connStatus=false;
    /**
     * 接收数据线程
     */
    private ReceiveThread receiveThread;
    /**
     * 处理数据线程
     */
    private ProcessingDataThread processingDataThread;
    /**
     * 将接收到的数据进行分类管理
     */
    private SwitchMessage switchMessage;
    /**
     * 发送心跳包线程
     */
    private SendHeartbeatThread sendHeartbeatThread;
    private boolean connIsFree;

    /**
     * 连接服务器成功后建立conn类
     * @param socketClient
     * @param socket
     */
    public Conn(SocketClient socketClient, Socket socket, InfoManager infoManager){
        this.mSocketClient=socketClient;
        this.mSocket=socket;
        switchMessage=new SwitchMessage(infoManager);
        buffSize= SocketConfiguration.BUFF_SIZE;
        readBuff=new byte[buffSize];
        buffStartIndex=0;
        unpackData=new UnpackData();
        connStatus=true;
        connIsFree=true;
        initThread();
    }

    /**
     * 正常关闭连接
     */
    public void closeConn(){
        Log.e("Socket接收","连接正常关闭");
        connStatus=false;
        mSocket=null;
        clearConnData();
    }

    /**
     * 因异常而关闭连接
     */
    public void errorCloseConn(String error){
        Log.e("Socket接收","连接异常关闭,错误:"+error);
        connStatus=false;
        mSocket=null;
        clearConnData();
    }

    /**
     * 清理连接数据
     */
    public void clearConnData(){

    }

    /**
     * 向服务器发送数据
     * @param message 数据内容
     * @return 发送成功与否
     */
    public boolean sendMessage(byte[] message){
        if(connStatus&&message!=null){
            try {
                Log.i("Socket连接","发送消息,长度:"+message.length);
                mSocket.getOutputStream().write(message);
                return true;
            } catch (IOException e) {
                errorCloseConn(e.toString());
            }
        }
        return false;
    }

    /**
     * 初始化各类线程
     */
    public void initThread(){
        receiveThread=new ReceiveThread();
        processingDataThread=new ProcessingDataThread();
        sendHeartbeatThread=new SendHeartbeatThread();
        receiveThread.start();
        processingDataThread.start();
        sendHeartbeatThread.start();
    }

    /**
     * 向解包类的数据链表添加数据
     */
    public void addDataToUnpack(){
        unpackData.addInitialData(ConvertTypeTool.CutByte(readBuff,0,buffStartIndex));
        buffStartIndex= 0;
    }

    /**
     * 获取读取缓存数据的剩余大小
     * @return 剩余大小
     */
    public int getBuffRemain(){
        return buffSize-buffStartIndex;
    }

    /**
     * 向缓存数据添加数据后更改缓存数据指示位置及执行向解包数据链表添加数据的操作
     * @param count 向缓存数据添加数据个数
     */
    public void addBuffStartIndex(int count){
        buffStartIndex+=count;
        addDataToUnpack();
    }

    /**
     * 获取缓存数据
     * @return 缓存数据
     */
    public byte[] getReadBuff() {
        return readBuff;
    }

    public boolean getConnStatus(){
        return connStatus;
    }

    /**
     * 接收数据线程
     */
    class ReceiveThread extends Thread{
        @Override
        public void run() {
            while (connStatus){
                try {
                    Log.i("Socket接收","接收新数据");
                    addBuffStartIndex(mSocket.getInputStream().read(getReadBuff()));
                    connIsFree=false;
                    sleep(500);
                } catch (IOException e) {
                    errorCloseConn(e.toString());
                    Log.e("Socket接收","接收数据失败");
                } catch (InterruptedException e) {
                    errorCloseConn(e.toString());
                    Log.e("Socket接收","Sleep失败");
                }
            }
        }
    }

    /**
     * 处理消息线程(将与接收合并)
     */
    class ProcessingDataThread extends Thread{
        @Override
        public void run() {
            while (connStatus){
                Log.i("Socket处理","处理数据");
                BasicMessage basicMessage=unpackData.processingInitialData();
                if(basicMessage!=null){
                    Log.i("Socket处理","执行数据");
                    switchMessage.switchMessageManager(basicMessage);
                }
                else {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 每过一段时间发送心跳包验证是否与服务器连接正常
     */
    class SendHeartbeatThread extends Thread{
       private int connIsFreeCount;
        SendHeartbeatThread(){
            connIsFreeCount=1;
        }
        @Override
        public void run() {
            while (connStatus){
                if(connIsFreeCount<3){
                    if (connIsFree){
                        try {
                            String heartbeat="我思";
                            mSocketClient.sendMessage(ClientMessageTypeConfiguration.CLIENT_HEARTBEAT,heartbeat.getBytes("utf-8"));
                            connIsFreeCount++;
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        connIsFree=true;
                        connIsFreeCount=1;
                    }
                    try {
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    errorCloseConn("服务器长期无响应,心跳包计时超时:"+connIsFreeCount+"*5秒");
                }
            }
        }
    }
}

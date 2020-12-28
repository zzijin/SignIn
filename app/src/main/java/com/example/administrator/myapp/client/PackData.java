package com.example.administrator.myapp.client;

import com.example.administrator.myapp.client.tool.ConvertTypeTool;

/**
 * 是通信数据的封装类
 * 提供数据封装方法
 */
public class PackData {
    /**
     * 数据包(basicMessage)大小
     */
    private byte[] dataSize;
    /**
     * Socket数据包
     */
    private BasicMessage basicMessage;

    PackData(){
        basicMessage=new BasicMessage();
    }

    PackData(BasicMessage basicMessage){
        this.basicMessage=basicMessage;
    }

    PackData(int type,byte[] data){
        basicMessage=new BasicMessage(type,data);
    }

    /**
     * 获取用于发送的单个数据包的byte[]形式
     * @return
     */
    public byte[] getPackData(){
        byte[] basicMessageBytes=basicMessage.generatedMessage();
        dataSize= ConvertTypeTool.IntToBytes(basicMessageBytes.length);
        return ConvertTypeTool.CopyByteOfSocket(dataSize,basicMessageBytes);
    }

    /**
     * 获取用于发送的单个数据包的byte[]形式
     * @param type
     * @param data
     * @return
     */
    public byte[] getPackData(int type,byte[] data){
        basicMessage=new BasicMessage(type,data);
        byte[] basicMessageBytes=basicMessage.generatedMessage();
        dataSize= ConvertTypeTool.IntToBytes(basicMessageBytes.length);
        return ConvertTypeTool.CopyByteOfSocket(dataSize,basicMessageBytes);
    }

    /**
     * 获取用于发送的单个数据包的byte[]形式
     * @param basicMessage
     * @return
     */
    public byte[] getPackData(BasicMessage basicMessage){
        this.basicMessage=basicMessage;
        byte[] basicMessageBytes=basicMessage.generatedMessage();
        dataSize= ConvertTypeTool.IntToBytes(basicMessageBytes.length);
        return ConvertTypeTool.CopyByteOfSocket(dataSize,basicMessageBytes);
    }
}

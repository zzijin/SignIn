package com.example.administrator.myapp.client;

import com.example.administrator.myapp.client.tool.ConvertTypeTool;

import java.util.Arrays;

/**
 * Socket数据包通用基本类
 * 额外提供拆分方法和以拆分dataType与mainData
 */
public class BasicMessage {
    /**
     * 数据/命令类别，用于识别主体数据然后加以不同方法进行处理
     */
    private int messageType;
    /**
     * 主体数据
     */
    private byte[] mainData;

    BasicMessage(){

    }

    BasicMessage(int messageType,byte[] mainData){
        this.messageType =messageType;this.mainData=mainData;
    }

    /**
     * 获取该消息包的主体数据
     * @return
     */
    public byte[] getMainData() {
        return mainData;
    }

    /**
     * 获取该消息包的类别
     * @return
     */
    public int getMessageType() {
        return messageType;
    }

    /**
     * 获取该消息包类别的Byte[]形式
     * @return
     */
    public byte[] getMessageTypeBytes(){
        return ConvertTypeTool.IntToBytes(messageType);
    }

    /**
     * 获取该数据包的主体数据大小
     * @return
     */
    public int getMainDataSize(){
        return mainData.length;
    }

    /**
     * 获取该数据包的byte[]形式
     * @return
     */
    public byte[] generatedMessage(){
        return ConvertTypeTool.CopyByte(getMessageTypeBytes(),mainData);
    }


    /**
     * 拆分单个数据包(仅含有dataType和mainData)，得到请求id及数据主体
     * @param msg 单个数据包(仅含有dataType和mainData)
     * @return 数据包通用基本类
     */
    public static BasicMessage disassembleMessage(byte[] msg){
        byte[] typeString= Arrays.copyOfRange(msg,0,4);
        int typeInt = ConvertTypeTool.ByteToInt(typeString);
        byte[] dataBytes = Arrays.copyOfRange(msg,4,msg.length);
        return new BasicMessage(typeInt,dataBytes);
    }

    /**
     * 合成单个数据包，得到数据包的byte[]形式
     * @param basicMessage
     * @return
     */
    public static byte[] generatedMessage(BasicMessage basicMessage){
        return ConvertTypeTool.CopyByte(basicMessage.getMessageTypeBytes(),basicMessage.getMainData());
    }
    public static byte[] generatedMessage(int type, byte[] data){
        return ConvertTypeTool.CopyByte(ConvertTypeTool.IntToBytes(type),data);
    }
}

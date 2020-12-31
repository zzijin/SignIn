package com.example.administrator.myapp.client;

import android.util.Log;

import com.example.administrator.myapp.client.configuration.SocketConfiguration;
import com.example.administrator.myapp.client.tool.ConvertTypeTool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnpackData {
    /**
     * 数据包包头(代表数据大小,占4字节)
     */
    private int packSize;
    /**
     * 获取到的需拆解数据(原始数据)
     */
    private LinkedList<Byte> initialData;
    /**
     * 原始数据锁，用于互斥
     */
    private Lock dataLock;
    /**
     * 数据量较大时使用，用于指示原始数据已完成解析的位置
     */
    private int dataStartIndex;

    public UnpackData(){
        dataLock=new ReentrantLock();
        initialData = new LinkedList<>();
        dataStartIndex=0;
    }

    /**
     * 向原始数据添加数据
     * @param data
     */
    public void addInitialData(byte[] data){
        dataLock.lock();
        for(int i=0;i<data.length;i++)
        {
            initialData.add(data[i]);
        }
        dataLock.unlock();
    }

    /**
     * 处理拆解原始数据
     */
    public BasicMessage processingInitialData(){
        int initialDataSize=initialData.size();
        if(initialDataSize<1)
        {
            Log.i("Socket处理","原始数据链大小小于0");
            return null;
        }

        if (dataStartIndex < initialDataSize)
        {
            if (initialData.get(dataStartIndex) == SocketConfiguration.DATA_START_TAG)
            {
                if (dataStartIndex + 5 <= initialDataSize)
                {
                    Log.i("Socket拆包","起始符正确，未丢失数据");

                    int packetSize=getPacketSize(subList( dataStartIndex + 1,4));

                    Log.i("Socket拆包","包头记录大小：" + packetSize);

                    if (initialDataSize >= packetSize+5+dataStartIndex)
                    {
                        //数据为完整数据则进行拆包
                        Log.i("Socket拆包","数据为完整数据进行拆包");
                        //取出数据
                        byte[] packetBytes=getPacket(subList(dataStartIndex+5,packetSize));
                        if(packetBytes!=null) {

                            BasicMessage basicMessage=BasicMessage.disassembleMessage(packetBytes);
                            Log.i("Socket拆包","已拆解数据");

                            if (initialData.get(dataStartIndex + 5 + packetSize) == SocketConfiguration.DATA_END_TAG)
                            {
                                dataStartIndex = dataStartIndex + 6 + packetSize;
                                //此处清理原始信息列表仅为Socket服务未完成完全搭建时的暂时方法
                                initialDataCleansing();
                                return basicMessage;
                            }
                            else
                            {
                                Log.e("Socket拆包","数据包结尾标识符错误");
                            }
                        }
                        else {
                            Log.i("Socket拆包","initialData被占用");
                        }
                    }
                    else
                    {
                        Log.i("Socket拆包","Size正确数据未接收完全，等待后续传输完毕");
                    }
                }
                else
                {
                    Log.i("Socket拆包","起始符正确数据未接收完全，等待后续传输完毕");
                }
            }
            else
            {
                //诊断数据数据:1.数据多余:向后查找起始标识，删除中间的多余数据 2.数据缺失:尝试向客户端发送命令，以重新发送数据
                Log.e("Socket拆包","起始字节错误");
            }
        }
        return null;
    }

    /**
     * 根据原始数据的起始位置指示清理原始数据列表中弃用或已拆解部分
     */
    public void initialDataCleansing()
    {
        Log.i("Socket拆包", "清理原始数据列表");
        dataLock.lock();
        for (int i=0;i<dataStartIndex;i++) {
            initialData.remove(0);
        }
        dataLock.unlock();

        dataStartIndex = 0;
        Log.i("Socket拆包", "清理完毕");

//        if(initialData.size()<1){
//            return;
//        }
        //若进行到此步表示原始数据存在异常(适用于UDP)(临时方法)
        //开始试图移除直到下一个信息标识符前的冗余信息以恢复正常
        //其余处理手段待实现
//        for (int i = 0; i < initialData.size(); i++) {
//            if (initialData.get(i) == SocketConfiguration.DataStartTag) {
//                if (i == initialData.size() - 1) {
//                    initialData.clear();
//                } else {
//                    for (int o = 0; o < i; o++) {
//                        initialData.remove(0);
//                    }
//                }
//                break;
//            }
//        }
    }

    /**
     * 获取原始数据中从起始位置开始一定长度的数据
     * @param startIndex 起始位置
     * @param count 长度
     * @return
     */
    public List<Byte> subList(int startIndex,int count){
        return initialData.subList(startIndex,startIndex+count);
    }

    /**
     * 获取数据包大小
     * @param sizeByteList
     * @return
     */
    public int getPacketSize(List<Byte> sizeByteList)
    {
        Byte[] sizeBytes=sizeByteList.toArray(new Byte[sizeByteList.size()]);

        int sizeBytesLength=sizeBytes.length;
        byte[] bytes=new byte[sizeBytesLength];

        for(int i=0;i<sizeBytesLength;i++){
            bytes[i]=sizeBytes[i].byteValue();
        }
        return ConvertTypeTool.ByteToInt(bytes);
    }

    /**
     * 获取基础数据包
     * @param packetByteList
     * @return
     */
    public byte[] getPacket(List<Byte> packetByteList){
        Byte[] packetBytes=packetByteList.toArray(new Byte[packetByteList.size()]);

        int packetBytesLength=packetBytes.length;
        byte[] packet=new byte[packetBytesLength];

        for(int i=0;i<packetBytesLength;i++){
            packet[i]=packetBytes[i].byteValue();
        }

        return packet;
    }
}

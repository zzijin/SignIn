package com.example.administrator.myapp.client.configuration;

public class SocketConfiguration {
    /**
     *  服务器IP地址
     */
    public static final String IP="120.79.158.83";
    /**
     * 测试用服务器IP地址
     */
    public static final String IP_TEST="";
    /**
     * 服务器端口
     */
    public static final int PORT=12345;
    /**
     * 测试用服务器端口
     */
    public static final int PORT_TEST=0;
    /**
     * conn类缓存区大小
     */
    public static final int BUFF_SIZE = 50*1000;
    /**
     * 数据包开始标识符
     */
    public static final byte DATA_START_TAG=(byte)Integer.parseInt("98",16);
    /**
     * 数据包结束标识符
     */
    public static final byte DATA_END_TAG = (byte)Integer.parseInt("99",16);
    /**
     * 包裹最大大小
     */
    public static final int PACK_SIZE=1000;
}

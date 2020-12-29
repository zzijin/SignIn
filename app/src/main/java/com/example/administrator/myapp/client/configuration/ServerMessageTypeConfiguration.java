package com.example.administrator.myapp.client.configuration;

public class ServerMessageTypeConfiguration {
    /**
     * 用户发送登录数据
     */
    final static public int SERVER_USER_LOGIN_INFO=10000;
    /**
     * 用户注册
     */
    final static public int SERVER_USER_REGISTER=10001;
    /**
     * 心跳包
     */
    final static public int SERVER_HEARTBEAT=0;
    /**
     * 通过用户id获取用户信息
     */
    final static public int SERVER_USER_GET_INFO=11000;
    final static public int SERVER_ACTIVITY_REGISTER=12000;
    final static public int SERVER_ACTIVITY_GET_INFO=12001;
    final static public int SERVER_ACTIVITY_GET_NUMBER_OF_PEOPLE=12002;
    final static public int SERVER_ACTIVITY_JOIN=12004;
}

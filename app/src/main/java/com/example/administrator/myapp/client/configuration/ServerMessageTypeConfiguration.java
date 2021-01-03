package com.example.administrator.myapp.client.configuration;

public class ServerMessageTypeConfiguration {
    /**
     * 用户发送登录数据
     */
    final static public int SERVER_MY_LOGIN_INFO=10000;
    /**
     * 用户注册
     */
    final static public int SERVER_MY_REGISTER=9000;
    final static public int SERVER_MY_JOIN_ACTIVITY=10100;
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
    final static public int SERVER_ACTIVITY_PARTICIPANT_LIST=12002;
    final static public int SERVER_ACTIVITY_SIGN_IN=12003;

}

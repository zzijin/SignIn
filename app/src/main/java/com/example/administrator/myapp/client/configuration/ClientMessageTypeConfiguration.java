package com.example.administrator.myapp.client.configuration;

public class ClientMessageTypeConfiguration {
    /**
     * 用户发送登录数据
     */
    final static public int CLIENT_MY_LOGIN_INFO =10000;
    final static public int CLIENT_MY_JOIN_ACTIVITY =10100;
    final static public int CLIENT_MY_REGISTER =9000;
    /**
     * 心跳包
     */
    final static public int CLIENT_HEARTBEAT=0;

    final static public int CLIENT_USER_GET_INFO_BY_UID=11000;
    final static public int CLIENT_ACTIVITY_REGISTER=12000;
    final static public int CLIENT_ACTIVITY_GET_INFO=12001;
    final static public int CLIENT_ACTIVITY_PARTICIPANT_LIST=12002;
}

package com.example.administrator.myapp.client.configuration;

public class ClientMessageTypeConfiguration {
    /**
     * 用户发送登录数据
     */
    final static public int CLIENT_USER_LOGIN_INFO=10000;
    final static public int CLIENT_USER_REGISTER=10001;
    /**
     * 心跳包
     */
    final static public int CLIENT_HEARTBEAT=0;

    final static public int CLIENT_USER_GET_INFO_BY_UID=11000;
    final static public int CLIENT_USER_GET_INFO_BY_NAME=11001;
    final static public int CLIENT_ACTIVITY_REGISTER=12000;
    final static public int CLIENT_ACTIVITY_GET_INFO=12001;
    final static public int CLIENT_ACTIVITY_GET_NUMBER_OF_PEOPLE=12002;
    final static public int CLIENT_ACTIVITY_GET_INVITATION_CODE=12003;
    final static public int CLIENT_ACTIVITY_JOIN=12004;
}

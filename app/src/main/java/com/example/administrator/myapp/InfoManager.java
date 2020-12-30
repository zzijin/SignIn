package com.example.administrator.myapp;


import com.example.administrator.myapp.Info.CheckInActivityInfo;
import com.example.administrator.myapp.Info.MyInfo;
import com.example.administrator.myapp.Info.UserInfo;
import com.example.administrator.myapp.client.SendMessageMethod;
import com.example.administrator.myapp.client.SocketClient;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InfoManager {
    LinkedList<CheckInActivityInfo> checkInActivityInfoList;
    boolean activityIsNew=false;
    LinkedList<UserInfo> userInfoList;
    boolean userInfoIsNew=false;
    MyInfo myInfo;

    /////基本构造函数///////
    public InfoManager(){
        checkInActivityInfoList=new LinkedList<>();
        userInfoList=new LinkedList<>();
        myInfo=new MyInfo();
    }
    ///////////////我的账户////////////////////
    /////用户登录///////

    /**
     * UI登录用户信息
     * @param myID
     * @param myPassword
     */
    public void uiLoginMyAccount(int myID,String myPassword){
        myInfo.uiLoginMyAccount(myID,myPassword);
    }

    /**
     * 服务器通知用户登录状态
     */
    public void clientLoginStatus(boolean loginStatus,int myID,String myName){
        if(loginStatus){
            myInfo.clientLoginSucceedMyInfo(myID,myName);
        }
        else {
            myInfo.clientLoginFailMyInfo();
        }
    }

    /////用户注册///////

    public void uiRegisterMyAccount(String myName,String myPassword){
        myInfo.uiRegisterMyAccount(myName,myPassword);
    }

    ///////////////所有用户////////////////////

    /////向服务器/本地获取用户信息///////
    /////步骤：1.从服务器/本地读取获取需要用户id(占位) 2.向服务器/本地读取用户信息

    /**
     * 界面获取某个用户的信息
     * @param socketClient
     * @param userID
     */
    public void uiAddUserInfo(SocketClient socketClient,int userID){
        userInfoList.add(new UserInfo(userID));
        SendMessageMethod.userGetInfoByID(socketClient,userID);
    }

    /**
     * 设置指定id的用户信息
     * @param userInfo
     */
    public void clientSetUserInfo(UserInfo userInfo){
        for(int i=0;i<userInfoList.size();i++){
            if(userInfoList.get(i).getUserID()==userInfo.getUserID()){
                userInfoList.get(i).clientSetUserInfo(userInfo);
                break;
            }
        }
    }

    ///////////////所有活动////////////////////
    /////用户注册活动///////
    public void uiRegisterActivityInfo(SocketClient socketClient,int activityInitiatorID, String activityTheme, double activityCheckInLongitude, double activityCheckInLatitude,
                                  int activityInvitationCode, String activityCheckInStartTime, String activityCheckInEndTime, String activityStartTime, String activityEndTime){
        CheckInActivityInfo checkInActivityInfo=new CheckInActivityInfo(activityInitiatorID, activityTheme, activityCheckInLongitude, activityCheckInLatitude,
                activityInvitationCode, activityCheckInStartTime, activityCheckInEndTime, activityStartTime, activityEndTime);
        checkInActivityInfoList.add(checkInActivityInfo);
        SendMessageMethod.activityRegister(socketClient,checkInActivityInfo);
    }

    public void clientSetActivityID(int activityIndex,int activityID,String activityTheme){
        if(activityIndex<checkInActivityInfoList.size()){
            if (checkInActivityInfoList.get(activityIndex).getActivityTheme().equals(activityTheme)){
                checkInActivityInfoList.get(activityIndex).clientSetActivityID(activityID);
            }
        }
    }
    /////用户向服务器请求活动信息///////
    /////步骤：1.从服务器/本地读取获取需要的活动id 2.向服务器/本地读取活动信息 3.向服务器/本地读取活动参与人数

    public void uiGetActivityInfo(SocketClient socketClient,int activityID){
        checkInActivityInfoList.add(new CheckInActivityInfo(activityID));
        SendMessageMethod.activityGetInfo(socketClient,activityID);
    }

    public void clientSetActivityInfo(CheckInActivityInfo checkInActivityInfo){
        for (int i=0;i<checkInActivityInfoList.size();i++){
            if(checkInActivityInfoList.get(i).getActivityID()==checkInActivityInfo.getActivityID()){
                checkInActivityInfoList.get(i).clientSetActivityInfo(checkInActivityInfo);
                break;
            }
        }
    }

    public void ClientSetActivityParticipantInfo(List<Integer> activityParticipant){

    }
}

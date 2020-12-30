package com.example.administrator.myapp;


import android.util.Log;

import com.example.administrator.myapp.Info.CheckInActivityInfo;
import com.example.administrator.myapp.Info.MyInfo;
import com.example.administrator.myapp.Info.UserInfo;
import com.example.administrator.myapp.client.SendMessageMethod;
import com.example.administrator.myapp.client.SocketClient;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InfoManager {
    private LinkedList<CheckInActivityInfo> checkInActivityInfoList;
    private boolean activityIsNew=false;
    private LinkedList<UserInfo> userInfoList;
    private boolean userInfoIsNew=false;
    private MyInfo myInfo;

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
    public void clientLoginStatus(boolean loginStatus,int myID,String myName,List<Integer> managedList){
        if(loginStatus){
            myInfo.clientLoginSucceedMyInfo(myID,myName,managedList);
        }
        else {
            myInfo.clientLoginFailMyInfo();
        }
    }

    /////用户注册///////

    /**
     * UI界面注册用户
     * @param myName
     * @param myPassword
     */
    public void uiRegisterMyAccount(String myName,String myPassword){
        myInfo.uiRegisterMyAccount(myName,myPassword);
    }

    /**
     * 从服务器获取注册信息
     * @param status
     * @param myID
     * @param myName
     */
    public void clientRegisterAccountStatus(boolean status,int myID,String myName){
        if(status){
            myInfo.clientRegisterSucceed(myID,myName);
        }
        else {
            myInfo.clientRegisterFail();
        }
    }

    /////用户申请加入活动///////

    /**
     * UI界面申请加入活动
     * @param socketClient
     * @param activityID
     */
    public void uiJoinActivity(SocketClient socketClient,int activityID){
        myInfo.uiJoinActivity(activityID);
        SendMessageMethod.activityJoin(socketClient,myInfo.getMyID(),activityID);
    }

    /**
     * ui界面获取加入失败列表
     * @return
     */
    public List<Integer> uiGetFailJoinList(){
        return myInfo.uiGetFailJoinList();
    }

    /**
     * 从服务器获取申请加入活动是否成功
     * @param joinStatus
     * @param activityID
     */
    public void clientJoinActivityStatus(boolean joinStatus,int activityID){
        if(joinStatus){
            myInfo.clientJoinSuccessActivity(activityID);
        }
        else {
            myInfo.clientJoinSuccessActivity(activityID);
        }
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

    /**
     * UI界面申请注册活动
     * @param socketClient
     * @param activityInitiatorID
     * @param activityTheme
     * @param activityCheckInLongitude
     * @param activityCheckInLatitude
     * @param activityInvitationCode
     * @param activityCheckInStartTime
     * @param activityCheckInEndTime
     * @param activityStartTime
     * @param activityEndTime
     */
    public void uiRegisterActivityInfo(SocketClient socketClient,int activityInitiatorID, String activityTheme, double activityCheckInLongitude, double activityCheckInLatitude,
                                  int activityInvitationCode, String activityCheckInStartTime, String activityCheckInEndTime, String activityStartTime, String activityEndTime){
        CheckInActivityInfo checkInActivityInfo=new CheckInActivityInfo(activityInitiatorID, activityTheme, activityCheckInLongitude, activityCheckInLatitude,
                activityInvitationCode, activityCheckInStartTime, activityCheckInEndTime, activityStartTime, activityEndTime);
        int index=checkInActivityInfoList.size();
        checkInActivityInfoList.add(checkInActivityInfo);
        SendMessageMethod.activityRegister(socketClient,checkInActivityInfo,index);
    }

    /**
     * 从服务器获取申请注册活动是否成功
     * @param activityIndex
     * @param activityID
     * @param activityTheme
     */
    public void clientSetActivityID(int activityIndex,int activityID,String activityTheme){
        if(activityIndex<checkInActivityInfoList.size()){
            if (checkInActivityInfoList.get(activityIndex).getActivityTheme().equals(activityTheme)){
                checkInActivityInfoList.get(activityIndex).clientSetActivityID(activityID);
            }
        }
    }
    /////用户向服务器请求活动信息///////
    /////步骤：1.从服务器/本地读取获取需要的活动id 2.向服务器/本地读取活动信息 3.向服务器/本地读取活动参与人数

    /**
     * UI界面获取指定活动信息
     * @param socketClient
     * @param activityID
     */
    public void uiGetActivityInfo(SocketClient socketClient,int activityID){
        checkInActivityInfoList.add(new CheckInActivityInfo(activityID));
        SendMessageMethod.activityGetInfo(socketClient,activityID);
        Log.i("信息管理","获取指定活动ID信息:"+activityID);
    }

    /**
     * 接收来自服务器的活动信息
     * @param checkInActivityInfo
     */
    public void clientSetActivityInfo(CheckInActivityInfo checkInActivityInfo){
        for (int i=0;i<checkInActivityInfoList.size();i++){
            if(checkInActivityInfoList.get(i).getActivityID()==checkInActivityInfo.getActivityID()){
                checkInActivityInfoList.get(i).clientSetActivityInfo(checkInActivityInfo);
                return;
            }
        }
        checkInActivityInfoList.add(checkInActivityInfo);
        Log.i("信息管理","获取到不在活动列表中的活动");
    }

    /**
     * 接收来自服务器的活动参与者信息
     * @param activityParticipant
     * @param activityID
     */
    public void ClientSetActivityParticipantInfo(List<Integer> activityParticipant,int activityID){
        for (int i=0;i<checkInActivityInfoList.size();i++){
            if(checkInActivityInfoList.get(i).getActivityID()==activityID){
                checkInActivityInfoList.get(i).clientSetActivityParticipantInfo(activityParticipant);
                return;
            }
        }
        Log.i("信息管理","获取到不明活动的参与者列表");
    }

    ///////////////基本信息////////////////////

    public MyInfo getMyInfo() {
        return myInfo;
    }

    public LinkedList<UserInfo> getUserInfoList() {
        return userInfoList;
    }

    public LinkedList<CheckInActivityInfo> getCheckInActivityInfoList() {
        return checkInActivityInfoList;
    }

}

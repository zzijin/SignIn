package com.example.administrator.myapp;


import com.example.administrator.myapp.Info.CheckInActivityInfo;
import com.example.administrator.myapp.Info.MyInfo;
import com.example.administrator.myapp.Info.UserInfo;

import java.util.LinkedList;
import java.util.List;

public class InfoManager {
    LinkedList<CheckInActivityInfo> checkInActivityInfoList;
    boolean activityIsNew=false;
    LinkedList<UserInfo> userInfoList;
    boolean userInfoIsNew=false;
    MyInfo myInfo;

    public InfoManager(){
        checkInActivityInfoList=new LinkedList<>();
        userInfoList=new LinkedList<>();
    }

    /**
     * 向活动列表添加活动(申请注册的活动)
     * @param checkInActivityInfo
     * @return
     */
    public int addCheckInActivityInfo(CheckInActivityInfo checkInActivityInfo){
        checkInActivityInfoList.add(checkInActivityInfo);
        return checkInActivityInfoList.size()-1;
    }

    /**
     * 向活动列表添加空的活动
     * @param activityID
     * @return
     */
    public int[] addActivityNullInfo(List<Integer> activityID){
        int[] activityIndex=new int[activityID.size()];
        for (int i=0;i<activityID.size();i++){
            activityIndex[i]=addCheckInActivityInfo(new CheckInActivityInfo(activityID.get(i)));
        }
        return activityIndex;
    }

    /**
     * 获取到来自服务器的信息后将该信息替换列表中的空信息
     * @param index
     * @param checkInActivityInfo
     * @return
     */
    public boolean addActivityInfoToNullActivityByIndex(int index,CheckInActivityInfo checkInActivityInfo){
        if(index<checkInActivityInfoList.size()){
            checkInActivityInfoList.set(index,checkInActivityInfo);

            return true;
        }
        else return false;
    }

    public int addUserNullInfo(int userID){
        userInfoList.add(new UserInfo(userID));
        return userInfoList.size()-1;
    }

    public boolean addUserInfoToNullUserByIndex(int index,UserInfo userInfo){
        if(index<userInfoList.size()){
            userInfoList.set(index,userInfo);
            return true;
        }
        else return false;
    }

    public void getLoginInfo(boolean loginStatus,int myID,String myName){
        if(loginStatus){
            myInfo.loginSucceedMyInfo(myID,myName);
        }
        else {
            myInfo.loginFailMyInfo();
        }
    }

    public void getJoinActivityStatus(boolean status,int activityID){
        if(status){
            myInfo.addJoinActivity(activityID);
        }
        else {
            myInfo.addFailActivity(activityID);
        }

    }

    public boolean addActivityIDToRegisterActivity(int index,int activityID){
        if(index<checkInActivityInfoList.size()){
            checkInActivityInfoList.get(index).setActivityID(activityID);
            return true;
        }
        else return false;
    }

    public void activityJoin(int activityID){
        myInfo.addJoinActivity(activityID);
    }


    /**
     * 向用户列表添加用户
     * @param userInfo
     */
    public void addUserInfo(UserInfo userInfo){
        userInfoList.add(userInfo);
    }

    /**
     * 通过用户ID获取用户信息
     * @param userID
     * @return
     */
    public UserInfo getUserInfoByUserID(int userID){
        for (int i=0;i<userInfoList.size();i++){
            if(userID==userInfoList.get(i).getUserID()){
                return userInfoList.get(i);
            }
        }
        return null;
    }

    /**
     * 获取活动信息通过活动ID
     * @param activityID
     * @return
     */
    public CheckInActivityInfo getCheckInActivityInfoByActivityID(int activityID) {
        for (int i=0;i<checkInActivityInfoList.size();i++){
            if(activityID==checkInActivityInfoList.get(i).getActivityID()){
                return checkInActivityInfoList.get(i);
            }
        }
        return null;
    }

    /**
     * 获取活动信息通过活动发起者ID
     * @param initiatorID
     * @return
     */
    public CheckInActivityInfo getCheckInActivityInfoByInitiatorID(int initiatorID) {
        for (int i=0;i<checkInActivityInfoList.size();i++){
            if(initiatorID==checkInActivityInfoList.get(i).getActivityInitiatorID()){
                return checkInActivityInfoList.get(i);
            }
        }
        return null;
    }

    public LinkedList<CheckInActivityInfo> getCheckInActivityInfoList() {
        return checkInActivityInfoList;
    }

    public LinkedList<UserInfo> getUserInfoList() {
        return userInfoList;
    }

    public boolean getActivityIsNew(){
        return activityIsNew;
    }

    public boolean getUserIsNew(){
        return userInfoIsNew;
    }
}

package com.example.administrator.myapp.Info;


import com.example.administrator.myapp.client.file.PictureFile;

import java.util.List;

public class UserInfo {
    private int userID;
    private String userName;
    private PictureFile userHead;
    private String userSignature;
    private String userBirthday;
    private String myEmail;

    private List<Integer> joinedActivities;
    private List<Integer> managedActivities;
    private List<Integer> initiatorActivities;

    /////基本构造函数(Test仅使用用户名/id)///////

    public UserInfo(int userID,String userName,List<Integer> joinedActivities, List<Integer> managedActivities, List<Integer> initiatorActivities){
        this.userID=userID;this.userName=userName;
        this.joinedActivities = joinedActivities;this.managedActivities=managedActivities;this.initiatorActivities=initiatorActivities;
    }

    /////向服务器/本地获取用户信息///////
    /////步骤：1.从服务器/本地读取获取需要用户id(占位) 2.向服务器/本地读取用户信息

    /**
     * 从服务器/本地读取获取需要用户id
     * @param userID
     */
    public UserInfo(int userID){
        this.userID=userID;
    }

    /**
     * 存入向服务器/本地读取到的用户信息
     * @param userInfo
     */
    public void clientSetUserInfo(UserInfo userInfo){
        this.userID=userInfo.userID;this.userName=userInfo.userName;
        clientSetManagedActivities(userInfo.getManagedActivities());
        clientSetInitiatorActivities(userInfo.getInitiatorActivities());
        clientSetJoinedActivities(userInfo.getJoinedActivities());
    }

    public void clientSetJoinedActivities(List<Integer> joinedActivities){
        this.joinedActivities =joinedActivities;
    }

    public void clientSetManagedActivities(List<Integer> managedActivities){
        this.managedActivities=managedActivities;
    }

    public void clientSetInitiatorActivities(List<Integer> initiatorActivities){
        this.initiatorActivities=initiatorActivities;
    }

    public boolean uiGetJoinedActivities(){
        return joinedActivities !=null;
    }

    public boolean uiGetManagedActivitiesStatus(){
        return managedActivities!=null;
    }

    public boolean uiGetInitiatorActivitiesStatus(){
        return initiatorActivities!=null;
    }

    /////获取用户信息的基本方法///////


    public List<Integer> getManagedActivities() {
        return managedActivities;
    }

    public List<Integer> getInitiatorActivities() {
        return initiatorActivities;
    }

    public List<Integer> getJoinedActivities() {
        return joinedActivities;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserID() {
        return userID;
    }

    public PictureFile getUserHead() {
        return userHead;
    }

    public String getMyEmail() {
        return myEmail;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public String getUserSignature() {
        return userSignature;
    }

}

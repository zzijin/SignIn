package com.example.administrator.myapp.Info;

import com.example.administrator.myapp.client.file.PictureFile;

import java.util.ArrayList;
import java.util.List;

public class MyInfo {
    private int myID;
    private String myName;
    private String myPassword;
    private PictureFile myHead;
    private String mySignature;
    private String myBirthday;
    private String myEmail;

    private List<Integer> joinedActivities;   //加入的活动
    private List<Integer> waitJoinStatusActivity;
    private List<Integer> failJoinActivity;
    private List<Integer> managedActivities;   //管理的活动
    private List<Integer> initiatorActivities; //发起的活动
    //-1:未登录；0:登录中;1:登录成功;2:登录失败;3:注册中;4:注册失败;5:注册成功
    private int myLoginStatus=-1;

    public MyInfo(){

    }

    /**
     * 服务器返回数据时使用，可扩增
     * @param myID
     * @param myName
     * @param joinedActivities
     * @param managedActivities
     * @param initiatorActivities
     */
    public MyInfo(int myID, String myName, List<Integer> joinedActivities, List<Integer> managedActivities, List<Integer> initiatorActivities){
        this.myID=myID;this.myName=myName;
        this.joinedActivities = joinedActivities;this.managedActivities=managedActivities;this.initiatorActivities=initiatorActivities;
    }

    /////用户注册///////

    /**
     * UI界面注册用户信息
     * @param myName
     * @param myPassword
     */
    public void uiRegisterMyAccount(String myName,String myPassword){
        this.myName=myName;this.myPassword=myPassword;waitJoinStatusActivity=new ArrayList<>();failJoinActivity=new ArrayList<>();managedActivities=new ArrayList<>();
        myLoginStatus=3;
    }

    public void clientRegisterSucceed(MyInfo myInfo){
        clientLoginSucceedMyInfo(myInfo);
    }

    public void clientRegisterFail(){
        myLoginStatus=4;
    }

    /////用户登录///////

    /**
     * UI登录用户信息
     * @param myID
     * @param myPassword
     */
    public void uiLoginMyAccount(int myID,String myPassword){
        this.myID=myID;this.myPassword=myPassword;waitJoinStatusActivity=new ArrayList<>();failJoinActivity=new ArrayList<>();myLoginStatus=0;
    }

    /**
     * 服务器通知用户登录失败
     */
    public void clientLoginFailMyInfo(){
        myLoginStatus=2;
    }

    /**
     * 服务器通知用户登录成功
     * @param myInfo 返回的用户信息
     */
    public void clientLoginSucceedMyInfo(MyInfo myInfo){
        this.myID=myInfo.getMyID();this.myName=myInfo.getMyName();
        clientSetManagedActivities(myInfo.getManagedActivities());
        clientSetInitiatorActivities(myInfo.getInitiatorActivities());
        clientSetJoinedActivities(myInfo.getJoinedActivities());
        myLoginStatus=1;
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

    /////用户申请加入活动///////

    /**
     * UI界面申请加入活动
     * @param activityID
     */
    public void uiJoinActivity(int activityID){
        waitJoinStatusActivity.add(activityID);
    }

    /**
     * UI获取加入失败列表
     * @return 失败列表
     */
    public List<Integer> uiGetFailJoinList(){
        List<Integer> fail=new ArrayList<>();
        for(int i=0;i<failJoinActivity.size();i++){
            fail.add(failJoinActivity.get(i));
        }
        failJoinActivity.clear();
        return fail;
    }

    /**
     *  服务器通知活动加入成功
     * @param activityID
     */
    public void clientJoinSuccessActivity(int activityID){
        for (int i=0;i<waitJoinStatusActivity.size();i++){
            if (waitJoinStatusActivity.get(i)==activityID){
                joinedActivities.add(activityID);
                waitJoinStatusActivity.remove(i);
                return;
            }
        }
    }

    /**
     * 服务器通知活动加入失败
     * @param activityID
     */
    public void clientJoinFailActivity(int activityID){
        for (int i=0;i<waitJoinStatusActivity.size();i++){
            if (waitJoinStatusActivity.get(i)==activityID){
                failJoinActivity.add(activityID);
                waitJoinStatusActivity.remove(i);
                return;
            }
        }
    }

    /////获取我的信息的基本方法///////


    public List<Integer> getInitiatorActivities() {
        return initiatorActivities;
    }

    public List<Integer> getManagedActivities() {
        return managedActivities;
    }

    public String getMyEmail() {
        return myEmail;
    }

    public int getMyID() {
        return myID;
    }

    public int getMyLoginStatus() {
        return myLoginStatus;
    }

    public List<Integer> getJoinedActivities() {
        return joinedActivities;
    }

    public PictureFile getMyHead() {
        return myHead;
    }

    public String getMyBirthday() {
        return myBirthday;
    }

    public String getMyName() {
        return myName;
    }

    public String getMySignature() {
        return mySignature;
    }
}

package com.example.administrator.myapp.Info;

import com.example.administrator.myapp.client.JsonByUTF8;
import com.example.administrator.myapp.client.SocketClient;
import com.example.administrator.myapp.client.configuration.ClientMessageTypeConfiguration;
import com.example.administrator.myapp.client.file.PictureFile;
import com.example.administrator.myapp.configuration.MessageNameConfiguration;

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
    private List<Integer> joinActivity;
    private List<Integer> waitJoinStatusActivity;
    private List<Integer> failJoinActivity;
    private List<Integer> managedActivities;
    //-1:未登录；0:登录中;1:登录成功;2:登录失败;3:注册中;4:注册失败;5:注册成功
    private int myLoginStatus=-1;

    /////用户注册///////

    /**
     * UI界面注册用户信息
     * @param myName
     * @param myPassword
     */
    public void uiRegisterMyAccount(String myName,String myPassword){
        this.myName=myName;this.myPassword=myPassword;joinActivity=new ArrayList<>();waitJoinStatusActivity=new ArrayList<>();failJoinActivity=new ArrayList<>();
        myLoginStatus=3;
    }

    public void clientRegisterSucceed(int myID,String myName){
        clientLoginSucceedMyInfo(myID,myName,new ArrayList<Integer>());
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
        this.myID=myID;this.myPassword=myPassword;joinActivity=new ArrayList<>();waitJoinStatusActivity=new ArrayList<>();failJoinActivity=new ArrayList<>();myLoginStatus=0;
    }

    /**
     * 服务器通知用户登录失败
     */
    public void clientLoginFailMyInfo(){
        myLoginStatus=2;
    }

    /**
     * 服务器通知用户登录成功
     * @param myID 用户id
     * @param myName 用户昵称
     */
    public void clientLoginSucceedMyInfo(int myID,String myName,List<Integer> managedActivities){
        this.myID=myID;this.myName=myName;this.managedActivities=managedActivities;
        myLoginStatus=1;
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
                joinActivity.add(activityID);
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

    public String getMyEmail() {
        return myEmail;
    }

    public int getMyID() {
        return myID;
    }

    public int getMyLoginStatus() {
        return myLoginStatus;
    }

    public List<Integer> getJoinActivity() {
        return joinActivity;
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

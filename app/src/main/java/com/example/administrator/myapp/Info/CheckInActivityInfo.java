package com.example.administrator.myapp.Info;

import com.example.administrator.myapp.client.SwitchMessage;
import com.example.administrator.myapp.client.file.PictureFile;

import java.util.ArrayList;
import java.util.List;

public class CheckInActivityInfo {
    /**
     * 活动发起人ID
     */
    private int activityInitiatorID;
    /**
     * 活动ID
     */
    private int activityID;
    /**
     * 活动邀请码
     */
    private int activityInvitationCode;
    /**
     * 活动签到起始时间
     */
    private String activityCheckInStartTime;
    /**
     * 活动签到结束时间
     */
    private String activityCheckInEndTime;
    /**
     * 活动签到定位中心经度
     */
    private double activityCheckInLongitude;
    /**
     * 活动签到定位中心纬度
     */
    private double activityCheckInLatitude;
    /**
     * 活动签到允许误差半径(m)
     */
    private int activityCheckInRadius;

    //以下为扩充内容
    /**
     * 活动封面
     */
    private PictureFile activityCover;
    /**
     * 活动开始时间
     */
    private String activityStartTime;
    /**
     * 活动结束时间
     */
    private String activityEndTime;
    /**
     * 活动主题
     */
    private String activityTheme;
    /**
     * 活动内容
     */
    private String activityContent;
    /**
     * 活动参与者
     */
    private List<Integer> activityParticipant;

    /////基本构造函数///////
    /**
     *  完整的活动信息
     * @param activityID
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
    public CheckInActivityInfo(int activityID,int activityInitiatorID, String activityTheme, double activityCheckInLongitude, double activityCheckInLatitude,
                               int activityInvitationCode, String activityCheckInStartTime, String activityCheckInEndTime, String activityStartTime, String activityEndTime){
        this.activityID=activityID;
        this.activityInitiatorID = activityInitiatorID;this.activityTheme = activityTheme;this.activityCheckInLongitude = activityCheckInLongitude;
        this.activityCheckInLatitude = activityCheckInLatitude;this.activityInvitationCode = activityInvitationCode;this.activityCheckInStartTime=activityCheckInStartTime;
        this.activityCheckInEndTime = activityCheckInEndTime;this.activityStartTime = activityStartTime;this.activityEndTime = activityEndTime;
        activityParticipant=new ArrayList<>();
    }

    /////用户注册活动///////

    /**
     * 用户向服务器请求注册活动信息时使用，稍后接收到的数据会将注册到的活动id填装到类中
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
    public CheckInActivityInfo(int activityInitiatorID, String activityTheme, double activityCheckInLongitude, double activityCheckInLatitude,
                               int activityInvitationCode, String activityCheckInStartTime, String activityCheckInEndTime, String activityStartTime, String activityEndTime){
        this.activityInitiatorID = activityInitiatorID;this.activityTheme = activityTheme;this.activityCheckInLongitude = activityCheckInLongitude;
        this.activityCheckInLatitude = activityCheckInLatitude;this.activityInvitationCode = activityInvitationCode;this.activityCheckInStartTime=activityCheckInStartTime;
        this.activityCheckInEndTime = activityCheckInEndTime;this.activityStartTime = activityStartTime;this.activityEndTime = activityEndTime;
        activityParticipant=new ArrayList<>();
    }

    /**
     * 成功注册后，将注册的id填入类中
     * @param activityID
     */
    public void clientSetActivityID(int activityID){
        this.activityID=activityID;
    }

    /////用户向服务器请求活动信息///////
    /////步骤：1.从服务器/本地读取获取需要的活动id 2.向服务器/本地读取活动信息 3.向服务器/本地读取活动参与人数
    /**
     * 用户向服务器/本地请求活动信息前，先将活动id填入，稍后接收到的数据将自动填装到类中
     * @param activityID
     */
    public CheckInActivityInfo(int activityID){
        this.activityID=activityID;
        activityParticipant=new ArrayList<>();
    }

    /**
     * 从服务器/本地获取到的除id的活动信息
     * @param checkInActivityInfo
     */
    public void clientSetActivityInfo(CheckInActivityInfo checkInActivityInfo){
        this.activityID=checkInActivityInfo.getActivityID();
        this.activityInitiatorID = checkInActivityInfo.getActivityInitiatorID();this.activityTheme = checkInActivityInfo.getActivityTheme();
        this.activityCheckInLongitude = checkInActivityInfo.getActivityCheckInLongitude();
        this.activityCheckInLatitude = checkInActivityInfo.getActivityCheckInLatitude();
        this.activityInvitationCode = checkInActivityInfo.getActivityInvitationCode();
        this.activityCheckInStartTime=checkInActivityInfo.getActivityCheckInStartTime();this.activityCheckInEndTime = checkInActivityInfo.getActivityCheckInEndTime();
        this.activityStartTime = checkInActivityInfo.getActivityStartTime();this.activityEndTime = checkInActivityInfo.getActivityEndTime();
        activityParticipant=new ArrayList<>();
    }

    /**
     * UI界面获取活动参与列表状态，返回值为null表示还未向服务器获取到参与者信息
     */
    public boolean uiGetActivityParticipantStatus(){
        return activityParticipant==null;
    }

    /**
     * 从本地或服务器获取到的活动参与用户列表
     * @param activityParticipant
     */
    public void clientSetActivityParticipantInfo(List<Integer> activityParticipant){
        this.activityParticipant=activityParticipant;
    }


    /////获取活动基本信息///////

    public List<Integer> getActivityParticipant() {
        return activityParticipant;
    }

    public PictureFile getActivityCover() {
        return activityCover;
    }

    public double getActivityCheckInLatitude() {
        return activityCheckInLatitude;
    }

    public double getActivityCheckInLongitude() {
        return activityCheckInLongitude;
    }

    public int getActivityCheckInRadius() {
        return activityCheckInRadius;
    }

    public int getActivityID() {
        return activityID;
    }

    public int getActivityInitiatorID() {
        return activityInitiatorID;
    }

    public int getActivityInvitationCode() {
        return activityInvitationCode;
    }

    public String getActivityCheckInEndTime() {
        return activityCheckInEndTime;
    }

    public String getActivityCheckInStartTime() {
        return activityCheckInStartTime;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public String getActivityEndTime() {
        return activityEndTime;
    }

    public String getActivityStartTime() {
        return activityStartTime;
    }

    public String getActivityTheme() {
        return activityTheme;
    }
}

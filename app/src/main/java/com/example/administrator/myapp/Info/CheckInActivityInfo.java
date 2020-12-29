package com.example.administrator.myapp.Info;

import com.example.administrator.myapp.client.SwitchMessage;
import com.example.administrator.myapp.client.file.PictureFile;

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

    //向服务器申请
    public CheckInActivityInfo(int activityID){
        this.activityID=activityID;
    }

    //向服务器注册活动
    public CheckInActivityInfo(int activityInitiatorID, String activityTheme, double activityCheckInLongitude, double activityCheckInLatitude,
                         int activityInvitationCode, String activityCheckInStartTime, String activityCheckInEndTime, String activityStartTime, String activityEndTime){
        this.activityInitiatorID = activityInitiatorID;this.activityTheme = activityTheme;this.activityCheckInLongitude = activityCheckInLongitude;
        this.activityCheckInLatitude = activityCheckInLatitude;this.activityInvitationCode = activityInvitationCode;this.activityCheckInStartTime=activityCheckInStartTime;
        this.activityCheckInEndTime = activityCheckInEndTime;this.activityStartTime = activityStartTime;this.activityEndTime = activityEndTime;
    }
    //向服务器获取到活动信息
    public CheckInActivityInfo(int activityID,int activityInitiatorID, String activityTheme, double activityCheckInLongitude, double activityCheckInLatitude,
                               int activityInvitationCode, String activityCheckInStartTime, String activityCheckInEndTime, String activityStartTime, String activityEndTime){
        this.activityID=activityID;
        this.activityInitiatorID = activityInitiatorID;this.activityTheme = activityTheme;this.activityCheckInLongitude = activityCheckInLongitude;
        this.activityCheckInLatitude = activityCheckInLatitude;this.activityInvitationCode = activityInvitationCode;this.activityCheckInStartTime=activityCheckInStartTime;
        this.activityCheckInEndTime = activityCheckInEndTime;this.activityStartTime = activityStartTime;this.activityEndTime = activityEndTime;
    }

    /**
     * 获取活动参与列表状态，返回值为null表示还未向服务器获取到参与者信息
     */
    public boolean getActivityParticipantStatus(){
        return activityParticipant==null;
    }

    public void addActivityParticipantInfo(List<Integer> activityParticipant){
        this.activityParticipant=activityParticipant;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
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

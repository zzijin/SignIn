package com.example.administrator.myapp.client;

import com.example.administrator.myapp.Info.CheckInActivityInfo;
import com.example.administrator.myapp.client.configuration.ClientMessageTypeConfiguration;
import com.example.administrator.myapp.configuration.MessageNameConfiguration;

/**
 * 业务逻辑，实现相应业务的静态方法
 */
public class SendMessageMethod {

    public static boolean myRegister(SocketClient socketClient,String userName,String userPassword){
        JsonByUTF8 json=new JsonByUTF8();
        json.putData(MessageNameConfiguration.REGISTER_NAME,userName);
        json.putData(MessageNameConfiguration.REGISTER_PASSWORD,userPassword);
        return socketClient.sendMessage(ClientMessageTypeConfiguration.CLIENT_MY_REGISTER,json.getMessage());
    }

    public static boolean myLoginByID(SocketClient socketClient,int myID,String myPassword){
        JsonByUTF8 json=new JsonByUTF8();
        json.putData(MessageNameConfiguration.LOGIN_ID,myID);
        json.putData(MessageNameConfiguration.LOGIN_PASSWORD,myPassword);
        return socketClient.sendMessage(ClientMessageTypeConfiguration.CLIENT_MY_LOGIN_INFO,json.getMessage());
    }

    public static boolean userGetInfoByID(SocketClient socketClient,int userID){
        JsonByUTF8 json=new JsonByUTF8();
        json.putData(MessageNameConfiguration.USER_ID,userID);
        return socketClient.sendMessage(ClientMessageTypeConfiguration.CLIENT_USER_GET_INFO_BY_UID,json.getMessage());
    }

    public static boolean activityRegister(SocketClient socketClient, CheckInActivityInfo activityInfo,int activityIndex){
        JsonByUTF8 json=new JsonByUTF8();
        json.putData(MessageNameConfiguration.REGISTER_ACTIVITY_INDEX,activityIndex);
        json.putData(MessageNameConfiguration.ACTIVITY_INITIATOR_ID,activityInfo.getActivityInitiatorID());
        json.putData(MessageNameConfiguration.ACTIVITY_THEME,activityInfo.getActivityTheme());
        json.putData(MessageNameConfiguration.ACTIVITY_LONGITUDE,activityInfo.getActivityCheckInLongitude());
        json.putData(MessageNameConfiguration.ACTIVITY_LATITUDE,activityInfo.getActivityCheckInLatitude());
        json.putData(MessageNameConfiguration.ACTIVITY_INVITATION_CODE,activityInfo.getActivityInvitationCode());
        json.putData(MessageNameConfiguration.ACTIVITY_CHECK_IN_START_TIME,activityInfo.getActivityCheckInStartTime());
        json.putData(MessageNameConfiguration.ACTIVITY_CHECK_IN_END_TIME,activityInfo.getActivityCheckInEndTime());
        json.putData(MessageNameConfiguration.ACTIVITY_START_TIME,activityInfo.getActivityStartTime());
        json.putData(MessageNameConfiguration.ACTIVITY_END_TIME,activityInfo.getActivityEndTime());
        return socketClient.sendMessage(ClientMessageTypeConfiguration.CLIENT_ACTIVITY_REGISTER,json.getMessage());
    }

    public static boolean activityGetInfo(SocketClient socketClient,int activityID){
        JsonByUTF8 json=new JsonByUTF8();
        json.putData(MessageNameConfiguration.ACTIVITY_ID,activityID);
        return socketClient.sendMessage(ClientMessageTypeConfiguration.CLIENT_ACTIVITY_GET_INFO,json.getMessage());
    }

    public static boolean activityGetParticipantList(SocketClient socketClient,int activityID){
        JsonByUTF8 json=new JsonByUTF8();
        json.putData(MessageNameConfiguration.ACTIVITY_ID,activityID);
        return socketClient.sendMessage(ClientMessageTypeConfiguration.CLIENT_ACTIVITY_PARTICIPANT_LIST,json.getMessage());
    }

    public static boolean activityJoin(SocketClient socketClient,int myID,int activityID,String code){
        JsonByUTF8 json=new JsonByUTF8();
        json.putData(MessageNameConfiguration.USER_ID,myID);
        json.putData(MessageNameConfiguration.ACTIVITY_ID,activityID);
        json.putData(MessageNameConfiguration.ACTIVITY_INVITATION_CODE,code);
        return socketClient.sendMessage(ClientMessageTypeConfiguration.CLIENT_MY_JOIN_ACTIVITY,json.getMessage());
    }
}

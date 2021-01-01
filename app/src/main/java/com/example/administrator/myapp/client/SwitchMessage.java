package com.example.administrator.myapp.client;


import android.util.Log;

import com.example.administrator.myapp.Info.CheckInActivityInfo;
import com.example.administrator.myapp.Info.MyInfo;
import com.example.administrator.myapp.Info.Participant;
import com.example.administrator.myapp.Info.UserInfo;
import com.example.administrator.myapp.InfoManager;
import com.example.administrator.myapp.client.configuration.ServerMessageTypeConfiguration;
import com.example.administrator.myapp.client.tool.ConvertTypeTool;
import com.example.administrator.myapp.configuration.MessageNameConfiguration;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用以处理各项数据
 */
public class SwitchMessage {
    InfoManager infoManager;

    SwitchMessage(InfoManager infoManager){
        this.infoManager = infoManager;
    }

    /**
     * 通过此方法将不同的数据加以处理
     * @param basicMessage
     * @return
     */
    public boolean switchMessageManager(BasicMessage basicMessage){
        switch (basicMessage.getMessageType()){
            case ServerMessageTypeConfiguration.SERVER_HEARTBEAT:
                caseHeartBeat(basicMessage);break;
            case ServerMessageTypeConfiguration.SERVER_MY_LOGIN_INFO:
                caseMyLoginInfo(basicMessage);break;
            case ServerMessageTypeConfiguration.SERVER_MY_REGISTER:
                 caseMyRegister(basicMessage);break;
            case ServerMessageTypeConfiguration.SERVER_USER_GET_INFO:
                caseUserGetInfoByUID(basicMessage);break;
            case ServerMessageTypeConfiguration.SERVER_ACTIVITY_REGISTER:
                caseActivityRegister(basicMessage);break;
            case ServerMessageTypeConfiguration.SERVER_ACTIVITY_GET_INFO:
                caseActivityGetInfo(basicMessage);break;
            case ServerMessageTypeConfiguration.SERVER_MY_JOIN_ACTIVITY:
                caseActivityJoin(basicMessage);break;
            case ServerMessageTypeConfiguration.SERVER_ACTIVITY_PARTICIPANT_LIST:
                ;break;
            default:
                caseDefault(basicMessage);break;
        }
        return false;
    }

    private void caseActivityRegister(BasicMessage basicMessage){
        JsonByUTF8 json=new JsonByUTF8(basicMessage.getMainData());
        try {
            infoManager.clientSetActivityID(json.getJson().getInt(MessageNameConfiguration.REGISTER_ACTIVITY_INDEX),
                    json.getJson().getInt(MessageNameConfiguration.ACTIVITY_ID),json.getJson().getString(MessageNameConfiguration.ACTIVITY_THEME));
            Log.i("Socket分置数据","用户注册活动返回数据,ActivityID:"+json.getJson().getInt(MessageNameConfiguration.ACTIVITY_ID));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("Socket分置数据","用户注册活动返回数据,错误:"+e.getMessage());
        }
    }

    private void caseActivityGetInfo(BasicMessage basicMessage){
        JsonByUTF8 json=new JsonByUTF8(basicMessage.getMainData());
        try {
            CheckInActivityInfo checkInActivityInfo=new CheckInActivityInfo(json.getJson().getInt(MessageNameConfiguration.ACTIVITY_ID),
                    json.getJson().getInt(MessageNameConfiguration.ACTIVITY_INITIATOR_ID),json.getJson().getString(MessageNameConfiguration.ACTIVITY_THEME),
                    json.getJson().getDouble(MessageNameConfiguration.ACTIVITY_LONGITUDE),json.getJson().getDouble(MessageNameConfiguration.ACTIVITY_LATITUDE),
                    json.getJson().getString(MessageNameConfiguration.ACTIVITY_INVITATION_CODE),json.getJson().getString(MessageNameConfiguration.ACTIVITY_CHECK_IN_START_TIME),
                    json.getJson().getString(MessageNameConfiguration.ACTIVITY_CHECK_IN_END_TIME),json.getJson().getString(MessageNameConfiguration.ACTIVITY_START_TIME),
                    json.getJson().getString(MessageNameConfiguration.ACTIVITY_END_TIME));
            infoManager.clientSetActivityInfo(checkInActivityInfo);
            Log.i("Socket分置数据","获取活动消息返回数据,ActivityID:"+json.getJson().getInt(MessageNameConfiguration.ACTIVITY_ID));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("Socket分置数据","获取活动信息返回数据,错误:"+e.getMessage());
        }
    }

    private void caseActivityJoin(BasicMessage basicMessage){
        JsonByUTF8 json=new JsonByUTF8(basicMessage.getMainData());
        try {
            infoManager.clientJoinActivityStatus(json.getJson().getBoolean(MessageNameConfiguration.ACTIVITY_JOIN_STATUS),
                    json.getJson().getInt(MessageNameConfiguration.ACTIVITY_ID));
            Log.i("Socket分置数据","用户加入活动返回数据,ActivityID:"+json.getJson().getInt(MessageNameConfiguration.ACTIVITY_ID)+";加入状态:"
                    +json.getJson().getBoolean(MessageNameConfiguration.ACTIVITY_JOIN_STATUS));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("Socket分置数据","用户加入活动返回数据,错误:"+e.getMessage());
            infoManager.clientJoinActivityStatus(false,-1);
        }
    }

    private void caseMyLoginInfo(BasicMessage basicMessage){
        JsonByUTF8 json=new JsonByUTF8(basicMessage.getMainData());
        try {
            MyInfo myInfo=new MyInfo(json.getJson().getInt(MessageNameConfiguration.LOGIN_ID),
                    json.getJson().getString(MessageNameConfiguration.LOGIN_NAME),
                    ConvertTypeTool.StringToIntList(json.getJson().getString(MessageNameConfiguration.LOGIN_JOINED_ACTIVITY_LIST),"-"),
                    ConvertTypeTool.StringToIntList(json.getJson().getString(MessageNameConfiguration.LOGIN_MANAGED_ACTIVITY_LIST),"-"),
                    ConvertTypeTool.StringToIntList(json.getJson().getString(MessageNameConfiguration.LOGIN_INITIATOR_ACTIVITY_LIST),"-"));
            Log.i("Socket分置数据","用户登录返回数据,UserID:"+json.getJson().getInt(MessageNameConfiguration.LOGIN_ID)+";登录状态:"
                    +json.getJson().getBoolean(MessageNameConfiguration.LOGIN_STATUS));
            infoManager.clientLoginStatus(json.getJson().getBoolean(MessageNameConfiguration.LOGIN_STATUS),myInfo);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("Socket分置数据","用户登录返回数据,错误:"+e.getMessage());
            infoManager.clientLoginStatus(false,null);
        }
    }

    private void caseMyRegister(BasicMessage basicMessage){
        JsonByUTF8 json=new JsonByUTF8(basicMessage.getMainData());
        try {
            MyInfo userInfo=new MyInfo(json.getJson().getInt(MessageNameConfiguration.LOGIN_ID),
                    json.getJson().getString(MessageNameConfiguration.LOGIN_NAME),new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>());
            infoManager.clientRegisterAccountStatus(json.getJson().getBoolean(MessageNameConfiguration.REGISTER_STATUS),userInfo);
            Log.i("Socket分置数据","用户注册返回数据,UserID:"+json.getJson().getInt(MessageNameConfiguration.LOGIN_ID)+";注册状态:"
                    +json.getJson().getBoolean(MessageNameConfiguration.REGISTER_STATUS));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("Socket分置数据","用户注册返回数据,错误:"+e.getMessage());
            infoManager.clientRegisterAccountStatus(false,null);
        }
    }

    private void caseUserGetInfoByUID(BasicMessage basicMessage){
        JsonByUTF8 json=new JsonByUTF8(basicMessage.getMainData());
        try {
            UserInfo userInfo=new UserInfo(json.getJson().getInt(MessageNameConfiguration.USER_ID),json.getJson().getString(MessageNameConfiguration.USER_NAME),
                    ConvertTypeTool.StringToIntList(json.getJson().getString(MessageNameConfiguration.USER_JOINED_ACTIVITY_LIST),"-"),
                    ConvertTypeTool.StringToIntList(json.getJson().getString(MessageNameConfiguration.USER_MANAGED_ACTIVITY_LIST),"-"),
                    ConvertTypeTool.StringToIntList(json.getJson().getString(MessageNameConfiguration.USER_INITIATOR_ACTIVITY_LIST),"-"));
            infoManager.clientSetUserInfo(userInfo);
            Log.i("Socket分置数据","获取其它用户数据存放完毕,UserID:"+json.getJson().getInt(MessageNameConfiguration.USER_ID));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("Socket分置数据","获取其它用户返回数据,错误:"+e.getMessage());
        }
    }

    private void caseGetActivityParticipantList(BasicMessage basicMessage){
        JsonByUTF8 json=new JsonByUTF8(basicMessage.getMainData());
        try {
            List<Participant> participants=new ArrayList<>();
            List<Integer> userIDList=ConvertTypeTool.StringToIntList(json.getJson().getString(MessageNameConfiguration.ACTIVITY_PARTICIPANT_USER_ID),"-");
            List<Boolean> clockInList=ConvertTypeTool.StringToBooleanList(json.getJson().getString(MessageNameConfiguration.ACTIVITY_PARTICIPANT_CLOCK_IN),"-");
            List<Boolean> administratorList=ConvertTypeTool.StringToBooleanList(json.getJson().getString(MessageNameConfiguration.ACTIVITY_PARTICIPANT_ADMINISTRATOR),"-");
            for(int i=0;i<userIDList.size();i++){
                Participant participant=new Participant(userIDList.get(i),
                        clockInList.get(i),administratorList.get(i));
                participants.add(participant);
            }
            infoManager.clientSetActivityParticipantInfo(participants,json.getJson().getInt(MessageNameConfiguration.ACTIVITY_ID));
            Log.i("Socket分置数据","活动参与列表数据存放完毕,ActivityID:"+json.getJson().getInt(MessageNameConfiguration.ACTIVITY_ID));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("Socket分置数据","参与活动列表返回数据,错误:"+e.getMessage());
        }

    }

    private void caseHeartBeat(BasicMessage basicMessage){
        try {
            Log.i("Socket分置数据","接收到来自服务器回应的心跳包:"+new String(basicMessage.getMainData(),0,basicMessage.getMainData().length,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private void caseDefault(BasicMessage basicMessage){
        Log.i("Socket分置数据","接收到其它不在配置中的数据:"+basicMessage.getMainData());
    }
}

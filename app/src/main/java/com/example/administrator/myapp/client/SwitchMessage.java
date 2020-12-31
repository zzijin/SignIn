package com.example.administrator.myapp.client;


import android.util.Log;

import com.example.administrator.myapp.Info.CheckInActivityInfo;
import com.example.administrator.myapp.Info.MyInfo;
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
                caseUserLoginInfo(basicMessage);break;
            case ServerMessageTypeConfiguration.SERVER_MY_REGISTER:
                caseUserRegister(basicMessage);break;
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void caseActivityGetInfo(BasicMessage basicMessage){
        JsonByUTF8 json=new JsonByUTF8(basicMessage.getMainData());
        try {
            List<CheckInActivityInfo.Participant> participants=new ArrayList<>();

            CheckInActivityInfo checkInActivityInfo=new CheckInActivityInfo(json.getJson().getInt(MessageNameConfiguration.ACTIVITY_ID),
                    json.getJson().getInt(MessageNameConfiguration.ACTIVITY_INITIATOR_ID),json.getJson().getString(MessageNameConfiguration.ACTIVITY_THEME),
                    json.getJson().getDouble(MessageNameConfiguration.ACTIVITY_LONGITUDE),json.getJson().getDouble(MessageNameConfiguration.ACTIVITY_LATITUDE),
                    json.getJson().getInt(MessageNameConfiguration.ACTIVITY_INVITATION_CODE),json.getJson().getString(MessageNameConfiguration.ACTIVITY_CHECK_IN_START_TIME),
                    json.getJson().getString(MessageNameConfiguration.ACTIVITY_CHECK_IN_END_TIME),json.getJson().getString(MessageNameConfiguration.ACTIVITY_START_TIME),
                    json.getJson().getString(MessageNameConfiguration.ACTIVITY_END_TIME));
            infoManager.clientSetActivityInfo(checkInActivityInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void caseActivityJoin(BasicMessage basicMessage){
        JsonByUTF8 json=new JsonByUTF8(basicMessage.getMainData());
        try {
            infoManager.clientJoinActivityStatus(json.getJson().getBoolean(MessageNameConfiguration.ACTIVITY_JOIN_STATUS),json.getJson().getInt(MessageNameConfiguration.ACTIVITY_ID));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void caseUserLoginInfo(BasicMessage basicMessage){
        JsonByUTF8 json=new JsonByUTF8(basicMessage.getMainData());
        try {
            MyInfo myInfo=new MyInfo(json.getJson().getInt(MessageNameConfiguration.LOGIN_ID),
                    json.getJson().getString(MessageNameConfiguration.LOGIN_NAME),
                    ConvertTypeTool.StringToIntList(json.getJson().getString(MessageNameConfiguration.LOGIN_JOINED_ACTIVITY_LIST),"-"),
                    ConvertTypeTool.StringToIntList(json.getJson().getString(MessageNameConfiguration.LOGIN_MANAGED_ACTIVITY_LIST),"-"),
                    ConvertTypeTool.StringToIntList(json.getJson().getString(MessageNameConfiguration.LOGIN_INITIATOR_ACTIVITY_LIST),"-"));
            infoManager.clientLoginStatus(json.getJson().getBoolean(MessageNameConfiguration.LOGIN_STATUS),myInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void caseUserRegister(BasicMessage basicMessage){
        JsonByUTF8 json=new JsonByUTF8(basicMessage.getMainData());
        try {
            MyInfo userInfo=new MyInfo(json.getJson().getInt(MessageNameConfiguration.LOGIN_ID),
                    json.getJson().getString(MessageNameConfiguration.LOGIN_NAME),new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>());
            infoManager.clientRegisterAccountStatus(json.getJson().getBoolean(MessageNameConfiguration.REGISTER_STATUS),userInfo);
        } catch (JSONException e) {
            e.printStackTrace();
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void caseGetActivityParticipantList(BasicMessage basicMessage){
        JsonByUTF8 json=new JsonByUTF8(basicMessage.getMainData());

        try {
            List<CheckInActivityInfo.Participant> participants=new ArrayList<>();
            List<Integer> userIDList=ConvertTypeTool.StringToIntList(json.getJson().getString(MessageNameConfiguration.ACTIVITY_PARTICIPANT_USER_ID),"-");
            List<Boolean> clockInList=ConvertTypeTool.StringToBooleanList(json.getJson().getString(MessageNameConfiguration.ACTIVITY_PARTICIPANT_CLOCK_IN),"-");
            List<Boolean> administratorList=ConvertTypeTool.StringToBooleanList(json.getJson().getString(MessageNameConfiguration.ACTIVITY_PARTICIPANT_ADMINISTRATOR),"-");
            for(int i=0;i<userIDList.size();i++){
                CheckInActivityInfo.Participant participant=new CheckInActivityInfo.Participant(userIDList.get(i),
                        clockInList.get(i),administratorList.get(i));
            }
            infoManager.clientSetActivityParticipantInfo();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void caseHeartBeat(BasicMessage basicMessage){
        try {
            Log.i("Socket接收","接收到来自服务器回应的心跳包:"+new String(basicMessage.getMainData(),0,basicMessage.getMainData().length,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private void caseDefault(BasicMessage basicMessage){
        Log.i("Socket接收","接收到其它不在配置中的数据:"+basicMessage.getMainData());
    }
}

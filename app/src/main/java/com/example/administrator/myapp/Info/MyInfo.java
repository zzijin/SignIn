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
    //-1:未登录；0:登录中;1:登录成功;2:登录失败
    private int myLoginStatus=-1;

    public MyInfo(){

    }

    public MyInfo(String myName,String myPassword){
        this.myName=myName;this.myPassword=myPassword;joinActivity=new ArrayList<>();waitJoinStatusActivity=new ArrayList<>();failJoinActivity=new ArrayList<>();
    }

    public MyInfo(int myID,String myPassword){
        this.myID=myID;this.myPassword=myPassword;joinActivity=new ArrayList<>();waitJoinStatusActivity=new ArrayList<>();failJoinActivity=new ArrayList<>();
    }

    public void addWaitJoin(int activityID){
        waitJoinStatusActivity.add(activityID);
    }

    public void addFailActivity(int activityID){
        for (int i=0;i<waitJoinStatusActivity.size();i++){
            if (waitJoinStatusActivity.get(i)==activityID){
                failJoinActivity.add(activityID);
                waitJoinStatusActivity.remove(i);
                return;
            }
        }
    }

    public void loginMyInfo(String myName, String myPassword, SocketClient socketClient){
        this.myName=myName;this.myPassword=myPassword;
        JsonByUTF8 json=new JsonByUTF8();
        json.PutData(MessageNameConfiguration.LOGIN_NAME,myName);
        json.PutData(MessageNameConfiguration.LOGIN_PASSWORD,myPassword);
        socketClient.sendMessage(ClientMessageTypeConfiguration.CLIENT_USER_LOGIN_INFO_BY_ID,json.getMessage());
        myLoginStatus=0;
    }

    public void loginMyInfo(int myID,String myPassword,SocketClient socketClient){
        this.myID=myID;this.myPassword=myPassword;
        JsonByUTF8 json=new JsonByUTF8();
        json.PutData(MessageNameConfiguration.LOGIN_ID,myID);
        json.PutData(MessageNameConfiguration.LOGIN_PASSWORD,myPassword);
        socketClient.sendMessage(ClientMessageTypeConfiguration.CLIENT_USER_LOGIN_INFO_BY_ID,json.getMessage());
        myLoginStatus=0;
    }

    public void loginFailMyInfo(){
        myLoginStatus=2;
    }

    public void loginSucceedMyInfo(int myID,String myName){
        this.myID=myID;this.myName=myName;myLoginStatus=1;
    }

    public int[] getFailJoinList(){
        int[] fail=new int[failJoinActivity.size()];
        for(int i=0;i<failJoinActivity.size();i++){
            fail[i]=failJoinActivity.get(i);
        }
        failJoinActivity.clear();
        return fail;
    }

    public void addJoinActivity(int activityID){
        for (int i=0;i<waitJoinStatusActivity.size();i++){
            if (waitJoinStatusActivity.get(i)==activityID){
                joinActivity.add(activityID);
                waitJoinStatusActivity.remove(i);
                return;
            }
        }
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

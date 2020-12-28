package com.example.administrator.myapp.Info;


import com.example.administrator.myapp.client.file.PictureFile;

public class UserInfo {
    /**
     * 用户ID
     */
    private int userID;
    /**
     * 用户昵称
     */
    private String userName;

    //以下信息可以为空
    /**
     * 用户头像
     */
    private PictureFile userHead;
    /**
     * 用户签名
     */
    private String userSignature;
    /**
     * 用户生日
     */
    private String userBirthday;
    private String myEmail;

    public UserInfo(int userID){
        this.userID=userID;
    }

    public UserInfo(int userID,String userName){
        this.userID=userID;this.userName=userName;
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

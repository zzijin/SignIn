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

    /////基本构造函数(Test仅使用用户名/id)///////
    public UserInfo(int userID,String userName){
        this.userID=userID;this.userName=userName;
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
    }

    /////获取用户信息的基本方法///////

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

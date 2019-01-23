package com.jh.lottery.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jh.lottery.utils.XPreferencesUtils;

/**
 * Created by sangcixiang on 2018/7/29.
 */

public class UserModel {

    private String acctBaseId;
    private String nickName;
    private String memberLever;
    private String memberScore;
    private String memberHeadImagePath;
    private String token;


    public static UserModel getUserInfo(){

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String userStr = (String) XPreferencesUtils.get("user","");
        if (userStr.isEmpty()){
            return null;
        }
        UserModel user = gson.fromJson(userStr,UserModel.class);
        if (null != user){
            return user;
        }
        return null;

    }

    public static void saveUserInfo(UserModel user){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String userJOSN = gson.toJson(user);
        XPreferencesUtils.put("user",userJOSN);

    }

    public static void removeUserInfo(){
        XPreferencesUtils.remove("user");
    }


    public String getAcctBaseId() {
        return acctBaseId;
    }

    public void setAcctBaseId(String acctBaseId) {
        this.acctBaseId = acctBaseId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMemberLever() {
        return memberLever;
    }

    public void setMemberLever(String memberLever) {
        this.memberLever = memberLever;
    }

    public String getMemberScore() {
        return memberScore;
    }

    public void setMemberScore(String memberScore) {
        this.memberScore = memberScore;
    }

    public String getMemberHeadImagePath() {
        return memberHeadImagePath;
    }

    public void setMemberHeadImagePath(String memberHeadImagePath) {
        this.memberHeadImagePath = memberHeadImagePath;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

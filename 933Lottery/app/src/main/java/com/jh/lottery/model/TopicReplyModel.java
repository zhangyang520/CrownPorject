package com.jh.lottery.model;

/**
 * Created by sangcixiang on 2018/8/4.
 */

public class TopicReplyModel {

    private int id;
    private long createTime;
    private String content;
    private String nickName;
    private String memberHeadImagePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMemberHeadImagePath() {
        return memberHeadImagePath;
    }

    public void setMemberHeadImagePath(String memberHeadImagePath) {
        this.memberHeadImagePath = memberHeadImagePath;
    }
}

package com.jh.lottery.model;

import java.io.Serializable;

/**
 * Created by sangcixiang on 2018/8/4.
 */

public class TopicModel implements Serializable {

    private int id;
    private int plateCode;
    private String adminName;
    private String title;
    private int comment;
    private int likeNum;
    private long createTime;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlateCode() {
        return plateCode;
    }

    public void setPlateCode(int plateCode) {
        this.plateCode = plateCode;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
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
}

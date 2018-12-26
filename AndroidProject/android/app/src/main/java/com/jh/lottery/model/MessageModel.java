package com.jh.lottery.model;

/**
 * Created by sangcixiang on 2018/8/7.
 */

public class MessageModel {

    private int id;
    private String title;
    private String content;
    private String createDateTime;
    private String newsImgurl;

    public String getNewsImgurl() {
        return newsImgurl;
    }

    public void setNewsImgurl(String newsImgurl) {
        this.newsImgurl = newsImgurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }
}

package com.jh.lottery.model;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by sangcixiang on 2018/7/30.
 */

public class NewsModel implements Serializable {

    private int id;
    private String newsTitle;
    private long newsCreateDate;
    private String newsCreate;
    private String newsImgurl;
    private String newsContent;





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public long getNewsCreateDate() {
        return newsCreateDate;
    }

    public void setNewsCreateDate(long newsCreateDate) {
        this.newsCreateDate = newsCreateDate;
    }

    public String getNewsCreate() {
        return newsCreate;
    }

    public void setNewsCreate(String newsCreate) {
        this.newsCreate = newsCreate;
    }

    public String getNewsImgurl() {
        return newsImgurl;
    }

    public void setNewsImgurl(String newsImgurl) {
        this.newsImgurl = newsImgurl;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }
}

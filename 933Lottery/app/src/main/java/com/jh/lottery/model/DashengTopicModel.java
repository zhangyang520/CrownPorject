package com.jh.lottery.model;

import java.io.Serializable;

/**
 * Created by sangcixiang on 2018/8/2.
 */

public class DashengTopicModel implements Serializable {

    private String manitoArticleTitle;
    private String headImageUrl;
    private String manitoArticleContent;
    private long manitoArticleCreateDate;


    public String getManitoArticleTitle() {
        return manitoArticleTitle;
    }

    public void setManitoArticleTitle(String manitoArticleTitle) {
        this.manitoArticleTitle = manitoArticleTitle;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public String getManitoArticleContent() {
        return manitoArticleContent;
    }

    public void setManitoArticleContent(String manitoArticleContent) {
        this.manitoArticleContent = manitoArticleContent;
    }

    public long getManitoArticleCreateDate() {
        return manitoArticleCreateDate;
    }

    public void setManitoArticleCreateDate(long manitoArticleCreateDate) {
        this.manitoArticleCreateDate = manitoArticleCreateDate;
    }
}

package com.jh.lottery.model;

/**
 * Created by sangcixiang on 2018/8/2.
 */

public class GiftModel {

    private int id;
    private String giftName;
    private int giftCode;
    private int giftIntegral;
    private int giftSortCode;
    private String giftImgurl;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public int getGiftCode() {
        return giftCode;
    }

    public void setGiftCode(int giftCode) {
        this.giftCode = giftCode;
    }

    public int getGiftIntegral() {
        return giftIntegral;
    }

    public void setGiftIntegral(int giftIntegral) {
        this.giftIntegral = giftIntegral;
    }

    public int getGiftSortCode() {
        return giftSortCode;
    }

    public void setGiftSortCode(int giftSortCode) {
        this.giftSortCode = giftSortCode;
    }

    public String getGiftImgurl() {
        return giftImgurl;
    }

    public void setGiftImgurl(String giftImgurl) {
        this.giftImgurl = giftImgurl;
    }
}

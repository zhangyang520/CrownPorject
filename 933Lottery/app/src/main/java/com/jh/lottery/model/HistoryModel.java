package com.jh.lottery.model;

/**
 * Created by sangcixiang on 2018/8/6.
 */

public class HistoryModel {

    private int id;
    private String codeCn;
    private String consumptionScore;
    private long createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeCn() {
        return codeCn;
    }

    public void setCodeCn(String codeCn) {
        this.codeCn = codeCn;
    }

    public String getConsumptionScore() {
        return consumptionScore;
    }

    public void setConsumptionScore(String consumptionScore) {
        this.consumptionScore = consumptionScore;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}

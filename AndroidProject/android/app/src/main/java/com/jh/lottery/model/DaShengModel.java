package com.jh.lottery.model;

import java.io.Serializable;

/**
 * Created by sangcixiang on 2018/8/3.
 */

public class DaShengModel implements Serializable{

    private int id;
    private String godLabel;
    private String headImageUrl;
    private String godName;
    private int godStar;
    private String godIntro;
    private int position;
    private boolean isDashen = true;

    public boolean isDashen() {
        return isDashen;
    }

    public void setIsDashen(boolean dashen) {
        isDashen = dashen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGodLabel() {
        return godLabel;
    }

    public void setGodLabel(String godLabel) {
        this.godLabel = godLabel;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public String getGodName() {
        return godName;
    }

    public void setGodName(String godName) {
        this.godName = godName;
    }

    public int getGodStar() {
        return godStar;
    }

    public void setGodStar(int godStar) {
        this.godStar = godStar;
    }

    public void setDashen(boolean dashen) {
        isDashen = dashen;
    }

    public String getGodIntro() {
        return godIntro;
    }

    public void setGodIntro(String godIntro) {
        this.godIntro = godIntro;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}

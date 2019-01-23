package com.jh.lottery.model;

import java.io.Serializable;

/**
 * Created by sangcixiang on 2018/8/4.
 */

public class BlockModel implements Serializable {
    private int id;
    private int plateCode;
    private int areaCode;
    private String plateName;
    private String plateDescription;


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

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public String getPlateDescription() {
        return plateDescription;
    }

    public void setPlateDescription(String plateDescription) {
        this.plateDescription = plateDescription;
    }
}

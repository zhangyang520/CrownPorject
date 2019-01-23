package com.jh.lottery.model;

/**
 * Created by sangcixiang on 2018/8/2.
 */

public class LotteryModel {
    private String lotteryCode;
    private String lotteryPeriods;
    private String lotteryOpenNumber;
    private String lotteryDate;


    public String getName(){
        if (getLotteryCode().equals("SSQ") || getLotteryCode().equals("1100")){
            return "双色球";
        }else if (getLotteryCode().equals("PLS") || getLotteryCode().equals("2300")){
            return "排列3";
        }else if (getLotteryCode().equals("QLC") || getLotteryCode().equals("1340")){
            return "七乐彩";
        }else if (getLotteryCode().equals("PLW") || getLotteryCode().equals("2300")){
            return "排列5";
        }else if (getLotteryCode().equals("DLT") || getLotteryCode().equals("2100")){
            return "大乐透";
        }else if (getLotteryCode().equals("QXC") || getLotteryCode().equals("2320")){
            return "七星彩";
        }else if (getLotteryCode().equals("FC3D") || getLotteryCode().equals("1110")){
            return "福彩3D";
        }else {
            return "";
        }
    }

    public String getLotteryCode() {
        return lotteryCode;
    }

    public void setLotteryCode(String lotteryCode) {
        this.lotteryCode = lotteryCode;
    }

    public String getLotteryPeriods() {
        return lotteryPeriods;
    }

    public void setLotteryPeriods(String lotteryPeriods) {
        this.lotteryPeriods = lotteryPeriods;
    }

    public String getLotteryOpenNumber() {
        return lotteryOpenNumber;
    }

    public void setLotteryOpenNumber(String lotteryOpenNumber) {
        this.lotteryOpenNumber = lotteryOpenNumber;
    }

    public String getLotteryDate() {
        return lotteryDate;
    }

    public void setLotteryDate(String lotteryDate) {
        this.lotteryDate = lotteryDate;
    }
}

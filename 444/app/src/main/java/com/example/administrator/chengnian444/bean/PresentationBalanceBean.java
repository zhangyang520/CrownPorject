package com.example.administrator.chengnian444.bean;

/**
 *  提现实体
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date 2018/12/2516:17
 */
public class PresentationBalanceBean {
    String name; //提现名称
    Double balance; //余额
    String date; //日期
    Double income;//收入

    public PresentationBalanceBean(String name, Double balance, String date, Double income) {
        this.name = name;
        this.balance = balance;
        this.date = date;
        this.income = income;
    }

    public PresentationBalanceBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    @Override
    public String toString() {
        return "PresentationBalanceBean{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                ", date='" + date + '\'' +
                ", income=" + income +
                '}';
    }
}

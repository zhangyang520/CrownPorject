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
    double balance; //余额
    String date; //日期
    double income;//收入
    int status; //0 成功 ，1 失败， 2 拒绝

    public PresentationBalanceBean(String name, Double balance, String date, Double income) {
        this.name = name;
        this.balance = balance;
        this.date = date;
        this.income = income;
    }

    public PresentationBalanceBean(String name, double balance, String date, double income, int status) {
        this.name = name;
        this.balance = balance;
        this.date = date;
        this.income = income;
        this.status = status;
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

    public void setBalance(double balance) {
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

    public void setIncome(double income) {
        this.income = income;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PresentationBalanceBean{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                ", date='" + date + '\'' +
                ", income=" + income +
                ", status=" + status +
                '}';
    }
}

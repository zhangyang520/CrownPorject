package com.example.administrator.chengnian444.bean;

public class MessageCodeBean {

    /**
     * code : 200
     * msg : OK
     * data : 短信发送成功!
     */
    private int code;
    private String message;
    private String data="";

    public MessageCodeBean() {
    }

    public MessageCodeBean(int code, String message, String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public MessageCodeBean(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

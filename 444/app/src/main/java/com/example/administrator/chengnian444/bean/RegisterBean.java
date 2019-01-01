package com.example.administrator.chengnian444.bean;

/**
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date 2019/1/121:15
 */
public class RegisterBean {
    private int code;
    private String message;
    private boolean data=false;

    public RegisterBean() {
    }

    public RegisterBean(int code, String message, boolean data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}

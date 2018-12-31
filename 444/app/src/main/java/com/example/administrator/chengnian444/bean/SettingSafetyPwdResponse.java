package com.example.administrator.chengnian444.bean;

/**
 * 安全密码 设置 请求的回调
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date 2018/12/3116:27
 */
public class SettingSafetyPwdResponse {
    private int code;
    private boolean data;
    private String message;

    public SettingSafetyPwdResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

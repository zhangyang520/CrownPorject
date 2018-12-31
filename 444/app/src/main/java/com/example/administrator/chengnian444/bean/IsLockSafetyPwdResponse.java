package com.example.administrator.chengnian444.bean;

/**
 * 是否 绑定安全密码
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date 2018/12/3118:30
 */
public class IsLockSafetyPwdResponse {
    private int code;
    private boolean data;
    private String message;

    public IsLockSafetyPwdResponse() {
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

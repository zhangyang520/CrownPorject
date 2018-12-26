package com.example.administrator.chengnian933.bean;

public class AuthBean {

    /**
     * randomKey : f56mgq
     * token : eyJhbGciOiJIUzUxMiJ9.eyJyYW5kb21LZXkiOiJmNTZtZ3EiLCJzdWIiOiJhZG1pbiIsImV4cCI6MTUzODk4OTg2MSwiaWF0IjoxNTM4Mzg1MDYxfQ.ahHXeNlEa-uTS7ZWmhEi1uUKRKpZ7BRMKkEY3sudJXtc76HgwDY9-wZoOTYj25cGdsuJNiSDlMvmLqRI30SNXA
     * userType : 0
     */

    private String randomKey;
    private String token;
    private String userType;

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}

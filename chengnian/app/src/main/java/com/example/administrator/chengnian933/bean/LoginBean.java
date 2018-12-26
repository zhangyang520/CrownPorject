package com.example.administrator.chengnian933.bean;

public class LoginBean {


    /**
     * code : 200
     * data : {"account":"18672671812","status":2,"token":"eyJhbGciOiJIUzUxMiJ9.eyJyYW5kb21LZXkiOiI4d2Y2cjYiLCJzdWIiOiIxODY3MjY3MTgxMiIsImV4cCI6MTUzOTA3NjE4OSwiaWF0IjoxNTM4NDcxMzg5fQ.SErri3sUQiF4uqBNJyw3mD9-174mR4WEdwKwX6bioU7OJgx7R_VpvFiAiHBd2uR2WKr0zS0SpVn9kX_OVmTz7g"}
     * message : 登录成功
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * account : 18672671812
         * status : 2
         * token : eyJhbGciOiJIUzUxMiJ9.eyJyYW5kb21LZXkiOiI4d2Y2cjYiLCJzdWIiOiIxODY3MjY3MTgxMiIsImV4cCI6MTUzOTA3NjE4OSwiaWF0IjoxNTM4NDcxMzg5fQ.SErri3sUQiF4uqBNJyw3mD9-174mR4WEdwKwX6bioU7OJgx7R_VpvFiAiHBd2uR2WKr0zS0SpVn9kX_OVmTz7g
         */

        private String account;
        private int status;
        private String token;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}

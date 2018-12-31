package com.example.administrator.chengnian444.bean;

/**
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date 2018/12/3114:47
 */
public class UserInfoResponse {
    private int code;
    private DataBean data;
    private String message;

    /**
     * 内部的数据 的实体
     */
   public  class DataBean {
      float balance;  //账户余额
      String promoteNum; //推广人数
      String lockStatus; //是否绑定推广码  0 未绑定邀请码，1 已绑定邀请码
      String eqCodeUrl; //对应二维码图片链接地址

        public float getBalance() {
            return balance;
        }

        public void setBalance(float balance) {
            this.balance = balance;
        }

        public String getPromoteNum() {
            return promoteNum;
        }

        public void setPromoteNum(String promoteNum) {
            this.promoteNum = promoteNum;
        }

        public String getLockStatus() {
            return lockStatus;
        }

        public void setLockStatus(String lockStatus) {
            this.lockStatus = lockStatus;
        }

        public String getEqCodeUrl() {
            return eqCodeUrl;
        }

        public void setEqCodeUrl(String eqCodeUrl) {
            this.eqCodeUrl = eqCodeUrl;
        }
    }

    public UserInfoResponse() {
    }

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
}

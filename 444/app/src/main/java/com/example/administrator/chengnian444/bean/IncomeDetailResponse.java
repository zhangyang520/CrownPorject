package com.example.administrator.chengnian444.bean;

import java.util.List;

/**
 * 收益明細的詳情
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date 2019/1/118:50
 */
public class IncomeDetailResponse {
    private int code;
    private List<IncomeDetail> data;
    private String message;

    public IncomeDetailResponse() {
    }

    public IncomeDetailResponse(int code, List<IncomeDetail> data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<IncomeDetail> getData() {
        return data;
    }

    public void setData(List<IncomeDetail> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
}

   public  class IncomeDetail{
        private String blanceName; //余额操作名称
        private float moneyCount; //操作金额数
        private float balance; //账户余额
        private long createTime; //交易时间

        public IncomeDetail() {
        }

        public IncomeDetail(String blanceName, float moneyCount, float balance, long createTime) {
            this.blanceName = blanceName;
            this.moneyCount = moneyCount;
            this.balance = balance;
            this.createTime = createTime;
        }

        public String getBlanceName() {
            return blanceName;
        }

        public void setBlanceName(String blanceName) {
            this.blanceName = blanceName;
        }

        public float getMoneyCount() {
            return moneyCount;
        }

        public void setMoneyCount(float moneyCount) {
            this.moneyCount = moneyCount;
        }

        public float getBalance() {
            return balance;
        }

        public void setBalance(float balance) {
            this.balance = balance;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }
}

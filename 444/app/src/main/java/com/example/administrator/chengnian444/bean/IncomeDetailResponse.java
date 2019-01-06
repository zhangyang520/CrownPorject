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
    private IncomeDetailBean data;
    private String message;

    public IncomeDetailResponse() {
    }

    public IncomeDetailResponse(int code, IncomeDetailBean data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public IncomeDetailBean getData() {
        return data;
    }

    public void setData(IncomeDetailBean data) {
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

    /**
     * 收入的bean
     */
   public class IncomeDetailBean{
        private int currentPage;
        private int pageSize;
        private int totalPage;
        private int totalRecord;
        private List<IncomeDetail> dataList;

        public IncomeDetailBean() {
        }

        public IncomeDetailBean(int currentPage, int pageSize, int totalPage, int totalRecord, List<IncomeDetail> dataList) {
            this.currentPage = currentPage;
            this.pageSize = pageSize;
            this.totalPage = totalPage;
            this.totalRecord = totalRecord;
            this.dataList = dataList;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getTotalRecord() {
            return totalRecord;
        }

        public void setTotalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
        }

        public List<IncomeDetail> getDataList() {
            return dataList;
        }

        public void setDataList(List<IncomeDetail> dataList) {
            this.dataList = dataList;
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

}

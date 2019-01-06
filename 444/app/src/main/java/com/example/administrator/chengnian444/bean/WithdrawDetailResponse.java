package com.example.administrator.chengnian444.bean;

import java.util.List;

/**
 * 提现详情 返回實體
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date 2019/1/120:28
 */
public class WithdrawDetailResponse {
    private int code;
    private WithdrawDetailBean data;
    private String message;

    public WithdrawDetailResponse() {
    }

    public WithdrawDetailResponse(int code, WithdrawDetailBean data, String message) {
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

    public WithdrawDetailBean getData() {
        return data;
    }

    public void setData(WithdrawDetailBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 返回的数据集合
     */
    public class WithdrawDetailBean{
        private int currentPage;
        private List<WithdrawDetail> dataList;
        private int pageSize;
        private int totalPage;
        private int totalRecord;

        public WithdrawDetailBean() {
        }

        public WithdrawDetailBean(int currentPage, List<WithdrawDetail> dataList, int pageSize, int totalPage, int totalRecord) {
            this.currentPage = currentPage;
            this.dataList = dataList;
            this.pageSize = pageSize;
            this.totalPage = totalPage;
            this.totalRecord = totalRecord;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public List<WithdrawDetail> getDataList() {
            return dataList;
        }

        public void setDataList(List<WithdrawDetail> dataList) {
            this.dataList = dataList;
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


        public class  WithdrawDetail{
            private String blanceName; //余额操作名称
            private float moneyCount; //操作金额数
            private float balance; //账户余额
            private long createTime; //交易时间
            private String detailEventName;

            public WithdrawDetail() {
            }

            public String getDetailEventName() {
                return detailEventName;
            }

            public void setDetailEventName(String detailEventName) {
                this.detailEventName = detailEventName;
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

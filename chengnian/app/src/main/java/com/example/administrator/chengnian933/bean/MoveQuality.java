package com.example.administrator.chengnian933.bean;

import java.util.List;

public class MoveQuality {

    /**
     * code : 200
     * message : success
     * data : {"currentPage":1,"pageSize":10,"totalRecord":2,"totalPage":1,"dataList":[{"pageSize":10,"currentPage":1,"id":1,"seriesCn":"苍老师系列","seriesCode":1001,"img":"1"},{"pageSize":10,"currentPage":1,"id":2,"seriesCn":"波多野结衣系列","seriesCode":1002,"img":"2"}]}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * currentPage : 1
         * pageSize : 10
         * totalRecord : 2
         * totalPage : 1
         * dataList : [{"pageSize":10,"currentPage":1,"id":1,"seriesCn":"苍老师系列","seriesCode":1001,"img":"1"},{"pageSize":10,"currentPage":1,"id":2,"seriesCn":"波多野结衣系列","seriesCode":1002,"img":"2"}]
         */

        private int currentPage;
        private int pageSize;
        private int totalRecord;
        private int totalPage;
        private List<DataListBean> dataList;

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

        public int getTotalRecord() {
            return totalRecord;
        }

        public void setTotalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<DataListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListBean> dataList) {
            this.dataList = dataList;
        }

        public static class DataListBean {
            /**
             * pageSize : 10
             * currentPage : 1
             * id : 1
             * seriesCn : 苍老师系列
             * seriesCode : 1001
             * img : 1
             */

            private int pageSize;
            private int currentPage;
            private int id;
            private String seriesCn;
            private int seriesCode;
            private String img;
            private String seriesName;

            public String getSeriesName() {
                return seriesName;
            }

            public void setSeriesName(String seriesName) {
                this.seriesName = seriesName;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getSeriesCn() {
                return seriesCn;
            }

            public void setSeriesCn(String seriesCn) {
                this.seriesCn = seriesCn;
            }

            public int getSeriesCode() {
                return seriesCode;
            }

            public void setSeriesCode(int seriesCode) {
                this.seriesCode = seriesCode;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}

package com.example.administrator.chengnian444.bean;

public class ShareBean {

    /**
     * code : 200
     * data : {"createTime":"2018-10-09 20:27:59","id":1,"shareContent":"撒擦拭擦擦是1","type":"933"}
     * message : success
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
         * createTime : 2018-10-09 20:27:59
         * id : 1
         * shareContent : 撒擦拭擦擦是1
         * type : 933
         */

        private String createTime;
        private int id;
        private String shareContent;
        private String type;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getShareContent() {
            return shareContent;
        }

        public void setShareContent(String shareContent) {
            this.shareContent = shareContent;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}

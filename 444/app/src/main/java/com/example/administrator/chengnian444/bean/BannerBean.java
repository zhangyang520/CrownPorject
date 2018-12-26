package com.example.administrator.chengnian444.bean;

import java.util.List;

public class BannerBean {

    /**
     * code : 200
     * message : success
     * data : [{"id":2,"title":"这是是是是是 ","image":"/uploads/banner/5b8a6bab4ab96.png","type":"10001","createTime":1535798191000,"url":"https://www.baidu.com"},{"id":3,"title":"这是网赚项目","image":"/uploads/banner/5b8a6bcecc7a0.png","type":"10001","createTime":1535798225000,"url":"https://www.baidu.com"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * title : 这是是是是是
         * image : /uploads/banner/5b8a6bab4ab96.png
         * type : 10001
         * createTime : 1535798191000
         * url : https://www.baidu.com
         */

        private int id;
        private String title;
        private String image;
        private String type;
        private long createTime;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

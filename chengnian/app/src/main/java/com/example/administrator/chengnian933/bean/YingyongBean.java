package com.example.administrator.chengnian933.bean;

import java.util.List;

public class YingyongBean {

    /**
     * code : 200
     * data : [{"applicationUrl":"http://www.933.cc","createTime":"2018-10-04 14:28:09","description":"933彩票","id":3,"img":"http://221.120.162.112:9006/movie-image/5b5577a0af00afffcdf6b10ffe0b3425.jpg","iseffective":"T","name":"933彩票"}]
     * message : success
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
         * applicationUrl : http://www.933.cc
         * createTime : 2018-10-04 14:28:09
         * description : 933彩票
         * id : 3
         * img : http://221.120.162.112:9006/movie-image/5b5577a0af00afffcdf6b10ffe0b3425.jpg
         * iseffective : T
         * name : 933彩票
         */

        private String applicationUrl;
        private String createTime;
        private String description;
        private int id;
        private String img;
        private String iseffective;
        private String name;

        public String getApplicationUrl() {
            return applicationUrl;
        }

        public void setApplicationUrl(String applicationUrl) {
            this.applicationUrl = applicationUrl;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getIseffective() {
            return iseffective;
        }

        public void setIseffective(String iseffective) {
            this.iseffective = iseffective;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

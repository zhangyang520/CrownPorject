package com.example.administrator.chengnian933.bean;

public class OneBannerBean {

    /**
     * code : 200
     * data : {"createTime":"2018-10-04 14:02:36","id":64,"image":"http://221.120.162.112:9006/movie-image/70e5041ac623252c60069328b02eaa0f.jpg","title":"广告banner1","type":"1","url":"http://www.933.cc"}
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
         * createTime : 2018-10-04 14:02:36
         * id : 64
         * image : http://221.120.162.112:9006/movie-image/70e5041ac623252c60069328b02eaa0f.jpg
         * title : 广告banner1
         * type : 1
         * url : http://www.933.cc
         */

        private String createTime;
        private int id;
        private String image;
        private String title;
        private String type;
        private String url;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

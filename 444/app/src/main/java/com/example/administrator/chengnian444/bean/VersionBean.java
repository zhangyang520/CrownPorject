package com.example.administrator.chengnian444.bean;

public class VersionBean {

    /**
     * code : 200
     * data : {"createTime":"2018-10-11 13:58:40","id":5,"type":"779android","url":"https://ddxw365.com/android779/app-779-release.apk","version":"app1.0"}
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
         * createTime : 2018-10-11 13:58:40
         * id : 5
         * type : 779android
         * url : https://ddxw365.com/android779/app-779-release.apk
         * version : app1.0
         */

        private String createTime;
        private int id;
        private String type;
        private String url;
        private String version;

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

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}

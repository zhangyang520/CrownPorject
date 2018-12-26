package com.example.administrator.chengnian444.bean;

import java.util.List;

public class MoveTypeBean {

    /**
     * code : 200
     * message : success
     * data : [{"id":1,"type":"中文有碼","typenum":6},{"id":2,"type":"中文無碼","typenum":5},{"id":3,"type":"日本有碼","typenum":7},{"id":4,"type":"日本無碼","typenum":8},{"id":5,"type":"歐美無碼","typenum":4},{"id":6,"type":"三級劇情","typenum":1},{"id":7,"type":"卡通動漫","typenum":2},{"id":8,"type":"偷拍自拍","typenum":3}]
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
         * id : 1
         * type : 中文有碼
         * typenum : 6
         */

        private int id;
        private String type;
        private int typenum;

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

        public int getTypenum() {
            return typenum;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", type='" + type + '\'' +
                    ", typenum=" + typenum +
                    '}';
        }

        public void setTypenum(int typenum) {
            this.typenum = typenum;
        }
    }
}

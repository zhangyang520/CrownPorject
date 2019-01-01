package com.example.administrator.chengnian444.bean;

import java.util.List;

/**
 *  類別返回類型
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date 2019/1/118:00
 */
public class LiveTypesResponseInfo {
    private int code;
    private List<LiveTypesResponse> data;
    private String message;

    public LiveTypesResponseInfo() {
    }

    public LiveTypesResponseInfo(int code, List<LiveTypesResponse> data, String message) {
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

    public List<LiveTypesResponse> getData() {
        return data;
    }

    public void setData(List<LiveTypesResponse> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class LiveTypesResponse{
        private int id; //电影类型id
        private int sort; //电影类型排序
        private String type;//电影类型名称
        private String typeNum;//电影类型编码

        public LiveTypesResponse() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeNum() {
            return typeNum;
        }

        public void setTypeNum(String typeNum) {
            this.typeNum = typeNum;
        }
    }
}

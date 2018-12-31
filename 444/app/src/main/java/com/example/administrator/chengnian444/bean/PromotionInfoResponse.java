package com.example.administrator.chengnian444.bean;

/**
 * 推广信息 封装实体
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date 2018/12/3115:27
 */
public class PromotionInfoResponse {

    private int code;
    private DataBean data;
    private String message;

    /**
     * 内部数据的实体
     */
    public class DataBean{
        String promoteCode; //用户推广码
        String title; //推广码文字信息
        String content; //分享链接文字信息
        String url; //分享下载链接地址

        public DataBean() {
        }

        public String getPromoteCode() {
            return promoteCode;
        }

        public void setPromoteCode(String promoteCode) {
            this.promoteCode = promoteCode;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public PromotionInfoResponse() {
    }

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
}

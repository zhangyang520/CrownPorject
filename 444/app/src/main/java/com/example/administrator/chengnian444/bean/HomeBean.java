package com.example.administrator.chengnian444.bean;

import java.util.List;

public class HomeBean {

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
        private int id;
        private String title;
        private String image;
        private String type;
        private long createTime;
        private String url;
        private List<LiveMoviesBean> liveMovies;

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

        public List<LiveMoviesBean> getLiveMovies() {
            return liveMovies;
        }

        public void setLiveMovies(List<LiveMoviesBean> liveMovies) {
            this.liveMovies = liveMovies;
        }

        public static class LiveMoviesBean {


            private int pageSize;
            private int currentPage;
            private int id;
            private int typenum;
            private String createtime;
            private int watchCount;
            private String name;
            private String url;
            private String bigimage;
            private String smallimage;
            private String type;
            private String actor;
            private String style;
            private String duration;
            private String size;
            private String definition;
            private String resolution;
            private String introduce;
            private Object seriesCode;
            private Object seriesCn;
            private String moveTime;

            public String getMoveTime() {
                return moveTime;
            }

            public void setMoveTime(String moveTime) {
                this.moveTime = moveTime;
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

            public int getTypenum() {
                return typenum;
            }

            public void setTypenum(int typenum) {
                this.typenum = typenum;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public int getWatchCount() {
                return watchCount;
            }

            public void setWatchCount(int watchCount) {
                this.watchCount = watchCount;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getBigimage() {
                return bigimage;
            }

            public void setBigimage(String bigimage) {
                this.bigimage = bigimage;
            }

            public String getSmallimage() {
                return smallimage;
            }

            public void setSmallimage(String smallimage) {
                this.smallimage = smallimage;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getActor() {
                return actor;
            }

            public void setActor(String actor) {
                this.actor = actor;
            }

            public String getStyle() {
                return style;
            }

            public void setStyle(String style) {
                this.style = style;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public String getDefinition() {
                return definition;
            }

            public void setDefinition(String definition) {
                this.definition = definition;
            }

            public String getResolution() {
                return resolution;
            }

            public void setResolution(String resolution) {
                this.resolution = resolution;
            }

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }

            public Object getSeriesCode() {
                return seriesCode;
            }

            public void setSeriesCode(Object seriesCode) {
                this.seriesCode = seriesCode;
            }

            public Object getSeriesCn() {
                return seriesCn;
            }

            public void setSeriesCn(Object seriesCn) {
                this.seriesCn = seriesCn;
            }
        }
    }

}

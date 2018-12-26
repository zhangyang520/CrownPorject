package com.example.administrator.chengnian444.http;

public class Constant {
    public static final String HOST = "app.ldtx365.com";
    //public static final String HOST = "192.168.0.66:8082";
   //public static final String HOST = "203.189.234.147:8082";
    public static final String BASEURL = "http://" + HOST;

    //图片地址
    public static final String IMG ="http://47.244.13.89:8080/images/";

    //首页数据
    public static  final String HOMELIST =BASEURL+"/qsMovie/homeMovies";
    //首页更换
    public static  final String HOMECHANGE =BASEURL+"/qsMovie/homeChangeMovies";

    //获取视频列表
    public static  final String MOVELIST =BASEURL+"/qsMovie/moviesPage";
    //banner
    public static  final String BANNER =BASEURL+"/qsMovie/liveBanners";

    //获取首页广告图
   //type
    public static  final String HOMEPIC =BASEURL+"/qsMovie/liveBannersBytype";

    //获取电影类型列表(暂未用)
    public static  final String MOVETYPE =BASEURL+"/qsMovie/liveTypes";

    //获取影视系列分页列表
    public static  final String MOVEQUALITY =BASEURL+"/qsMovie/liveSeries";

    //获取应用认证授权的token
    public static  final String TOKEN =BASEURL+"/auth";

    //根据type（影视类型）获取一张广告图
    public static  final String GETONEBANNER =BASEURL+"/qsMovie/liveBannersBytype";

    //获取应用列表
    public static  final String YINGYONGLIST =BASEURL+"/qsMovie/liveApplications";
    //获取验证码
    public static  final String GETCODE =BASEURL+"/reder/sendVerificationCode";
    //注册
    public static  final String REGISTER =BASEURL+"/reder/registerReder";
    //登录
    public static  final String LOGIN =BASEURL+"/reder/login";
    //修改密码
    public static  final String CHANGEPASSWORD =BASEURL+"/reder/changePassword";
    //重置密码
    public static  final String UPDATAPASSWORD =BASEURL+"/reder/resetPassword";
    //跳转链接
    public static  final String GOTOCAIPIAO ="http://www.933.cc";

    //分享
    public static  final String SHARE =BASEURL+"/qsMovie/selectByParams";

    //观影次数
    public static  final String WATCHCOUNT =BASEURL+"/qsMovie/updateWatchCount";

    //注册的显示隐藏
    public static  final String REGISTERISOPEN =BASEURL+"/qsMovie/isOpen";
 //获取版本信息
 public static  final String VERSIONBYTYPE =BASEURL+"/qsMovie/versionByType";
}

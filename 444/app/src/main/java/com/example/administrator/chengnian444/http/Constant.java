package com.example.administrator.chengnian444.http;

public class Constant {
    public static final String HOST = "app.ldtx365.com";
    //public static final String HOST = "192.168.0.66:8082";
   //public static final String HOST = "203.189.234.147:8082";
    public static  String BASEURL = "";

    //渠道的id
    public static String channel_id="";
    //平台的id
    public static String platform_id="";

    //图片地址
    public static final String IMG ="http://47.244.13.89:8080/images/";

    //首页数据
    public static   String HOMELIST ="/qsMovie/homeMovies";
    //首页更换
    public static   String HOMECHANGE ="/qsMovie/homeChangeMovies";

    //获取视频列表
    public static   String MOVELIST ="/qsMovie/moviesPage";
    //banner
    public static   String BANNER ="/qsMovie/liveBanners";

    //获取首页广告图
   //type
    public static   String HOMEPIC ="/qsMovie/liveBannersBytype";

    //获取电影类型列表(暂未用)
    public static   String MOVETYPE ="/qsMovie/liveTypes";

    //获取影视系列分页列表
    public static   String MOVEQUALITY ="/qsMovie/liveSeries";

    //获取应用认证授权的token
    public static   String TOKEN ="/auth";

    //根据type（影视类型）获取一张广告图
    public static   String GETONEBANNER ="/qsMovie/liveBannersBytype";

    //获取应用列表
    public static   String YINGYONGLIST ="/qsMovie/liveApplications";
    //获取验证码
    public static   String GETCODE ="/reder/sendVerificationCode";
    //注册
    public static   String REGISTER ="/reder/registerReder";
    //登录
    public static   String LOGIN ="/reder/login";
    //修改密码
    public static   String CHANGEPASSWORD ="/reder/changePassword";
    //重置密码
    public static   String UPDATAPASSWORD ="/reder/resetPassword";
    //跳转链接
    public static   String GOTOCAIPIAO ="http://www.933.cc";

    //分享
    public static   String SHARE ="/qsMovie/selectByParams";

    //观影次数
    public static   String WATCHCOUNT =BASEURL+"/qsMovie/updateWatchCount";

    //注册的显示隐藏
    public static   String REGISTERISOPEN ="/qsMovie/isOpen";
   //获取版本信息
   public static   String VERSIONBYTYPE ="/qsMovie/versionByType";

   //获取 分享推广信息链接
    public static  String PROMOTION_INFO="/reder/getPromoteCode";

    //获取用户的信息
    public static  String ACCOUNT_INFO="/reder/getAccountInfo";

    //设置安全密码
    public static  String setSecurityPassword="/reder/setSecurityPassword";

    //重置安全密码
    public static  String resetSafetyPwd="/reder/resetSecurityPassword";

    //是否绑定安全密码
    public static  String validateSecurityPwd="/reder/validateSecurityPassword";

    //推广码验证的接口
    public static  String  verifyPromoteCode="/reder/verifyPromoteCode";

    //申请提现的验证
    public static  String validateCashWithdraw="/reder/validateCashWithdraw";

    //提交 申请接口
    public static  String cashWithdraw="/reder/cashWithdraw";

    //提現 詳情接口
    public static String withDrawDetail="/reder/withdrawDetail";

    //收益 詳情接口
    public static String incomeDetail="/reder/incomeDetail";
}

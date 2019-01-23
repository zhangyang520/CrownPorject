package com.jh.lottery;

import android.Manifest;

import com.jh.lottery.utils.Tools;

/**
 * Created by sangcixiang on 2018/7/29.
 */

public class AppConfig {

    public static final String APPFILEPATH = Tools.getSDCardPath()+"JGXY/";
    public static final String UPLOADPICPATH = APPFILEPATH + "pic/";
    public static final String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    public static final int REQUEST_CAMERA = 3;


    public static String domain = "http://116.206.92.82:8080/server";
    /** 登入 */
    public static String loginUrl = domain + "/Member/login";
    /** 注册 */
    public static String registerUrl = domain + "/accmemberinfo/searchUtil";
    /** 获取短信验证码 */
    public static String getValidateCodeUrl = domain + "/accmemberinfo/validatePhoneNumberThenSendMessage";
    /** 资讯列表 */
    public static String getNewsListUrl = domain + "/noteLottery/News";
    /** 礼物列表 */
    public static String giftListUrl = domain + "/points/gainPointsGiftList";
    //首页轮播
    public static String homeBannerUrl = domain + "/noteLottery/shufflingURL";
    //查询公告消息
    public static String getAfficheUrl = domain+"/noteLottery/affiche";
    //查询全部彩种开奖信息
    public static String getOpenNumberUrl = domain + "/tempPlanController/getAllLatestOpenNumber";
    //查询单个开奖信息
    public static String getLettoryNumberUrl = domain + "/recentDrawContro/getRecentDrawByColor";
    //大神推荐列表
    public static String getDaShengListUrl = domain + "/according/queryBigGodImg";
    //大神人物列表
    public static String getDashengPersonUrl = domain + "/according/queryBigGod";
    //大神信息和文章列表
    public static String getDsDetailsUrl = domain + "/according/essayUseByid";
    //社区板块列表
    public static String getOtherBlockUrl = domain + "/communitybbs/getBbsAreas";
    //社区滚动图
    public static String getCommunityBannerUrl = domain + "/communitybbs/getBbsPagePics";
    //社区热门板块
    public static String getHotBlockUrl = domain + "/communitybbs/getBbsPlates";
    //获取版块对应的帖子
    public static String getTopicListUrl = domain + "/communitybbs/bbsPlateActivitysByCode";
    //获取帖子回复列表
    public static String getReplyListUrl = domain + "/communitybbs/getReplys";
    //发表回复
    public static String postReplyUrl = domain + "/communitybbs/addReply";
    //兑换礼物
    public static String pointsGiftUrl = domain + "/points/queyPointsGift";
    //兑换记录列表
    public static String historyScoreRecordUrl = domain + "/points/gainScoreRecordList";
    //站内信息
    public static String getLetterListUrl = domain + "/noticeListController/noticeList";
    //更改昵称
    public static String updateNickNameUrl = domain + "/accmemberinfo/greviseUserNickName";
    //更改密码
    public static String updatePasswordUrl = domain + "/accmemberinfo/modifyPassword";
    //上传头像
    public static String uploadImageUrl = domain + "/accmemberinfo/queryUpdateHeadImage";
    //检查更新
    public static String appUpdateUrl = domain + "/fileUpload/isUpdate";















}

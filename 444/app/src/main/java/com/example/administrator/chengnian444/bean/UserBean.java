package com.example.administrator.chengnian444.bean;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 用户的信息的实体
 *
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date 2018/12/2815:10
 */
@Table
public class UserBean {
    @Id
    @NoAutoIncrement
    public int id;

    public String loginToken;

    //余额的总数
    public  double totalBalance;

    //总金额
    public double totalCash;

    //推广人数
    public int extensitionCount=0;

    //一级推广收益
    public double firstPromotionBenfits;

    //二级推广收益
    public double secondPormotionBenfits;

    //三级推广收益
    public double thirdPromotionBenfits;

    //用户名  对应的可能是手机号 或者 后端 分配的账号
    public  String userName;

    //密码
    public String pwd;
    //是否登录
    public boolean isLocalUser;// 是否为本地用户

    //安全密码
    public String  safePwd;

    //安全密码是否绑定
    public boolean isSafeLocked;

    //推广码
    public  String extendistinCode="";

    //对应的推广的二维码图片的路径
    public String zcodeImgUrl;

    //你输入的 推广码 是否已经被绑定
    public boolean isExtendistionState;

    //下载的链接  分享下载链接地址
    public String  url;

}

package com.example.administrator.chengnian444.bean;

import com.lidroid.xutils.db.annotation.Id;
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
    public int id;

    //余额的总数
    public  double totalBalance;

    //昨日的收益
    double yesterdayCash;
    //总金额
    double totalCash;
    //推广人数
    int extensitionCount;
    //用户名
    String userName;
    //密码
    String pwd;
    //是否登录
    public boolean isLocalUser;// 是否为本地用户

    //安全密码
    String  safePwd;
    //推广码
    String extendistinCode;
}

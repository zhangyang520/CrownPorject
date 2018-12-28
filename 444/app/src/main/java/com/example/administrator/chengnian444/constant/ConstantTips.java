package com.example.administrator.chengnian444.constant;

/**
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date 2018/12/2713:28
 */
public class ConstantTips {

    //手机号 格式 不正确的提示语
    public static final String PHONE_REG_FORMATE_ERROR = "手机号必须是11位数字";
    //登录密码输入格式的提示
    public static final String PWD_FORMATE_ERROR="密码必须是6位数字或字母";

    //旧密码的格式错误
    public static final String OLD_PWD_FORMATE_ERROR="旧密码必须是6位数字或字母";

    //新密码的格式错误
    public static final String NEW_PWD_FORMATE_ERROR="新密码必须是6位数字或字母";

    //验证码 的格式错误的提示语
    public static final String VERIFY_CDOE_ERROR="验证码必须是4-6位数字";
    /**
     *
     *   格式化的字符串
     */

    //手机号的 regex的字符串  \d||\w{1,11}  "\\d{11}"
    public static final String PHONE_REGEX = "\\d||\\w{1,11}";

    //登录密码的regex的字符串
    public static final String LOGIN_PWD_REGEX="\\d||\\w{6}";

    //验证码的regex的字符串
    public static final String VERIFY_CODE_REGEX="\\d{4,6}";


    //一些的键 特定字符串
    public static final String isSettingSafePwd="isSettingSafePwd";

    //数据库的版本号
    public static int dbVersion=1;
}

/**
 * Copyright ecVision Limited (c) 2012. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of ecVision Limited.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from Uet or an authorized sublicensor.
 */
package com.example.administrator.chengnian933.utils;

import java.security.MessageDigest;
import java.util.Random;

/**
 * 
 * @Description:MD5加密工具类
 * @ClassName: CipherUtils
 * @author chenw
 * @date 2016年11月14日
 */
public class MD5Utils {

	// 十六进制下数字到字符的映射数组
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	private static MessageDigest md;
	public static final String DEFAULT_PWD = "123123";
	public static final int MIN_LENGTH = 6;
	public static final int MAX_LENGTH = 18;
	public static final int MD5_LENGTH = 32;

	public static void init() throws Exception {
		try {
			// 创建具有指定算法名称的信息摘要
			md = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	/**
	 * 把inputString加密
	 * @throws Exception
	 * 
	 */
	public static String generatePassword(String inputString) throws Exception {
		return encodeByMD5(inputString);
	}

	/**
	 * 验证输入的密码是否正确
	 * 
	 * @param password
	 *            加密后的密码
	 * @param inputString
	 *            输入的字符串
	 * @return 验证结果，TRUE:正确 FALSE:错误
	 * @throws Exception
	 */
	public static boolean validatePassword(String password, String inputString) throws Exception {
		if (password.equals(encodeByMD5(inputString))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 对字符串进行MD5加密
	 * @throws Exception
	 * 
	 */
	private static String encodeByMD5(String originString) throws Exception {
		if (originString != null) {
			// 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
			if (md == null) {
				init();
			}
			byte[] results = md.digest(originString.getBytes());
			// 将得到的字节数组变成字符串返回
			String resultString = byteArrayToHexString(results);
			return resultString.toLowerCase();
		}
		return null;
	}

	/**
	 * 转换字节数组为十六进制字符串
	 * 
	 * @return 十六进制字符串
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 将一个字节转化成十六进制形式的字符串
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static void main(String[] args) {
		try {
			String pwd1 = "用户手机号" + "用户ID" + "ZYHR#66@JR88";


			System.out.println("加密后的密码1:" + MD5Utils.generatePassword(pwd1));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	/**
	 * 
	 * @Description:生成6位纯数字密码
	 * @Title: generatePwd
	 * @author chenw
	 * @date 2016年12月21日
	 * @return    
	 * String
	 */
	public static String generatePwd() {
		String pwd = String.valueOf((int) (Math.random() * 899999 + 100000));
		return pwd;
	}

	/**
	 * 
	 * @Description:获取任意长度的随机数字字符串. 
	 * @Title: generateFixLengthRandomNumberString
	 * @author chenw
	 * @date 2016年12月21日
	 * @param length
	 * @return    
	 * String
	 */
	public static String generateFixLengthRandomNumberString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

}

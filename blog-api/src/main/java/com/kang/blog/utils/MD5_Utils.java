package com.kang.blog.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @ClassName: MD5Util
 * @Description:MD5加密/验证工具类
 */
public class MD5_Utils {

    public static final String saltValue="MSZL#";

    /**
     * @Title: md5Lower
     * @Description: 不加盐值32位小写
     */
    public static String md5Lower(String plainText) {
        String md5 = null;
        if (null != plainText && !"".equals(plainText)) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(plainText.getBytes(StandardCharsets.UTF_8));
                md5 =  new BigInteger(1, md.digest()).toString(16);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return md5;
    }

    /**
     * @Title: md5Lower
     * @Description:加盐值32位小写
     */
    public static String md5Lower(String plainText, String saltValue) {
        String md5 = null;
        if (null != plainText && !"".equals(plainText) && null != saltValue && !"".equals(saltValue)) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(plainText.getBytes(StandardCharsets.UTF_8));
                md.update(saltValue.getBytes(StandardCharsets.UTF_8));
                md5 = new BigInteger(1, md.digest()).toString(16);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return md5;
    }

    /**
     * @Title: md5_16Lower
     * @Description:不加盐值16位小写
     */
    public static String md5_16Lower(String plainText) {
        String md5 = md5Lower(plainText);
        return null==md5?md5:md5.substring(8, 24);
    }

    /**
     * @Title: md5_16Lower
     * @Description:加盐值16位小写
     */
    public static String md5_16Lower(String plainText, String saltValue) {
        String md5 = md5Lower(plainText, saltValue);
        return null==md5?md5:md5.substring(8, 24);
    }

    /**
     * @Title: md5_16Upper
     * @Description:不加盐值16位大写
     */
    public static String md5_16Upper(String plainText) {
        String md5 = md5_16Lower(plainText);
        return null==md5?md5:md5.toUpperCase();
    }

    /**
     * @Title: md5_16Upper
     * @Description:加盐值16位大写
     */
    public static String md5_16Upper(String plainText, String saltValue) {
        String md5 = md5_16Lower(plainText, saltValue);
        return null==md5?md5:md5.toUpperCase();
    }

    /**
     * @Title: md5Upper
     * @Description:不加盐值32位大写
     */
    public static String md5Upper(String plainText) {
        String md5 = md5Lower(plainText);
        return null==md5?md5:md5.toUpperCase();
    }

    /**
     * @Title: md5Upper
     * @Description:加盐值32位大写
     */
    public static String md5Upper(String plainText, String saltValue) {
        String md5 = md5Lower(plainText, saltValue);
        return null==md5?md5:md5.toUpperCase();
    }

    public static void main(String[] args) {
        String plainText = "admin";//明文
        String saltValue = "MSZL#";//盐值

        System.out.println(md5_16Lower(plainText));
        System.out.println(md5_16Upper(plainText));
        System.out.println(md5Lower(plainText));
        System.out.println(md5Upper(plainText));
        System.out.println("====================");
        System.out.println(md5_16Lower(plainText, saltValue));
        System.out.println(md5_16Upper(plainText, saltValue));
        System.out.println(md5Lower(plainText, saltValue));
        System.out.println(md5Upper(plainText, saltValue));
    }

}
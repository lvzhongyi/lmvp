package com.zhuoxiang.watermanager.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lvzhongyi
 *         <p>
 *         description
 *         date 16/6/27
 *         email lvzhongyiforchrome@gmail.com
 *         </p>
 */
public class CheckUtils {
    /**
     * 邮箱格式验证
     *
     * @param email
     */
    public static boolean emailCheck(String email) {
        //电子邮件
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)" +
                "+[a-zA-Z]{2,}$";
        return matcherCheck(check, email);
    }

    /**
     * 手机格式验证
     *
     * @param phone
     * @return
     */
    public static boolean phoneCheck(String phone) {
        String check = "^(1[3,5,7,8][0-9]|15[0,8,9,1,7]|188|187)\\d{8}$";
        return matcherCheck(check, phone);
    }

    /**
     * 身份证号码
     *
     * @param idNumber
     * @return
     */
    public static boolean idNumberCheck(String idNumber) {
        String check = "/^[1-9]\\d{5}[0-9]\\d{1}[1,2]\\d{3}[0-9]\\d{1}[1," +
                "0]\\d[0-9]\\d{1}[0-3]\\d{1}[0-9]\\d{4}[0-9]([0-9]|X|x)$/;";
        return matcherCheck(check, idNumber);
    }

    /**
     * 正则表达式匹配
     *
     * @param check
     * @param value
     * @return
     */
    private static boolean matcherCheck(String check, String value) {
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(value);
        boolean isMatched = matcher.matches();
        return isMatched;
    }
}

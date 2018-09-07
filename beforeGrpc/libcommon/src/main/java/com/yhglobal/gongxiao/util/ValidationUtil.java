package com.yhglobal.gongxiao.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类
 *
 * @author: 陈浩
 * @create: 2018-03-16 19:01
 **/
public class ValidationUtil {
    /**
     * 校验参数列表是否为空,返回false表示有字段不符合规则
     *
     * @param args 需校验的字段
     * @return
     */
    public static boolean isNotEmpty(String... args) {
        for (String arg : args) {
            if (arg == null || "".equals(arg)||"null".equals(arg)) {
                return false;
            }
        }
        return true;
    }

    /**
     *  校验数值
     *  值大于0为true 值小于0为false
     * @param args
     * @return
     */
    public static boolean valueGreaterZero(double... args){
        for (double arg:args){
            if (arg<0){
                return false;
            }
        }
        return true;
    }

    /**
     * 手机号验证
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isMobile( String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }



        /**
         * 校验参数列表是否跟邮箱的格式相同,返回false表示有字段不符合规则
         * @param args 需校验的字段
         * @return
         */
    public static boolean validateEmail(String... args) {
        for (String arg : args) {
            if (arg == null || "".equals(arg)) {
                return false;
            }
            //设置邮箱的匹配规则 ^ 开始
            String rex = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$";
            Pattern p = Pattern.compile(rex);
            Matcher m = p.matcher(arg);
            boolean isMatch = m.matches();
            if (!isMatch){
                return false;
            }
        }
        return true;
    }

    public boolean validateStringLength(String... args){
        for (String arg : args) {

        }
        return true;
    }

    public static void main(String[] args) {
        boolean b = validateEmail("287329409@qq");
        System.out.println(b);
    }

}

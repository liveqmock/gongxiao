package com.yhglobal.gongxiao.utils;

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
            if (arg == null || "".equals(arg)) {
                return false;
            }
        }
        return true;
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

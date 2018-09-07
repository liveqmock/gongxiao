package com.yhglobal.gongxiao.util;

import com.yhglobal.gongxiao.BaseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

public class CommonUtil {

    private static Logger logger  =  LoggerFactory.getLogger(CommonUtil.class);

    public static String getServerMode() {
        String env = System.getProperty("server.mode");
        if(env == null || env.equals(""))    env = System.getenv("server.mode");
        if(env == null || env.equals("")) {
            logger.error("server.mode is not set properly...exit");
            System.exit(1); //强制性设置server.mode参数，不设的话则系统直接退出
        }
        return env;
    }

    /**
     * 获取机器的内网ip
     * @return inetIpAddress
     */
    public static String getInetIpAddress() {
        String inetIpAddress = null;
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {// 遍历所有的网卡
                NetworkInterface ni = netInterfaces.nextElement();
                if (!ni.isUp() || ni.isLoopback() || ni.isVirtual() || ni.isPointToPoint()) {
                    continue;
                }
                Enumeration<InetAddress> addresss = ni.getInetAddresses();
                while (addresss.hasMoreElements()) {
                    InetAddress address = addresss.nextElement();
                    if (address instanceof Inet4Address && !address.getHostAddress().equals("127.0.0.1")) {
                        inetIpAddress = address.getHostAddress();
                        break;
                    }
                }
                if (inetIpAddress != null) {
                    break;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return inetIpAddress;
    }

    /**
     * 字符串按照指定格式转换为日期类型
     *
     * @param dateString 需要转换的字符串
     * @param format     需要转换成的格式
     * @return
     */
    public static Date StringToDateWithFormat(String dateString, String format) {
        if (dateString == null || "".equals(dateString)){
            return new Date();
        }
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将日期转换为字符串
     * @param date 日期
     * @param format 字符串格式
     * @return
     */
    public static String  DateToString(Date date,String format){
        if (date == null){
            return null;
        }
        SimpleDateFormat sf = new SimpleDateFormat(format);
        String dataString = sf.format(date);
        System.out.println(dataString);
        return dataString;
    }
    /**
     * 获取当前时间的毫秒数
     * @return 毫秒数
     */
    public static long getOderNumber(){
        Date date = new Date();
        long time = date.getTime();
        return time;
    }

    /**
     * 将double数字保留三位小数 ("0.000")
     * @param number 需要被转换的数字
     * @param format 保留的格式
     * @return
     */
    public static String doubleToString(double number,String format){
        DecimalFormat df = new DecimalFormat(format);
        String value = df.format(number);
        return value;
    }

    /**
     * 金额用逗号分隔
     * @param money
     * @return
     */
    public static String doubleFormatComma(double money){
        if (Math.abs(money)<1.0 ){
            String s = doubleToString(money, "0.00");
            return s;
        }
        DecimalFormat df = new DecimalFormat("#,###.00");
        String m = df.format(money);
        return m;
    }

    /**
     * 数量逗号分隔
     * @param number 数量
     * @return
     */
    public static String intFormatComma(int number){
        DecimalFormat df = new DecimalFormat("#,###");
        String m = df.format(number);
        return m;
    }

    public static void setCreated(BaseModel model){
        model.setCreatedById(0);
        model.setCreatedByName("system");
        model.setDataVersion(0L);
        model.setCreateTime(new Date());
    }
    public static void main(String[] args){
        String a = "a.b.c";
        String[] split = a.split("\\.");
        System.out.println(split.toString());
    }



}

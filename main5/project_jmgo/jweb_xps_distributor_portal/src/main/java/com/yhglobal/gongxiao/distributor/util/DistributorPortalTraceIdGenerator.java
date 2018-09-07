package com.yhglobal.gongxiao.distributor.util;

public class DistributorPortalTraceIdGenerator {

    /**
     * traceId总长度
     */
    final static int  traceIdLength = 48;

    /**
     * traceId各部分之间的分隔符
     */
    final static String DELIMITER= "_";

    /**
     * URL路径的分隔符
     */
    final static String URL_SEPARATOR = "/";

    /**
     * 每个请求都需要分配traceId
     *
     * 格式: uid_terminalCode_gatewayId_module_padding
     * 样例: 10001_T9_YH001_inventory_getProductInventory_395
     *
     * @param uid  用户id
     * @param terminalCode  终端类型: android/ios/web
     * @param portalId  入口归属的portalId
     * @param servletPath  入口url的路径 注: 不包含contextPath
     * @return traceId
     */
    public static String genTraceId(String uid, int terminalCode, String portalId, String servletPath) {
        StringBuilder builder = new StringBuilder(traceIdLength);
        builder.append(uid);
        builder.append(DELIMITER);
        builder.append("T").append(terminalCode);
        builder.append(DELIMITER);
        builder.append("ADGW").append(portalId);
        builder.append(DELIMITER);

        //根据servletPath获取moduleName和endUrlPath
        int separatorLength = URL_SEPARATOR.length();
        int idx1 = servletPath.indexOf(URL_SEPARATOR, separatorLength);
        int idx2 = servletPath.lastIndexOf(URL_SEPARATOR);
        String moduleName = null;
        if (idx1 > 0) {
            moduleName = servletPath.substring(separatorLength, idx1);
            builder.append(moduleName);
            builder.append(DELIMITER);
        }
        String endUrlPath = null;
        if (idx2 > 0) {
            int remain = traceIdLength - builder.length() - 2;  //保留至少2位给随机数
            if (servletPath.length() - idx2 > remain) {
                endUrlPath = servletPath.substring(idx2 + 1, idx2 + remain);
            } else {
                endUrlPath = servletPath.substring(idx2 + 1);
            }

            builder.append(endUrlPath);
            builder.append(DELIMITER);
        }

        //填充随机数
        int paddingLength = traceIdLength - builder.length();
        int divisor = 1;
        for (int i=0; i<paddingLength; i++) {
            divisor *= 10;
        }
        long millis = System.currentTimeMillis();
        builder.append(millis % divisor);

        return builder.toString();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(genTraceId("10001", 9, "001", "/inventory/product/getProductInventoryddd"));
    }

}

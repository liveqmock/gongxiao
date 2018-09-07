package com.yhglobal.gongxiao.util;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 供销平台Grpc通用工具类
 *
 * @author 葛灿
 */
public class GrpcCommonUtil {

    /**
     * 创建Grpc通用的错误结果对象(GongxiaoRpc.RpcResult)
     *
     * @param code 错误码
     * @param msg  错误信息
     * @return com.yhglobal.gongxiao.microservice.GongxiaoRpc
     */
    public static GongxiaoRpc.RpcResult fail(int code, String msg) {
        GongxiaoRpc.RpcResult response = GongxiaoRpc.RpcResult.newBuilder()
                .setReturnCode(code)
                .setMsg(msg)
                .build();
        return response;
    }

    /**
     * 创建Grpc通用的错误结果对象(GongxiaoRpc.RpcResult)
     *
     * @param errorCode 枚举
     * @return com.yhglobal.gongxiao.microservice.GongxiaoRpc
     */
    public static GongxiaoRpc.RpcResult fail(ErrorCode errorCode) {
        GongxiaoRpc.RpcResult response = GongxiaoRpc.RpcResult.newBuilder()
                .setReturnCode(errorCode.getErrorCode())
                .setMsg(errorCode.getMessage())
                .build();
        return response;
    }

    /**
     * 创建Grpc通用的成功结果对象(GongxiaoRpc.RpcResult)
     *
     * @return com.yhglobal.gongxiao.microservice.GongxiaoRpc
     */
    public static GongxiaoRpc.RpcResult success() {
        GongxiaoRpc.RpcResult response = GongxiaoRpc.RpcResult.newBuilder()
                .setReturnCode(0)
                .setMsg("")
                .build();
        return response;
    }

    /**
     * 安全的获取时间格式化参数
     * 适用于JDK8以上版本
     *
     * @author weizecheng
     * @date 2018/9/4 19:46
     */
    public static String getDateTimeFormat(Long time){
        if (time == 0) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter =DateTimeFormatter.ofPattern(DateUtil.DATE_TIME_FORMAT);
        return dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
    }

    /**
     * java Date类型 => proto int64类型
     *
     * @param javaDate java时间
     * @return long 毫秒值
     */
    public static long getProtoParam(Date javaDate) {
        if (javaDate == null) {
            return 0;
        }
        return javaDate.getTime();
    }

    /**
     * java String类型 => proto string类型
     *
     * @param javaParam 可能为null的字符串
     * @return 如果为null,转为""的字符串
     */
    public static String getProtoParam(String javaParam) {
        if (javaParam == null) {
            return "";
        }
        return javaParam;
    }

    public static boolean getProtoParam(Boolean javaParam) {
        if (javaParam == null) {
            return false;
        }
        return javaParam;
    }

    public static long getProtoParam(Long javaParam) {
        if (javaParam == null) {
            return 0;
        }
        return javaParam;
    }

    public static double getProtoParam(Double javaParam) {
        if (javaParam == null) {
            return 0;
        }
        return javaParam;
    }

    public static byte getProtoParam(Byte javaParam) {
        if (javaParam == null) {
            return 0;
        }
        return javaParam;
    }

    public static int getProtoParam(Integer javaParam) {
        if (javaParam == null) {
            return 0;
        }
        return javaParam;
    }

    /**
     * proto int64类型 => java Date类型
     *
     * @param protoParam proto时间
     * @return 返回Date(protoParam),如果毫秒值为0,转换为null
     */
    public static Date getJavaParam(long protoParam) {
        if (protoParam == 0) {
            return null;
        }
        return new Date(protoParam);
    }

    /**
     * proto string类型 => java String类型
     *
     * @param protoParam proto字符串
     * @return 如果为"",转为null的字符串
     */
    public static String getJavaParam(String protoParam) {
        if (protoParam.equals("")) {
            return null;
        }
        return protoParam;
    }

    /**
     * java List<TraceLog> => proto List<TraceLog>
     *
     * @param javaTraceLogs
     * @return
     */
    public static List<GongxiaoRpc.TraceLog> getProtoTraceLog(List<TraceLog> javaTraceLogs) {
        LinkedList<GongxiaoRpc.TraceLog> protoList = new LinkedList<>();
        for (TraceLog javaTraceLog : javaTraceLogs) {
            GongxiaoRpc.TraceLog protoTraceLog = GongxiaoRpc.TraceLog.newBuilder()
                    .setOpTime(javaTraceLog.getOpTime())
                    .setOpName(javaTraceLog.getOpName())
                    .setOpUid(javaTraceLog.getOpUid())
                    .setContent(javaTraceLog.getContent())
                    .build();
            protoList.add(protoTraceLog);
        }
        return protoList;
    }

    /**
     *返回Grpc需要的参数
     *
     *@auther weizecheng
     *@date $date& &time&
     *@param javaTraceLogs 生产的新日志
     *@return List<GongxiaoRpc.TraceLog>
     */
    public static List<GongxiaoRpc.TraceLog> getProtoTraceLog(String javaTraceLogs) {
        if(javaTraceLogs == null){
            return null;
        }
        return getProtoTraceLog(JSON.parseArray(javaTraceLogs, TraceLog.class));
    }

    /**
     * proto List<TraceLog> => java List<TraceLog>
     *
     * @param protoTraceLogs
     * @return
     */
    public static List<TraceLog> getJavaTraceLog(List<GongxiaoRpc.TraceLog> protoTraceLogs) {
        LinkedList<TraceLog> javaList = new LinkedList<>();
        for (GongxiaoRpc.TraceLog protoTraceLog : protoTraceLogs) {
            TraceLog javaTraceLog = new TraceLog();
            javaTraceLog.setContent(protoTraceLog.getContent());
            javaTraceLog.setOpName(protoTraceLog.getOpName());
            javaTraceLog.setOpTime(protoTraceLog.getOpTime());
            javaTraceLog.setOpUid(protoTraceLog.getOpUid());
            javaList.add(javaTraceLog);
        }
        return javaList;
    }



}

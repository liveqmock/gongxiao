package com.yhglobal.gongxiao.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.TraceLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用tracelog工具类
 */
public class TraceLogUtil {

    /**
     * 追加tracelog
     *
     * @param existingTraceLog  现有tracelog的JSON串 可为null
     * @param newTraceLog  待追加的tracelog
     * @return 添加了newTraceLog后的JSON串
     */
    public static String appendTraceLog(String existingTraceLog, TraceLog newTraceLog) {
        List<TraceLog> traceLogList;
        if (existingTraceLog == null) {
            traceLogList = new ArrayList<>();
        } else {
            traceLogList = JSON.parseObject(existingTraceLog, new TypeReference<List<TraceLog>>(){});
        }
        traceLogList.add(newTraceLog);
        return JSON.toJSONString(traceLogList);
    }

    /**
     * 将json string字段转换为数组
     * @param existingTraceLog
     * @return
     */
    public static List<String> jsonToList(String existingTraceLog){
        List<String > traceLogList = new ArrayList<>();
        if (existingTraceLog == null){
            traceLogList = new ArrayList<>();
        }else {
            traceLogList= JSON.parseObject(existingTraceLog, new TypeReference<List<String>>(){});
        }
        return traceLogList;
    }

}

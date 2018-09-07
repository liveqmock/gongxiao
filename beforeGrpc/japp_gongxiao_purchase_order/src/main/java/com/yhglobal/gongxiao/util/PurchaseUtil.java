package com.yhglobal.gongxiao.util;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yhglobal.gongxiao.purchase.temp.OperateHistoryRecord;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * 业务工具类
 *
 * @author: 陈浩
 * @create: 2018-02-05 15:22
 **/
public class PurchaseUtil {

    /**
     * 向json中添加一条操作记录
     * @param jsonArray json数组
     * @param operateRecord  操作记录
     */
    public static void addOperateInfoJson(JSONArray jsonArray , OperateHistoryRecord operateRecord){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("function",operateRecord.getOperateFunction());
        jsonObject.put("time",operateRecord.getOperateTime());
        jsonObject.put("personId",operateRecord.getOperateId());
        jsonObject.put("personName",operateRecord.getOperateName());

        jsonArray.add(jsonObject);
    }




}

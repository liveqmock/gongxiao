package com.yhglobal.gongxiao.foudation.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yhglobal.gongxiao.foudation.model.ResultData;
import okhttp3.*;

import java.io.IOException;

/**
 * 获取主数据信息的工具类
 *
 * @author: 陈浩
 **/
public class OKHttpUtil {
    public static void main(String[] args) throws IOException {
        String url = "http://apigateway.dev.yhglobal.cn/api/v1/MasterData/GetProjects";
        String data = "{}";
        postJson(url, data);
    }


    public static ResultData postJson(String url, String data) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_JSON, data))
                .build();
        Response response = null;
        ResultData resultData = null;
        try {
            response = client.newCall(request).execute();
            String str = response.body().string();
            resultData = JSON.parseObject(str, ResultData.class);
            String data1 = resultData.getData();
            JSONObject jsonObject = JSON.parseObject(data1);
            if (jsonObject==null || "".equals(jsonObject)){
                return null;
            }
            Object data2 = jsonObject.get("items");
            System.out.println(data2.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.close();
        }
        return resultData;
    }


}

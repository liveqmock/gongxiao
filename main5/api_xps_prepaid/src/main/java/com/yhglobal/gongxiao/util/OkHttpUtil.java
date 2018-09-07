package com.yhglobal.gongxiao.util;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * http请求工具类
 */
public class OkHttpUtil {

    private static OkHttpClient mOkHttpClient;

    static {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(6, TimeUnit.SECONDS)
                .readTimeout(6, TimeUnit.SECONDS)
                .writeTimeout(6, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 该方法不会开启异步线程
     */
    public static Response execute(Request request) throws IOException {
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 开启异步线程访问网络
     */
    public static void asyncEnqueue(Request request, Callback responseCallback){
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    /**
     * 开启异步线程访问网络, 且不在意返回结果（实现空callback）
     */
    public static void asyncEnqueue(Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

        });
    }


}

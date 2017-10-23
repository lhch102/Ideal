package com.lhch.ideal.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 与服务器交互工具类
 * Created by Administrator on 2017/9/29.
 */

public class HttpUtil {

    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}

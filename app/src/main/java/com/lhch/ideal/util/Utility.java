package com.lhch.ideal.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lhch.ideal.db.MovieInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 解析json格式的数据工具类
 * Created by Administrator on 2017/9/29.
 */

public class Utility {

    /**
     * 解析和处理服务器返回的影片信息
     *
     * @param jsonData
     * @return
     */
    public static boolean parseJSONWithGSON(String jsonData) {

        if (!TextUtils.isEmpty(jsonData)) {
            List<MovieInfo> movieInfos = new Gson().fromJson(jsonData,new TypeToken<List<MovieInfo>>(){}.getType());
            for (MovieInfo movieInfo : movieInfos){
                movieInfo.save();
            }
            return true;
        }
        return false;
    }
}

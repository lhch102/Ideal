package com.lhch.ideal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lhch.ideal.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * APP默认打开页面
 * Created by Administrator on 2017/9/27.
 */

public class SplashActivity extends Activity {

    private static final int WHAT_JUMP = 1;

    private ImageView bingPicImg;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT_JUMP) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bingPicImg = (ImageView) findViewById(R.id.iv_logo);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String bingPic = prefs.getString("bing_pic",null);
        if(bingPic != null){
            Glide.with(this).load(bingPic).into(bingPicImg);
        }else {
            loadBingPic();
        }
        handler.sendEmptyMessageDelayed(1, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(WHAT_JUMP);
    }

    /**
     * 加载必应每日一图
     */
    private void loadBingPic(){
        String reuestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(reuestBingPic, new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();;
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        Glide.with(SplashActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
}

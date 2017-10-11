package com.lhch.ideal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 影片详情活动
 * Created by Administrator on 2017/10/8.
 */

public class MovieDetailActivity extends AppCompatActivity {

    public static void actionStart(Context context,int id){
        Intent intent  = new Intent(context,MovieDetailActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = Integer.valueOf(getIntent().getStringExtra("id"));
    }
}

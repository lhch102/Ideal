package com.lhch.ideal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lhch.ideal.adapter.TopListAdapter;
import com.lhch.ideal.common.BaseActivity;
import com.lhch.ideal.db.TopList;
import com.lhch.ideal.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 影片详情活动
 * Created by Administrator on 2017/10/8.
 */

public class MovieDetailActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TopListAdapter topListAdapter;
    private List<TopList> topList;

    public static void actionStart(Context context, String name) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
//        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView iv = (ImageView) findViewById(R.id.iv);
        Glide.with(this).load("http://seopic.699pic.com/photo/50008/2836.jpg_wh1200.jpg").into(iv);

        TextView movieName = (TextView) findViewById(R.id.movie_name);
        Intent intent = getIntent();
        movieName.setText(intent.getStringExtra("name"));
        TextView movieYear = (TextView) findViewById(R.id.movie_year);
        movieYear.setText("2016");
        TextView movieCountry = (TextView) findViewById(R.id.movie_country);
        movieCountry.setText("美国");
        TextView movieType = (TextView) findViewById(R.id.movie_type);
        movieType.setText("动作");
        TextView director = (TextView) findViewById(R.id.director);
        director.setText("导演：昆汀·塔伦蒂诺");
        TextView starring = (TextView) findViewById(R.id.starring);
        starring.setText("主演：施瓦辛格");
        TextView minute = (TextView) findViewById(R.id.minute);
        minute.setText("时长：120 分钟");
        init();
        //榜单评分
        mRecyclerView = (RecyclerView) findViewById(R.id.top_score);
        //设置增加，删除条目的动画，默认动画很炫
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(topListAdapter = new TopListAdapter(topList));
        //swipeRefreshLayout是google推出的刷新的框架，效果也很不错，实现起来也很容易
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                topListAdapter.addData(0);
//            }
//        });
        ButterKnife.bind(this);
    }

    /**
     * 初始化影片信息
     */
    private void initMovieInfo() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:   //返回键的id
                this.finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 初始化页面
     */
    private void init() {
        topList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            TopList dbScoreTop = new TopList("豆瓣评分", (float) 9.2);
            topList.add(dbScoreTop);
            TopList IMDB_Top = new TopList("IMDB评分", (float) 9.0);
            topList.add(IMDB_Top);
            TopList letterboxdTop = new TopList("Letterboxd", (float) 9.0);
            topList.add(letterboxdTop);
        }
    }
}

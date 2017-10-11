package com.lhch.ideal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //初始化影片数据
        /*initMovie();
        MovieAdapter adapter = new MovieAdapter(HomeActivity.this,R.layout.movie_item, movieList);
        ListView listView = (ListView)findViewById(R.id.recycler_view);
        listView.setAdapter(adapter);*/
    }

}

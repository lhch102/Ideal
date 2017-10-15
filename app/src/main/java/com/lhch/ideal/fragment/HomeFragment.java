package com.lhch.ideal.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lhch.ideal.MovieDetailActivity;
import com.lhch.ideal.R;
import com.lhch.ideal.adapter.MovieAdapter;
import com.lhch.ideal.adapter.TopListAdapter;
import com.lhch.ideal.db.MovieInfo;
import com.lhch.ideal.db.TopList;
import com.lhch.ideal.util.HttpUtil;
import com.lhch.ideal.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/9/27.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    private List<MovieInfo> movieList = new ArrayList<>();
    //本周流行
    private List<MovieInfo> movieWeekList = new ArrayList<>();
    //本周新片
    private List<MovieInfo> movieNewList = new ArrayList<>();

    private List<TopList> topList = new ArrayList<>();
    private MovieAdapter adapterWeek;
    private MovieAdapter adapterNew;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        //本周流行
        RecyclerView recyclerWeek = (RecyclerView) view.findViewById(R.id.recycler_view_week);
        recyclerWeek.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        //本周新片
        RecyclerView recyclerNew = (RecyclerView) view.findViewById(R.id.recycler_view_new);
        recyclerNew.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //榜单推荐
        RecyclerView top = (RecyclerView) view.findViewById(R.id.recycler_view_top);
        top.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // 初始化界面控件
        initMovie();
        //本周流行
        adapterWeek = new MovieAdapter(movieWeekList);
        //本周新片
        adapterNew = new MovieAdapter(movieNewList);
        TopListAdapter topListAdapter = new TopListAdapter(topList);
        recyclerWeek.setAdapter(adapterWeek);
        recyclerNew.setAdapter(adapterNew);
        //榜单推荐
        top.setAdapter(topListAdapter);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapterWeek.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MovieInfo movieInfo = movieWeekList.get(position);
                if(movieInfo != null){
                    MovieDetailActivity.actionStart(view.getContext(),movieInfo.getChineseName());
                }
                //根据影片ID查询影片详情
//                queryMovieWeekInfo();
            }
        });

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 初始化页面
     */
    private void initMovie() {

        /*
         * 查询本周流行影片
         * 根据影片热度查询前10条影片
         */
//        movieWeekList = DataSupport.select("id", "chineseName", "images", "grade", "filmYears").order("head desc").limit(10).find(MovieInfo.class);

        /*
         * 查询本周新片
         * 根据影片添加日期查询本周新片
         */
//        movieNewList = DataSupport.select("id", "chineseName", "images", "grade", "filmYears").order("createTime desc").find(MovieInfo.class);

       /* if (movieWeekList.size() < 1 || movieNewList.size() < 1) {
            //TODO 地址待完善
            String address = "http://...";
            //去服务端查询影片信息
            queryFromService(address);
        }*/

        for (int i = 0; i < 1; i++) {

            //本周流行
            MovieInfo xsk = new MovieInfo("肖生克的救赎", R.drawable.p480747492);
            movieWeekList.add(xsk);
            MovieInfo bwbj = new MovieInfo("霸王别姬", R.drawable.p1910813120);
            movieWeekList.add(bwbj);
            MovieInfo zg = new MovieInfo("这个射手不太冷", R.drawable.p511118051);
            movieWeekList.add(zg);
            MovieInfo ag = new MovieInfo("阿甘正传", R.drawable.p510876377);
            movieWeekList.add(ag);

            //本周新片
            MovieInfo ml = new MovieInfo("美丽人生", R.drawable.p510861873);
            movieNewList.add(ml);
            MovieInfo qy = new MovieInfo("千与千寻", R.drawable.p1910830216);
            movieNewList.add(qy);
            MovieInfo xdl = new MovieInfo("辛德勒的名单", R.drawable.p492406163);
            movieNewList.add(xdl);


            //榜单推荐
            TopList dbTop = new TopList("豆瓣榜", R.drawable.p480747492);
            topList.add(dbTop);
            TopList netflixTop = new TopList("Netflix榜单", R.drawable.p1910813120);
            topList.add(netflixTop);
            TopList facebookTop = new TopList("Facebook榜单", R.drawable.p511118051);
            topList.add(facebookTop);

        }
    }

    //OnTouch监听器
    private class PicOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            return true;
        }
    }

    private void queryFromService(String address) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String reponseText = response.body().string();
                boolean result = Utility.parseJSONWithGSON(reponseText);
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                //通过runOnUtiThired()方法回到主线程处理逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}

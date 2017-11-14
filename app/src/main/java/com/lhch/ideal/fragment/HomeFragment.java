package com.lhch.ideal.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.lhch.ideal.util.LogUtil;
import com.lhch.ideal.util.Utility;
import com.lhch.ideal.view.SwipeRefreshView;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 首页碎片
 * Created by Administrator on 2017/9/27.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    //本周流行
    private List<MovieInfo> movieWeekList = new ArrayList<MovieInfo>();
    //本周新片
    private List<MovieInfo> movieNewList = new ArrayList<MovieInfo>();

    private List<TopList> topList;
    private MovieAdapter adapterWeek;
    private MovieAdapter adapterNew;
    private ProgressDialog progressDialog;
    private SwipeRefreshView mSwipeRefreshView;

    private final String TAG = this.getClass().getSimpleName();

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        mSwipeRefreshView = view.findViewById(R.id.swipe_container);
        /*
        设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
        设置下拉进度的背景颜色，默认就是白色的
         */
        mSwipeRefreshView.setProgressBackgroundColorSchemeResource(android.R.color.white);

        // 设置下拉进度的主题颜色
        mSwipeRefreshView.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        mSwipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "onRefresh", Toast.LENGTH_SHORT).show();
                // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //去服务端获取全部影片信息
                        queryFromService();

                        Toast.makeText(getContext(), "刷新了一条数据", Toast.LENGTH_SHORT).show();
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        mSwipeRefreshView.setRefreshing(false);

                    }
                }, 1200);
            }
        });

        //本周流行
        RecyclerView recyclerWeek = (RecyclerView) view.findViewById(R.id.recycler_view_week);
        recyclerWeek.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        //本周新片
        RecyclerView recyclerNew = (RecyclerView) view.findViewById(R.id.recycler_view_new);
        recyclerNew.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //榜单推
        RecyclerView top = (RecyclerView) view.findViewById(R.id.recycler_view_top);
        top.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // 初始化界面控件
        initMovie();
        //本周流行
        adapterWeek = new MovieAdapter(getContext(), movieWeekList);
        //本周新片
        adapterNew = new MovieAdapter(getContext(), movieNewList);
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
                if (movieInfo != null) {
                    //跳转至影片详情
                    MovieDetailActivity.actionStart(view.getContext(), movieInfo.getChineseName());
                }
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

        MovieInfo movieInfo = new MovieInfo();
        movieInfo.updateAll();
        for (int i = 0; i < 1; i++) {

            //查询本周流行影片，根据影片热度查询前10条影片
            movieWeekList = DataSupport.select("id", "chineseName", "images", "grade", "filmYears").order("head desc").limit(10).find(MovieInfo.class);
            //查询本周新片，根据影片添加日期查询本周新片
            movieNewList = DataSupport.select("id", "chineseName", "images", "grade", "filmYears").order("createTime desc").find(MovieInfo.class);

            topList = new ArrayList<>();
            //榜单推荐
            TopList dbTop = new TopList("豆瓣榜", R.drawable.p480747492);
            topList.add(dbTop);
            TopList netflixTop = new TopList("Netflix榜单", R.drawable.p1910813120);
            topList.add(netflixTop);
            TopList facebookTop = new TopList("Facebook榜单", R.drawable.p511118051);
            topList.add(facebookTop);

        }

        if (movieWeekList.size() < 1 || movieNewList.size() < 1) {
            //去服务端获取全部影片信息
            queryFromService();
        }

    }

    private void queryFromService() {
        //获取影片信息的接口
        String address = "http://47.93.235.231:8080/IdealService/api/v1/queryMovieInfo";
        //显示进度对话框
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                LogUtil.d(TAG, body);
                //解析json
                boolean result = Utility.parseJSONWithGSON(body);
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //关闭进度对话框
                            closeProgressDialog();
                            Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            //对异常进行处理
            @Override
            public void onFailure(Call call, IOException e) {
                //通过runOnUtiThired()方法回到主线程处理逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //关闭进度对话框
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

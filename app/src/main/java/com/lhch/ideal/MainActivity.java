package com.lhch.ideal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.lhch.ideal.fragment.HomeFragment;
import com.lhch.ideal.fragment.SearchFragment;
import com.lhch.ideal.fragment.UserFragment;
import com.lhch.ideal.observable.EventBadgeItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Observer {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    Fragment indexFragment;
    private TextBadgeItem badgeMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initBottomBar();
    }

    /**
     * 初始化底部导航栏
     */
    private void initBottomBar() {
        EventBadgeItem.getInstance().register(this);
        switchContent(indexFragment, new HomeFragment());
        /*
         * Mode
         * MODE_DEFAULT 默认模式（如果Item的个数<=3 就会使用MODE_FIXED模式，否则使用MODE_SHIFTING模式）
         * MODE_FIXED 填充模式，未选中的Item会显示文字，没有换挡动画
         * MODE_SHIFTING 换挡模式，未选中的Item不会显示文字，选中的会显示文字。在切换的时候会有一个像换挡的动画
         */
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //未选中的item的图标和文本颜色
        bottomNavigationBar.setInActiveColor(R.color.tab_unchecked);
        //设置选中的颜色
        bottomNavigationBar.setActiveColor(R.color.colorPrimary);
        bottomNavigationBar.setBarBackgroundColor(R.color.white);

        badgeMsg = new TextBadgeItem()
                .setBorderWidth(1)
                .setAnimationDuration(200)
                .setBackgroundColorResource(R.color.colorPrimary)
                .setHideOnSelect(false)//选中时隐藏
                .setText("");

        badgeMsg.hide();//默认隐藏


        List<BottomNavigationItem> items = new ArrayList<>();
        items.add(getItem(R.drawable.ic_home_black_24dp, R.string.title_home));
        items.add(getItem(R.drawable.ic_search_black_24dp, R.string.title_search));
        items.add(getItem(R.drawable.ic_user_black_24dp, R.string.title_user).setBadgeItem(badgeMsg));

        for (BottomNavigationItem item : items) {
            bottomNavigationBar.addItem(item);
        }
        //默认选中第一个
        bottomNavigationBar.setFirstSelectedPosition(0);
        //初始化
        bottomNavigationBar.initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int i) {
                switch (i) {
                    case 0:
                        switchContent(indexFragment, new HomeFragment());
                        break;
                    case 1:
                        switchContent(indexFragment, new SearchFragment());
                        break;
                    case 2:
                        switchContent(indexFragment, new UserFragment());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(int i) {

            }

            @Override
            public void onTabReselected(int i) {

            }
        });

    }

    public void switchContent(Fragment from, Fragment to) {
        indexFragment = to;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (from == null) {
            transaction.add(R.id.content, to).commit();
        } else if (!to.isAdded()) {
            transaction.hide(from).add(R.id.content, to).commit();
        } else {
            transaction.hide(from).show(to).commit();
        }
    }

    public void setTitle (CharSequence title){

    }
    private BottomNavigationItem getItem(int icon, int string) {
        return new BottomNavigationItem(icon, string);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBadgeItem.getInstance().unRegister(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o != null) {
            if ((Integer) o > 0) {
                badgeMsg.show();
                badgeMsg.setText(String.valueOf(o));
            }
        }
    }
}

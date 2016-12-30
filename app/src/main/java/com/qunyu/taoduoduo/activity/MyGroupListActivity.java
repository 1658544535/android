package com.qunyu.taoduoduo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;

import com.andbase.library.view.tabpager.AbTabPagerView;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.fragment.MyGroupListFragment;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/12.
 */

public class MyGroupListActivity extends BaseActivity {
    @BindView(R.id.tabPagerView_mygroup)
    AbTabPagerView tabPageView;
    private ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.mygroup_list_activity);
        ButterKnife.bind(this);
        setTab();
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void init() {
        super.init();
        baseSetText("我的团");
    }

    private void setTab(){
        fragmentList=new ArrayList<>();
        //0-全部1-拼团中2-拼团结束3-拼团失败
        fragmentList.add(MyGroupListFragment.newInstance("0"));
        fragmentList.add(MyGroupListFragment.newInstance("1"));
        fragmentList.add(MyGroupListFragment.newInstance("2"));
        fragmentList.add(MyGroupListFragment.newInstance("3"));

        tabPageView.getViewPager().setOffscreenPageLimit(2);
//
        String tabText[]=new String[]{"全部","拼团中","已拼团","拼团失败"};

        tabPageView.setTabTextSize(12);
        int tabTextColor[]=new int[]{this.getResources().getColor(R.color.text_02),this.getResources().getColor(R.color.text_01)};
        tabPageView.setTabTextColors(tabTextColor);
        tabPageView.setTabBackgroundResource(android.R.color.white);
        tabPageView.getTabLayout().setSelectedTabIndicatorColor(this.getResources().getColor(R.color.text_01));
        tabPageView.getTabLayout().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, com.blankj.utilcode.utils.ScreenUtils.getScreenWidth(this) / 10));
        tabPageView.setTabs(tabText,fragmentList);
    }
}

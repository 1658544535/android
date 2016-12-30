package com.qunyu.taoduoduo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;

import com.andbase.library.view.tabpager.AbTabPagerView;
import com.blankj.utilcode.utils.ScreenUtils;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.fragment.CaijiaFragment;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 * 我的猜价
 */

public class MyCaiJiaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.my_caijia_activity);
        setTabTop();
    }

    private void setTabTop() {
        AbTabPagerView tabPageView= (AbTabPagerView) findViewById(R.id.tabPagerView_caijia);
        List<Fragment> fragmentList=new ArrayList<Fragment>();

        //列表类型(0全部,1进行中,2已结束,3待开奖)
        fragmentList.add(CaijiaFragment.newInstance("0"));
        fragmentList.add(CaijiaFragment.newInstance("1"));
        fragmentList.add(CaijiaFragment.newInstance("2"));
        fragmentList.add(CaijiaFragment.newInstance("3"));

        tabPageView.getViewPager().setOffscreenPageLimit(1);

        String tabText[]=new String[]{"全部","进行中","已结束","待开奖"};
        int tabTextColor[]=new int[]{this.getResources().getColor(R.color.text_02),this.getResources().getColor(R.color.text_01)};
        tabPageView.setTabTextColors(tabTextColor);
        tabPageView.setTabTextSize(12);
        tabPageView.setTabBackgroundResource(android.R.color.white);
        tabPageView.getTabLayout().setSelectedTabIndicatorColor(this.getResources().getColor(R.color.text_01));
        tabPageView.getTabLayout().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenWidth(this)/10));
        tabPageView.setTabs(tabText,fragmentList);


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
        baseSetText("我的猜价");
    }
}

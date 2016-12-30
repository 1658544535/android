package com.qunyu.taoduoduo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.LinearLayout;

import com.andbase.library.view.tabpager.AbTabPagerView;
import com.blankj.utilcode.utils.ScreenUtils;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.fragment.OrdersFragment;
import com.qunyu.taoduoduo.global.AnyEventType;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends BaseActivity {
    String page = "0";

    @Subscribe
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_orders);
        //Bundle bundle = this.getIntent().getExtras();
        page = getIntent().getStringExtra("page");
        //page = bundle.getString("page");
        EventBus.getDefault().register(this);
        baseSetText("我的订单");
        setTabtop();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Subscribe
    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(mr);
        EventBus.getDefault().unregister(this);
//        EventBus.getDefault().unregister(page2);
//        EventBus.getDefault().unregister(page3);EventBus.getDefault().unregister(page4);
//        EventBus.getDefault().unregister(page5);
//        EventBus.getDefault().unregister(page6);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    OrdersFragment page1, page2, page3, page4, page5, page6, mr;

    @Subscribe
    public void onEvent(AnyEventType event) {
        try {
            String[] ary = event.getMsg().split(";");
            if (ary[1].equals("finish")) {
                tabPagerView.getViewPager().setCurrentItem(5);
            }

        } catch (Exception e) {
        }
    }

    AbTabPagerView tabPagerView;

    private void setTabtop() {
        tabPagerView = (AbTabPagerView) findViewById(R.id.tabPagerView);

        //缓存数量
        tabPagerView.getViewPager().setOffscreenPageLimit(6);
        page1 = new OrdersFragment().newInstance("7", "0");
        page2 = new OrdersFragment().newInstance("1", "0");
        page3 = new OrdersFragment().newInstance("2", "0");
        page4 = new OrdersFragment().newInstance("21", "0");
        page5 = new OrdersFragment().newInstance("3", "0");
        page6 = new OrdersFragment().newInstance("4", "0");
//

        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(page1);
        fragmentList.add(page2);
        fragmentList.add(page3);
        fragmentList.add(page4);
        fragmentList.add(page5);
        fragmentList.add(page6);
        String[] tabTexts = new String[]{
                "全部", "待付款", "拼团中", "待发货", "待收货", "已完成"
        };

        tabPagerView.setTabTextSize(12);
        int[] tabTextColors = new int[]{this.getResources().getColor(R.color.text_02), this.getResources().getColor(R.color.text_01)};
        tabPagerView.setTabTextColors(tabTextColors);
        tabPagerView.setTabBackgroundResource(android.R.color.white);
        tabPagerView.getTabLayout().setSelectedTabIndicatorColor(this.getResources().getColor(R.color.text_01));
        tabPagerView.getTabLayout().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenWidth(this) / 10));
        tabPagerView.setTabs(tabTexts, fragmentList);
        tabPagerView.getViewPager().setCurrentItem(Integer.parseInt(page));
    }


}

package com.qunyu.taoduoduo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andbase.library.view.tabpager.AbTabPagerView;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.fragment.CouponsFragment;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */

public class CouponsActivity extends BaseActivity{
    private ArrayList<Fragment> fragmentList;
    public static Handler handler;

    private TextView tv_noUse,tv_over,tv_used;
    private  AbTabPagerView tabPageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.coupon_activity);
        tv_noUse= (TextView) findViewById(R.id.tv_noUse);
        tv_over= (TextView) findViewById(R.id.tv_over);
        tv_used= (TextView) findViewById(R.id.tv_used);
        setTabTop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTabText();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private void setTabText(){

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                tv_noUse.setText("未使用("+new CouponsFragment().notUsedNum+")");
                tv_over.setText("已过期("+new CouponsFragment().overdueNum+")");
                tv_used.setText("已使用("+new CouponsFragment().usedNum+")");

            }
        };
        tabPageView.getViewPager().setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                tv_noUse.setTextColor(CouponsActivity.this.getResources().getColor(R.color.text_02));
                tv_over.setTextColor(CouponsActivity.this.getResources().getColor(R.color.text_02));
                tv_used.setTextColor(CouponsActivity.this.getResources().getColor(R.color.text_02));
                switch (position){
                    case 0:
                        tv_noUse.setTextColor(CouponsActivity.this.getResources().getColor(R.color.text_01));
                        break;
                    case 1:
                        tv_over.setTextColor(CouponsActivity.this.getResources().getColor(R.color.text_01));
                        break;
                    case 2:
                        tv_used.setTextColor(CouponsActivity.this.getResources().getColor(R.color.text_01));
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }
    private void setTabTop() {
        tabPageView= (AbTabPagerView) findViewById(R.id.tabPagerView_coupons);
        fragmentList=new ArrayList<>();
        //类型(1未使用,2已过期,3已使用)
        fragmentList.add(CouponsFragment.newInstance("1"));
        fragmentList.add(CouponsFragment.newInstance("2"));
        fragmentList.add(CouponsFragment.newInstance("3"));

        tabPageView.getViewPager().setOffscreenPageLimit(2);
//        String tabText[]=new String[]{"未使用("+new CouponsFragment().notUsedNum+")"
//                ,"已使用("+new CouponsFragment().notUsedNum+")"
//                ,"已过期("+new CouponsFragment().notUsedNum+")"};
        String tabText[]=new String[]{"","",""};

        tabPageView.setTabTextSize(12);
        int tabTextColor[]=new int[]{this.getResources().getColor(R.color.text_02),this.getResources().getColor(R.color.text_01)};
        tabPageView.setTabTextColors(tabTextColor);
        tabPageView.setTabBackgroundResource(android.R.color.white);
        tabPageView.getTabLayout().setSelectedTabIndicatorColor(this.getResources().getColor(R.color.text_01));
        tabPageView.getTabLayout().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, com.blankj.utilcode.utils.ScreenUtils.getScreenWidth(this) / 10));
        tabPageView.setTabs(tabText,fragmentList);


    }

    @Override
    protected void init() {
        super.init();
        baseSetText("优惠券");

    }
}

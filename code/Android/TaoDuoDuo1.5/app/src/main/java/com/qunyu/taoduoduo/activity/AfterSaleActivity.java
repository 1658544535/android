package com.qunyu.taoduoduo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andbase.library.view.tabpager.AbTabPagerView;
import com.blankj.utilcode.utils.ScreenUtils;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.fragment.AfterSaleFragments;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/28.
 * 售后列表
 */

public class AfterSaleActivity extends BaseActivity {
    @BindView(R.id.tv_all)
    TextView tv_all;
    @BindView(R.id.tv_shenhezhong)
    TextView tv_shenhezhong;
    @BindView(R.id.tv_shengheguo)
    TextView tv_shg;
    @BindView(R.id.tv_shenhebuguo)
    TextView tv_shbg;
    @BindView(R.id.tv_wancheng)
    TextView tv_wc;

    private AbTabPagerView tabPagerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.after_sale_activity);
        ButterKnife.bind(this);
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

    private void setTabText() {

        tabPagerView.getViewPager().setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_all.setTextColor(AfterSaleActivity.this.getResources().getColor(R.color.text_02));
                tv_shenhezhong.setTextColor(AfterSaleActivity.this.getResources().getColor(R.color.text_02));
                tv_shg.setTextColor(AfterSaleActivity.this.getResources().getColor(R.color.text_02));
                tv_shbg.setTextColor(AfterSaleActivity.this.getResources().getColor(R.color.text_02));
                tv_wc.setTextColor(AfterSaleActivity.this.getResources().getColor(R.color.text_02));
                switch (position) {
                    case 0:
                        tv_all.setTextColor(AfterSaleActivity.this.getResources().getColor(R.color.text_01));
                        break;
                    case 1:
                        tv_shenhezhong.setTextColor(AfterSaleActivity.this.getResources().getColor(R.color.text_01));
                        break;
                    case 2:
                        tv_shg.setTextColor(AfterSaleActivity.this.getResources().getColor(R.color.text_01));
                        break;
                    case 3:
                        tv_shbg.setTextColor(AfterSaleActivity.this.getResources().getColor(R.color.text_01));
                        break;
                    case 4:
                        tv_wc.setTextColor(AfterSaleActivity.this.getResources().getColor(R.color.text_01));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    private void setTabTop() {
        tabPagerView = (AbTabPagerView) findViewById(R.id.tabPagerView_aftersale);
        List<Fragment> listFragment = new ArrayList<Fragment>();

        //售后状态(0全部,1审核中,2审核通过,3审核不同,4完成)
        listFragment.add(AfterSaleFragments.newInstance("0"));
        listFragment.add(AfterSaleFragments.newInstance("1"));
        listFragment.add(AfterSaleFragments.newInstance("2"));
        listFragment.add(AfterSaleFragments.newInstance("3"));
        listFragment.add(AfterSaleFragments.newInstance("4"));

        tabPagerView.getViewPager().setOffscreenPageLimit(1);
        String tabText[] = new String[]{"", "", "", "", ""};
        int tabTextColor[] = new int[]{this.getResources().getColor(R.color.text_02), this.getResources().getColor(R.color.text_01)};
        tabPagerView.setTabTextSize(9);
        tabPagerView.setTabTextColors(tabTextColor);
        tabPagerView.setTabBackgroundResource(android.R.color.white);
        tabPagerView.getTabLayout().setSelectedTabIndicatorColor(this.getResources().getColor(R.color.text_01));
        tabPagerView.getTabLayout().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenWidth(this) / 10));
        tabPagerView.setTabs(tabText, listFragment);
    }

    @Override
    protected void init() {
        super.init();
        baseSetText("售后列表");
    }
}

package com.qunyu.taoduoduo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.andbase.library.app.adapter.AbFragmentPagerAdapter;
import com.andbase.library.view.sample.AbViewPager;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.fragment.MyDrawFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/9.
 */

public class MyDrawsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_lottery)
    TextView tv_lottery;
    @BindView(R.id.tv_freelottery)
    TextView tv_freelottery;
    @BindView(R.id.vp_content)
    AbViewPager vp_content;

    private String[] titleList = null;
    MyDrawFragment fragment;
    private ArrayList<Fragment> fragmentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.my_draws_activity);
        ButterKnife.bind(this);
        initFragment();
        tv_lottery.performClick();

    }

    @Override
    protected void init() {
        super.init();
        baseSetText("我的抽奖");
    }

    private void initFragment() {
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(MyDrawFragment.getIntence("1"));
        fragmentArrayList.add(MyDrawFragment.getIntence("2"));
        vp_content.setOffscreenPageLimit(2);
        AbFragmentPagerAdapter adapter = new AbFragmentPagerAdapter(getSupportFragmentManager(), titleList, fragmentArrayList);
        vp_content.setAdapter(adapter);
        vp_content.setPagingEnabled(false);
    }

    private void setBaseText() {
        tv_lottery.setTextColor(getResources().getColor(R.color.text_05));
        tv_lottery.setBackgroundResource(R.color.white);
        tv_freelottery.setTextColor(getResources().getColor(R.color.text_05));
        tv_freelottery.setBackgroundResource(R.color.white);
    }

    @Override
    @OnClick({R.id.tv_lottery, R.id.tv_freelottery})
    public void onClick(View v) {
        setBaseText();
        if (v.getId() == R.id.tv_lottery) {
            tv_lottery.setTextColor(getResources().getColor(R.color.white));
            tv_lottery.setBackgroundResource(R.color.red);
            vp_content.setCurrentItem(0);
        } else if (v.getId() == R.id.tv_freelottery) {
            tv_freelottery.setTextColor(getResources().getColor(R.color.white));
            tv_freelottery.setBackgroundResource(R.color.red);
            vp_content.setCurrentItem(1);
        }
    }
}

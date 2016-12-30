package com.qunyu.taoduoduo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;

import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.fragment.GuideFragment;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends FragmentActivity implements View.OnTouchListener {

    ViewPager viewPager;
    List<Fragment> fragments = new ArrayList<>();
    FragmentPagerAdapter mAdapter;
    boolean next = false;
    float downX;

    private int[] guide = new int[]{
            R.mipmap.guide_01,
            R.mipmap.guide_02

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        for (int i = 0; i < guide.length; i++){
            Bundle bundle = new Bundle();
            bundle.putInt("imagesrc",guide[i]);
            GuideFragment guideFragment = new GuideFragment();
            guideFragment.setArguments(bundle);
            fragments.add(guideFragment);
        }


        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return fragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                // TODO Auto-generated method stub
                return fragments.get(arg0);
            }
        };
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == fragments.size() - 1) {
                    next = true;
                } else {
                    next = false;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setOnTouchListener(this);
        viewPager.setAdapter(mAdapter);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                // 手指按下的X坐标
                downX = event.getX();
                // v.getParent().requestDisallowInterceptTouchEvent(true);
                break;
            }
            case MotionEvent.ACTION_UP: {
                float lastX = event.getX();
                // 抬起的时候的X坐标大于按下的时候就显示上一张图片
                if ((lastX < downX && next) || (lastX == downX && next)) {

                    startActivity(new Intent(GuideActivity.this,
                            TabActivity.class));

                    finish();
                }

            }

            break;
            case MotionEvent.ACTION_CANCEL:
                // v.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return false;
    }
}

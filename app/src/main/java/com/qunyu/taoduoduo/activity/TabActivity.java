package com.qunyu.taoduoduo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.app.adapter.AbFragmentPagerAdapter;
import com.andbase.library.config.AbAppConfig;
import com.andbase.library.view.sample.AbViewPager;
import com.jaeger.library.StatusBarUtil;
import com.meiqia.core.MQMessageManager;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.fragment.HomeFragment;
import com.qunyu.taoduoduo.fragment.MyNoticeFragment;
import com.qunyu.taoduoduo.fragment.PersonalCenterFragment;
import com.qunyu.taoduoduo.fragment.ProductCategoryFragment;
import com.qunyu.taoduoduo.fragment.ZoneFragment;
import com.qunyu.taoduoduo.global.AnyEventType;
import com.qunyu.taoduoduo.global.MyApplication;
import com.qunyu.taoduoduo.receiver.MessageReceiver;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

public class TabActivity extends AppCompatActivity {
    private MyApplication application;
    private TabLayout tabLayout;
    public AbViewPager viewPager = null;


    private String[] titleList = null;
    private int[] icons_press = new int[]{
            R.mipmap.merry_dd_home_on,
            R.mipmap.merry_tab_select_new_icon,
            R.mipmap.merry_dd_ss_on,
            R.mipmap.message_click,
            R.mipmap.merry_dd_wd_on
    };

    private int[] icons = new int[]{
            R.mipmap.merry_dd_home_off,
            R.mipmap.merry_tab_new_icon,
            R.mipmap.merry_dd_ss_off,
            R.mipmap.message,
            R.mipmap.merry_dd_wd_off
    };

    private HomeFragment fragment1;
    private ZoneFragment fragment2;
    //    private CaiJiaGeFragment fragment2;
    private ProductCategoryFragment fragment3;
    private MyNoticeFragment fragment5;
    private PersonalCenterFragment fragment4;

    private ArrayList<Fragment> fragmentList = null;
    MessageReceiver messageReceiver;

    @Subscribe
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TabActivity", "onCreate");
        LogUtil.Log("AbAppConfig.USER_AGENT:" + AbAppConfig.USER_AGENT);
        setContentView(R.layout.activity_tab);
        StatusBarUtil.setTranslucent(TabActivity.this, 55);
        application = (MyApplication) this.getApplication();
        EventBus.getDefault().register(TabActivity.this);
        messageReceiver = new MessageReceiver();
        IntentFilter intentFilter = new IntentFilter();

        //接收新消息广播
        intentFilter.addAction(MQMessageManager.getInstance(TabActivity.this).ACTION_NEW_MESSAGE_RECEIVED);

        // 注册
//        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, intentFilter);
        initFragment();
        //bugly
        //Bugly.init(getApplicationContext(), "f7a2b539e4", true);
    }

    @Subscribe
    @Override
    public void onDestroy() {
        // 取消注册
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);

        EventBus.getDefault().unregister(TabActivity.this);
        try {
            JPushInterface.stopPush(this);
        } catch (Exception c) {
        }

        super.onStop();
    }

    @Subscribe
    public void onEvent(AnyEventType event) {
        if (event.getMsg().equals("home")) {
            setHome();
        }
    }

    public void setHome() {
        viewPager.setCurrentItem(0, false);
        HomeFragment.tabPagerView.getViewPager().setCurrentItem(0);
        for (int i = 0; i < titleList.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            View view = tab.getCustomView();
            ImageView img = (ImageView) view.findViewById(R.id.tab_icon);
            TextView txt = (TextView) view.findViewById(R.id.textView);
            if (i == 0) {
                img.setImageResource(icons_press[i]);
                txt.setTextColor(getApplicationContext().getResources().getColor(R.color.text_01));
            } else {
                img.setImageResource(icons[i]);
                txt.setTextColor(getApplicationContext().getResources().getColor(R.color.text_02));
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            finish();
        } else {
            viewPager.setCurrentItem(0, false);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (viewPager.getCurrentItem() != 0) {
            viewPager.setCurrentItem(0, false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    public void initFragment() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        viewPager = (AbViewPager) findViewById(R.id.view_paper);

        viewPager.setOffscreenPageLimit(4);

        fragmentList = new ArrayList<Fragment>();

        fragment1 = new HomeFragment();
        fragment2 = new ZoneFragment();
        fragment3 = new ProductCategoryFragment();
        fragment4 = new PersonalCenterFragment();
        fragment5 = new MyNoticeFragment();

        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        fragmentList.add(fragment5);
        fragmentList.add(fragment4);


        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        titleList = new String[]{
                "首页", "新品", "搜索", "消息", "个人中心"
        };

        AbFragmentPagerAdapter adapter = new AbFragmentPagerAdapter(getSupportFragmentManager(), titleList, fragmentList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //禁止滑动
        viewPager.setPagingEnabled(false);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < titleList.length; i++) {
                    TabLayout.Tab tab = tabLayout.getTabAt(i);
                    View view = tab.getCustomView();
                    ImageView img = (ImageView) view.findViewById(R.id.tab_icon);
                    TextView txt = (TextView) view.findViewById(R.id.textView);
                    if (position == i) {
                        img.setImageResource(icons_press[i]);
                        txt.setTextColor(getApplicationContext().getResources().getColor(R.color.text_01));
                        if (position == 0) {
                            try {
                                HomeFragment.tabPagerView.getViewPager().setCurrentItem(0, false);
                            } catch (Exception E) {
                            }
                        }
                    } else {
                        img.setImageResource(icons[i]);
                        txt.setTextColor(getApplicationContext().getResources().getColor(R.color.text_02));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //为TabLayout添加tab名称
        for (int i = 0; i < titleList.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(getTabView(i));
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtil.Log(tab.getPosition() + "");
                //v1.5
                if (tab.getPosition() == 3) {
                    if (!UserInfoUtils.isLogin()) {
                        startActivityForResult(new Intent(TabActivity.this, PhoneLoginActivity.class), 0);
                    } else {
                        viewPager.setCurrentItem(3, false);
                    }
                } else {
                    viewPager.setCurrentItem(tab.getPosition(), false);
                }
//                viewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //点击消息tab
                //v1.5
                if (tab.getPosition() == 3) {
                    if (!UserInfoUtils.isLogin()) {
                        startActivityForResult(new Intent(TabActivity.this, PhoneLoginActivity.class), 0);
                    } else {
                        viewPager.setCurrentItem(3, false);
                    }
                }
            }
        });
        viewPager.setCurrentItem(0, false);

    }


    /**
     * 添加getTabView的方法，来进行自定义Tab的布局View
     *
     * @param position
     * @return
     */
    public View getTabView(int position) {
        LayoutInflater mInflater = LayoutInflater.from(this);
        View view = null;

        view = mInflater.inflate(R.layout.item_bottom_tab, null);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(titleList[position]);
        ImageView img = (ImageView) view.findViewById(R.id.tab_icon);
        if (position == 0) {
            img.setImageResource(icons_press[position]);
            tv.setTextColor(getApplicationContext().getResources().getColor(R.color.text_01));
        } else {
            img.setImageResource(icons[position]);
            tv.setTextColor(getApplicationContext().getResources().getColor(R.color.text_02));
        }
        return view;
    }

    public void selectPager(int index) {
        viewPager.setCurrentItem(index, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                viewPager.setCurrentItem(viewPager.getCurrentItem());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        try {
            JPushInterface.resumePush(this);
        } catch (Exception c) {
        }
        //ToastUtils.showShortToast(getBaseContext(), "测试");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}


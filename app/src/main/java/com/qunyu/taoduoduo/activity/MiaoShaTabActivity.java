package com.qunyu.taoduoduo.activity;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.adapter.AbFragmentPagerAdapter;
import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.andbase.library.view.sample.AbViewPager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.GetShareContentBean;
import com.qunyu.taoduoduo.fragment.HomeFragment;
import com.qunyu.taoduoduo.fragment.HrMiaoFragment;
import com.qunyu.taoduoduo.fragment.JrMiaoFragment;
import com.qunyu.taoduoduo.fragment.MrMiaoFragment;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MiaoShaTabActivity extends BaseActivity {
    private TabLayout tabLayout;
    private AbViewPager viewPager = null;


    private String[] titleList = null;
    private int[] icons_press = new int[]{
            R.mipmap.dd_jr_on,
            R.mipmap.dd_mr_on,
            R.mipmap.dd_hr_on,
    };

    private int[] icons = new int[]{
            R.mipmap.dd_jr_off,
            R.mipmap.dd_mr_off,
            R.mipmap.dd_hr_off,
    };

    private JrMiaoFragment fragment1;
    private MrMiaoFragment fragment2;
    private HrMiaoFragment fragment3;
    private ArrayList<Fragment> fragmentList = null;
    ImageView iv_fenxiang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_miao_sha_tab);
        iv_fenxiang = (ImageView) findViewById(R.id.iv_fenxiang);
        iv_fenxiang.setVisibility(View.VISIBLE);
        iv_fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(MiaoShaTabActivity.this, 0.5f);
                getSharePopupWindow();
                // 这里是位置显示方式,在屏幕的左侧
                popupWindow1.showAtLocation(v, Gravity.BOTTOM, 0, 0);
            }
        });
        baseSetText("掌上秒杀");
        initFragment();
        GetShareContentApiGet();
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
    protected void setRight() {
        super.setRight();
    }

    public void initFragment() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        viewPager = (AbViewPager) findViewById(R.id.view_paper);

        viewPager.setOffscreenPageLimit(4);

        fragmentList = new ArrayList<Fragment>();

        fragment1 = new JrMiaoFragment();
        fragment2 = new MrMiaoFragment();
        fragment3 = new HrMiaoFragment();

        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        titleList = new String[]{
                "今日秒杀", "明日预告", "后日预告"
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
                            HomeFragment.tabPagerView.getViewPager().setCurrentItem(0);
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

        viewPager.setCurrentItem(0);

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
        viewPager.setCurrentItem(index);
    }

    GetShareContentBean share;

    private void GetShareContentApiGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("id", "15");
        params.put("type", "15");
        LogUtil.Log(Constant.getShareContentApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(MiaoShaTabActivity.this).get(Constant.getShareContentApi, params, new AbStringHttpResponseListener() {

            @Override
            public void onStart() {
//                AbDialogUtil.showProgressDialog(LoginActivity.this, 0, "正在登录...");
            }

            @Override
            public void onFinish() {
//                AbDialogUtil.removeDialog(LoginActivity.this);
            }

            @Override
            public void onFailure(int statusCode, String content, Throwable error) {
                ToastUtils.showShortToast(MiaoShaTabActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);

                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<GetShareContentBean>>() {
                    }.getType();
                    BaseModel<GetShareContentBean> base = gson.fromJson(content, type);
                    if (base.result != null) {
                        share = base.result;

                        Log.d("++++", "onSuccess: " + content);
                    }
                } else {
                    ToastUtils.showShortToast(MiaoShaTabActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }

    /***
     * 获取PopupWindow实例
     */
    private void getSharePopupWindow() {
        if (null != popupWindow1) {
            popupWindow1.dismiss();
            return;
        } else {
            initSharePopuptWindow();
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    PopupWindow popupWindow1;

    /**
     * 创建PopupWindow
     */
    protected void initSharePopuptWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(
                R.layout.pop_fx, null, false);

        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow1 = new PopupWindow(popupWindow_view,
                PercentRelativeLayout.LayoutParams.MATCH_PARENT, PercentRelativeLayout.LayoutParams.WRAP_CONTENT, true);
        // 设置动画效果
        popupWindow1.setAnimationStyle(R.style.AnimBottom);
        // 点击其他地方消失
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindow1.setBackgroundDrawable(dw);
        backgroundAlpha(MiaoShaTabActivity.this, 0.5f);// 0.0-1.0
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                backgroundAlpha(MiaoShaTabActivity.this, 1f);
            }
        });
        ImageView iv_wb = (ImageView) popupWindow_view.findViewById(R.id.iv_wb);
        iv_wb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // System.out.println("++++++++++++++++");
                UMImage urlImage = new UMImage(MiaoShaTabActivity.this,
                        share.getImage());
                new ShareAction(MiaoShaTabActivity.this)
                        .setPlatform(SHARE_MEDIA.SINA)
                        .withText(share.getContent()).withTitle(share.getTitle()).withMedia(urlImage)
                        .withTargetUrl(share.getUrl())
                        .setCallback(umShareListener).share();

            }
        });
        ImageView iv_wx = (ImageView) popupWindow_view.findViewById(R.id.iv_wx);
        iv_wx.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // System.out.println("++++++++++++++++");
                UMImage urlImage = new UMImage(MiaoShaTabActivity.this,
                        share.getImage());
                new ShareAction(MiaoShaTabActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .withText(share.getContent()).withTitle(share.getTitle()).withMedia(urlImage)
                        .withTargetUrl(share.getUrl())
                        .setCallback(umShareListener).share();

            }
        });
        ImageView iv_pyq = (ImageView) popupWindow_view
                .findViewById(R.id.iv_pyq);
        iv_pyq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // System.out.println("++++++++++++++++");
                UMImage urlImage = new UMImage(MiaoShaTabActivity.this,
                        share.getImage());
                new ShareAction(MiaoShaTabActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withText(share.getContent()).withTitle(share.getTitle()).withMedia(urlImage)
                        .withTargetUrl(share.getUrl())
                        .setCallback(umShareListener).share();

            }
        });
        // popupWindow.setTouchable(true);
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow1 != null && popupWindow1.isShowing()) {
                    popupWindow1.dismiss();
                    popupWindow1 = null;
                }
                return false;
            }
        });
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);

            Toast.makeText(MiaoShaTabActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MiaoShaTabActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(MiaoShaTabActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
}

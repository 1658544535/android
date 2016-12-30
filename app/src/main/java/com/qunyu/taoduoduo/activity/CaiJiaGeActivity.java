package com.qunyu.taoduoduo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.GetShareContentBean;
import com.qunyu.taoduoduo.fragment.CaiJiaGeFragment;
import com.qunyu.taoduoduo.fragment.CaiJiaGeWanQiFragment;
import com.qunyu.taoduoduo.fragment.HomeFragment;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.ButterKnife;

public class CaiJiaGeActivity extends BaseActivity {

    //    @BindView(R.id.lv_t)
//    PullableListView lvT;
//    @BindView(R.id.refresh_view)
//    PullToRefreshLayout refreshView;
    int pageNo = 1;
    ImageView iv_fenxiang;
    private CaiJiaGeFragment fragment1;
    private CaiJiaGeWanQiFragment fragment2;
    private TabLayout tabLayout;
    private AbViewPager viewPager = null;


    private String[] titleList = null;
    private int[] icons_press = new int[]{
            R.mipmap.prize_ing_select_icon,
            R.mipmap.chakan_wanqi_select_icon,
    };

    private int[] icons = new int[]{
            R.mipmap.prize_ing_icon,
            R.mipmap.chakan_wanqi_icon,
    };
    private ArrayList<Fragment> fragmentList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_cai_jia_ge);
//        GuessBannerGet();
        ButterKnife.bind(this);
        baseSetText("猜价格赢好礼");
        initFragment();
//        handler.postDelayed(runnable, 1000);
//        refreshView.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
//            @Override
//            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
//                new Handler() {
//                    @Override
//                    public void handleMessage(Message msg) {
//                        // 千万别忘了告诉控件刷新完毕了哦！
//                        pageNo = 1;
//                        GuessActivityGet();
//
//                    }
//                }.sendEmptyMessageDelayed(0, 800);
//            }
//
//            @Override
//            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
//                new Handler() {
//                    @Override
//                    public void handleMessage(Message msg) {
//                        // 千万别忘了告诉控件加载完毕了哦！
//                        pageNo++;
//                        GuessActivityGet();
//                    }
//                }.sendEmptyMessageDelayed(0, 800);
//            }
//        });
//        refreshView.setPullDownEnable(false);
//        lvT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                    long arg3) {
//                // TODO Auto-generated method stub
//                Bundle bundle = new Bundle();
//                bundle.putString("activityId", date.get(arg2 - 1).getActivityId() + "");
//                bundle.putString("productId", date.get(arg2 - 1).getProductId() + "");
//                BaseUtil.ToAcb(CaiJiaGeActivity.this, CaiGoodsDetailActivity.class, bundle);
//            }
//
//        });
        GetShareContentApiGet();
        iv_fenxiang = (ImageView) findViewById(R.id.iv_fenxiang);
        iv_fenxiang.setVisibility(View.VISIBLE);
        iv_fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundAlpha(CaiJiaGeActivity.this, 0.5f);
                getPopupWindow();
                // 这里是位置显示方式,在屏幕的左侧
                popupWindow1.showAtLocation(view, Gravity.BOTTOM, 0, 0);
//                BaseUtil.ToAc(getActivity(), ZhuanTiFengLeiActivity.class);
            }
        });
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void initFragment() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        viewPager = (AbViewPager) findViewById(R.id.view_paper);

        viewPager.setOffscreenPageLimit(4);

        fragmentList = new ArrayList<Fragment>();

        fragment1 = new CaiJiaGeFragment();
        fragment2 = new CaiJiaGeWanQiFragment();

        fragmentList.add(fragment1);
        fragmentList.add(fragment2);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        titleList = new String[]{
                "正在进行中", "查看往期"
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        handler.removeCallbacksAndMessages(null);
    }

    GetShareContentBean share;

    private void GetShareContentApiGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("id", "10");
        params.put("type", "10");
        LogUtil.Log(Constant.getShareContentApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(CaiJiaGeActivity.this).get(Constant.getShareContentApi, params, new AbStringHttpResponseListener() {

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
                ToastUtils.showShortToast(CaiJiaGeActivity.this, "网络异常，数据加载失败");
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
//                        Log.d("++++", "onSuccess: " + networkImages.get(1).getImage());
                    }
                } else {
                    ToastUtils.showShortToast(CaiJiaGeActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }

    Handler handler = new Handler();
//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
////            date = GrtJs(date);
//            if (date != null) {
//                for (int i = 0; i < date.size(); i++) {
//                    try {
//                        long hour = Long.parseLong(date.get(i).getHour());
//                        long min = Long.parseLong(date.get(i).getMin());
//                        long s = Long.parseLong(date.get(i).getSs());
//                        if (hour != 0 || min != 0 || s != 0) {
//                            s--;
//                            if (s < 0) {
//                                min--;
//                                s = 59;
//                                if (min < 0) {
//                                    min = 59;
//                                    hour--;
//                                    if (hour < 0) {
//                                        // 倒计时结束
//                                        hour = 0;
//                                    }
//                                }
//                            }
//                            date.get(i).setHour(BaseUtil.Bl(hour));
//                            date.get(i).setMin(BaseUtil.Bl(min));
//                            date.get(i).setSs(BaseUtil.Bl(s));
//                        } else {
//                            date.remove(i);
//                        }
//
//                    } catch (Exception e) {
//                    }
//                }
//            }
//            try {
//                adapter.notifyDataSetChanged();
//            } catch (Exception e) {
//            }
//            handler.postDelayed(this, 1000);
//
//        }
//    };

//    private ArrayList<GuessActivityBean> GrtJs(ArrayList<GuessActivityBean> date) {
//        if (date != null) {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            for (int i = 0; i < date.size(); i++) {
//                try {
//                    Date parse1 = dateFormat.parse(date.get(i).getEndTime());
//                    Date parse = dateFormat.parse(date.get(i).getNowTime());
//                    long diff = parse1.getTime() - parse.getTime();
//                    long day = diff / (24 * 60 * 60 * 1000);
//                    long hour = (diff - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60) + (day * 24);
//                    long hours = (diff - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
//                    long min = ((diff / (60 * 1000)) - day * 24 * 60 - hours * 60);
//                    long s = (diff / 1000 - day * 24 * 60 * 60 - hours * 60 * 60 - min * 60);
//                    if (hour != 0 || min != 0 || s != 0) {
//                        s--;
//                        if (s < 0) {
//                            min--;
//                            s = 59;
//                            if (min < 0) {
//                                min = 59;
//                                hour--;
//                                if (hour < 0) {
//                                    // 倒计时结束
//                                    hour = 0;
//                                }
//                            }
//                        }
//                    }
//                    date.get(i).setHour(BaseUtil.Bl(hour));
//                    date.get(i).setMin(BaseUtil.Bl(min));
//                    date.get(i).setSs(BaseUtil.Bl(s));
//
//                } catch (Exception e) {
//                }
//            }
//            return date;
//
//        }
//        return date;
//    }
//
//    private void GuessBannerGet() {
//        AbHttpUtil.getInstance(CaiJiaGeActivity.this).get(Constant.guessBannerApi, null, new AbStringHttpResponseListener() {
//
//            @Override
//            public void onStart() {
////                AbDialogUtil.showProgressDialog(LoginActivity.this, 0, "正在登录...");
//            }
//
//            @Override
//            public void onFinish() {
////                AbDialogUtil.removeDialog(LoginActivity.this);
//            }
//
//            @Override
//            public void onFailure(int statusCode, String content, Throwable error) {
//                ToastUtils.showShortToast(CaiJiaGeActivity.this, "网络异常，数据加载失败");
//            }
//
//            @Override
//            public void onSuccess(int statusCode, String content) {
//                AbResult result = new AbResult(content);
//                if (result.getResultCode() == AbResult.RESULT_OK) {
//                    Gson gson = new Gson();
//                    Type type = new TypeToken<BaseModel<GuessBannerBean>>() {
//                    }.getType();
//                    BaseModel<GuessBannerBean> base = gson.fromJson(content, type);
//                    View vHead = View.inflate(CaiJiaGeActivity.this, R.layout.fragment_cai_jia_ge_list_head, null);
//                    ImageView imageView1 = (ImageView) vHead.findViewById(R.id.banners);
//                    imageView1.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                        }
//                    });
//                    Glide.with(CaiJiaGeActivity.this).load(base.result.getBanner()).into(imageView1);
//                    lvT.addHeaderView(vHead);
////                    GuessActivityGet();
//                } else {
//                    ToastUtils.showShortToast(CaiJiaGeActivity.this, result.getResultMessage());
//                }
//
//            }
//        });
//
//    }
//
//    GuessActivityApiAdapter adapter;
//    ArrayList<GuessActivityBean> date;
//
//    private void GuessActivityGet() {
//        AbRequestParams params = new AbRequestParams();
//        params.put("pageNo", pageNo);
//        AbHttpUtil.getInstance(CaiJiaGeActivity.this).get(Constant.guessActivityApi, params, new AbStringHttpResponseListener() {
//
//            @Override
//            public void onStart() {
////                AbDialogUtil.showProgressDialog(LoginActivity.this, 0, "正在登录...");
//            }
//
//            @Override
//            public void onFinish() {
////                AbDialogUtil.removeDialog(LoginActivity.this);
//                try {
//                    refreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
//                    refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
//                } catch (Exception e) {
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, String content, Throwable error) {
//                ToastUtils.showShortToast(CaiJiaGeActivity.this, "网络异常，数据加载失败");
//            }
//
//            @Override
//            public void onSuccess(int statusCode, String content) {
//                AbResult result = new AbResult(content);
//                if (result.getResultCode() == AbResult.RESULT_OK) {
//                    Gson gson = new Gson();
//                    Type type = new TypeToken<BaseModel<ArrayList<GuessActivityBean>>>() {
//                    }.getType();
//                    BaseModel<ArrayList<GuessActivityBean>> base = gson.fromJson(content, type);
//                    if (base.result != null) {
//                        if (pageNo == 1) {
//                            date = GrtJs(base.result);
//                            adapter = new GuessActivityApiAdapter(CaiJiaGeActivity.this, date);
//                            lvT.setAdapter(adapter);
//                        } else {
//                            date.addAll(GrtJs(base.result));
//                            adapter.notifyDataSetChanged();
//                        }
//                    } else {
//                        ToastUtils.showShortToast(CaiJiaGeActivity.this, base.error_msg);
//                    }
//                } else {
//                    ToastUtils.showShortToast(CaiJiaGeActivity.this, result.getResultMessage());
//                }
//
//            }
//        });
//
//    }

    PopupWindow popupWindow1;

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

    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow() {
        if (null != popupWindow1) {
            popupWindow1.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }

    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(
                R.layout.pop_fx, null, false);

        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow1 = new PopupWindow(popupWindow_view,
                PercentRelativeLayout.LayoutParams.MATCH_PARENT, PercentRelativeLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow1.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置动画效果
        popupWindow1.setAnimationStyle(R.style.AnimBottom);
        // 点击其他地方消失
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindow1.setBackgroundDrawable(dw);
        backgroundAlpha(CaiJiaGeActivity.this, 0.5f);// 0.0-1.0
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                backgroundAlpha(CaiJiaGeActivity.this, 1f);
            }
        });
        ImageView iv_wb = (ImageView) popupWindow_view.findViewById(R.id.iv_wb);
        iv_wb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // System.out.println("++++++++++++++++");
                UMImage urlImage = new UMImage(CaiJiaGeActivity.this,
                        share.getImage());
                new ShareAction(CaiJiaGeActivity.this)
                        .setPlatform(SHARE_MEDIA.SINA)
                        .withText(BaseUtil.Kl(share.getTitle())).withTitle(share.getTitle()).withMedia(urlImage)
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
                UMImage urlImage = new UMImage(CaiJiaGeActivity.this,
                        share.getImage());
                new ShareAction(CaiJiaGeActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .withText(BaseUtil.Kl(share.getContent())).withTitle(share.getTitle()).withMedia(urlImage)
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
                UMImage urlImage = new UMImage(CaiJiaGeActivity.this,
                        share.getImage());
                new ShareAction(CaiJiaGeActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withText(BaseUtil.Kl(share.getContent())).withTitle(share.getTitle()).withMedia(urlImage)
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
            com.umeng.socialize.utils.Log.d("plat", "platform" + platform);

            Toast.makeText(CaiJiaGeActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(CaiJiaGeActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                com.umeng.socialize.utils.Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(CaiJiaGeActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
//        pageNo = 1;
//        GuessActivityGet();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}

package com.qunyu.taoduoduo.activity;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.andbase.library.view.tabpager.AbTabPagerView;
import com.blankj.utilcode.utils.ScreenUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.GetShareContentBean;
import com.qunyu.taoduoduo.bean.SpecialTypeBean;
import com.qunyu.taoduoduo.fragment.ZhuanTiTypeFragment;
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
import java.util.List;

public class ZhuanTiFengLeiActivity extends BaseActivity implements View.OnClickListener {
    ImageView iv_fenxiang;//分享
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_zhuan_ti_feng_lei);
        id = getIntent().getStringExtra("id");
        LogUtil.Log("id" + id);
        ProductTypeGet();
        GetShareContentApiGet();
        iv_fenxiang = (ImageView) findViewById(R.id.iv_fenxiang);
        iv_fenxiang.setVisibility(View.VISIBLE);
        iv_fenxiang.setOnClickListener(this);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void ProductTypeGet() {
        AbHttpUtil.getInstance(ZhuanTiFengLeiActivity.this).get(Constant.specialTypeApi, new AbStringHttpResponseListener() {

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
                ToastUtils.showShortToast(ZhuanTiFengLeiActivity.this, "网络异常，数据加载失败");
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<ArrayList<SpecialTypeBean>>>() {
                    }.getType();
                    BaseModel<ArrayList<SpecialTypeBean>> base = gson.fromJson(content, type);
                    if (base.result != null) {
                        setTabtop(base.result);
                    }
                } else {
                    ToastUtils.showShortToast(ZhuanTiFengLeiActivity.this, "网络异常，数据加载失败");
                }

            }
        });

    }

    GetShareContentBean share;

    private void GetShareContentApiGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("id", "12");
        params.put("type", "12");
        LogUtil.Log(Constant.getShareContentApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(this).get(Constant.getShareContentApi, params, new AbStringHttpResponseListener() {

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
                ToastUtils.showShortToast(ZhuanTiFengLeiActivity.this, "网络异常，数据加载失败");
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
                    ToastUtils.showShortToast(ZhuanTiFengLeiActivity.this, "网络异常，数据加载失败");
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fenxiang:
                backgroundAlpha(ZhuanTiFengLeiActivity.this, 0.5f);
                getPopupWindow();
                // 这里是位置显示方式,在屏幕的左侧
                popupWindow1.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                break;
        }
    }

    private TabLayout tabLayout;
    private AbTabPagerView tabPagerView = null;
    String[] tabTexts;

    private void setTabtop(final ArrayList<SpecialTypeBean> date) {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabPagerView = (AbTabPagerView) findViewById(R.id.tabPagerView);
        //缓存数量
        tabPagerView.getViewPager().setOffscreenPageLimit(15);
        List<Fragment> fragmentList = new ArrayList<Fragment>();
//        HomesFragment page1 = new HomesFragment();
//        fragmentList.add(page1);
        List<String> list = new ArrayList<String>();
//        list.add("首页");
        for (int i = 0; i < date.size(); i++) {
            ZhuanTiTypeFragment page = new ZhuanTiTypeFragment().newInstance(date.get(i).getId(), date.get(i).getName());
            fragmentList.add(page);
            list.add(date.get(i).getName());
        }
        tabTexts = list.toArray(new String[list.size()]);
        tabPagerView.setTabTextSize(12);
        tabPagerView.getTabLayout().setVisibility(View.GONE);
        int[] tabTextColors = new int[]{this.getResources().getColor(R.color.text_02), this.getResources().getColor(R.color.text_01)};
        tabPagerView.setTabTextColors(tabTextColors);
        tabPagerView.setTabBackgroundResource(android.R.color.white);
        tabPagerView.getTabLayout().setSelectedTabIndicatorColor(this.getResources().getColor(R.color.text_01));
        tabPagerView.getTabLayout().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenWidth(this) / 10));
        tabPagerView.setTabs(tabTexts, fragmentList);
        tabLayout.setupWithViewPager(tabPagerView.getViewPager());
        baseSetText(date.get(0).getName());
        for (int i = 0; i < date.size(); i++) {
            if (id.equals(date.get(i).getId())) {
                tabPagerView.getViewPager().setCurrentItem(i);
                baseSetText(date.get(i).getName());
            }
        }
        tabPagerView.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                baseSetText(date.get(position).getName());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
        tv.setText(tabTexts[position]);
        ImageView img = (ImageView) view.findViewById(R.id.tab_icon);
        if (position == 0) {
//            img.setImageResource(icons_press[position]);
            tv.setTextColor(this.getResources().getColor(R.color.text_01));
        } else {
//            img.setImageResource(icons[position]);
            tv.setTextColor(this.getResources().getColor(R.color.text_02));
        }
        return view;
    }

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
        // 设置动画效果
        popupWindow1.setAnimationStyle(R.style.AnimBottom);
        // 点击其他地方消失
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindow1.setBackgroundDrawable(dw);
        backgroundAlpha(ZhuanTiFengLeiActivity.this, 0.5f);// 0.0-1.0
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                backgroundAlpha(ZhuanTiFengLeiActivity.this, 1f);
            }
        });
        ImageView iv_wb = (ImageView) popupWindow_view.findViewById(R.id.iv_wb);
        iv_wb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // System.out.println("++++++++++++++++");
                UMImage urlImage = new UMImage(ZhuanTiFengLeiActivity.this,
                        share.getImage());
                new ShareAction(ZhuanTiFengLeiActivity.this)
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
                UMImage urlImage = new UMImage(ZhuanTiFengLeiActivity.this,
                        share.getImage());
                new ShareAction(ZhuanTiFengLeiActivity.this)
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
                UMImage urlImage = new UMImage(ZhuanTiFengLeiActivity.this,
                        share.getImage());
                new ShareAction(ZhuanTiFengLeiActivity.this)
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
            com.umeng.socialize.utils.Log.d("plat", "platform" + platform);

            Toast.makeText(ZhuanTiFengLeiActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ZhuanTiFengLeiActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                com.umeng.socialize.utils.Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ZhuanTiFengLeiActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}

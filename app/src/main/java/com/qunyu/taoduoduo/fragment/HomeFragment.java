package com.qunyu.taoduoduo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.andbase.library.view.tabpager.AbTabPagerView;
import com.blankj.utilcode.utils.ScreenUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.EventActivity;
import com.qunyu.taoduoduo.activity.StartActivity;
import com.qunyu.taoduoduo.activity.TuanMianActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.CheckGroupFreeBean;
import com.qunyu.taoduoduo.bean.ProductTypeBean;
import com.qunyu.taoduoduo.global.AnyEventType;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.global.MyApplication;
import com.qunyu.taoduoduo.global.Untool;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;

import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {
    String TAG = "HomeFragment";
    MyApplication application;
    View view = null;
    private TabLayout tabLayout;
    public static AbTabPagerView tabPagerView = null;
    String[] tabTexts;
    CheckGroupFreeBean date = null;
    ImageView iv_hongbao;//红包按钮
    TextView shibai;

    @Subscribe
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_home_tab_top, container, false);
        application = (MyApplication) this.getActivity().getApplication();
        shibai = (TextView) view.findViewById(R.id.shibai);
        if (StartActivity.list == null) {
            ProductTypeGet();
        } else {
            list = StartActivity.list;
            setTabtop(list);
            CheckGroupFreeGet();
        }
        iv_hongbao = (ImageView) view.findViewById(R.id.iv_hongbao);
        iv_hongbao.setOnClickListener(this);
        shibai.setOnClickListener(this);

        //第一次登录时打开红包
        if (UserInfoUtils.getIsFirst()) {
            iv_hongbao.performClick();
            UserInfoUtils.setFirst(false);
        }
        return view;
    }


    @Subscribe
    public void onEvent(AnyEventType event) {
        if (event.getMsg().equals("home")) {
            try {
                tabPagerView.getViewPager().setCurrentItem(0);
            } catch (Exception e) {
            }
        }
    }

    public static ArrayList<ProductTypeBean> list;

    private void ProductTypeGet() {
        LogUtil.Log(Constant.productType);
        AbHttpUtil.getInstance(getActivity()).get(Constant.productType, new AbStringHttpResponseListener() {

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
                ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                Constant.TYPE = content;
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<ArrayList<ProductTypeBean>>>() {
                    }.getType();
                    BaseModel<ArrayList<ProductTypeBean>> base = gson.fromJson(content, type);
                    Log.d(TAG, "onSuccess: " + content);
                    if (base.result != null) {
                        list = base.result;
                        setTabtop(base.result);

                        CheckGroupFreeGet();

                    }
                } else {
                    ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }

    private void CheckGroupFreeGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("userId", Untool.getUid());
        AbHttpUtil.getInstance(getActivity()).get(Constant.checkGroupFreeApi, params, new AbStringHttpResponseListener() {

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
                ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<CheckGroupFreeBean>>() {
                    }.getType();
                    BaseModel<CheckGroupFreeBean> base = gson.fromJson(content, type);
                    Log.d(TAG, "onSuccess: " + content);
                    if (base.result != null) {
                        date = base.result;
                        if (base.result.getIsStart() == 1) {
                            try {
                                initPopuptWindow(date);
                                backgroundAlpha(getActivity(), 0.5f);
                                getPopupWindow();
                                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                            } catch (Exception e) {
                            }
                        }
                    }
                } else {
                    ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }

    PopupWindow popupWindow;

    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow(CheckGroupFreeBean date) {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getActivity().getLayoutInflater().inflate(
                R.layout.dialog_mt, null, false);

        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 设置动画效果
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        // 点击其他地方消失
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        backgroundAlpha(getActivity(), 0.5f);// 0.0-1.0
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                backgroundAlpha(getActivity(), 1f);
            }
        });
        try {
            TextView textView7 = (TextView) popupWindow_view.findViewById(R.id.textView7);
            textView7.setText("有效期：" + date.getBeginTime() + "-" + date.getEndTime());
            TextView textView8 = (TextView) popupWindow_view.findViewById(R.id.textView8);
            textView8.setText("￥" + "0");
        } catch (Exception e) {
            LogUtil.Log(e + "");
        }
        TextView textView6 = (TextView) popupWindow_view.findViewById(R.id.textView6);
        textView6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    popupWindow.dismiss();
                    popupWindow = null;
                } catch (Exception e) {
                    LogUtil.Log(e + "");
                }
            }
        });
        ImageView imageView4 = (ImageView) popupWindow_view.findViewById(R.id.imageView4);
        imageView4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    BaseUtil.ToAc(getActivity(), TuanMianActivity.class);
                    popupWindow.dismiss();
                } catch (Exception e) {
                    LogUtil.Log(e + "");
                }
            }
        });

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

    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow(date);
        }
    }

    private void setTabtop(ArrayList<ProductTypeBean> date) {
        shibai.setVisibility(View.GONE);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabPagerView = (AbTabPagerView) view.findViewById(R.id.tabPagerView);
        //缓存数量
        tabPagerView.getViewPager().setOffscreenPageLimit(15);
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        HomesFragment page1 = new HomesFragment();
        fragmentList.add(page1);
        List<String> list = new ArrayList<String>();
        list.add("首 页");
        for (int i = 0; i < date.size(); i++) {
            HomeTypeFragment page = new HomeTypeFragment().newInstance(date.get(i).oneId, "T");
            fragmentList.add(page);
//            if (i == 0) {
//                list.add("丑");
//            } else {
            list.add(date.get(i).oneName);
//            }
        }

        tabTexts = list.toArray(new String[list.size()]);
        tabPagerView.setTabTextSize(12);
        tabPagerView.getTabLayout().setVisibility(View.GONE);
        int[] tabTextColors = new int[]{getActivity().getResources().getColor(R.color.text_02), getActivity().getResources().getColor(R.color.text_01)};
        tabPagerView.setTabTextColors(tabTextColors);
        tabPagerView.setTabBackgroundResource(android.R.color.white);
        tabPagerView.getTabLayout().setSelectedTabIndicatorColor(getActivity().getResources().getColor(R.color.text_01));
        tabPagerView.getTabLayout().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenWidth(getActivity()) / 10));
        tabPagerView.setTabs(tabTexts, fragmentList);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(tabPagerView.getViewPager());
        TextView tv = new TextView(getActivity());
        ViewGroup.LayoutParams mParams1 = new ViewGroup.LayoutParams(ScreenUtils.getScreenWidth(getActivity()) / 12, ScreenUtils.getScreenWidth(getActivity()) / 18);
        tv.setLayoutParams(mParams1);
        tv.setGravity(Gravity.CENTER);
        tv.setText("首页");
        tabLayout.getTabAt(0).setCustomView(tv);
        try {
            for (int i = 0; i < date.size(); i++) {
                if (date.get(i).oneFlag.equals("1")) {
                    ImageView im = new ImageView(getActivity());
                    ViewGroup.LayoutParams mParams = new ViewGroup.LayoutParams(ScreenUtils.getScreenWidth(getActivity()) / 8, ScreenUtils.getScreenWidth(getActivity()) / 14);
                    im.setLayoutParams(mParams);
                    im.setImageResource(R.drawable.chou);
//                    Glide.with(getActivity()).load(date.get(i).oneIcon).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(im);
                    tabLayout.getTabAt(i + 1).setCustomView(im);
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 添加getTabView的方法，来进行自定义Tab的布局View
     *
     * @param position
     * @return
     */
    public View getTabView(int position) {
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View view = null;

        view = mInflater.inflate(R.layout.item_bottom_tab, null);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(tabTexts[position]);
        ImageView img = (ImageView) view.findViewById(R.id.tab_icon);
        if (position == 0) {
//            img.setImageResource(icons_press[position]);
            tv.setTextColor(getActivity().getResources().getColor(R.color.text_01));
        } else {
//            img.setImageResource(icons[position]);
            tv.setTextColor(getActivity().getResources().getColor(R.color.text_02));
        }
        return view;
    }

//    public void selectPager(int index) {
//        viewPager.setCurrentItem(index);
//    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_hongbao:
                startActivity(new Intent(getContext(), EventActivity.class));
                break;
            case R.id.shibai:
                ProductTypeGet();
                break;
        }
    }
}

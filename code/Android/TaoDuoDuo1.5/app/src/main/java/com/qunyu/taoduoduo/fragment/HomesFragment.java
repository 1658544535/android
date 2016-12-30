package com.qunyu.taoduoduo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.allure.lbanners.LMBanners;
import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.andbase.library.view.sample.AbViewPager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.CaiJiaGeActivity;
import com.qunyu.taoduoduo.activity.FreeLotteryListActivity;
import com.qunyu.taoduoduo.activity.GoodsDetailActivity;
import com.qunyu.taoduoduo.activity.LotteryListActivity;
import com.qunyu.taoduoduo.activity.MiaoShaTabActivity;
import com.qunyu.taoduoduo.activity.ZoneActivity;
import com.qunyu.taoduoduo.adapter.HomeGroupProductAdapter;
import com.qunyu.taoduoduo.adapter.UrlImgAdapter;
import com.qunyu.taoduoduo.api.HomeGroupProductsApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.GroupHomeBean;
import com.qunyu.taoduoduo.bean.HomeGroupProductBean;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.global.MyApplication;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout2;
import com.qunyu.taoduoduo.pulltorefresh.PullableListView;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomesFragment extends Fragment implements View.OnClickListener {
    String TAG = "HomesFragment";

    private AbViewPager viewPager = null;

    MyApplication application;
    View view = null;
    LMBanners banners = null;
    PercentRelativeLayout layout_freedraw, layout_caijiage, layout_99temai, layout_timemiaosha, layout_0_1_miaosha;
    int pageNo = 1;
    @BindView(R.id.refresh_view)
    PullToRefreshLayout2 refreshView;
    @BindView(R.id.lv_t)
    PullableListView lvT;
    ArrayList<HomeGroupProductBean> date;
    ArrayList<HomeGroupProductBean> msdate;
    HomeGroupProductAdapter adapter;
    boolean isf = true;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    private ArrayList<GroupHomeBean> networkImages = new ArrayList<GroupHomeBean>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_home, container, false);
        application = (MyApplication) this.getActivity().getApplication();
        ButterKnife.bind(this, view);
        viewPager = (AbViewPager) getActivity().findViewById(R.id.view_paper);
        init();
        GroupHomeApiGet();

        return view;
    }

    private void GroupHomeApiGet() {
        AbHttpUtil.getInstance(getActivity()).get(Constant.groupHomeApi, null, new AbStringHttpResponseListener() {

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
                ToastUtils.showToast(getActivity(), "网络异常，加载失败！");
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<ArrayList<GroupHomeBean>>>() {
                    }.getType();
                    BaseModel<ArrayList<GroupHomeBean>> base = gson.fromJson(content, type);
                    networkImages.clear();
                    for (int i = 0; i < base.result.size(); i++) {
                        networkImages.add(base.result.get(i));
                    }
                    homeSecKillListApi();
                } else {
                    ToastUtils.showToast(getActivity(), "网络异常，加载失败！");
                }

            }
        });

    }

    private void homeSecKillListApi() {
        Log.d(TAG, "onSuccess: " + Constant.homeSecKillListApi);
        AbHttpUtil.getInstance(getActivity()).get(Constant.homeSecKillListApi, null, new AbStringHttpResponseListener() {

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
                HomeGroupProductsApiGet();
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<ArrayList<HomeGroupProductBean>>>() {
                    }.getType();
                    BaseModel<ArrayList<HomeGroupProductBean>> base = gson.fromJson(content, type);
                    Log.d(TAG, "onSuccess: " + content);
                    if (base.result != null) {
                        msdate = base.result;
                        HomeGroupProductsApiGet();
                    }
                } else {
                    ToastUtils.showToast(getActivity(), "网络异常，加载失败！");
                    HomeGroupProductsApiGet();
                }

            }
        });

    }

    private void HomeGroupProductsApiGet() {
        HomeGroupProductsApi HomeGroupProductsApi = new HomeGroupProductsApi();
        HomeGroupProductsApi.setpageNo(pageNo);
        Log.d(TAG, "onSuccess: " + HomeGroupProductsApi.getUrl() + HomeGroupProductsApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(getActivity()).get(HomeGroupProductsApi.getUrl(), HomeGroupProductsApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onStart() {
//                AbDialogUtil.showProgressDialog(LoginActivity.this, 0, "正在登录...");

            }

            @Override
            public void onFinish() {
//                AbDialogUtil.removeDialog(LoginActivity.this);
                try {
                    refreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
                    refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(int statusCode, String content, Throwable error) {
                ToastUtils.showToast(getActivity(), "网络异常，加载失败！");
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<ArrayList<HomeGroupProductBean>>>() {
                    }.getType();
                    BaseModel<ArrayList<HomeGroupProductBean>> base = gson.fromJson(content, type);
                    Log.d(TAG, "onSuccess: " + content);
                    if (base.result != null) {
                        if (pageNo == 1) {
                            if (msdate == null) {
                                date = base.result;
                            } else {
                                date = msdate;
                                date.addAll(base.result);
                            }
                            setbanner(date);
                        } else {
                            date.addAll(base.result);
                            adapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    ToastUtils.showToast(getActivity(), "网络异常，加载失败！");
                }

            }
        });

    }

    private void init() {
        //
        refreshView.setOnPullListener(new MyPullListener());
        lvT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeGroupProductBean homeGroupProductBean = (HomeGroupProductBean) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("activityId", homeGroupProductBean.activityId);
                intent.putExtra("pid", homeGroupProductBean.productId);
                startActivity(intent);

            }
        });
        ivTop.setVisibility(View.GONE);
        lvT.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (getScrollY() > 1000) {
                    ivTop.setVisibility(View.VISIBLE);
                } else {
                    ivTop.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

    private boolean issb = true;

    private void setbanner(ArrayList<HomeGroupProductBean> date) {
        View vHead = View.inflate(getActivity(), R.layout.fg_home_list_head, null);
        banners = (LMBanners) vHead.findViewById(R.id.banners);
        //设置Banners高度
//        banners.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenWidth(getActivity()) / 2));
        //本地用法
//        banners.setAdapter(new LocalImgAdapter(getActivity()), localImages);
        //网络图片
        banners.setAdapter(new UrlImgAdapter(getActivity()), networkImages);
        //参数设置
        banners.setAutoPlay(true);//自动播放
        banners.setVertical(false);//是否可以垂直
        banners.setScrollDurtion(222);//两页切换时间
        banners.setCanLoop(true);//循环播放
        banners.setSelectIndicatorRes(R.drawable.page_indicator_select);//选中的原点
        banners.setUnSelectUnIndicatorRes(R.drawable.page_indicator_unselect);//未选中的原点
//        banners.setHoriZontalTransitionEffect(TransitionEffect.Default);//选中喜欢的样式
//        banners.setHoriZontalCustomTransformer(new ParallaxTransformer(R.id.id_image));//自定义样式
        banners.setDurtion(2000);//切换时间
        banners.hideIndicatorLayout();//隐藏原点
        banners.showIndicatorLayout();//显示原点
        banners.setIndicatorPosition(LMBanners.IndicaTorPosition.BOTTOM_RIGHT);//设置原点显示位置
//        lvT.addHeaderView(vHead);
        if (issb) {
            lvT.addHeaderView(vHead);
            issb = false;
        }
        adapter = new HomeGroupProductAdapter(getActivity(), date);
        lvT.setAdapter(adapter);

        //1.2新增的首页按钮
        layout_caijiage = (PercentRelativeLayout) vHead.findViewById(R.id.layout_caijiage);
        layout_99temai = (PercentRelativeLayout) vHead.findViewById(R.id.layout_99temai);
        layout_timemiaosha = (PercentRelativeLayout) vHead.findViewById(R.id.layout_timemiaosha);
        layout_0_1_miaosha = (PercentRelativeLayout) vHead.findViewById(R.id.layout_0_1miaosha);
        layout_freedraw = (PercentRelativeLayout) vHead.findViewById(R.id.layout_freedrawlist);
        layout_freedraw.setOnClickListener(this);
        layout_caijiage.setOnClickListener(this);
        layout_99temai.setOnClickListener(this);
        layout_timemiaosha.setOnClickListener(this);
        layout_0_1_miaosha.setOnClickListener(this);


    }


    @OnClick(R.id.iv_top)
    public void onClick() {
        lvT.smoothScrollToPosition(0);
    }

    //v1.2首页新增按钮
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_caijiage:
                BaseUtil.ToAc(getActivity(), CaiJiaGeActivity.class);
                break;
            case R.id.layout_99temai:
                BaseUtil.ToAc(getActivity(), ZoneActivity.class);
                break;
            case R.id.layout_timemiaosha:
                BaseUtil.ToAc(getActivity(), MiaoShaTabActivity.class);
                break;
            case R.id.layout_0_1miaosha:
                BaseUtil.ToAc(getActivity(), LotteryListActivity.class);
                break;
            case R.id.layout_freedrawlist:
                BaseUtil.ToAc(getActivity(), FreeLotteryListActivity.class);
                break;
        }
    }


    /**
     * 下拉刷新与上拉加载更多监听器
     */
    public class MyPullListener implements PullToRefreshLayout2.OnPullListener {
        @Override
        public void onRefresh(PullToRefreshLayout2 pullToRefreshLayout) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件刷新完毕了哦！
                    pageNo = 1;
                    homeSecKillListApi();

                }
            }.sendEmptyMessageDelayed(0, 800);
        }

        @Override
        public void onLoadMore(PullToRefreshLayout2 pullToRefreshLayout) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件加载完毕了哦！
                    pageNo++;
                    homeSecKillListApi();
                }
            }.sendEmptyMessageDelayed(0, 800);
        }
    }


    public int getScrollY() {
        View c = lvT.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = lvT.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight();
    }

}

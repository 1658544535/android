package com.qunyu.taoduoduo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.adapter.PrizeDetailMoreAdapter;
import com.qunyu.taoduoduo.api.PrizeDetailApi;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.PrizeDetailBean;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullableExpandableListView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/28.
 * 中奖结果更多
 */

public class PrizeDetailMoreActivity extends BaseActivity {
    int pageNo = 1;
    String activityId;
    String activityType;//0.1-5,免费抽奖-7

    @BindView(R.id.iv_prizeDetailLogom)
    ImageView iv_prizeDetailLogom;
    @BindView(R.id.tv_prizeDetailTitlem)
    TextView tv_prizeDetailTitlem;
    @BindView(R.id.tv_openedPrizem)
    TextView tv_openedPrizem;
    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshLayout;
    @BindView(R.id.elv_prizeDetail)
    PullableExpandableListView expandableListView;
    @BindView(R.id.tv_prizeDetailPricem)
    TextView tv_prizeDetailPricem;

    private PrizeDetailApi prizeDetailApi;
    private PrizeDetailBean bean;
    private ArrayList<PrizeDetailBean.prizelists> list, list1;
    private ArrayList<PrizeDetailBean> list2;
    private PrizeDetailBean.prizelists mData;
    private PrizeDetailMoreAdapter adapter;
    private PrizeDetailBean.prizelists.groupLists groupbean;
    ArrayList<PrizeDetailBean.prizelists.groupLists> groupListses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.prize_detail_more_activity);
        ButterKnife.bind(this);
        bean = new PrizeDetailBean();
        groupListses = new ArrayList<>();
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        activityId = getIntent().getStringExtra("activityId");
        activityType = getIntent().getStringExtra("activityType");
        onListener();
    }

    @Override
    protected void init() {
        super.init();
        baseSetText("中奖结果");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        getMsg();
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private void onListener() {
        refreshLayout.setPullDownEnable(true);
        refreshLayout.setOnPullListener(new MyPullListener());
    }

    private void getMsg() {
        prizeDetailApi = new PrizeDetailApi();
        prizeDetailApi.setPageNo(pageNo + "");
        prizeDetailApi.setActivityId(activityId);
        prizeDetailApi.setActivityType(activityType);
        LogUtil.Log(prizeDetailApi.getUrl() + "?" + prizeDetailApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(this).get(prizeDetailApi.getUrl(), prizeDetailApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                AbResult result = new AbResult(s);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<PrizeDetailBean>>() {
                    }.getType();
                    BaseModel<PrizeDetailBean> base = gson.fromJson(s, type);
                    if (base.result != null) {
                        bean = base.result;
                        setMsg();
                        if (pageNo == 1) {
                            list = bean.getPrizelist();
                            setListMsg();
                        } else {
                            list.addAll(bean.getPrizelist());
//                            if (adapter != null) {

//                                for (int b = 0; b < list.size(); b++) {//每一项都展开
//                                    LogUtil.Log("" + b);
//                                    expandableListView.collapseGroup(b);
//                                    expandableListView.expandGroup(b);
//                                }

//                            }
                            setListMsg();
                        }
                    }
                } else {
                    ToastUtils.showShortToast(PrizeDetailMoreActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                try {
                    refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, String s, Throwable error) {
                ToastUtils.showShortToast(PrizeDetailMoreActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }
        });
    }

    private void setMsg() {
        Glide.with(this).load(bean.getProductImage())
                .placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(iv_prizeDetailLogom);
        tv_prizeDetailTitlem.setText(bean.getProductName());

        //为开奖状态
        if (bean.getStatus() != null && bean.getStatus().equals("0")) {
            tv_openedPrizem.setText("未开奖");
        } else {
            LogUtil.Log("" + bean.getStatus());
            tv_openedPrizem.setText("已开奖");
        }
        tv_prizeDetailPricem.setText("￥" + bean.getProductPrice());
    }

    private void setListMsg() {
        if (list != null) {
            adapter = new PrizeDetailMoreAdapter(PrizeDetailMoreActivity.this, list);
            //屏蔽组件的点击事件

            expandableListView.setAdapter(adapter);
            for (int i = 0; i < list.size(); i++) {//每一项都展开
                LogUtil.Log("" + i);
                expandableListView.expandGroup(i);
            }
            expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    return true;
                }
            });
            expandableListView.setGroupIndicator(null);
            // expandableListView.expandGroup(0);
        }
    }


    private class MyPullListener implements PullToRefreshLayout.OnPullListener {

        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    pageNo = 1;
                    getMsg();
                }
            }.sendEmptyMessageDelayed(0, 800);
        }

        @Override
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    pageNo++;
                    getMsg();
                }
            }.sendEmptyMessageDelayed(0, 800);
        }
    }
}

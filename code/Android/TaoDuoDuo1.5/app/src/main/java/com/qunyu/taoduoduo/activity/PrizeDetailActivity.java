package com.qunyu.taoduoduo.activity;

import android.os.Bundle;
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
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.PrizeDetailBean;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullableExpandableListView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/28.
 * 中奖结果我的团
 */

public class PrizeDetailActivity extends BaseActivity {
    int pageNo = 1;
    String activityType;//活动类型(5-0.1抽奖7-免费抽奖)

    @BindView(R.id.iv_prizeDetailLogo)
    ImageView iv_prizeDetailLogo;
    @BindView(R.id.tv_prizeDetailTitle)
    TextView tv_prizeDetailTitle;
    @BindView(R.id.tv_prizeDetailPrice)
    TextView tv_prizeDetailPrice;
    @BindView(R.id.iv_openedPrize)
    ImageView iv_openedPrize;
    //    @BindView(R.id.iv_prizeDetail_touxian1)
//    ImageView iv_touxian_tuanzhang;
//    @BindView(R.id.iv_prizeDetail_touxian)
//    ImageView iv_touxian;
//    @BindView(R.id.tv_prizeDetail_name1)
//    TextView tv_name_tuanzhang;
//    @BindView(R.id.tv_prizeDetail_name)
//    TextView tv_name;
//    @BindView(R.id.tv_prizeDetail_orderNum1)
//    TextView tv_prizeDetail_orderNum1;
//    @BindView(R.id.tv_prizeDetail_orderNum)
//    TextView tv_prizeDetail_orderNum;
//    @BindView(R.id.tv_prizeDetail_time1)
//    TextView tv_prizeDetail_time1;
//    @BindView(R.id.tv_prizeDetail_time)
//    TextView tv_prizeDetail_time;
    @BindView(R.id.tv_loadmore)
    TextView tv_loadmore;
    @BindView(R.id.elv_prizeDetail)
    PullableExpandableListView expandableListView;


    PullToRefreshLayout refreshLayout;
    TextView loadstate_tv;


    //商品详情
    @BindView(R.id.layout_proDetail)
    PercentRelativeLayout layout_proDetail;

    private PrizeDetailApi detailApi;
    private PrizeDetailBean bean;
    private ArrayList<PrizeDetailBean.prizelists> list;
    private PrizeDetailMoreAdapter adapter;

    String attendId;
    String orderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.prize_detail_activity);
        ButterKnife.bind(this);
        bean = new PrizeDetailBean();
        list = new ArrayList<>();
        attendId = getIntent().getStringExtra("attendId");
        activityType = getIntent().getStringExtra("activityType");
        orderStatus = getIntent().getStringExtra("orderStatus");

//        refreshLayout= (PullToRefreshLayout) findViewById(R.id.refresh_view);
//        loadstate_tv= (TextView) refreshLayout.findViewById(R.id.loadstate_tv);
//        loadstate_tv.setVisibility(View.GONE);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
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

        //商品详情跳转
        layout_proDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //记载更多点击事件
        tv_loadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("activityId", bean.getActivityId());
                b.putString("activityType", activityType);
                LogUtil.Log("activityId===" + bean.getActivityId());
                BaseUtil.ToAcb(PrizeDetailActivity.this, PrizeDetailMoreActivity.class, b);
            }
        });
    }

    private void getMsg() {
        detailApi = new PrizeDetailApi();
        detailApi.setPageNo(pageNo + "");
        detailApi.setAttendId(attendId);
        detailApi.setActivityType(activityType);
        LogUtil.Log("activityType" + activityType);
        LogUtil.Log(detailApi.getUrl() + "?" + detailApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(this).get(detailApi.getUrl(), detailApi.getParamMap(), new AbStringHttpResponseListener() {
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
                        list = bean.getPrizelist();
                        setMsg();
                        if (list != null && list.size() > 0) {
                            setListMsg();
                        }
                    }
                } else {
                    ToastUtils.showShortToast(PrizeDetailActivity.this, "网络异常，数据加载失败");
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable error) {
                ToastUtils.showShortToast(PrizeDetailActivity.this, "网络异常，数据加载失败");
            }
        });
    }

    private void setListMsg() {
        if (orderStatus != null) {
            switch (orderStatus) {
                case "3":
                case "4":
                case "6":
                case "7":
                    expandableListView.setVisibility(View.GONE);
                    break;
            }
        }
        if (list != null) {
            adapter = new PrizeDetailMoreAdapter(PrizeDetailActivity.this, list);
            expandableListView.setAdapter(adapter);

            for (int i = 0; i < list.size(); i++) {//每一项都展开
                LogUtil.Log("" + i);
                expandableListView.expandGroup(i);
            }
            //屏蔽组件的点击事件
            expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    return true;
                }
            });
            expandableListView.setGroupIndicator(null);//取消group默认的上拉下拉图标
            // expandableListView.expandGroup(0);
        }
    }

    private void setMsg() {
        Glide.with(this).load(bean.getProductImage())
                .placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(iv_prizeDetailLogo);
        tv_prizeDetailTitle.setText(bean.getProductName());
//        Glide.with(this).load(list.get(0).getUserlogo()).transform(new CircleTransform(this))
//                .placeholder(R.mipmap.default_touxiang).error(R.mipmap.default_touxiang).into(iv_touxian_tuanzhang);
//
//        tv_name_tuanzhang.setText(list.get(0).getName());
//
//        tv_prizeDetail_orderNum1.setText(list.get(0).getOrderNo());
//
//        tv_prizeDetail_time1.setText(list.get(0).getAttendTime());
//
//        if (list.size() > 1) {
//            Glide.with(this).load(list.get(1).getUserlogo()).transform(new CircleTransform(this))
//                    .placeholder(R.mipmap.default_touxiang).error(R.mipmap.default_touxiang).into(iv_touxian);
//            tv_prizeDetail_orderNum.setText(list.get(1).getOrderNo());
//            tv_name.setText(list.get(1).getName());
//            tv_prizeDetail_time.setText(list.get(1).getAttendTime());
//        }
    }
}

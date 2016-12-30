package com.qunyu.taoduoduo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.adapter.UserOrderNoticeAdapter;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.UserOrderNoticeBean;
import com.qunyu.taoduoduo.config.AppConfig;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullableListView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/7.
 * 订单消息
 */

public class UserOrderNoticeActivity extends BaseActivity {
    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshLayout;
    @BindView(R.id.lv_userOrderNotic)
    PullableListView lv_userOrderNotic;

    int index;
    int pageNo = 1;
    ArrayList<UserOrderNoticeBean> list;
    UserOrderNoticeAdapter adapter;

    TextView state_tv, loadstate_tv;
    String count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.userordernotice_activity);
        ButterKnife.bind(this);
//        state_tv= (TextView) refreshLayout.findViewById(R.id.state_tv);
//        loadstate_tv= (TextView) refreshLayout.findViewById(R.id.loadstate_tv);
//        state_tv.setText("下拉加载更多");
//        loadstate_tv.setText("上拉刷新");
        onListener();

        list = new ArrayList<>();
        count = getIntent().getStringExtra("count");
        LogUtil.Log("count:" + count);
        if (count != null) {
            AppConfig.MYNOTIC_SIZE = Integer.parseInt(count);
        }

        LogUtil.Log("count:" + AppConfig.MYNOTIC_SIZE);

        getMsg();
    }

    @Override
    protected void init() {
        super.init();
        baseSetText("订单消息");
    }

    private void onListener() {
        refreshLayout.setPullDownEnable(true);
        refreshLayout.setOnPullListener(new MyPullListener());
        View L = View.inflate(UserOrderNoticeActivity.this, R.layout.load_more, null);
        TextView Lt = (TextView) L.findViewById(R.id.loadstate_tv);
        Lt.setVisibility(View.GONE);
        refreshLayout.setCustomLoadmoreView(L);
        View H = View.inflate(UserOrderNoticeActivity.this, R.layout.refresh_head, null);
        TextView Ht = (TextView) H.findViewById(R.id.state_tv);
        Ht.setVisibility(View.GONE);
        refreshLayout.setCustomRefreshView(H);
    }

    private void getMsg() {
        AbRequestParams params = new AbRequestParams();
        params.put("userId", UserInfoUtils.GetUid());
        params.put("pageNo", pageNo);
        LogUtil.Log(Constant.userOrderNoticeApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(this).get(Constant.userOrderNoticeApi, params, new AbStringHttpResponseListener() {

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                try {
                    refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, String s, Throwable error) {
                ToastUtils.showShortToast(UserOrderNoticeActivity.this, "网络异常，加载数据失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int i, String s) {
                AbResult result = new AbResult(s);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<ArrayList<UserOrderNoticeBean>>>() {
                    }.getType();
                    BaseModel<ArrayList<UserOrderNoticeBean>> base = gson.fromJson(s, type);
                    if (base.result != null) {
                        if (pageNo == 1) {
                            //刷新
                            if (base.result.size() > 0) {
                                Collections.reverse(base.result);//将ArrayList倒叙
                                list = base.result;
                                adapter = new UserOrderNoticeAdapter(UserOrderNoticeActivity.this, list);
                                lv_userOrderNotic.setAdapter(adapter);
                                lv_userOrderNotic.setSelection(list.size() - 1);//listview跳转到最后一行
                            }
                        } else {
                            //加载更多
                            /*
                            * 下拉下载更多后，visiblePosition依然是上一次数据的第一个
                            * 加载后的数据是添加到List后面的，进行倒叙
                            * 所以visiblePosition应该是加载出来的数据的size*/
                            index = base.result.size();
                            Collections.reverse(list);//将上一次加载出的数据倒叙回来
                            list.addAll(base.result);//将加载更多获取的数据添加进来
                            Collections.reverse(list);//再统一进行倒叙
                            if (adapter != null) {
                                adapter.notifyDataSetChanged();
                            }
                            lv_userOrderNotic.setSelection(index);//设置跳转visiblePosition
                        }

                    }

                } else {
                    ToastUtils.showShortToast(UserOrderNoticeActivity.this, "网络异常，加载数据失败");
                    LogUtil.Log(result.getResultMessage());
                }
            }
        });
    }

    class MyPullListener implements PullToRefreshLayout.OnPullListener {

        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    //下拉加载更多
                    pageNo++;
                    LogUtil.Log("index=====" + list.size());
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
                    //上拉刷新
                    pageNo = 1;
                    getMsg();
                }
            }.sendEmptyMessageDelayed(0, 800);
        }
    }
}

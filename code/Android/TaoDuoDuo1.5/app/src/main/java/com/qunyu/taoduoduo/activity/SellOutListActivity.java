package com.qunyu.taoduoduo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.adapter.SellOutListAdapter;
import com.qunyu.taoduoduo.api.SellOutListApi;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.SellOutListBean;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullableListView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/26.
 * 售罄商品
 */

public class SellOutListActivity extends BaseActivity {

    @BindView(R.id.lv_sellout)
    PullableListView listView;
    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshLayout;
    @BindView(R.id.iv_selloutNull)
    ImageView iv_sellOutNull;
    @BindView(R.id.tv_selloutNull)
    TextView tv_sellOutNull;

    SellOutListApi sellOutListApi;
    ArrayList<SellOutListBean> list;
    SellOutListAdapter adapter;

    int pageNo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.selloutlist_activity);
        ButterKnife.bind(this);
        list = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

        onListener();
        getSellOutMessage();
    }

    @Override
    protected void init() {
        super.init();
        baseSetText("售罄商品");
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void onListener() {
        refreshLayout.setPullDownEnable(true);
        refreshLayout.setOnPullListener(new MyPullListener());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                b.putString("pid", list.get(position).getProductId());
                b.putString("activityId", list.get(position).getActivityId());
                BaseUtil.ToAcb(SellOutListActivity.this, GoodsDetailActivity.class, b);
            }
        });
    }

    private void getSellOutMessage() {
        sellOutListApi = new SellOutListApi();
        sellOutListApi.setPageNo(pageNo + "");
        LogUtil.Log(sellOutListApi.getUrl() + "?" + sellOutListApi.getParamMap());
        AbHttpUtil.getInstance(this).get(sellOutListApi.getUrl(), sellOutListApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                AbResult result = new AbResult(s);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<ArrayList<SellOutListBean>>>() {
                    }.getType();
                    BaseModel<ArrayList<SellOutListBean>> base = gson.fromJson(s, type);
                    if (base.result != null && base.result.size() > 0) {
                        list.clear();
                        if (pageNo == 1) {
                            list = base.result;
                            adapter = new SellOutListAdapter(SellOutListActivity.this, list);
                            listView.setAdapter(adapter);
                        } else {
                            list.addAll(base.result);
                            if (adapter != null) {
                                adapter.notifyDataSetChanged();
                            }
                        }
                    } else {
                        refreshLayout.setVisibility(View.GONE);
                        iv_sellOutNull.setVisibility(View.VISIBLE);
                        tv_sellOutNull.setVisibility(View.VISIBLE);
                    }
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
                ToastUtils.showShortToast(SellOutListActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
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
                    pageNo = 1;
                }
            }.sendEmptyMessageDelayed(0, 1600);
        }

        @Override
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    pageNo++;
                }
            }.sendEmptyMessageDelayed(0, 1600);
        }
    }
}

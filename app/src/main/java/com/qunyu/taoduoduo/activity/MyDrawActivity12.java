//package com.qunyu.taoduoduo.activity;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.TextView;
//
//import com.andbase.library.http.AbHttpUtil;
//import com.andbase.library.http.listener.AbStringHttpResponseListener;
//import com.andbase.library.http.model.AbResult;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.qunyu.taoduoduo.R;
//import com.qunyu.taoduoduo.adapter.MyDrawAdapter;
//import com.qunyu.taoduoduo.api.MyDrawApi;
//import com.qunyu.taoduoduo.base.BaseActivity;
//import com.qunyu.taoduoduo.base.BaseModel;
//import com.qunyu.taoduoduo.base.BaseUtil;
//import com.qunyu.taoduoduo.bean.MyDrawBean;
//import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
//import com.qunyu.taoduoduo.pulltorefresh.PullableListView;
//import com.qunyu.taoduoduo.utils.LogUtil;
//import com.qunyu.taoduoduo.utils.ToastUtils;
//import com.qunyu.taoduoduo.utils.UserInfoUtils;
//import com.umeng.analytics.MobclickAgent;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * Created by Administrator on 2016/11/24.
// * 我的抽奖1.2
// */
//
//public class MyDrawActivity12 extends BaseActivity {
//    private View view;
//
//    int pageNo = 1;
//
//    private MyDrawApi myDrawApi;
//    private ArrayList<MyDrawBean> list;
//    private MyDrawAdapter adapter;
//
//    @BindView(R.id.refresh_view)
//    PullToRefreshLayout refreshLayout;
//    @BindView(R.id.lv_mydraw)
//    PullableListView listView;
//    @BindView(R.id.tv_orderNull)
//    TextView tv_orderNull;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        baseSetContentView(R.layout.mydraw_activity12);
//        list = new ArrayList<>();
//        ButterKnife.bind(this);
//        onListener();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        MobclickAgent.onResume(this);
//        getMsgs();
//
//    }
//    public void onPause() {
//        super.onPause();
//        MobclickAgent.onPause(this);
//    }
//    @Override
//    protected void init() {
//        super.init();
//        baseSetText("我的抽奖");
//    }
//
//    private void onListener() {
//        refreshLayout.setPullDownEnable(true);
//        refreshLayout.setOnPullListener(new MyDrawActivity12.MyPullListener());
//        //抽奖订单详情跳转
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Bundle b = new Bundle();
//                b.putString("type", "555");
//                b.putString("oid", list.get(position).getOrderId());
//                BaseUtil.ToAcb(MyDrawActivity12.this, OrderDetailActivity.class, b);
//                pageNo = 1;
//            }
//        });
//    }
//
//    private void getMsgs() {
//        myDrawApi = new MyDrawApi();
//        myDrawApi.setType("1");
//        myDrawApi.setPageNo(pageNo + "");
//        myDrawApi.setUserId(UserInfoUtils.GetUid());
//        LogUtil.Log(myDrawApi.getUrl() + "?" + myDrawApi.getParamMap().getParamString());
//        AbHttpUtil.getInstance(MyDrawActivity12.this).get(myDrawApi.getUrl(), myDrawApi.getParamMap(), new AbStringHttpResponseListener() {
//            @Override
//            public void onSuccess(int i, String s) {
//                AbResult result = new AbResult(s);
//                if (result.getResultCode() == AbResult.RESULT_OK) {
//                    Gson gson = new Gson();
//                    Type type = new TypeToken<BaseModel<ArrayList<MyDrawBean>>>() {
//                    }.getType();
//                    BaseModel<ArrayList<MyDrawBean>> base = gson.fromJson(s, type);
//                    if (base.result != null && base.result.size() > 0) {
//                        if (pageNo == 1) {
//                            list = base.result;
//                            adapter = new MyDrawAdapter(MyDrawActivity12.this, list, "");
//                            listView.setAdapter(adapter);
//                        } else {
//                            list.addAll(base.result);
//                            if (adapter != null) {
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//                    } else {
//                        if (adapter != null) {
//                            adapter.notifyDataSetChanged();
//                        }
//                        if (pageNo == 1) {
//                            tv_orderNull.setVisibility(View.VISIBLE);
//                            refreshLayout.setVisibility(View.GONE);
//                        }
//                    }
//                } else {
//                    ToastUtils.showShortToast(MyDrawActivity12.this, "网络异常，数据加载失败");
//                    LogUtil.Log(result.getResultMessage());
//                }
//            }
//
//            @Override
//            public void onStart() {
//            }
//
//            @Override
//            public void onFinish() {
//                try {
//                    refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
//                    refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int i, String s, Throwable error) {
//                ToastUtils.showShortToast(MyDrawActivity12.this,"网络异常，数据加载失败");
//                LogUtil.Log(error.getMessage());
//            }
//        });
//
//    }
//
//    private class MyPullListener implements PullToRefreshLayout.OnPullListener {
//
//        @Override
//        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
//            new Handler() {
//                @Override
//                public void handleMessage(Message msg) {
//                    super.handleMessage(msg);
//                    pageNo = 1;
//                    getMsgs();
//                }
//            }.sendEmptyMessageDelayed(0, 800);
//        }
//
//        @Override
//        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
//            new Handler() {
//                @Override
//                public void handleMessage(Message msg) {
//                    super.handleMessage(msg);
//                    pageNo++;
//                    getMsgs();
//                }
//            }.sendEmptyMessageDelayed(0, 800);
//        }
//    }
//}

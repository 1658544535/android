package com.qunyu.taoduoduo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.OrderDetailActivity;
import com.qunyu.taoduoduo.adapter.MyDrawAdapter;
import com.qunyu.taoduoduo.api.MyDrawApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.MyDrawBean;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullableListView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/24.
 */

public class MyDrawFragment extends Fragment {
    private View view;

    int pageNo = 1;
    String type;//1:0.1抽奖    2:免费抽奖

    private MyDrawApi myDrawApi;
    private ArrayList<MyDrawBean> list;
    private MyDrawAdapter adapter;

    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshLayout;
    @BindView(R.id.lv_mydraw)
    PullableListView listView;
    @BindView(R.id.tv_orderNull)
    TextView tv_orderNull;
    @BindView(R.id.iv_orderNull)
    ImageView iv_orderNull;

    public static MyDrawFragment getIntence(String type) {
        MyDrawFragment f = new MyDrawFragment();
        Bundle b = new Bundle();
        b.putString("type", type);
        f.setArguments(b);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mydraw_fragment, container, false);
        ButterKnife.bind(this, view);
        type = this.getArguments().getString("type");
        list = new ArrayList<>();
        onListener();
        getMsgs();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void onListener() {
        refreshLayout.setPullDownEnable(true);
        refreshLayout.setOnPullListener(new MyPullListener());
        //抽奖订单详情跳转
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                b.putString("type", "555");
                b.putString("oid", list.get(position).getOrderId());
                BaseUtil.ToAcb(getActivity(), OrderDetailActivity.class, b);
                pageNo = 1;
            }
        });
    }

    private void getMsgs() {
        myDrawApi = new MyDrawApi();
        myDrawApi.setType(type);
        myDrawApi.setPageNo(pageNo + "");
        myDrawApi.setUserId(UserInfoUtils.GetUid());
        LogUtil.Log(myDrawApi.getUrl() + "?" + myDrawApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(getActivity()).get(myDrawApi.getUrl(), myDrawApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                AbResult result = new AbResult(s);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type types = new TypeToken<BaseModel<ArrayList<MyDrawBean>>>() {
                    }.getType();
                    BaseModel<ArrayList<MyDrawBean>> base = gson.fromJson(s, types);
                    if (base.result != null) {
                        if (pageNo == 1) {
                            if (base.result.size() > 0) {
                                list = base.result;
                                adapter = new MyDrawAdapter(getActivity(), list, type);
                                listView.setAdapter(adapter);
                            } else {
                                tv_orderNull.setVisibility(View.VISIBLE);
                                iv_orderNull.setVisibility(View.VISIBLE);
                                refreshLayout.setVisibility(View.GONE);
                            }
                        } else {
                            list.addAll(base.result);
                            if (adapter != null) {
                                adapter.notifyDataSetChanged();
                            }
                        }
                    } else {
//                        if (adapter != null) {
//                            adapter.notifyDataSetChanged();
//                        }
                        tv_orderNull.setVisibility(View.VISIBLE);
                        iv_orderNull.setVisibility(View.VISIBLE);
                        refreshLayout.setVisibility(View.GONE);
                    }
                } else {
                    ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
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
                ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }
        });

    }

    private class MyPullListener implements PullToRefreshLayout.OnPullListener {

        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    pageNo = 1;
                    getMsgs();
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
                    getMsgs();
                }
            }.sendEmptyMessageDelayed(0, 800);
        }
    }
}

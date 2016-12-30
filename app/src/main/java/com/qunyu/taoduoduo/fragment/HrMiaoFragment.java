package com.qunyu.taoduoduo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.GoodsDetailActivity;
import com.qunyu.taoduoduo.activity.SellOutListActivity;
import com.qunyu.taoduoduo.adapter.SecKillListAdapter;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.SecKillListApiBean;
import com.qunyu.taoduoduo.bean.SecKillListsApiBean;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullableListView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HrMiaoFragment extends Fragment {


    @BindView(R.id.lv_t)
    PullableListView lvT;
    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshView;
    ArrayList<SecKillListApiBean> date;
    SecKillListAdapter adapter;
    ArrayList<SecKillListsApiBean> dat = null;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.activity_start)
    PercentLinearLayout activityStart;
    @BindView(R.id.iv_selloutNull)
    ImageView ivSelloutNull;
    @BindView(R.id.tv_selloutNull)
    TextView tvSelloutNull;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jrmiao_list, container, false);
        ButterKnife.bind(this, view);
        refreshView.setPullDownEnable(false);
        refreshView.setPullUpEnable(false);
        lvT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dat.get(position).getActivityId().equals("999") && dat.get(position).getProductId().equals("999")) {
                    if (dat.get(position).getIsStart().equals("1")) {
                        Intent intent = new Intent(getActivity(), SellOutListActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                    intent.putExtra("activityId", dat.get(position).getActivityId());
                    intent.putExtra("pid", dat.get(position).getProductId());
                    startActivity(intent);
                }

            }
        });
        ivTop.setVisibility(View.GONE);
        lvT.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (getScrollY() > 800) {
                    ivTop.setVisibility(View.VISIBLE);
                } else {
                    ivTop.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        GsecKillListGet();
    }

    private void GsecKillListGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("type", "3");
        LogUtil.Log(Constant.secKillListApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(getActivity()).get(Constant.secKillListApi, params, new AbStringHttpResponseListener() {

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
                    Type type = new TypeToken<BaseModel<ArrayList<SecKillListApiBean>>>() {
                    }.getType();
                    BaseModel<ArrayList<SecKillListApiBean>> base = gson.fromJson(content, type);
                    if (base.result != null && base.result.size() != 0) {
//                        if (pageNo == 1) {
                        date = base.result;
                        dat = new ArrayList<SecKillListsApiBean>();
//                        int sl = 0;
                        for (int i = 0; i < date.size(); i++) {
                            SecKillListsApiBean st = new SecKillListsApiBean();
                            st.setIsStart(date.get(i).getIsStart());
                            st.setKillId(date.get(i).getKillId());
                            st.setTime(date.get(i).getTime());
                            st.setActivityId("999");
                            st.setAlonePrice("999");
                            st.setIsSellOut("999");
                            st.setLimitNum("999");
                            st.setProductId("999");
                            st.setProductImage("999");
                            st.setProductName("999");
                            st.setProductPrice("999");
                            st.setSalePerce("999");
                            if (date.get(i).getIsStart().equals("1")) {
                                st.type = "1";
                            } else {
                                st.type = "2";
                            }
                            dat.add(st);
                            for (int k = 0; k < date.get(i).getSecKillList().size(); k++) {
//                                sl++;
                                SecKillListsApiBean st1 = new SecKillListsApiBean();
                                st1.setIsStart(date.get(i).getIsStart());
                                st1.setKillId(date.get(i).getKillId());
                                st1.setTime("999");
                                st1.setActivityId(date.get(i).getSecKillList().get(k).getActivityId());
                                st1.setAlonePrice(date.get(i).getSecKillList().get(k).getAlonePrice());
                                st1.setIsSellOut(date.get(i).getSecKillList().get(k).getIsSellOut());
                                st1.setLimitNum(date.get(i).getSecKillList().get(k).getLimitNum());
                                st1.setProductId(date.get(i).getSecKillList().get(k).getProductId());
                                st1.setProductImage(date.get(i).getSecKillList().get(k).getProductImage());
                                st1.setProductName(date.get(i).getSecKillList().get(k).getProductName());
                                st1.setProductPrice(date.get(i).getSecKillList().get(k).getProductPrice());
                                st1.setSalePerce(date.get(i).getSecKillList().get(k).getSalePerce());
                                if (date.get(i).getIsStart().equals("1")) {
                                    st1.type = "3";
                                } else {
                                    st1.type = "4";
                                }
                                dat.add(st1);
                            }
                        }
//                        if (sl < 4) {
//                            ivTop.setVisibility(View.GONE);
//                        } else {
//                            ivTop.setVisibility(View.VISIBLE);
//                        }
                        adapter = new SecKillListAdapter(getActivity(), dat);
                        lvT.setAdapter(adapter);
//                        } else {
//                            date.addAll(base.result);
//                            adapter.notifyDataSetChanged();
////                        }
                    } else {
                        ivSelloutNull.setVisibility(View.VISIBLE);
                        tvSelloutNull.setVisibility(View.VISIBLE);
                    }
                } else {
                    ivSelloutNull.setVisibility(View.VISIBLE);
                    tvSelloutNull.setVisibility(View.VISIBLE);
                    ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }

    @OnClick(R.id.iv_top)
    public void onClick() {
        lvT.smoothScrollToPosition(0);
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

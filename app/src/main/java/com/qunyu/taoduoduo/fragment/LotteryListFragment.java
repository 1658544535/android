package com.qunyu.taoduoduo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.GoodsDetailActivity;
import com.qunyu.taoduoduo.adapter.LotteryListAdapter;
import com.qunyu.taoduoduo.api.LotteryListApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.LotteryListBean;
import com.qunyu.taoduoduo.bean.ZoneBean;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullableListView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/27.
 */

public class LotteryListFragment extends Fragment {
    int pageNo = 1;
    String type;

    private View view;
    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshLayout;
    @BindView(R.id.lv_lotterylist)
    PullableListView listView;
    @BindView(R.id.layout_rule)
    ImageView rule_content;
    @BindView(R.id.iv_lotteryNull)
    ImageView iv_null;
    @BindView(R.id.tv_lotteryNull)
    TextView tv_null;
    @BindView(R.id.layout_ivrule)
    PercentRelativeLayout layout_ivrule;
    @BindView(R.id.tv_rule)
    TextView tv_rule;

    LotteryListApi lotteryListApi;
    ArrayList<LotteryListBean> list;
    LotteryListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.lotterylist_fragment, null, false);
        ButterKnife.bind(this, view);
        Bundle b = getArguments();
        type = b.getString("type");
        list = new ArrayList<>();

        onListener();
        if (type != null && type.equals("1")) {
            rule_content.setVisibility(View.VISIBLE);
        } else {
            rule_content.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        PizeBannerGet();

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
                BaseUtil.ToAcb(getActivity(), GoodsDetailActivity.class, b);
            }
        });
    }

    private void PizeBannerGet() {
        AbHttpUtil.getInstance(getActivity()).get(Constant.prizeBannerApi, new AbStringHttpResponseListener() {

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
                            Type type = new TypeToken<BaseModel<ZoneBean>>() {
                            }.getType();
                            BaseModel<ZoneBean> base = gson.fromJson(content, type);
                            if (base.result != null) {
                                Glide.with(getActivity()).load(base.result.banner).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(rule_content);
                                getLotteryList();
                            }
                        } else {
                            ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                            LogUtil.Log(result.getResultMessage());
                        }

                    }
                }

        );

    }

    private void getLotteryList() {
        lotteryListApi = new LotteryListApi();
        lotteryListApi.setPageNo(pageNo + "");
        lotteryListApi.setType(type);
        LogUtil.Log(lotteryListApi.getUrl() + "?" + lotteryListApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(getActivity()).get(lotteryListApi.getUrl(), lotteryListApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                LogUtil.Log(s);
                AbResult result = new AbResult(s);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type0 = new TypeToken<BaseModel<ArrayList<LotteryListBean>>>() {
                    }.getType();
                    BaseModel<ArrayList<LotteryListBean>> base = gson.fromJson(s, type0);
                    if (base.result != null && base.result.size() > 0) {
                        if (pageNo == 1) {
                            list = base.result;
                            adapter = new LotteryListAdapter(getActivity(), list, type);
                            listView.setAdapter(adapter);
                        } else {
                            list.addAll(base.result);
                            if (adapter != null) {
                                adapter.notifyDataSetChanged();
                            }
                        }
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
                    getLotteryList();
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
                    getLotteryList();
                }
            }.sendEmptyMessageDelayed(0, 800);
        }
    }

    private void showRule() {
//        View ruleView= LayoutInflater.from(getActivity()).inflate(R.layout.iv_rule_layout,null);
        View ruleView = getLayoutInflater(getArguments()).inflate(
                R.layout.iv_rule_layout, null, false);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        final PopupWindow popupWindow = new PopupWindow(ruleView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
//
//        ColorDrawable dw = new ColorDrawable(this.getResources().getColor(R.color.white));
//        popupWindow.setBackgroundDrawable(dw);//背景


        popupWindow.setWidth((int) (dm.widthPixels * 0.90));
        popupWindow.setHeight((int) (dm.heightPixels * 0.7));

        popupWindow.setAnimationStyle(R.style.AnimBottom);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        backgroundAlpha(getActivity(), 0.5f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(getActivity(), 1f);
            }
        });

        ImageView btn_close = (ImageView) ruleView.findViewById(R.id.btn_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
}

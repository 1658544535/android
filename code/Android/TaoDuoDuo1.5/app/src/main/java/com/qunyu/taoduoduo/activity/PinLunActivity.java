package com.qunyu.taoduoduo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.adapter.GetDrawCommentDetailsApiAdapter;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.GetDrawCommentDetailsBean;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullableListView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PinLunActivity extends BaseActivity {
    String activityId, pid, image, name;
    @BindView(R.id.lv_t)
    PullableListView lvT;
    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshView;
    int pageNo = 1;
    public static GetDrawCommentDetailsBean date;
    GetDrawCommentDetailsApiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_pin_lun);
        baseSetText("评论列表");
        ButterKnife.bind(this);
        activityId = getIntent().getStringExtra("activityId");
        pid = getIntent().getStringExtra("pid");
        GetDrawCommentDetailsApi();
        refreshView.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        // 千万别忘了告诉控件刷新完毕了哦！
                        pageNo = 1;
                        GetDrawCommentDetailsApi();

                    }
                }.sendEmptyMessageDelayed(0, 800);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        // 千万别忘了告诉控件加载完毕了哦！
                        pageNo++;
                        GetDrawCommentDetailsApi();
                    }
                }.sendEmptyMessageDelayed(0, 800);
            }
        });
        refreshView.setPullDownEnable(false);
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private void GetDrawCommentDetailsApi() {
        AbRequestParams params = new AbRequestParams();
        params.put("activityId", activityId);
        params.put("pageNo", pageNo + "");
        params.put("productId", pid);
        AbHttpUtil.getInstance(PinLunActivity.this).get(Constant.getDrawCommentDetailsApi, params, new AbStringHttpResponseListener() {
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
                ToastUtils.showShortToast(PinLunActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<GetDrawCommentDetailsBean>>() {
                    }.getType();
                    BaseModel<GetDrawCommentDetailsBean> base = gson.fromJson(content, type);
                    if (base.result != null) {
                        if (pageNo == 1) {
                            date = base.result;
                            View vHead = View.inflate(PinLunActivity.this, R.layout.activity_pin_lun_head, null);
                            ImageView iv_logo = (ImageView) vHead.findViewById(R.id.iv_logo);
                            TextView tv_name = (TextView) vHead.findViewById(R.id.tv_name);
                            Glide.with(PinLunActivity.this).load(date.getProductImage()).placeholder(R.mipmap.default_load_long).error(R.mipmap.default_load_long).into(iv_logo);
                            tv_name.setText(date.getProductName());
                            lvT.addHeaderView(vHead);
                            adapter = new GetDrawCommentDetailsApiAdapter(PinLunActivity.this, date.getUserInfo());
                            lvT.setAdapter(adapter);
                        } else {
                            date.getUserInfo().addAll(base.result.getUserInfo());
                            adapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    ToastUtils.showShortToast(PinLunActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }

            }
        });

    }
}

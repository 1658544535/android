package com.qunyu.taoduoduo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.adapter.GroupFreeListAdapter;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.GroupFreeListBean;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.global.Untool;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullableListView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TuanMianActivity extends BaseActivity {


    int pageNo = 1;
    @BindView(R.id.lv_t)
    PullableListView lvT;
    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_tuan_mian);
        ButterKnife.bind(this);
        init1();
        GroupFreeListGet();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void init1() {
        baseSetText("团免列表");
        View vHead = View.inflate(this, R.layout.activity_tuan_mian_list_head, null);
        lvT.addHeaderView(vHead);
        refreshView.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        // 千万别忘了告诉控件刷新完毕了哦！
                        pageNo = 1;
                        GroupFreeListGet();

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
                        GroupFreeListGet();
                    }
                }.sendEmptyMessageDelayed(0, 800);
            }
        });
        refreshView.setPullDownEnable(false);
        lvT.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                try {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("tag", "tag");
                    bundle1.putString("activityId", date.get(arg2 - 1).getActivityId() + "");
                    bundle1.putString("pid", date.get(arg2 - 1).getProductId() + "");
                    BaseUtil.ToAcb(TuanMianActivity.this, GoodsDetailActivity.class, bundle1);
                } catch (Exception c) {
                }
            }

        });
    }

    ArrayList<GroupFreeListBean> date = null;
    GroupFreeListAdapter adapter;

    private void GroupFreeListGet() {
        AbRequestParams params = new AbRequestParams();
        params.put("userId", Untool.getUid());
        params.put("pageNo", pageNo);
        LogUtil.Log(Constant.groupFreeListApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(this).get(Constant.groupFreeListApi, params, new AbStringHttpResponseListener() {
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
                ToastUtils.showShortToast(TuanMianActivity.this, "网络异常，数据加载失败");
            }

            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult(content);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<ArrayList<GroupFreeListBean>>>() {
                    }.getType();
                    Log.d("+++", "onSuccess: " + content);
                    BaseModel<ArrayList<GroupFreeListBean>> base = gson.fromJson(content, type);
                    if (base.result != null) {
                        if (pageNo == 1) {
                            date = base.result;
                            adapter = new GroupFreeListAdapter(TuanMianActivity.this, date);
                            lvT.setAdapter(adapter);
                        } else {
                            date.addAll(base.result);
                            adapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    ToastUtils.showShortToast(TuanMianActivity.this, "网络异常，数据加载失败");
                }

            }
        });

    }

}

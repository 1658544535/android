package com.qunyu.taoduoduo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.adapter.MyCollectListAdapter;
import com.qunyu.taoduoduo.api.DelFavoriteApi;
import com.qunyu.taoduoduo.api.MyCollectListApi;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.MyCollectListBean;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullableListView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/10.
 */

public class MyCollectListActivity extends BaseActivity {
    public static Handler handler;
    private int pageNo;
    private String activityId;
    private int mPosition;

    private ArrayList<MyCollectListBean> list;
    private MyCollectListApi myCollectListApi;
    private MyCollectListAdapter adapter;
    private DelFavoriteApi delFavoriteApi;

    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshLayout;
    @BindView(R.id.lv_mycollect)
    PullableListView listView;
    @BindView(R.id.tv_orderNull)
    TextView tv_orderNull;
    @BindView(R.id.iv_orderNull)
    ImageView iv_orderNull;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.my_collect_list_activity);
        ButterKnife.bind(this);
        pageNo=1;
        list=new ArrayList<>();
        onListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        getCollectList();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle b=msg.getData();
                activityId=b.getString("activityId");
                mPosition=msg.arg1;
                cancelCollect();
            }
        };
    }

    @Override
    protected void init() {
        super.init();
        baseSetText("我的收藏");
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void onListener(){
        refreshLayout.setPullDownEnable(true);
        refreshLayout.setOnPullListener(new MyPullListener());
    }
    //获取收藏列表
    private void getCollectList(){
        myCollectListApi=new MyCollectListApi();
        myCollectListApi.setPageNo(pageNo+"");
        myCollectListApi.setUserId(UserInfoUtils.GetUid());
        AbHttpUtil.getInstance(this).get(myCollectListApi.getUrl(), myCollectListApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String content) {
                AbResult result=new AbResult(content);
                if(result.getResultCode()==AbResult.RESULT_OK){
                    Gson gson=new Gson();
                    Type type=new TypeToken<BaseModel<ArrayList<MyCollectListBean>>>(){}.getType();
                    BaseModel<ArrayList<MyCollectListBean>> base=gson.fromJson(content,type);
                    if (base.result != null) {
                        if(pageNo==1){
                            if (base.result.size() > 0) {
                                list = base.result;
                                adapter = new MyCollectListAdapter(MyCollectListActivity.this, list);
                                listView.setAdapter(adapter);
                            } else {
                                tv_orderNull.setVisibility(View.VISIBLE);
                                iv_orderNull.setVisibility(View.VISIBLE);
                                refreshLayout.setVisibility(View.GONE);
                                listView.setVisibility(View.GONE);
                            }

                        }else{
                            if (base.result.size() > 0) {
                                list.addAll(base.result);
                                if (adapter != null) {
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    } else {
                        tv_orderNull.setVisibility(View.VISIBLE);
                        iv_orderNull.setVisibility(View.VISIBLE);
                        refreshLayout.setVisibility(View.GONE);
                        listView.setVisibility(View.GONE);
                    }
                }else{
                    ToastUtils.showShortToast(MyCollectListActivity.this, "网络异常，数据加载失败");
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
                ToastUtils.showShortToast(MyCollectListActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }
        });
    }
    //取消收藏
    private void cancelCollect(){
        delFavoriteApi=new DelFavoriteApi();
        delFavoriteApi.setActivityId(activityId);
        delFavoriteApi.setUid(UserInfoUtils.GetUid());
        AbHttpUtil.getInstance(MyCollectListActivity.this).get(delFavoriteApi.getUrl(),delFavoriteApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable error) {
                ToastUtils.showShortToast(MyCollectListActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }

            @Override
            public void onSuccess(int i, String content) {
                AbResult result=new AbResult(content);
                if(result.getResultCode()==AbResult.RESULT_OK){
                    try {
                        JSONObject jsonObject=new JSONObject(content);
                        ToastUtils.showShortToast(MyCollectListActivity.this, jsonObject.optString("error_msg"));
                        if(jsonObject.optBoolean("success")){
                            list.remove(mPosition);
                            if(adapter!=null){
                                adapter.notifyDataSetChanged();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private class MyPullListener implements PullToRefreshLayout.OnPullListener{

        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    pageNo=1;
                    getCollectList();
                }
            }.sendEmptyMessageDelayed(0,1600);
        }

        @Override
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    pageNo++;
                    getCollectList();
                }
            }.sendEmptyMessageDelayed(0,1600);
        }
    }
}

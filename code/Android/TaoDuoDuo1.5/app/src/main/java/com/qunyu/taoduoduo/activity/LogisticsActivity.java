package com.qunyu.taoduoduo.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.adapter.LogisticsListAdapter;
import com.qunyu.taoduoduo.api.LogisticsMsgApi;
import com.qunyu.taoduoduo.api.RefundExpressApi;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.LogisticsBean;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/28.
 * 查看物流
 */

public class


LogisticsActivity extends BaseActivity {
    private String orderId;
    private ArrayList<LogisticsBean.ExpressBean> list;
    private LogisticsBean bean;
    private LogisticsMsgApi logisticsApi;
    private LogisticsListAdapter adapter;
    private ArrayList<LogisticsBean> blist;
    private ListView lv_logistics;

    private TextView tv_expressType;
    private TextView tv_expressNum;

    private String refundStatus=null;
    private RefundExpressApi refundExpressApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.my_logistics_activity);
        orderId=getIntent().getStringExtra("orderId");
        refundStatus=getIntent().getStringExtra("refundStatus");
        bean=new LogisticsBean();
        blist=new ArrayList<>();
        list=new ArrayList<>();
        lv_logistics= (ListView) findViewById(R.id.lv_logistics);
        tv_expressType= (TextView) findViewById(R.id.expressType);
        tv_expressNum= (TextView) findViewById(R.id.expressNum);
        if (StringUtils.isNotBlank(refundStatus) && refundStatus.equals("3")) {
            getRefundExpressMsg();

        } else {
            getLogisticsMsg();
        }

    }
    @Override
    protected void init() {
        super.init();
        baseSetText("查看物流");
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    private void getLogisticsMsg() {
        logisticsApi=new LogisticsMsgApi();
        logisticsApi.setOrderId(orderId);
        LogUtil.Log(logisticsApi.getUrl() + "?" + logisticsApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(this).get(logisticsApi.getUrl(), logisticsApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String content) {
                AbResult result=new AbResult(content);
                if(result.getResultCode()==AbResult.RESULT_OK){
                    Gson gson=new Gson();
                    Type type=new TypeToken<BaseModel<LogisticsBean>>(){}.getType();
                    BaseModel<LogisticsBean> base=gson.fromJson(content,type);
                    if(base!=null){
                        bean=base.result;
                       // blist=base.result;
                        tv_expressType.setText("物流公司："+bean.getExpressType());
                        tv_expressNum.setText("运单编号："+bean.getExpressNumber());
                        list.clear();
                        list=bean.getExpress();
                        for(int a=0;a<list.size();a++){
                            list.get(a).setIndex(""+a);
                        }
                        adapter=new LogisticsListAdapter(LogisticsActivity.this,list);
                        lv_logistics.setAdapter(adapter);

                    }
                }else{
                    ToastUtils.showShortToast(LogisticsActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
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
                ToastUtils.showShortToast(LogisticsActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }
        });

    }

    private void getRefundExpressMsg(){
        refundExpressApi=new RefundExpressApi();
        refundExpressApi.setOid(orderId);
        LogUtil.Log(refundExpressApi.getUrl() + "?" + refundExpressApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(this).get(refundExpressApi.getUrl(), refundExpressApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String content) {
                AbResult result=new AbResult(content);
                if(result.getResultCode()==AbResult.RESULT_OK){
                    Gson gson=new Gson();
                    Type type=new TypeToken<BaseModel<LogisticsBean>>(){}.getType();
                    BaseModel<LogisticsBean> base=gson.fromJson(content,type);
                    if(base!=null){
                        bean=base.result;
                        // blist=base.result;
                        tv_expressType.setText("物流公司："+bean.getExpressType());
                        tv_expressNum.setText("运单编号："+bean.getExpressNumber());
                        list.clear();
                        list=bean.getExpress();
                        for(int a=0;a<list.size();a++){
                            list.get(a).setIndex(""+a);
                        }
                        adapter=new LogisticsListAdapter(LogisticsActivity.this,list);
                        lv_logistics.setAdapter(adapter);
                        refundStatus=null;
                    }
                }else{
                    ToastUtils.showShortToast(LogisticsActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
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
                ToastUtils.showShortToast(LogisticsActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }
        });
    }
}

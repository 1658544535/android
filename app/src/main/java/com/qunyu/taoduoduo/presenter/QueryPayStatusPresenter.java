package com.qunyu.taoduoduo.presenter;

import android.content.Context;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.qunyu.taoduoduo.api.QueryPayStatusApi;
import com.qunyu.taoduoduo.utils.LogUtil;


/**
 * 订单同步
 * Created by Administrator on 2016/10/8.
 */

public class QueryPayStatusPresenter {
    QueryPayStatusApi queryPayStatusApi;
    Context context;

    public QueryPayStatusPresenter(Context context) {
        this.context = context;
        queryPayStatusApi = new QueryPayStatusApi();
    }

    public void loadData(String outTradeNo, String payMethod) {
        queryPayStatusApi.setOutTradeNo(outTradeNo);
        queryPayStatusApi.setPayMethod(payMethod);

        LogUtil.Log(queryPayStatusApi.getUrl() + "?" + queryPayStatusApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(context).post(queryPayStatusApi.getUrl(), queryPayStatusApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {

                LogUtil.Log(s);

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {

            }
        });
    }


}

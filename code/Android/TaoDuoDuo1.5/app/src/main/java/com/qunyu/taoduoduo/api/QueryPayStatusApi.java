package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.utils.MD5;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单付款同步处理
 * Created by Administrator on 2016/9/30.
 */

public class QueryPayStatusApi implements BaseApi {
    private String outTradeNo;//对外支付订单号string(250)
    private String payMethod;//	支付方式1:支付宝 ；2：微信支付


    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params = new AbRequestParams();
        params.put("outTradeNo", outTradeNo);
        params.put("payMethod", payMethod);
        return params;
    }


    @Override
    public String getUrl() {
        return Constant.QUERYPAYSTATUS_URL;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }
}

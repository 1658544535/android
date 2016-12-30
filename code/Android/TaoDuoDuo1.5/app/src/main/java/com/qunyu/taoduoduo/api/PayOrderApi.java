package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * Created by Administrator on 2016/10/11.
 * 订单付款(老接口)
 */

public class PayOrderApi implements BaseApi {
    private String orderNo;
    private String payMethod;//支付方式： 1:支付宝 ；2：微信支付；4：钱包支付
    private String uid;
    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params=new AbRequestParams();
        params.put("orderNo",orderNo);
        params.put("payMethod",payMethod);
        params.put("uid",uid);
        return params;
    }

    @Override
    public String getUrl() {
        return Constant.payOrder;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

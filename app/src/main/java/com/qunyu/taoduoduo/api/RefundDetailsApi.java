package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * Created by Administrator on 2016/10/12.
 * 售后详情
 */

public class RefundDetailsApi implements BaseApi {
    private String oid;
    private String uid;
    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params=new AbRequestParams();
        params.put("oid",oid);
        params.put("uid",uid);
        return params;
    }

    @Override
    public String getUrl() {
        return Constant.refundDetailsApi;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
}

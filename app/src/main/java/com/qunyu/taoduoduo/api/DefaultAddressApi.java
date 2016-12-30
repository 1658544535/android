package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * Created by Administrator on 2016/10/11.
 * 返回默认地址
 */

public class DefaultAddressApi implements BaseApi {
    private String uid;
    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params=new AbRequestParams();
        params.put("uid",uid);
        return params;
    }

    @Override
    public String getUrl() {
        return Constant.defaultAddress;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

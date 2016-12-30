package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * Created by Administrator on 2016/9/28.
 * 我的基本信息
 */

public class PersonalCenterMsgApi implements BaseApi {
    private String userId; //用户id

    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params = new AbRequestParams();
        params.put("userId", userId);
        return params;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getUrl() {
        return Constant.myInfoApi;
    }
}

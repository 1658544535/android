package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * 拼团-参团页面
 */

public class GroupDetailApi implements BaseApi {
    private String recordId;//		团活动记录Id
    private String userId;//	会员id
    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params=new AbRequestParams();
        params.put("recordId",recordId);
        params.put("userId",userId);
        return params;
    }


    @Override
    public String getUrl() {
        return Constant.GROUPDETAIL_URL;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

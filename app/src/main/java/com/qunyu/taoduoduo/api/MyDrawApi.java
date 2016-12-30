package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * Created by Administrator on 2016/10/27.
 */

public class MyDrawApi implements BaseApi {
    private String pageNo;
    private String type;//1-0.1抽奖2-免费抽奖
    private String userId;

    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params = new AbRequestParams();
        params.put("pageNo", pageNo);
        params.put("type", type);
        params.put("userId", userId);
        return params;
    }

    @Override
    public String getUrl() {
        return Constant.getDrawApi;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

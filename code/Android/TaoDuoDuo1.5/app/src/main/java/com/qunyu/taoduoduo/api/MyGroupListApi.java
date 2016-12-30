package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * Created by Administrator on 2016/10/12.
 * 我的团
 */

public class MyGroupListApi implements BaseApi {
    private String pageNo;
    private String status;//0-全部1-拼团中2-拼团结束3-拼团失败
    private String userId;
    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params=new AbRequestParams();
        params.put("pageNo",pageNo);
        params.put("status",status);
        params.put("userId",userId);
        return params;
    }

    @Override
    public String getUrl() {
        return Constant.myGroupListApi;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

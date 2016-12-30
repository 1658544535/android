package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * Created by Administrator on 2016/10/10.
 */

public class AfterSaleListApi implements BaseApi {
    private String pageNo;
    private String status;//	售后状态(0全部,1审核中,2审核通过,3审核不同,4完成)
    private String userId;//用户id
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
        return Constant.afterSaleListApi;
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

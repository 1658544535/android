package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * 首页-推荐拼团列表
 * Created by Administrator on 2016/9/27.
 */

public class HomeGroupProductsApi implements BaseApi{
    private int pageNo; //页码

    @Override
    public AbRequestParams getParamMap() {
        // TODO Auto-generated method stub
        AbRequestParams params = new AbRequestParams();
        params.put("pageNo", pageNo);
        return params;
    }

    public int getpageNo() {
        return pageNo;
    }

    public void setpageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    @Override
    public String getUrl() {
        return Constant.homeGroupProductApi;
    }
}

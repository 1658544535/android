package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * Created by Administrator on 2016/10/26.
 * 今日售罄商品
 */

public class SellOutListApi implements BaseApi {
    private String pageNo;//页码

    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params = new AbRequestParams();
        params.put("pageNo", pageNo);
        return params;
    }

    @Override
    public String getUrl() {
        return Constant.sellOutListApi;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }
}

package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * 新品专区
 * Created by Administrator on 2016/9/30.
 */

public class NewSpecialApi implements BaseApi {
    private Integer pageNo;//	页码

    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params = new AbRequestParams();
        params.put("pageNo", pageNo);
        return params;
    }


    @Override
    public String getUrl() {
        return Constant.NEWSPECIALAPI_URL;
    }


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
}

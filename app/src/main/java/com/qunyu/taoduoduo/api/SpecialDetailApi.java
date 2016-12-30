package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * 专题详情
 * Created by Administrator on 2016/9/30.
 */

public class SpecialDetailApi implements BaseApi {
    private String specialId;//	专题id
    private Integer pageNo;//	页码

    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params = new AbRequestParams();
        params.put("specialId", specialId);
        params.put("pageNo", pageNo);
        return params;
    }


    @Override
    public String getUrl() {
        return Constant.SPECIALDETAIL_URL;
    }

    public String getSpecialId() {
        return specialId;
    }

    public void setSpecialId(String specialId) {
        this.specialId = specialId;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
}

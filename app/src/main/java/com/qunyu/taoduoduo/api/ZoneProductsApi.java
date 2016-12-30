package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * 专区信息
 * Created by Administrator on 2016/9/30.
 */

public class ZoneProductsApi implements BaseApi {
    private String id;//	专区id
    private Integer pageNo;//	页码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params = new AbRequestParams();
        params.put("id", id);
        params.put("pageNo", pageNo);

        return params;
    }


    @Override
    public String getUrl() {
        return Constant.ZONEPRODUCTS_URL;
    }


}

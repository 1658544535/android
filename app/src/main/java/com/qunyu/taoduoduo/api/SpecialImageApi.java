package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * 专题图片
 *
 */

public class SpecialImageApi implements BaseApi {
    private String specialId;//	专题id

    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params = new AbRequestParams();
        params.put("specialId", specialId);
        return params;
    }


    @Override
    public String getUrl() {
        return Constant.SPECIALIMAGEAPI_URL;
    }

    public String getSpecialId() {
        return specialId;
    }

    public void setSpecialId(String specialId) {
        this.specialId = specialId;
    }

}

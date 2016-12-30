package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * 专区信息
 * Created by Administrator on 2016/9/30.
 */

public class ZoneApi implements BaseApi {

    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params = new AbRequestParams();
        return params;
    }


    @Override
    public String getUrl() {
        return Constant.ZONE_URL;
    }


}

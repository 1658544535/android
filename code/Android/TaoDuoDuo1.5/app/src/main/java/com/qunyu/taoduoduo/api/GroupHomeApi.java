package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * 首页-平台广告轮播图
 * Created by Administrator on 2016/9/27.
 */

public class GroupHomeApi implements BaseApi{

    @Override
    public AbRequestParams getParamMap() {
        // TODO Auto-generated method stub
        AbRequestParams map = new AbRequestParams();
        return map;
    }

    @Override
    public String getUrl() {
        return Constant.groupHomeApi;
    }
}

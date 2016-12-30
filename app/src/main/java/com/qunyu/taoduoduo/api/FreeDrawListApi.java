package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * Created by Administrator on 2016/11/21.
 * 免费抽奖
 */

public class FreeDrawListApi implements BaseApi {
    public String pageNo;
    public String type;

    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params = new AbRequestParams();
        params.put("pageNo", pageNo);
        params.put("type", type);
        return params;
    }

    @Override
    public String getUrl() {
        return Constant.freeDrawListApi;
    }
}

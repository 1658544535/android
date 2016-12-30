package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * Created by Administrator on 2016/11/21.
 */

public class ActivityBannerApi implements BaseApi {
    public String type;//类型：2-猜价 3-0.1抽奖 4-免费抽奖

    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params = new AbRequestParams();
        params.put("type", type);
        return params;
    }

    @Override
    public String getUrl() {
        return Constant.activityBannerApi;
    }
}

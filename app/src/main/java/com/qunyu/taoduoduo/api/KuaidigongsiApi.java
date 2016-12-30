package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * Created by Administrator on 2016/10/17.
 */

public class KuaidigongsiApi implements BaseApi {
    @Override
    public AbRequestParams getParamMap() {
        return null;
    }

    @Override
    public String getUrl() {
        return Constant.logisticsApi;
    }
}

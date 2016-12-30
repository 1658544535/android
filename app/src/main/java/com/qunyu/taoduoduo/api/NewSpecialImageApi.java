package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * 新品专区顶部图片
 *
 */

public class NewSpecialImageApi implements BaseApi {


    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params = new AbRequestParams();
        return params;
    }


    @Override
    public String getUrl() {
        return Constant.NEWSPECIALIMAGEAPI_URL;
    }



}

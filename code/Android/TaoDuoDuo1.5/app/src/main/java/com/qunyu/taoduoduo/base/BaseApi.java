package com.qunyu.taoduoduo.base;

import com.andbase.library.http.model.AbRequestParams;

/**
 * Created by Administrator on 2016/9/26.
 */

public interface BaseApi {
    AbRequestParams getParamMap();

    String getUrl();
}

package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * Created by Administrator on 2016/10/27.
 * 0.1抽奖列表
 */

public class LotteryListApi implements BaseApi {
    private String pageNo;
    private String type;//1正在进行中；2结束

    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params = new AbRequestParams();
        params.put("pageNo", pageNo);
        params.put("type", type);
        return params;
    }

    @Override
    public String getUrl() {
        return Constant.lotteryListApi;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

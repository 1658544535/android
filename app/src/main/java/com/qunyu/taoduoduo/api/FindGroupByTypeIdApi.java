package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * 首页-根据分类查询拼团列表
 * Created by Administrator on 2016/9/27.
 */

public class FindGroupByTypeIdApi implements BaseApi {
    private int pageNo; //页码
    private String id;

    @Override
    public AbRequestParams getParamMap() {
        // TODO Auto-generated method stub
        AbRequestParams params = new AbRequestParams();
        params.put("id", id);
        params.put("pageNo", pageNo);
        return params;
    }

    public int getpageNo() {
        return pageNo;
    }

    public void setpageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    @Override
    public String getUrl() {
        return Constant.findGroupByTypeId;
    }
}

package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * Created by Administrator on 2016/10/7.
 * 搜索商品
 */

public class SearchAllApi implements BaseApi{
    private String name;//商品名称
    private String pageNo;//页码
    private String sorting;//排序（1为销量降序，11为销量升序，2为价格升序，22为价格降序， 3为点击数降序，3为点击数升序）

    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params=new AbRequestParams();
        params.put("name",name);
        params.put("pageNo",pageNo);
        params.put("sorting",sorting);
        return params;
    }

    @Override
    public String getUrl() {
        return Constant.searchAllProductApi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getSorting() {
        return sorting;
    }

    public void setSorting(String sorting) {
        this.sorting = sorting;
    }
}

package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * Created by Administrator on 2016/10/10.
 */

public class SubmitLogisticsApi implements BaseApi {
    private String logisticsName;//物流名称
    private String logisticsNum;//物流单号
    private String oid;//订单id
    private String uid;//会员id
    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params=new AbRequestParams();
        params.put("logisticsName",logisticsName);
        params.put("logisticsNum",logisticsNum);
        params.put("oid",oid);
        params.put("uid",uid);
        return params;
    }

    @Override
    public String getUrl() {
        return Constant.submitLogisticsApi;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getLogisticsNum() {
        return logisticsNum;
    }

    public void setLogisticsNum(String logisticsNum) {
        this.logisticsNum = logisticsNum;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

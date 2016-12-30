package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * Created by Administrator on 2016/10/11.
 * 立即购买页面
 */

public class AddPurchaseApi implements BaseApi {
   private String activityId;//活动id,默认0-普通商品
   private String attendId;//该团记录id(参团时必填)
   private String num;//商品数量
   private String pid;//商品ID
   private String skuLinkId;//商品skuid,没有时传空
   private String source;//来源，1-普通拼团2-团免3-猜价
   private String uid;//会员id

    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params=new AbRequestParams();
        params.put("activityId",activityId);
        params.put("attendId",attendId);
        params.put("num",num);
        params.put("pid",pid);
        params.put("skuLinkId",skuLinkId);
        params.put("source",source);
        params.put("uid",uid);
        return params;
    }

    @Override
    public String getUrl() {
        return Constant.addPurchase;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getAttendId() {
        return attendId;
    }

    public void setAttendId(String attendId) {
        this.attendId = attendId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSkuLinkId() {
        return skuLinkId;
    }

    public void setSkuLinkId(String skuLinkId) {
        this.skuLinkId = skuLinkId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

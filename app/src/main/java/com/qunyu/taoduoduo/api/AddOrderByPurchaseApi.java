package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * Created by Administrator on 2016/10/11.
 * 立即支付按钮
 */

public class AddOrderByPurchaseApi implements BaseApi {
   private String activityId;//活动id,默认0-普通商品
   private String addressId;//我的地址ID，快递时必填
   private String attendId;//该团记录id(参团时必填,开团)
   private String buyer_message;//买家留言
   //private String channel;//订单渠道(1app,2微信)
   private String consigneeType;//收货方式（1，快递2，自提）
   private String couponNo;//优惠券码
   private String num;//商品数量
   private String payMethod;//1:支付宝；2：微信支付
   private String pid;//商品ID
   private String skuLinkId;//商品skuid,没有时传空
   private String source;//来源，1-普通拼团2-团免3-猜价
   private String uid;//会员id


    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params=new AbRequestParams();
        params.put("activityId",activityId);
        params.put("addressId",addressId);
        params.put("attendId",attendId);
        params.put("buyer_message",buyer_message);
        params.put("channel","1");
        params.put("consigneeType",consigneeType);
        params.put("couponNo",couponNo);
        params.put("num",num);
        params.put("payMethod",payMethod);
        params.put("pid",pid);
        params.put("skuLinkId",skuLinkId);
        params.put("source",source);
        params.put("uid",uid);
        return params;
    }

    @Override
    public String getUrl() {
        return Constant.addOrderByPurchaseApi;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAttendId() {
        return attendId;
    }

    public void setAttendId(String attendId) {
        this.attendId = attendId;
    }

    public String getBuyer_message() {
        return buyer_message;
    }

    public void setBuyer_message(String buyer_message) {
        this.buyer_message = buyer_message;
    }

//    public String getChannel() {
//        return channel;
//    }
//
//    public void setChannel(String channel) {
//        this.channel = channel;
//    }

    public String getConsigneeType() {
        return consigneeType;
    }

    public void setConsigneeType(String consigneeType) {
        this.consigneeType = consigneeType;
    }

    public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
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

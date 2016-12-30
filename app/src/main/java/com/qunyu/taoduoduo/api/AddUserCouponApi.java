package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.utils.MD5;
import com.qunyu.taoduoduo.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 优惠券兑换
 *
 */

public class AddUserCouponApi implements BaseApi {
    private String couponNo;//	优惠券码
    private String uid;//		用户ID


    @Override
    public AbRequestParams getParamMap() {
        // TODO Auto-generated method stub
        AbRequestParams params = new AbRequestParams();
        params.put("couponNo", couponNo);
        params.put("uid", uid);
        return params;
    }

    public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String getUrl() {
        return Constant.ADDUSERCOUPON_URL;
    }
}

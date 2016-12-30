package com.qunyu.taoduoduo.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/28.
 */

public class MyCouponsListBean {
    /**
     * 我的优惠券列表
     */
    private String  notUsedNum;//未使用条数
    private String  overdueNum;//已过期
    private String  usedNum;//已使用
    private ArrayList<CouponList> couponList;

    public String getNotUsedNum() {
        return notUsedNum;
    }

    public void setNotUsedNum(String notUsedNum) {
        this.notUsedNum = notUsedNum;
    }

    public String getOverdueNum() {
        return overdueNum;
    }

    public void setOverdueNum(String overdueNum) {
        this.overdueNum = overdueNum;
    }

    public String getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(String usedNum) {
        this.usedNum = usedNum;
    }

    public ArrayList<MyCouponsListBean.CouponList> getCouponList() {
        return couponList;
    }

    public void setCouponList(ArrayList<MyCouponsListBean.CouponList> couponList) {
        this.couponList = couponList;
    }

    //优惠卷列表
    public class CouponList implements Serializable{

        private String activityId;//活动id
        private String couponId;      //优惠券类型id
        private String couponName;//优惠券名
        private String couponNo;//优惠券码
        private String isProduct;//是否指定产品(0-否1-是)
        private String m;//减掉金额m
        private String om;//订单金额满om
        private String overdue;//过期状态，0-否，1-是
        private String productId;//指定的商品id
        private String source;//来源：1-新人礼包、2-首单礼包、3-节日活动、4-互动活动、5-用户绑定
        private String useTime;//使用时间
        private String used;//使用状态，0-否，1-是
        private String validEtime;//有效开始时间
        private String validStime;//有效结束时间
        public String couponType;//优惠券类型1-满减2-直减

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public String getIsProduct() {
            return isProduct;
        }

        public void setIsProduct(String isProduct) {
            this.isProduct = isProduct;
        }

        public String getCouponNo() {
            return couponNo;
        }

        public void setCouponNo(String couponNo) {
            this.couponNo = couponNo;
        }

        public String getM() {
            return m;
        }

        public void setM(String m) {
            this.m = m;
        }

        public String getOm() {
            return om;
        }

        public void setOm(String om) {
            this.om = om;
        }

        public String getOverdue() {
            return overdue;
        }

        public void setOverdue(String overdue) {
            this.overdue = overdue;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getUseTime() {
            return useTime;
        }

        public void setUseTime(String useTime) {
            this.useTime = useTime;
        }

        public String getUsed() {
            return used;
        }

        public void setUsed(String used) {
            this.used = used;
        }

        public String getValidEtime() {
            return validEtime;
        }

        public void setValidEtime(String validEtime) {
            this.validEtime = validEtime;
        }

        public String getValidStime() {
            return validStime;
        }

        public void setValidStime(String validStime) {
            this.validStime = validStime;
        }

        public String getActivityId() {
            return activityId;
        }

        public String getCouponType() {
            return couponType;
        }

        public void setCouponType(String couponType) {
            this.couponType = couponType;
        }

        public void setActivityId(String activityId) {

            this.activityId = activityId;
        }
    }
}

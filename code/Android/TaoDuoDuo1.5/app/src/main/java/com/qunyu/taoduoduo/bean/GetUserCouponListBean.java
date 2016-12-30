package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/9/26.
 */

public class GetUserCouponListBean {
    /**
     * 我的-优惠卷列表
     * couponId : 2
     * couponName : b
     * couponNo : 2
     * overdue : 0
     * useTime : 1977-06-01 04:00:38
     * used : 0
     * validEtime : 1999-04-07 08:22:35
     * validStime : 1970-09-29 16:22:35
     */

    private String couponId;
    private String couponName;
    private String couponNo;
    private int overdue;
    private String useTime;
    private int used;
    private String validEtime;
    private String validStime;

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

    public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo;
    }

    public int getOverdue() {
        return overdue;
    }

    public void setOverdue(int overdue) {
        this.overdue = overdue;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
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
}

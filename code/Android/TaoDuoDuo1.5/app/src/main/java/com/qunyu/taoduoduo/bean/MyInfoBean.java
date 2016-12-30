package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/9/26.
 */

public class MyInfoBean {
    /**
     * 我的-基本信息
     * couponBTime : 1998-05-23 20:01:02
     * couponETime : 1999-07-08 15:05:59
     * groupingNum : 6
     * isGroupFree : 1
     * name : 小明
     * saleSerNum : 5
     * uid : 68
     * userName : 小明
     * waitComNum : 6
     * waitPayNum : 4
     * waitRecNum : 3
     * waitSendNum : 4
     */

    private String couponBTime;
    private String couponETime;
    private int groupingNum;
    private int isGroupFree;
    private String name;
    private int saleSerNum;
    private String uid;
    private String userName;
    private int waitComNum;
    private int waitPayNum;
    private int waitRecNum;
    private int waitSendNum;

    public String getCouponBTime() {
        return couponBTime;
    }

    public void setCouponBTime(String couponBTime) {
        this.couponBTime = couponBTime;
    }

    public String getCouponETime() {
        return couponETime;
    }

    public void setCouponETime(String couponETime) {
        this.couponETime = couponETime;
    }

    public int getGroupingNum() {
        return groupingNum;
    }

    public void setGroupingNum(int groupingNum) {
        this.groupingNum = groupingNum;
    }

    public int getIsGroupFree() {
        return isGroupFree;
    }

    public void setIsGroupFree(int isGroupFree) {
        this.isGroupFree = isGroupFree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSaleSerNum() {
        return saleSerNum;
    }

    public void setSaleSerNum(int saleSerNum) {
        this.saleSerNum = saleSerNum;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getWaitComNum() {
        return waitComNum;
    }

    public void setWaitComNum(int waitComNum) {
        this.waitComNum = waitComNum;
    }

    public int getWaitPayNum() {
        return waitPayNum;
    }

    public void setWaitPayNum(int waitPayNum) {
        this.waitPayNum = waitPayNum;
    }

    public int getWaitRecNum() {
        return waitRecNum;
    }

    public void setWaitRecNum(int waitRecNum) {
        this.waitRecNum = waitRecNum;
    }

    public int getWaitSendNum() {
        return waitSendNum;
    }

    public void setWaitSendNum(int waitSendNum) {
        this.waitSendNum = waitSendNum;
    }
}

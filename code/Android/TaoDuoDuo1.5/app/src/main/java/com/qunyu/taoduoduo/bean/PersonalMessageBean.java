package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/9/27.
 */

public class PersonalMessageBean {
    /**
     * 个人中心
     */
    private String couponBTime; //团免券开始时间
    private String couponETime; //团免券结束时间
    private String groupingNum; //拼团中数量
    private String isGroupFree; //团免券是否激活(0否1是)
    private String name;        // 昵称
    private String saleSerNum;  // 售后/退货数量
    private String uid;         // 会员id
    private String userImage;   // 用户图片
    private String userName;    // 账户
    private String waitComNum;  // 待评论
    private String waitPayNum;  // 待付款数量
    private String waitRecNum;  // 待收货数量
    private String waitSendNum; // 待发货数量

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

    public String getGroupingNum() {
        return groupingNum;
    }

    public void setGroupingNum(String groupingNum) {
        this.groupingNum = groupingNum;
    }

    public String getIsGroupFree() {
        return isGroupFree;
    }

    public void setIsGroupFree(String isGroupFree) {
        this.isGroupFree = isGroupFree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSaleSerNum() {
        return saleSerNum;
    }

    public void setSaleSerNum(String saleSerNum) {
        this.saleSerNum = saleSerNum;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWaitComNum() {
        return waitComNum;
    }

    public void setWaitComNum(String waitComNum) {
        this.waitComNum = waitComNum;
    }

    public String getWaitPayNum() {
        return waitPayNum;
    }

    public void setWaitPayNum(String waitPayNum) {
        this.waitPayNum = waitPayNum;
    }

    public String getWaitRecNum() {
        return waitRecNum;
    }

    public void setWaitRecNum(String waitRecNum) {
        this.waitRecNum = waitRecNum;
    }

    public String getWaitSendNum() {
        return waitSendNum;
    }

    public void setWaitSendNum(String waitSendNum) {
        this.waitSendNum = waitSendNum;
    }
}

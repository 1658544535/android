package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/10/27.
 * 我的抽奖
 */

public class MyDrawBean {
    private String activityId;//活动ID
    private String attendId;//参团记录ID
    private String isPrize;//中奖信息（0-不显示1-显示）
    private String isShow;//我要晒图（0-不显示1-显示）
    /*
    * 1-待支付 2-拼团中，差N人 3-未成团，退款中
     * 4-未成团，已退款 5-交易已取消 6-未中奖，待返款
     * 7-未中奖，已返款 8-已成团，待开奖 9-已中奖，已完成
     * 10-已中奖，待发货 11-已中奖，待收货
     * */
    private String orderStatus;
    private String poorNum;//拼团还差人数
    private String productId;//商品ID
    private String productImage;//
    private String productName;//
    private String productPrice;//
    private String orderId;

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

    public String getIsPrize() {
        return isPrize;
    }

    public void setIsPrize(String isPrize) {
        this.isPrize = isPrize;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPoorNum() {
        return poorNum;
    }

    public void setPoorNum(String poorNum) {
        this.poorNum = poorNum;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}

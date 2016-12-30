package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/9/26.
 * 我的团
 */

public class MyGroupListBean {
    /**
     * 我的-拼团列表
     * activityId : 206
     * activityStatus : 2
     * alonePrice : 237
     * groupNum : 115
     * productId : 163
     * productImage : http://dummyimage.com/200x200/ffcc33/FFF.png
     * productName : 小钢琴
     * productPrice : 168
     */

    private String activityId;
    private String activityStatus;//1-拼团中2-拼团结束3-拼团失败
    private String alonePrice;
    private String groupNum;//n人团
    private String groupRecId;//团记录id
    private String orderId;//订单id
    private String productId;
    private String productImage;
    private String productName;
    private String productPrice;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }



    public String getAlonePrice() {
        return alonePrice;
    }

    public void setAlonePrice(String alonePrice) {
        this.alonePrice = alonePrice;
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

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public String getGroupRecId() {
        return groupRecId;
    }

    public void setGroupRecId(String groupRecId) {
        this.groupRecId = groupRecId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}

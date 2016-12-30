package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/9/26.
 */

public class MyGuessListBean {
    /**
     * 我的-猜价列表
     * activityId : 31
     * activityStatus : 1
     * alonePrice : 3
     * productId : 179
     * productImage : http://dummyimage.com/200x200/ffcc33/FFF.png
     * productName : 汽车
     * productPrice : 12
     */

    private String activityId;
    private int activityStatus;
    private String alonePrice;
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

    public int getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(int activityStatus) {
        this.activityStatus = activityStatus;
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
}

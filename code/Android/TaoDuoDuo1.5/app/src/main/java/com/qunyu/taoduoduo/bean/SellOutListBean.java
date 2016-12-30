package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/10/26.
 * 今日售罄商品1.2
 */

public class SellOutListBean {

    private String activityId;//活动id
    private String alonePrice;//商品原价
    private String productId;//商品id
    private String productImage;//商品图片
    private String productName;//商品名称
    private String productPrice;//商品价格

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
}

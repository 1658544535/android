package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/9/26.
 */

public class MyCollectListBean {
    /**
     * 我的-收藏列表
     * activityId : 128
     * alonePrice : 4
     * groupNum : 6
     * proSellrNum : 102
     * productId : 197
     * productImage : http://dummyimage.com/200x200/ffcc33/FFF.png
     * productName : '变形金刚'
     * productPrice : 196
     */

    private String activityId;//活动id
    private String alonePrice;//单独购价格
    private String collectId;//收藏记录Id
    private int groupNum;//n人购买
    private int proSellrNum;//商品销量
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

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public int getProSellrNum() {
        return proSellrNum;
    }

    public void setProSellrNum(int proSellrNum) {
        this.proSellrNum = proSellrNum;
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

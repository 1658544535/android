package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/9/26.
 */

public class FindGroupByTypeIdBean {
    /**
     * 首页-根据分类查询拼团列表
     * activityId : 77
     * alonePrice : 168
     * groupNum : 5
     * proSellrNum : 247
     * productId : 91
     * productImage : http://dummyimage.com/200x200/ffcc33/FFF.png
     * productName : 148
     * productPrice : 15
     */

    private String activityId;
    private String alonePrice;
    private int groupNum;
    private int proSellrNum;
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

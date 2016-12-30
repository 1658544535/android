package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/9/28.
 */

public class MyCaijiaListBean {
    /**
     * 我的猜价列表
     */
   private String activityId;    //猜价活动id
   private String activityStatus;    //活动类型(1进行中,2未得奖,3已得奖)
    private String isRecCoupon;//是否已分发优惠卷(0否1是)
   private String maxPrice;    //最高价格
   private String minPrice;    //最低价格
    private String prize;//奖项
   private String productId;    //商品id
   private String productImage;    //商品图片
   private String productName;    //商品名称
    private String realPrice;//最终价格
   private String userPrice;    //用户猜价

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
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

    public String getUserPrice() {
        return userPrice;
    }

    public void setUserPrice(String userPrice) {
        this.userPrice = userPrice;
    }

    public String getIsRecCoupon() {
        return isRecCoupon;
    }

    public void setIsRecCoupon(String isRecCoupon) {
        this.isRecCoupon = isRecCoupon;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }
}

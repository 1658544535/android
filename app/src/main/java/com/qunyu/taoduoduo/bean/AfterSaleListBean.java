package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/10/10.
 * 售后列表
 */

public class AfterSaleListBean {

    private String activityType;//活动类型(1-普通拼团2-团免3-猜价4-单独购5-0.1抽奖6-限时秒杀7-免费抽奖)
    private String activityId;//活动id
   private String orderId;//订单id
   private String orderPrice;//实付价格
   private String productId;//商品id
   private String productImage;//商品图片
   private String productName;//商品名称
   private String reason;//审核不通过原因
   private String refundId;//售后id
   private String refundStatus;//售后状态售后状态(0=>未申请，1=>审核，2=>请退货，3=>退货中，4=>退货成功，5=>退货)

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
}

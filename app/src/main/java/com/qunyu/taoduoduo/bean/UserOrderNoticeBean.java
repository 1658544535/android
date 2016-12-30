package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/12/7.
 * 订单消息列表
 */

public class UserOrderNoticeBean {

    /**
     * content : 开团成功提醒
     * 订单金额：0.12元
     * 商品详情：蓝色游击艇
     * 收货信息：
     * 订单编号：1481101461096398851
     * 您已开团成功，赶紧邀请小伙伴们加入吧
     * time : 17:04
     * title : 开团成功通知
     * linkParam : 1511
     * productImage : http://pin.taozhuma.com/upfiles/product/20161202041430300697.jpg
     * linkType : 1
     * productName : 蓝色游击艇
     * linkName : 订单详情
     */

    private String activityType;//活动类型--商品详情页时有值
    private String content;
    private String time;
    private String title;
    private String linkParam;
    private String productImage;
    private String linkType;
    private String productName;
    private String linkName;
    private String productId;//商品id--商品详情页时有值

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkParam() {
        return linkParam;
    }

    public void setLinkParam(String linkParam) {
        this.linkParam = linkParam;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}

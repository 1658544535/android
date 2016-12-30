package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/9/26.
 */

public class GuessActivityBean {

    /**
     * activityId : 227
     * beginTime : 1980-05-22 09:26:01
     * endTime : 2009-03-11 21:15:36
     * joinNum : 4
     * maxPrice : 63
     * minPrice : 83
     * nowTime : 测试内容8drd
     * productId : 219
     * productImage : http://dummyimage.com/200x200/ffcc33/FFF.png
     * productName : 玩具猜价
     * hour : 玩具猜价
     * min : 玩具猜价
     * ss : 玩具猜价
     */
    public String isPrize;
    private int activityId;
    private String beginTime;
    private String endTime;
    private int joinNum;
    private String maxPrice;
    private String minPrice;
    private String nowTime;
    private int productId;
    private String productImage;
    private String productName;
    private String hour;
    private String min;
    private String ss;

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(int joinNum) {
        this.joinNum = joinNum;
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

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
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

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }
}

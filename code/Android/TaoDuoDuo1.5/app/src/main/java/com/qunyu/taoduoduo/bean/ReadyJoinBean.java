package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/9/26.
 */

public class ReadyJoinBean {


    /**
     * beginTime : 1988-02-04 23:29:06
     * couponEndTime : 测试内容lggn
     * couponNo : 测试内容1r71
     * couponPrice : 测试内容ied7
     * endTime : 2012-01-28 01:28:56
     * isAlert : 测试内容0w72
     * isJoin : 0
     * isPublic : 1
     * isRecCoupon : 测试内容68t4
     * isStart : 2
     * isWin : 0
     * joinNum : 2
     * maxPrice : 1000
     * minPrice : 10
     * nowTime : 测试内容fix1
     * prize : 1
     * productId : 测试内容e8h5
     * productName : 小钢琴
     * productSketch : 测试内容l5u1
     * realPrice : 150
     * userInfo : {"joinTime":"1998-12-21 13:35:39","userImage":"http://dummyimage.com/200x200/ffcc33/FFF.png","userName":"小明","userPrice":257}
     */

    private String beginTime;
    private String couponEndTime;
    private String couponNo;
    private String couponPrice;
    private String endTime;
    private String isAlert;
    private String isJoin;
    private String isPublic;
    private String isRecCoupon;
    private String isStart;
    private String isWin;
    private String joinNum;
    private String maxPrice;
    private String minPrice;
    private String nowTime;
    private String prize;
    private String productId;
    private String productName;
    private String productSketch;
    private String realPrice;
    /**
     * joinTime : 1998-12-21 13:35:39
     * userImage : http://dummyimage.com/200x200/ffcc33/FFF.png
     * userName : 小明
     * userPrice : 257
     */

    private UserInfoBean userInfo;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getCouponEndTime() {
        return couponEndTime;
    }

    public void setCouponEndTime(String couponEndTime) {
        this.couponEndTime = couponEndTime;
    }

    public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo;
    }

    public String getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(String couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIsAlert() {
        return isAlert;
    }

    public void setIsAlert(String isAlert) {
        this.isAlert = isAlert;
    }

    public String getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(String isJoin) {
        this.isJoin = isJoin;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getIsRecCoupon() {
        return isRecCoupon;
    }

    public void setIsRecCoupon(String isRecCoupon) {
        this.isRecCoupon = isRecCoupon;
    }

    public String getIsStart() {
        return isStart;
    }

    public void setIsStart(String isStart) {
        this.isStart = isStart;
    }

    public String getIsWin() {
        return isWin;
    }

    public void setIsWin(String isWin) {
        this.isWin = isWin;
    }

    public String getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(String joinNum) {
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

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSketch() {
        return productSketch;
    }

    public void setProductSketch(String productSketch) {
        this.productSketch = productSketch;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean {
        private String joinTime;
        private String userImage;
        private String userName;
        private String userPrice;

        public String getJoinTime() {
            return joinTime;
        }

        public void setJoinTime(String joinTime) {
            this.joinTime = joinTime;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPrice() {
            return userPrice;
        }

        public void setUserPrice(String userPrice) {
            this.userPrice = userPrice;
        }
    }
}

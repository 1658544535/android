package com.qunyu.taoduoduo.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */

public class GuessWinListBean {
    /**
     * 猜价-详情得奖人列表
     * friPrizeList : [{"joinTime":"2008-12-19 15:31:25","userImage":"http://dummyimage.com/100x100/ffcc33/FFF.png","userName":"小明","userPrice":8},{"joinTime":"2008-05-07 05:24:07","userImage":"http://dummyimage.com/100x100/ffcc33/FFF.png","userName":"小明","userPrice":251}]
     * friPrizeNum : 2
     * thrPrizeList : [{"joinTime":"2001-02-15 15:00:17","userImage":"http://dummyimage.com/100x100/ffcc33/FFF.png","userName":"小明","userPrice":224},{"joinTime":"1995-07-08 16:16:36","userImage":"http://dummyimage.com/100x100/ffcc33/FFF.png","userName":"小明","userPrice":156}]
     * thrPrizeNum : 2
     * twoPrizeList : [{"joinTime":"1971-05-17 00:35:41","userImage":"http://dummyimage.com/100x100/ffcc33/FFF.png","userName":"小明","userPrice":87},{"joinTime":"1988-04-14 15:50:49","userImage":"http://dummyimage.com/100x100/ffcc33/FFF.png","userName":"小明","userPrice":92}]
     * twoPrizeNum : 2
     */

    private String friPrizeNum;
    private String thrPrizeNum;
    private String twoPrizeNum;
    /**
     * joinTime : 2008-12-19 15:31:25
     * userImage : http://dummyimage.com/100x100/ffcc33/FFF.png
     * userName : 小明
     * userPrice : 8
     */

    private List<FriPrizeListBean> friPrizeList;
    /**
     * joinTime : 2001-02-15 15:00:17
     * userImage : http://dummyimage.com/100x100/ffcc33/FFF.png
     * userName : 小明
     * userPrice : 224
     */

    private List<ThrPrizeListBean> thrPrizeList;
    /**
     * joinTime : 1971-05-17 00:35:41
     * userImage : http://dummyimage.com/100x100/ffcc33/FFF.png
     * userName : 小明
     * userPrice : 87
     */

    private List<TwoPrizeListBean> twoPrizeList;

    public String getFriPrizeNum() {
        return friPrizeNum;
    }

    public void setFriPrizeNum(String friPrizeNum) {
        this.friPrizeNum = friPrizeNum;
    }

    public String getThrPrizeNum() {
        return thrPrizeNum;
    }

    public void setThrPrizeNum(String thrPrizeNum) {
        this.thrPrizeNum = thrPrizeNum;
    }

    public String getTwoPrizeNum() {
        return twoPrizeNum;
    }

    public void setTwoPrizeNum(String twoPrizeNum) {
        this.twoPrizeNum = twoPrizeNum;
    }

    public List<FriPrizeListBean> getFriPrizeList() {
        return friPrizeList;
    }

    public void setFriPrizeList(List<FriPrizeListBean> friPrizeList) {
        this.friPrizeList = friPrizeList;
    }

    public List<ThrPrizeListBean> getThrPrizeList() {
        return thrPrizeList;
    }

    public void setThrPrizeList(List<ThrPrizeListBean> thrPrizeList) {
        this.thrPrizeList = thrPrizeList;
    }

    public List<TwoPrizeListBean> getTwoPrizeList() {
        return twoPrizeList;
    }

    public void setTwoPrizeList(List<TwoPrizeListBean> twoPrizeList) {
        this.twoPrizeList = twoPrizeList;
    }

    public static class FriPrizeListBean {
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

    public static class ThrPrizeListBean {
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

    public static class TwoPrizeListBean {
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

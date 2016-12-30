package com.qunyu.taoduoduo.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/26.
 */

public class WinListBean {
    /**
     * 猜价-更多得奖人列表
     * joinNum : 10
     * prizeList : [{"joinTime":"1971-07-30 00:37:41","userImage":"http://dummyimage.com/100x100/ffcc33/FFF.png","userName":"小明","userPrice":195},{"joinTime":"1979-10-31 10:21:30","userImage":"http://dummyimage.com/100x100/ffcc33/FFF.png","userName":"小明","userPrice":227},{"joinTime":"1987-06-09 19:43:28","userImage":"http://dummyimage.com/100x100/ffcc33/FFF.png","userName":"小明","userPrice":271},{"joinTime":"1970-07-22 01:17:49","userImage":"http://dummyimage.com/100x100/ffcc33/FFF.png","userName":"小明","userPrice":139},{"joinTime":"1997-08-28 04:58:43","userImage":"http://dummyimage.com/100x100/ffcc33/FFF.png","userName":"小明","userPrice":80},{"joinTime":"2002-11-30 00:47:57","userImage":"http://dummyimage.com/100x100/ffcc33/FFF.png","userName":"小明","userPrice":260},{"joinTime":"2000-10-03 15:39:11","userImage":"http://dummyimage.com/100x100/ffcc33/FFF.png","userName":"小明","userPrice":87}]
     */

    private String joinNum;
    /**
     * joinTime : 1971-07-30 00:37:41
     * userImage : http://dummyimage.com/100x100/ffcc33/FFF.png
     * userName : 小明
     * userPrice : 195
     */

    private ArrayList<PrizeListBean> prizeList;

    public String getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(String joinNum) {
        this.joinNum = joinNum;
    }

    public ArrayList<PrizeListBean> getPrizeList() {
        return prizeList;
    }

    public void setPrizeList(ArrayList<PrizeListBean> prizeList) {
        this.prizeList = prizeList;
    }

    public static class PrizeListBean {
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

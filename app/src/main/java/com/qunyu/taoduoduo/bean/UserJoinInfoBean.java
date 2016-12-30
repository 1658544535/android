package com.qunyu.taoduoduo.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/26.
 */

public class UserJoinInfoBean {
    /**
     * joinNum : @natural(1,300)
     * joinUserList : [{"joinTime":"@natural(1,300)","userImage":"@image('200x200', '#ffcc33', '#FFF', 'png', '')","userName":"小明","userPrice":"@natural(1,300)"}]
     */

    private String joinNum;
    /**
     * joinTime : @natural(1,300)
     * userImage : @image('200x200', '#ffcc33', '#FFF', 'png', '')
     * userName : 小明
     * userPrice : @natural(1,300)
     */

    private ArrayList<JoinUserListBean> joinUserList;
    /**
     * isGuess : 1
     */

    private int isGuess;

    public String getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(String joinNum) {
        this.joinNum = joinNum;
    }

    public ArrayList<JoinUserListBean> getJoinUserList() {
        return joinUserList;
    }

    public void setJoinUserList(ArrayList<JoinUserListBean> joinUserList) {
        this.joinUserList = joinUserList;
    }

    public int getIsGuess() {
        return isGuess;
    }

    public void setIsGuess(int isGuess) {
        this.isGuess = isGuess;
    }

    public static class JoinUserListBean {
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
    /**
     * 猜价-参与用户列表
     * joinNum : 22
     * joinTime : 1996-10-18 12:56:50
     * userImage : http://dummyimage.com/200x200/ffcc33/FFF.png
     * userName : 小明
     * userPrice : 78
     */

}
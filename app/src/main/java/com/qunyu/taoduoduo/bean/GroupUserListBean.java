package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/9/26.
 */

public class GroupUserListBean {
    /**
     * 拼团-参团人列表
     * isHead : 1
     * joinTime : 2000-12-28
     * userImage : http://dummyimage.com/50x50/ffcc33/FFF.png
     * userName : 小明
     */

    private int isHead;
    private String joinTime;
    private String userImage;
    private String userName;

    public int getIsHead() {
        return isHead;
    }

    public void setIsHead(int isHead) {
        this.isHead = isHead;
    }

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
}

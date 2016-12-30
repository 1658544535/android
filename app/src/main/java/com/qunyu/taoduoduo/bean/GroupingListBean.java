package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/9/26.
 */

public class GroupingListBean {
    /**
     * 拼团-进行中的团
     * activityId : 37
     * beginTime : 1989-10-02 00:22:14
     * endTime : 1981-08-04 18:36:44
     * oweNum : 1
     * userImage : @mock=http://dummyimage.com/100x100/ffcc33/FFF.png
     * userName : 小明
     */

    private String activityId;
    private String beginTime;
    private String endTime;
    private String oweNum;
    private String userImage;
    private String userName;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
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

    public String getOweNum() {
        return oweNum;
    }

    public void setOweNum(String oweNum) {
        this.oweNum = oweNum;
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

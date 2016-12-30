package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/12/5.
 * 我的消息
 */

public class MyNoticeBean {
    private String count;
    private String images;
    private String name;
    private String time;
    private String title;
    private String type;//1=>系统消息 2=>每日推荐 3=>订单消息

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

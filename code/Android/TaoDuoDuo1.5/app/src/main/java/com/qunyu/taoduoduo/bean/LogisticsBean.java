package com.qunyu.taoduoduo.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/26.
 */

public class LogisticsBean {
    /**
     * 查看物流
     * content :
     * time : 2015-08-10
     */

    private ArrayList<ExpressBean> express;
    private String expressNumber;
    private String expressType;
    private String productImages;
    private String state;

    public ArrayList<ExpressBean> getExpress() {
        return express;
    }

    public void setExpress(ArrayList<ExpressBean> express) {
        this.express = express;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getExpressType() {
        return expressType;
    }

    public void setExpressType(String expressType) {
        this.expressType = expressType;
    }

    public String getProductImages() {
        return productImages;
    }

    public void setProductImages(String productImages) {
        this.productImages = productImages;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static class ExpressBean {
        private String content;
        private String time;
        private String index;

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

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }
    }
}

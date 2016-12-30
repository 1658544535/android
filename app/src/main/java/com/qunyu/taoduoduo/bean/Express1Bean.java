package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/9/26.
 */

public class Express1Bean {
    /**
     * 查看物流
     * content :
     * time : 2015-08-10
     */

    private ExpressBean express;
    /**
     * express : {"content":"","time":"2015-08-10"}
     * expressNumber : 2015-08-10
     * expressType :
     * productImages : http://ext1.taozhuma.com/upfiles/product/small/20150805051456784799.jpg
     * state :
     */

    private String expressNumber;
    private String expressType;
    private String productImages;
    private String state;

    public ExpressBean getExpress() {
        return express;
    }

    public void setExpress(ExpressBean express) {
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
    }
}

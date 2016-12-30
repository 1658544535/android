package com.qunyu.taoduoduo.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/27.
 */

public class SecKillListApiBean {
    /**
     * isStart : 测试内容yw1y
     * killId : 测试内容ht45
     * secKillList : [{"activityId":"测试内容671v","alonePrice":"测试内容w31b","isSellOut":"测试内容3i2o","limitNum":"测试内容l7yg","productId":"测试内容3q7j","productImage":"测试内容798p","productName":"测试内容d8nz","productPrice":"测试内容yq24","salePerce":"测试内容ua83"}]
     * time : 测试内容v3h4
     */

    private String isStart;
    private String killId;
    private String time;
    /**
     * activityId : 测试内容671v
     * alonePrice : 测试内容w31b
     * isSellOut : 测试内容3i2o
     * limitNum : 测试内容l7yg
     * productId : 测试内容3q7j
     * productImage : 测试内容798p
     * productName : 测试内容d8nz
     * productPrice : 测试内容yq24
     * salePerce : 测试内容ua83
     */

    private ArrayList<SecKillListBean> secKillList;

    public String getIsStart() {
        return isStart;
    }

    public void setIsStart(String isStart) {
        this.isStart = isStart;
    }

    public String getKillId() {
        return killId;
    }

    public void setKillId(String killId) {
        this.killId = killId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<SecKillListBean> getSecKillList() {
        return secKillList;
    }

    public void setSecKillList(ArrayList<SecKillListBean> secKillList) {
        this.secKillList = secKillList;
    }

    public static class SecKillListBean {
        private String activityId;
        private String alonePrice;
        private String isSellOut;
        private String limitNum;
        private String productId;
        private String productImage;
        private String productName;
        private String productPrice;
        private String salePerce;

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getAlonePrice() {
            return alonePrice;
        }

        public void setAlonePrice(String alonePrice) {
            this.alonePrice = alonePrice;
        }

        public String getIsSellOut() {
            return isSellOut;
        }

        public void setIsSellOut(String isSellOut) {
            this.isSellOut = isSellOut;
        }

        public String getLimitNum() {
            return limitNum;
        }

        public void setLimitNum(String limitNum) {
            this.limitNum = limitNum;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
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

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public String getSalePerce() {
            return salePerce;
        }

        public void setSalePerce(String salePerce) {
            this.salePerce = salePerce;
        }
    }
}

package com.qunyu.taoduoduo.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/7.
 * 搜索商品
 */

public class SearchAllProductBean {

    private String count;//条数
    private ArrayList<productss> products;//

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public ArrayList<productss> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<productss> products) {
        this.products = products;
    }

    public class productss{

       private String activityId;//活动id
       private String proSellrNum;//已团数量
        private String attendNum;//已团数量
       private String groupNum;//成团人数
       private String productId;//商品id
       private String productImage;//商品图片
       private String productName;//商品名称
       private String productPrice;//商品价格

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }
        public String getproSellrNum() {
            return proSellrNum;
        }

        public void setproSellrNum(String attendNum) {
            this.proSellrNum = attendNum;
        }

        public String getAttendNum() {
            return attendNum;
        }

        public void setAttendNum(String attendNum) {
            this.attendNum = attendNum;
        }

        public String getGroupNum() {
            return groupNum;
        }

        public void setGroupNum(String groupNum) {
            this.groupNum = groupNum;
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
    }
}

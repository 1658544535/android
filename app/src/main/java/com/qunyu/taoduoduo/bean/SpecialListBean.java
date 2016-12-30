package com.qunyu.taoduoduo.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/18.
 */

public class SpecialListBean {
    /**
     * specialImage : http://pin.taozhuma.com/upfiles/special/20161018085713762696.jpg
     * specialName : 电动玩具专题
     * productList : [{"activityId":"73","num":"2","price":"20","alonePrice":"89","productImage":"http://pin.taozhuma.com/upfiles/product/20160928023753027108.jpg","grouponNum":"0","productName":"清华附小学校指定7册窦桂梅推荐绘本书籍 红鞋子儿童书汤素兰 我爸爸 猜猜我有多爱你绘本 爷爷一定有办法注音版 逃家小兔正版包邮","productId":"993"},{"activityId":"75","num":"4","price":"8","alonePrice":"9.8","productImage":"http://pin.taozhuma.com/upfiles/product/20160928024717931938.jpg","grouponNum":"0","productName":"我的爸爸叫焦尼/海豚绘本花园系列 宝宝情商启蒙读物 1-2-3-4-5-6岁幼儿图画故事书籍 儿童成长必备畅销正版书 亲子阅读睡前故事书","productId":"995"},{"activityId":"76","num":"2","price":"20","alonePrice":"21.8","productImage":"http://pin.taozhuma.com/upfiles/product/20160928024929726297.jpg","grouponNum":"7","productName":"少儿必读金典 安徒生童话 精装彩图注音版 小学生新课标必读 经典名著馆 7-9-10-12岁少儿童文学图书籍 校园课外励志童话故事读物","productId":"996"},{"activityId":"74","num":"4","price":"30","alonePrice":"175","productImage":"http://pin.taozhuma.com/upfiles/product/20160928024119263222.jpg","grouponNum":"0","productName":"【日本产经儿童出版文化奖】第一辑可爱的鼠小弟(1-12)平装+第二辑(13-22)正版全22册低幼儿童宝宝绘本图书籍0-1-2-3-6部分包邮岁","productId":"994"}]
     * specialId : 1
     */

    private String specialImage;
    private String specialName;
    private String specialId;
    /**
     * activityId : 73
     * num : 2
     * price : 20
     * alonePrice : 89
     * productImage : http://pin.taozhuma.com/upfiles/product/20160928023753027108.jpg
     * grouponNum : 0
     * productName : 清华附小学校指定7册窦桂梅推荐绘本书籍 红鞋子儿童书汤素兰 我爸爸 猜猜我有多爱你绘本 爷爷一定有办法注音版 逃家小兔正版包邮
     * productId : 993
     */

    private ArrayList<ProductListBean> productList;

    public String getSpecialImage() {
        return specialImage;
    }

    public void setSpecialImage(String specialImage) {
        this.specialImage = specialImage;
    }

    public String getSpecialName() {
        return specialName;
    }

    public void setSpecialName(String specialName) {
        this.specialName = specialName;
    }

    public String getSpecialId() {
        return specialId;
    }

    public void setSpecialId(String specialId) {
        this.specialId = specialId;
    }

    public ArrayList<ProductListBean> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductListBean> productList) {
        this.productList = productList;
    }

    public static class ProductListBean {
        private String activityId;
        private String num;
        private String price;
        private String alonePrice;
        private String productImage;
        private String grouponNum;
        private String productName;
        private String productId;

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getAlonePrice() {
            return alonePrice;
        }

        public void setAlonePrice(String alonePrice) {
            this.alonePrice = alonePrice;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getGrouponNum() {
            return grouponNum;
        }

        public void setGrouponNum(String grouponNum) {
            this.grouponNum = grouponNum;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }
    }
}

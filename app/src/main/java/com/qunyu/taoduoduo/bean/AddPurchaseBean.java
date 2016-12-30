package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/9/26.
 */

public class AddPurchaseBean {

    public Integer allCount;//购买数量
    public String espressPrice;//物流费
    public String shopId;//	店铺id
    public String shopLogo;//	店铺logo
    public String shopName;//店铺名称
    public String sumPrice;//	订单总价格
    public ProductsBean  products;//	商品
    public  class ProductsBean {
        public String activityId;//活动id
        public String allPrice;//订单总价格
        public String discount;//	折扣
        public String price;//	商品价格
        public String productId;//商品id
        public String productImage;//商品图片
        public String productName;//	商品名称
        public Integer productNumber;//	购买数量
        public String sellingPrice;//产品原价
        public String skuLinkId;//产品skuid
        public String skuValue;//颜色:xx;尺寸:xxx
        public String type;//	商品活动类型 0-普通商品 1-活动商品 2-秒杀商品 3-专题商品 4-场景 5-专场


    }
}

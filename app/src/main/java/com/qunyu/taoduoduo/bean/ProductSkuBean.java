package com.qunyu.taoduoduo.bean;


import java.util.List;

/**
 * 商品sku
 */

public class ProductSkuBean {

    public List<SkuList> skuList;//SKU信息
    public List<ValidSKu> validSKu;//有库存SKU
    public String spceType;//1一种规格2两种规格 3.6版本


    public class ValidSKu {
        public String id;//	skuLinkID
        public String status;//	状态
        public String skuColor;//颜色
        public String pid;//商品ID
        public String skuFormat;//规格
        public String skuImg;//sku图片
    }


    public class SkuList {
        public String skuType;//1颜色2规格
        public String skuTitle;//颜色/规格
        public List<SkuValue> skuValue;//sku列表


    }

    public class SkuValue {
        public String optionValue;//sku值
        public int clickable;//默认0可点击，其他不可点击
    }

}

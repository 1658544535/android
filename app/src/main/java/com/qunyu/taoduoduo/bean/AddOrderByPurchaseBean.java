package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/10/11.
 */

public class AddOrderByPurchaseBean {
    public Alipay aplipay;
    public String fullpay;//1-不用支付，0-需支付
    public Wxpay wxpay;
    public String orderId;

    public  class Alipay{
        public String _input_charset;//字符集
        public String anti_phishing_key;//防钓鱼
        public String appid;//
        public String body;//支付描述
        public String exter_invoke_ip;//ip
        public String notify_url;//异步付款通知url
        public String out_trade_no;//商户交易流水号，唯一
        public String partner;//合作身份者ID
        public String payment_type;//付款方式
        public String seller_email;//销售者邮箱
        public String service;//服务名
        public String show_url;//
        public String sign;//签名
        public String sign_type;//签名算法
        public String subject;//订单名称
        public String total_fee;//金额


    }
    public  class Wxpay{
        public String appid;//
        public String noncestr;//随机数（微信）
        public String out_trade_no;//商户交易流水号，唯一
        public String partnerid;//商户id
        public String prepayid;//预支付id
        public String sign;//签名
        public String timestamp;//10位时间

    }
}

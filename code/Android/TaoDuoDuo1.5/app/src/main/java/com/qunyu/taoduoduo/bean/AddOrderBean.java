package com.qunyu.taoduoduo.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/9/26.
 */

public class AddOrderBean {

    /**
     * 提交订单
     * _input_charset : utf-8
     * anti_phishing_key :
     * appid :
     * body :
     * exter_invoke_ip :
     * notify_url : http://b2c.taozhuma.com/getnotifyUrl.do
     * out_trade_no : 20160925115321584
     * partner : 2088021116930692
     * payment_type : 1
     * seller_email :
     * service : mobile.securitypay.pay
     * show_url : m.alipay.com
     * sign : _input_charset="utf-8"&body="正品邦尼超轻粘土太空小黄人系列坏仆人3D手DIY彩色彩泥;"&it_b_pay="30m"¬ify_url="http://b2c.taozhuma.com/getnotifyUrl.do"&out_trade_no="20160925115321584"&partner="2088021116930692"&payment_type="1"&seller_id="taozhumab2c@5315.cn"&service="mobile.securitypay.pay"&show_url="m.alipay.com"&subject="淘竹马-订单号:147477560166515368"&total_fee="12"&sign="ECwOkjhu3gx2QKmGKyKtbVBfM%2FHDgwSMr3K2QF0StAIN7tmRc4%2BX01524D4dMOVuiPQF3znasX1F02s%2FILDFygt4KT7rkZSOGOC8co5MvP%2BKtxOop2yG853ZZAbeScrzpVXB7yJZ6pSrl8rBreyBsIDpZ1SdpUdbe4%2FRYt97eQM%3D"&sign_type="RSA"", body: "正品邦尼超轻粘土太空小黄人系列坏仆人3D手DIY彩色彩泥;
     * sign_type : RSA
     * subject : 淘竹马-订单号:147477560166515368
     * total_fee : 12
     */

    private AlipayBean alipay;
    /**
     * alipay : {"_input_charset":"utf-8","anti_phishing_key":"","appid":"","body":"","exter_invoke_ip":"","notify_url":"http://b2c.taozhuma.com/getnotifyUrl.do","out_trade_no":"20160925115321584","partner":"2088021116930692","payment_type":"1","seller_email":"","service":"mobile.securitypay.pay","show_url":"m.alipay.com","sign":"_input_charset=\"utf-8\"&body=\"正品邦尼超轻粘土太空小黄人系列坏仆人3D手DIY彩色彩泥;\"&it_b_pay=\"30m\"¬ify_url=\"http://b2c.taozhuma.com/getnotifyUrl.do\"&out_trade_no=\"20160925115321584\"&partner=\"2088021116930692\"&payment_type=\"1\"&seller_id=\"taozhumab2c@5315.cn\"&service=\"mobile.securitypay.pay\"&show_url=\"m.alipay.com\"&subject=\"淘竹马-订单号:147477560166515368\"&total_fee=\"12\"&sign=\"ECwOkjhu3gx2QKmGKyKtbVBfM%2FHDgwSMr3K2QF0StAIN7tmRc4%2BX01524D4dMOVuiPQF3znasX1F02s%2FILDFygt4KT7rkZSOGOC8co5MvP%2BKtxOop2yG853ZZAbeScrzpVXB7yJZ6pSrl8rBreyBsIDpZ1SdpUdbe4%2FRYt97eQM%3D\"&sign_type=\"RSA\"\", body: \"正品邦尼超轻粘土太空小黄人系列坏仆人3D手DIY彩色彩泥;","sign_type":"RSA","subject":"淘竹马-订单号:147477560166515368","total_fee":"12"}
     * balance : 测试内容bwm0
     * fullpay : 测试内容yt5t
     * wxpay : {"appid":"wx88fa4c9662539a8f","noncestr":"2EA0FE6537CAD4719C194A119D437CDC","out_trade_no":"20160925132136134","package":"Sign=WXPay","partnerid":"1263513101","prepayid":"wx20160925132136b38943c7fd0619370858","sign":"_input_charset=\"utf-8\"&body=\"一六八玩具儿童玩具推车宝宝小推车仿真婴儿车女孩女童过家家玩具;\"&it_b_pay=\"30m\"¬ify_url=\"http://b2c.taozhuma.com/getnotifyUrl.do\"&out_trade_no=\"20160925132025157\"&partner=\"2088021116930692\"&payment_type=\"1\"&seller_id=\"taozhumab2c@5315.cn\"&service=\"mobile.securitypay.pay\"&show_url=\"m.alipay.com\"&subject=\"淘竹马-订单号:147478082546239781\"&total_fee=\"34.9\"&sign=\"oDwsIEf6ZOQURa33lh1Jbon0t%2BggkqIHZDTUIL1JhSMiJYK46uZJWsg6R1pWSSlYiD9gvoMfCzwQD6ktjxaUbfWzNvfulcP1j5m%2Bgh9I8BYod%2BwTo6Ebqoz1a0I1FgUPfdP4OkRDeC6Snir33ULtxhWNyji2x7OhgQq1jZYcCtw%3D\"&sign_type=\"RSA\"","timestamp":"1474780897"}
     */

    private String balance;
    private String fullpay;
    /**
     * appid : wx88fa4c9662539a8f
     * noncestr : 2EA0FE6537CAD4719C194A119D437CDC
     * out_trade_no : 20160925132136134
     * package : Sign=WXPay
     * partnerid : 1263513101
     * prepayid : wx20160925132136b38943c7fd0619370858
     * sign : _input_charset="utf-8"&body="一六八玩具儿童玩具推车宝宝小推车仿真婴儿车女孩女童过家家玩具;"&it_b_pay="30m"¬ify_url="http://b2c.taozhuma.com/getnotifyUrl.do"&out_trade_no="20160925132025157"&partner="2088021116930692"&payment_type="1"&seller_id="taozhumab2c@5315.cn"&service="mobile.securitypay.pay"&show_url="m.alipay.com"&subject="淘竹马-订单号:147478082546239781"&total_fee="34.9"&sign="oDwsIEf6ZOQURa33lh1Jbon0t%2BggkqIHZDTUIL1JhSMiJYK46uZJWsg6R1pWSSlYiD9gvoMfCzwQD6ktjxaUbfWzNvfulcP1j5m%2Bgh9I8BYod%2BwTo6Ebqoz1a0I1FgUPfdP4OkRDeC6Snir33ULtxhWNyji2x7OhgQq1jZYcCtw%3D"&sign_type="RSA"
     * timestamp : 1474780897
     */

    private WxpayBean wxpay;

    public AlipayBean getAlipay() {
        return alipay;
    }

    public void setAlipay(AlipayBean alipay) {
        this.alipay = alipay;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getFullpay() {
        return fullpay;
    }

    public void setFullpay(String fullpay) {
        this.fullpay = fullpay;
    }

    public WxpayBean getWxpay() {
        return wxpay;
    }

    public void setWxpay(WxpayBean wxpay) {
        this.wxpay = wxpay;
    }

    public static class AlipayBean {
        private String _input_charset;
        private String anti_phishing_key;
        private String appid;
        private String body;
        private String exter_invoke_ip;
        private String notify_url;
        private String out_trade_no;
        private String partner;
        private String payment_type;
        private String seller_email;
        private String service;
        private String show_url;
        private String sign;
        private String sign_type;
        private String subject;
        private String total_fee;

        public String get_input_charset() {
            return _input_charset;
        }

        public void set_input_charset(String _input_charset) {
            this._input_charset = _input_charset;
        }

        public String getAnti_phishing_key() {
            return anti_phishing_key;
        }

        public void setAnti_phishing_key(String anti_phishing_key) {
            this.anti_phishing_key = anti_phishing_key;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getExter_invoke_ip() {
            return exter_invoke_ip;
        }

        public void setExter_invoke_ip(String exter_invoke_ip) {
            this.exter_invoke_ip = exter_invoke_ip;
        }

        public String getNotify_url() {
            return notify_url;
        }

        public void setNotify_url(String notify_url) {
            this.notify_url = notify_url;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getPartner() {
            return partner;
        }

        public void setPartner(String partner) {
            this.partner = partner;
        }

        public String getPayment_type() {
            return payment_type;
        }

        public void setPayment_type(String payment_type) {
            this.payment_type = payment_type;
        }

        public String getSeller_email() {
            return seller_email;
        }

        public void setSeller_email(String seller_email) {
            this.seller_email = seller_email;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getShow_url() {
            return show_url;
        }

        public void setShow_url(String show_url) {
            this.show_url = show_url;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getSign_type() {
            return sign_type;
        }

        public void setSign_type(String sign_type) {
            this.sign_type = sign_type;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(String total_fee) {
            this.total_fee = total_fee;
        }
    }

    public static class WxpayBean {
        private String appid;
        private String noncestr;
        private String out_trade_no;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}

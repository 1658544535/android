package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/10/12.
 * 退款详情
 */

public class RefundDetailsBean {
   private String phone;
   private String refundImage1;
   private String refundImage2;
   private String refundImage3;
   private String refundPrice;
   private String refundType;
   private String remarks;//描述
   private String type;//退款类型1：退款 2：退货

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRefundImage1() {
        return refundImage1;
    }

    public void setRefundImage1(String refundImage1) {
        this.refundImage1 = refundImage1;
    }

    public String getRefundImage2() {
        return refundImage2;
    }

    public void setRefundImage2(String refundImage2) {
        this.refundImage2 = refundImage2;
    }

    public String getRefundImage3() {
        return refundImage3;
    }

    public void setRefundImage3(String refundImage3) {
        this.refundImage3 = refundImage3;
    }

    public String getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(String refundPrice) {
        this.refundPrice = refundPrice;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

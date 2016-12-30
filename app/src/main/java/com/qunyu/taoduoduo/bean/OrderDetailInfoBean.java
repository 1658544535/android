package com.qunyu.taoduoduo.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */

public class OrderDetailInfoBean {


    /**
     * activityId : 测试内容n7x0
     * addressInfo : {"address":"测试内容r14r","consignee":"测试内容xp9g","tel":"测试内容2w82"}
     * attendId : 测试内容h373
     * buymsg : 测试内容irpi
     * isCancel : 测试内容9n6i
     * isOpen : 测试内容3um1
     * isPrize : 测试内容9lve
     * isSuccess : 测试内容qu6n
     * orderInfo : {"confirmTime":"测试内容si41","createTime":"测试内容b8r3","groupTime":"测试内容s770","logisticsName":"测试内容xd83","logisticsNo":"测试内容933p","orderId":"测试内容l2d0","orderNo":"测试内容8op1","paymethod":"测试内容7c37","sendTime":"测试内容0p16"}
     * orderStatus : 测试内容i5sg
     * oweNum : 测试内容345b
     * productInfo : {"allPrice":"测试内容7d2o","espressPrice":"测试内容5p5c","number":"测试内容71q4","orderPrice":"测试内容tir5","productId":"测试内容ttrf","productImage":"测试内容lw35","productName":"测试内容uca2","skuLinkId":"测试内容2nof","skuValue":"测试内容ogi5"}
     * refPriStatus : 测试内容9qe6
     * refundStatus : 测试内容9w96
     * source : 测试内容78f4
     * userList : [{"openid":"测试内容9hbm"}]
     */

    private String activityId;
    /**
     * address : 测试内容r14r
     * consignee : 测试内容xp9g
     * tel : 测试内容2w82
     */

    private AddressInfoBean addressInfo;
    private String attendId;
    private String buymsg;
    private String isCancel;
    private String isOpen;
    private String isPrize;
    private String isSuccess;
    /**
     * confirmTime : 测试内容si41
     * createTime : 测试内容b8r3
     * groupTime : 测试内容s770
     * logisticsName : 测试内容xd83
     * logisticsNo : 测试内容933p
     * orderId : 测试内容l2d0
     * orderNo : 测试内容8op1
     * paymethod : 测试内容7c37
     * sendTime : 测试内容0p16
     */

    private OrderInfoBean orderInfo;
    private String orderStatus;
    private String oweNum;
    /**
     * allPrice : 测试内容7d2o
     * espressPrice : 测试内容5p5c
     * number : 测试内容71q4
     * orderPrice : 测试内容tir5
     * productId : 测试内容ttrf
     * productImage : 测试内容lw35
     * productName : 测试内容uca2
     * skuLinkId : 测试内容2nof
     * skuValue : 测试内容ogi5
     */

    private ProductInfoBean productInfo;
    private String refPriStatus;
    private String refundStatus;
    private String source;
    /**
     * openid : 测试内容9hbm
     */

    private List<UserListBean> userList;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public AddressInfoBean getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfoBean addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getAttendId() {
        return attendId;
    }

    public void setAttendId(String attendId) {
        this.attendId = attendId;
    }

    public String getBuymsg() {
        return buymsg;
    }

    public void setBuymsg(String buymsg) {
        this.buymsg = buymsg;
    }

    public String getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(String isCancel) {
        this.isCancel = isCancel;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getIsPrize() {
        return isPrize;
    }

    public void setIsPrize(String isPrize) {
        this.isPrize = isPrize;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public OrderInfoBean getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfoBean orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOweNum() {
        return oweNum;
    }

    public void setOweNum(String oweNum) {
        this.oweNum = oweNum;
    }

    public ProductInfoBean getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfoBean productInfo) {
        this.productInfo = productInfo;
    }

    public String getRefPriStatus() {
        return refPriStatus;
    }

    public void setRefPriStatus(String refPriStatus) {
        this.refPriStatus = refPriStatus;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }

    public static class AddressInfoBean {
        private String address;
        private String consignee;
        private String tel;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
    }

    public static class OrderInfoBean {
        private String confirmTime;
        private String createTime;
        private String groupTime;
        private String logisticsName;
        private String logisticsNo;
        private String orderId;
        private String orderNo;
        private String paymethod;
        private String sendTime;

        public String getConfirmTime() {
            return confirmTime;
        }

        public void setConfirmTime(String confirmTime) {
            this.confirmTime = confirmTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getGroupTime() {
            return groupTime;
        }

        public void setGroupTime(String groupTime) {
            this.groupTime = groupTime;
        }

        public String getLogisticsName() {
            return logisticsName;
        }

        public void setLogisticsName(String logisticsName) {
            this.logisticsName = logisticsName;
        }

        public String getLogisticsNo() {
            return logisticsNo;
        }

        public void setLogisticsNo(String logisticsNo) {
            this.logisticsNo = logisticsNo;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getPaymethod() {
            return paymethod;
        }

        public void setPaymethod(String paymethod) {
            this.paymethod = paymethod;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }
    }

    public static class ProductInfoBean {
        private String allPrice;
        private String espressPrice;
        private String number;
        private String orderPrice;
        private String productId;
        private String productImage;
        private String productName;
        private String skuLinkId;
        private String skuValue;

        public String getAllPrice() {
            return allPrice;
        }

        public void setAllPrice(String allPrice) {
            this.allPrice = allPrice;
        }

        public String getEspressPrice() {
            return espressPrice;
        }

        public void setEspressPrice(String espressPrice) {
            this.espressPrice = espressPrice;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(String orderPrice) {
            this.orderPrice = orderPrice;
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

        public String getSkuLinkId() {
            return skuLinkId;
        }

        public void setSkuLinkId(String skuLinkId) {
            this.skuLinkId = skuLinkId;
        }

        public String getSkuValue() {
            return skuValue;
        }

        public void setSkuValue(String skuValue) {
            this.skuValue = skuValue;
        }
    }

    public static class UserListBean {
        private String openid;

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }
    }
}

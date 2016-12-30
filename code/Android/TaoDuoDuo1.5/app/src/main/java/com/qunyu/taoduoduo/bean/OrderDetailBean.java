package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/10/11.
 * 订单详情
 */

public class OrderDetailBean {
    private String activityId;//活动id

    private AddressInfo addressInfo;
    private String attendId;//参团id
    private String buymsg;//买家留言
    private String isCancel;//是否取消状态
    private String isSuccess;//是否拼团成功
    private OrderInfo orderInfo;//订单信息
   private String orderStatus;//订单状态
   private String oweNum;//拼团差n人
    private ProductInfo productInfo;//商品信息
    private String refPriStatus;//退款状态：1退款中，2已退款(新增)
    private String refundStatus;//售后状态
    private String source;//来源

    public class AddressInfo{
        private String address;//收件人地址
        private String consignee;//收件人
        private String tel;//收件人电话

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
    public class OrderInfo{
       private String confirmTime;//完成时间
       private String createTime;//下单时间
       private String groupTime;//成团时间
       private String logisticsName;//快递名称
       private String logisticsNo;//快递单号
       private String orderId;//订单id
       private String orderNo;//订单号
       private String paymethod;//支付方式
       private String sendTime;//发货时间

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
    public class ProductInfo{
        private String allPrice;//订单总额
        private String espressPrice;//运费
        private String number;//增加-数量（新增）
        private String orderPrice;//实付金额
        private String productId;//商品id
        private String productImage;//商品图片
        private String productName;//商品名称
        private String skuLinkId;//skuid
        private String skuValue;//sku值

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

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
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

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
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

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
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
}

package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

import java.io.File;

/**
 * Created by Administrator on 2016/10/10.
 */

public class ApplyRefundApi implements BaseApi {
    private File image1;//凭证1
    private File image2;//凭证2
    private File image3;//凭证3
    private String oid;//订单id
    private String phone;//默认联系方式
    private String price;//退款价格
    private String refundReason;//描述
    private String refundType;//原因:1没收到货, 2商品质量有问题, 3商品与描述不一致, 4商品少发/漏发/发错, 5商品有划痕,6质疑假货,7其他)
    private String type;//1为退款2为退货3为售后
    private String uid;//
    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params=new AbRequestParams();
        params.put("image1",image1);
        params.put("image2",image2);
        params.put("image3",image3);
        params.put("oid",oid);
        params.put("phone",phone);
        params.put("price",price);
        params.put("refundReason",refundReason);
        params.put("refundType",refundType);
        params.put("type",type);
        params.put("uid",uid);
        return params;
    }

    @Override
    public String getUrl() {
        return Constant.applyRefundApi;
    }

    public File getImage1() {
        return image1;
    }

    public void setImage1(File image1) {
        this.image1 = image1;
    }

    public File getImage2() {
        return image2;
    }

    public void setImage2(File image2) {
        this.image2 = image2;
    }

    public File getImage3() {
        return image3;
    }

    public void setImage3(File image3) {
        this.image3 = image3;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

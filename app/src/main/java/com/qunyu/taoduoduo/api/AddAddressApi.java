package com.qunyu.taoduoduo.api;

import com.andbase.library.http.model.AbRequestParams;
import com.qunyu.taoduoduo.base.BaseApi;
import com.qunyu.taoduoduo.global.Constant;

/**
 * Created by Administrator on 2016/9/30.
 * 我的-添加地址（老接口）
 */

public class AddAddressApi implements BaseApi {
    private String address;//详细地址
    private String area;//区
    private String city;//城市
    private String isDefault;//默认地址（取业务字典：0否1是）
    private String name;//收货人
    private String postCode;//邮编
    private String province;//省份
    private String tel;//手机
    private String uid;//会员id
    @Override
    public AbRequestParams getParamMap() {
        AbRequestParams params=new AbRequestParams();
        params.put("address",address);
        params.put("area",area);
        params.put("city",city);
        params.put("isDefault",isDefault);
        params.put("name",name);
        params.put("postCode",postCode);
        params.put("province",province);
        params.put("tel",tel);
        params.put("uid",uid);
        return params;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String getUrl() {
        return Constant.addAddress;
    }
}
